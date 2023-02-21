package com.zhixin.zhfz.bacs.services.exhibit;

import com.zhixin.zhfz.bacs.dao.exhibit.IExhibitMapper;
import com.zhixin.zhfz.bacs.dao.exhibit.IExhibitcodMapper;
import com.zhixin.zhfz.bacs.dao.exhibit.IExhibitdetMapper;
import com.zhixin.zhfz.bacs.dao.serial.ISerialMapper;
import com.zhixin.zhfz.bacs.entity.BelongEntity;
import com.zhixin.zhfz.bacs.entity.ExhibitEntity;
import com.zhixin.zhfz.bacs.entity.ExhibitPhotoEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.services.person.IPersonLockersService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ExhibitServiceImpl implements IExhibitService {

    private static Logger logger = LoggerFactory.getLogger(ExhibitServiceImpl.class);

    @Autowired
    private IExhibitMapper exhibitMapper;

    @Autowired
    private IExhibitdetMapper exhibitdetMapper;

    @Autowired
    private IExhibitcodMapper exhibitcodMapper;
    @Autowired
    private ISerialMapper serialMapper;
    @Autowired
    private ISerialService serialService;

    @Autowired
    private IPersonLockersService personLockersService;

    /**
     * 查询具体的未领取的涉案存物信息
     *
     * @param map
     * @return
     */
    @Override
    public List<ExhibitEntity> listAllExhibitdet2(Map<String, Object> map) {
        return this.exhibitdetMapper.listAllExhibitdet2(map);
    }

    /**
     * 增加存物信息
     *
     * @param entity
     * @return
     */
    @Override
    public int insertExhibit(ExhibitEntity entity) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("serialId", entity.getSerialId());
        paramMap.put("lockerId", entity.getLockerId());
        // 查看柜子占用情况
        ExhibitEntity ss = exhibitMapper.selectinfo(paramMap);
        if (ss == null) {// 无占用
            // 查看该专属编号是否已经存物
            ExhibitEntity dd = exhibitMapper.selectSidnfo(paramMap);
            // 如果没有存过物品则创建belong表
            if (dd == null) {
                // 添加随身物品
                this.exhibitMapper.insertExhibit(entity);
                // 添加随身物品详情
                ExhibitEntity obj = new ExhibitEntity();
                obj.setId(obj.getId());
                obj.setExhibitId(entity.getId());
                obj.setName(entity.getName());
                obj.setIsGet(0);
                obj.setDetailCount(entity.getDetailCount());
                obj.setDescription(entity.getDescription());
                obj.setUnit(entity.getUnit());
                obj.setOp_pid(entity.getOp_pid());
                obj.setOp_user_id(entity.getOp_user_id());
                obj.setCreatedTime(new Date());
                this.exhibitdetMapper.insertExhibitdet(obj);
                // 修改入区状态为2物品暂存
                SerialEntity state = new SerialEntity();
                state.setId(entity.getSerialId());
                state.setStatus(2);
                state.setStatusChangeTime(new Date());
                try {
                    this.serialService.updateStatusById(entity.getSerialId(), 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    personLockersService.addExhibit(entity);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            } else {
                // 如果之前存过物品，只添加随身物品详情，只更新exhibitdetail表
                if (dd.getLockerId() != entity.getLockerId()) {
                    // 添加随身物品
                    this.exhibitMapper.insertExhibit(entity);
                }
                ExhibitEntity obj = new ExhibitEntity();
                obj.setId(obj.getId());
                obj.setExhibitId(entity.getId());
                obj.setName(entity.getName());
                obj.setIsGet(0);
                obj.setDetailCount(entity.getDetailCount());
                obj.setDescription(entity.getDescription());
                obj.setUnit(entity.getUnit());
                obj.setCreatedTime(new Date());
                obj.setOp_pid(entity.getOp_pid());
                obj.setOp_user_id(entity.getOp_user_id());
                this.exhibitdetMapper.insertExhibitdet(obj);
            }

        } else {// 占用时，判断是否同一个入区编号占用，是则插入
            if (ss.getSerialId().longValue() == entity.getSerialId().longValue()) {
                // 只添加随身物品详情
                ExhibitEntity obj = new ExhibitEntity();
                obj.setId(obj.getId());
                obj.setExhibitId(ss.getId());
                obj.setName(entity.getName());
                obj.setIsGet(0);
                obj.setDetailCount(entity.getDetailCount());
                obj.setDescription(entity.getDescription());
                obj.setUnit(entity.getUnit());
                obj.setCreatedTime(new Date());
                obj.setOp_pid(entity.getOp_pid());
                obj.setOp_user_id(entity.getOp_user_id());
                this.exhibitdetMapper.insertExhibitdet(obj);
                // 修改入区状态为2物品暂存
                SerialEntity state = new SerialEntity();
                state.setId(entity.getSerialId());
                state.setStatus(2);
                state.setStatusChangeTime(new Date());
                try {
                    this.serialService.updateStatusById(entity.getSerialId(), 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return 0;// 被占用
            }
        }
        return 1;// 成功
    }

    /**
     * 删除一条物品记录，如果所有物品都删除，则删除父记录
     */
    @Override
    public void deleteExhibitdetAndExhibit(int exhibitId, int detailId) {
        //删除物品
        this.exhibitdetMapper.deleteExhibitdet((long) detailId);
        //查询所有物品
        int num = this.exhibitdetMapper.countByBelongId(exhibitId);
        if (num == 0) {
            try {
                personLockersService.removeExhibit(exhibitId);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            //删除父记录
            this.exhibitMapper.deleteExhibit((long) exhibitId);
        }
    }

    /**
     * 修改涉案物品详情
     *
     * @param entity
     */
    @Override
    public void updateExhibitdet(ExhibitEntity entity) {
        this.exhibitdetMapper.updateExhibitdet(entity);

    }

    /**
     * 查询未领取的涉案物品信息
     *
     * @param map
     * @return
     */
    @Override
    public List<ExhibitEntity> listAllExhibitdet3(Map<String, Object> map) {
        return this.exhibitdetMapper.listAllExhibitdet3(map);
    }

    /**
     * 通过lockId 查询 serial_id
     *
     * @param lockId
     * @return
     */
    @Override
    public long getSerialIdByLockId(String lockId) {
        return exhibitMapper.getSerialIdByLockId(lockId);
    }

    /**
     * 随身物品开柜单保存(单个领取)----out----
     *
     * @param entity
     */
    @Override
    public void updateBoxopenouts(ExhibitEntity entity) throws Exception {
// 更新单个物品的提取状态，信息
        this.exhibitdetMapper.updateBoxopenouts(entity);
        int s = entity.getId();
        // 反查该物品所属上级编号是否已经全部提取完毕
        int dd = exhibitMapper.selectExhibitInfo(s);
        BelongEntity obj1 = new BelongEntity();
        obj1.setId(entity.getExhibitId());
        obj1.setIsGet(1);
        obj1.setGetWay(entity.getGetWay());
        obj1.setGetPerson(entity.getGetPerson());
        obj1.setGetTime(entity.getGetTime());
        // 判断如果全部提取完毕，就更新该专属编号总的提取状态，信息、
        if (dd == 0) {
            ExhibitEntity obj = new ExhibitEntity();
            obj.setId(entity.getExhibitId());
            obj.setIsGet(1);
            obj.setGetWay(entity.getGetWay());
            obj.setGetPerson(entity.getGetPerson());
            obj.setGetTime(entity.getGetTime());
            obj.setGetTime(entity.getGetTime());
            obj.setGetTime(entity.getGetTime());
            obj.setOp_pid(entity.getOp_pid());
            obj.setOp_user_id(entity.getOp_user_id());
            this.exhibitMapper.updateUpperInfo(obj);
            // 修改入区状态为8物品已领取
            Long sid = exhibitMapper.selectExhibitInfoById(entity.getExhibitId());
            SerialEntity state = new SerialEntity();
            state.setId(sid);
            state.setStatus(8);
            state.setStatusChangeTime(new Date());
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("status", 8);
            this.serialMapper.updateStatusById(map);
            try {
                personLockersService.getExhibit(entity.getExhibitId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        // 开柜记录
        ExhibitEntity objs = new ExhibitEntity();
        objs.setId(objs.getId());
        objs.setExhibitId(entity.getExhibitId());
        objs.setLockerId(entity.getLockerId());
        objs.setGetPerson(entity.getGetPerson());
        objs.setGetWay(entity.getGetWay());
        objs.setGetTime(entity.getGetTime());
        objs.setOp_pid(entity.getOp_pid());
        objs.setOp_user_id(entity.getOp_user_id());
        this.exhibitdetMapper.creatBoxopenouts(objs);
        try {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("status", 8);
            this.serialMapper.updateStatusById(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 全部领取
     *
     * @param entity
     */
    @Override
    public void updateBoxopenout(ExhibitEntity entity) {
        Long s = entity.getSerialId();
        //查询所有占用记录
        List<ExhibitEntity> list = exhibitMapper.selectloginfo(s);
        Integer bid = null;
        for (ExhibitEntity be : list) {
            ExhibitEntity obj = new ExhibitEntity();
            obj.setExhibitId(be.getExhibitId());
            obj.setLockerId(be.getLockerId());
            obj.setGetPerson(entity.getGetPerson());
            obj.setGetWay(entity.getGetWay());
            obj.setIsGet(1);
            obj.setCreatedTime(entity.getCreatedTime());
            obj.setGetTime(entity.getGetTime());
            obj.setOp_pid(entity.getOp_pid());
            obj.setOp_user_id(entity.getOp_user_id());
            //更新该专属编号下每件物品的提取状态，信息
            this.exhibitdetMapper.updateBoxopenoutdets(obj);
            //创建开柜日志
            this.exhibitdetMapper.creatBoxopenouts(obj);
            bid = be.getExhibitId();
        }
        this.exhibitMapper.updateBoxopenout(entity);
        try {
            if (s != null) {
                serialService.changestatus(s, 8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            personLockersService.getExhibit(bid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 根据serialid查询
     *
     * @param sid
     * @return
     */
    @Override
    public ExhibitEntity selectlockNobysid(int sid) {
        return exhibitMapper.selectlockNobysid(sid);
    }

    /**
     * 上传图片
     *
     * @param serialID
     * @param spath
     */
    @Override
    public void createxhibitphoto(String serialID, String spath,String pid,String opUserId) {
        //使用唯一编号反查
        ExhibitEntity dd = exhibitMapper.selectphotoinfo(serialID);
        ExhibitEntity obj = new ExhibitEntity();
        obj.setId(obj.getId());
        obj.setExhibitId(dd.getExhibitId());
        obj.setAreaId(dd.getAreaId());
        obj.setUrl(spath);
        obj.setCreatedTime(new Date());
        obj.setUpdatedTime(new Date());
        obj.setOp_pid(dd.getOp_pid());
        obj.setOp_user_id(dd.getOp_user_id());
        exhibitMapper.creatbelongphoto(obj);
    }

    /**
     * 查询嫌疑人所使用柜子信息
     * @param map
     * @return
     */
    @Override
    public List<ExhibitEntity> listAllExhibitByLocker(Map<String, Object> map) {
        return exhibitMapper.listAllExhibitByLocker(map);
    }

    /**
     * 查询嫌疑人使用柜子总数
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public int count(Map<String, Object> map) throws Exception {
        return exhibitMapper.count(map);
    }

    /**
     * 查询开柜记录
     * @param map3
     * @return
     */
    @Override
    public List<ExhibitEntity> queryExhibitCabinetLlog(Map<String, Object> map3) {
        return exhibitMapper.queryExhibitCabinetLlog(map3);
    }
    /**
     * 随身物品开柜记录总数
     *
     * @param map3
     * @return
     */
    @Override
    public int countyExhibitCabinetLog(Map<String, Object> map3) {
        return exhibitMapper.countyExhibitCabinetLog(map3);
    }

    @Override
    public int count1(Map<String, Object> params) {
        return exhibitdetMapper.count1(params);
    }

    @Override
    public List<ExhibitEntity> listAllExhibitdet(Map<String, Object> map) {
        return exhibitdetMapper.listAllExhibitdet(map);
    }

    /**
     * 查询图片
     * @param exhibitId
     * @return
     */
    @Override
    public List<ExhibitPhotoEntity> selectPhotoByExhibitId(long exhibitId) {
        return exhibitMapper.selectPhotoByExhibitId(exhibitId);
    }
}
