package com.zhixin.zhfz.bacs.services.policebelong;

import com.zhixin.zhfz.bacs.dao.belong.IOpenCabinetMapper;
import com.zhixin.zhfz.bacs.dao.policebelong.IPoliceBelongMapper;
import com.zhixin.zhfz.bacs.dao.policebelong.IPoliceBelongdetMapper;
import com.zhixin.zhfz.bacs.entity.ComboboxEntity;
import com.zhixin.zhfz.bacs.entity.OpenCabinetEntity;
import com.zhixin.zhfz.bacs.entity.PoliceBelongEntity;
import com.zhixin.zhfz.bacs.entity.PoliceBelongingsPhotoEntity;
import com.zhixin.zhfz.bacs.services.person.IPoliceLockersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PoliceBelongServiceImpl implements IPoliceBelongService {

    private static Logger logger = LoggerFactory.getLogger(PoliceBelongServiceImpl.class);

    @Autowired
    private IPoliceBelongMapper belongMapper;

    @Autowired
    private IPoliceBelongdetMapper belongdetMapper;

    @Autowired
    private IOpenCabinetMapper iopenCabinetMapper;

    @Autowired
    private IPoliceLockersService personLockersService;

    /**
     * 删除民警存物信息
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteBelong(Long id) throws Exception {
        this.belongMapper.deleteBelong(id);
        this.belongdetMapper.deleteBelongdetsecond(id);
        if (id != null) {
            personLockersService.removeBelong(id.longValue());
        }
    }

    /**
     * 删除随身物品详情
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteBelongdet(Long id) throws Exception {
        this.belongdetMapper.deleteBelongdet(id);
    }

    /**
     * 查询民警存物信息总数
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public int count(Map<String, Object> params) throws Exception {
        return this.belongMapper.count(params);
    }

    /**
     * 根据存物id查询总数
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public int count1(Map<String, Object> params) throws Exception {
        return this.belongdetMapper.count1(params);
    }

    /**
     * 增加存物信息
     *
     * @param entity
     * @return
     */
    @Override
    public int insertBelong(PoliceBelongEntity entity) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("policeId", entity.getPoliceId());
        paramMap.put("lockerId", entity.getLockerId());
        // 查看柜子占用情况
        PoliceBelongEntity ss = belongMapper.selectinfo(paramMap);
        if (ss == null) {// 无占用
            // 查看该专属编号是否已经存物
            PoliceBelongEntity dd = belongMapper.selectSidnfo(paramMap);
            // 如果没有存过物品则创建belong表
            if (dd == null) {
                // 添加随身物品
                this.belongMapper.insertBelong(entity);
                // 添加随身物品详情
                PoliceBelongEntity obj = new PoliceBelongEntity();
                obj.setId(obj.getId());
                obj.setBelongingsId(entity.getId());
                obj.setName(entity.getName());
                obj.setIsGet(0);
                obj.setDetailCount(entity.getDetailCount());
                obj.setDescription(entity.getDescription());
                obj.setUnit(entity.getUnit());
                obj.setSaveMethod(entity.getSaveMethod());
                obj.setCreatedTime(new Date());
                obj.setOpPid(entity.getOpPid());
                obj.setOpUserId(entity.getOpUserId());
                this.belongdetMapper.insertBelongdet(obj);
                try {
                    personLockersService.addBelong(entity);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            } else {
                // 如果之前存过物品，只添加随身物品详情，只更新belongdetail表
                PoliceBelongEntity obj = new PoliceBelongEntity();
                obj.setId(obj.getId());
                obj.setBelongingsId(dd.getId());
                obj.setName(entity.getName());
                obj.setIsGet(0);
                obj.setDetailCount(entity.getDetailCount());
                obj.setDescription(entity.getDescription());
                obj.setUnit(entity.getUnit());
                obj.setSaveMethod(entity.getSaveMethod());
                obj.setCreatedTime(new Date());
                obj.setOpPid(entity.getOpPid());
                obj.setOpUserId(entity.getOpUserId());
                this.belongdetMapper.insertBelongdet(obj);
            }
        } else {// 占用时，判断是否同一个入区编号占用，是则插入
            if (ss.getPoliceId().longValue() == entity.getPoliceId().longValue()) {
                // 只添加随身物品详情
                PoliceBelongEntity obj = new PoliceBelongEntity();
                obj.setId(obj.getId());
                obj.setBelongingsId(ss.getId());
                obj.setName(entity.getName());
                obj.setIsGet(0);
                obj.setDetailCount(entity.getDetailCount());
                obj.setDescription(entity.getDescription());
                obj.setUnit(entity.getUnit());
                obj.setSaveMethod(entity.getSaveMethod());
                obj.setCreatedTime(new Date());
                obj.setOpPid(entity.getOpPid());
                obj.setOpUserId(entity.getOpUserId());
                this.belongdetMapper.insertBelongdet(obj);
            } else {
                return 0;// 被占用
            }
        }
        return 1;// 成功
    }

    /**
     * 修改随身物品
     *
     * @param entity
     */
    @Override
    public void updateBelong(PoliceBelongEntity entity) {
        this.belongMapper.updateBelong(entity);
        if (entity.getIsGet() == 1) {
            try {
                personLockersService.getBelong(entity.getBelongingsId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }


    }

    /**
     * 修改随身物品详情
     *
     * @param entity
     */
    @Override
    public void updateBelongdet(PoliceBelongEntity entity) {
        this.belongdetMapper.updateBoxopenouts(entity);

    }

    /**
     * 查询存物信息
     *
     * @param map
     * @return
     */
    @Override
    public List<PoliceBelongEntity> listAllBelong(Map<String, Object> map) {
        return this.belongMapper.listAllBelong(map);
    }

    /**
     * 查询具体存物信息
     *
     * @param map2
     * @return
     */
    @Override
    public List<PoliceBelongEntity> listAllBelongdet(Map<String, Object> map2) {

        return this.belongdetMapper.listAllBelongdet(map2);
    }

    /**
     * 随身物品管理的开柜
     */
    @Override
    public void updateBoxopen(PoliceBelongEntity entity) {
        try {
            personLockersService.getBelong(entity.getBelongingsId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        // 所有物品领取
        belongdetMapper.updateBoxopenoutdets(entity);
        // 柜子更改状态
        belongMapper.updateBelong(entity);
        // 创建开柜日志
        this.belongdetMapper.creatBoxopenouts(entity);
    }

    /**
     * 查询存物信息列表
     *
     * @param map
     * @return
     */
    @Override
    public List<PoliceBelongEntity> listAllBelongdet2(Map<String, Object> map) {
        return this.belongdetMapper.listAllBelongdet2(map);
    }

    /**
     * 全部取物时根据唯一编号查询log所需信息
     *
     * @param sid
     * @return
     */
    public List<PoliceBelongEntity> selectloginfo(Long sid) {
        return belongMapper.selectloginfo(sid);
    }

    /**
     * 根据民警入区id修改未领取的物品信息
     *
     * @param entity
     */
    @Override
    public void updateBoxopenout(PoliceBelongEntity entity) {
        Long s = entity.getPoliceId();
        // 查询所有占用记录
        List<PoliceBelongEntity> list = belongMapper.selectloginfo(s);
        Long bid = null;
        if (list != null) {
            for (PoliceBelongEntity be : list) {
                PoliceBelongEntity obj = new PoliceBelongEntity();
                obj.setBelongingsId(be.getBelongingsId());
                obj.setLockerId(be.getLockerId());
                obj.setGetPerson(entity.getGetPerson());
                obj.setGetWay(entity.getGetWay());
                obj.setIsGet(1);
                obj.setCreatedTime(entity.getCreatedTime());
                obj.setGetTime(entity.getGetTime());
                obj.setOpUserId(entity.getOpUserId());
                obj.setOpPid(entity.getOpPid());
                // 更新该专属编号下每件物品的提取状态，信息
                this.belongdetMapper.updateBoxopenoutdets(obj);
                // 创建开柜日志
                this.belongdetMapper.creatBoxopenouts(obj);
                bid = be.getBelongingsId();
            }
            // 更新该专属编号总的提取状态，信息
            this.belongMapper.updateBoxopenout(entity);
            System.err.println("领取全部随身物品=");
        }
        try {
            personLockersService.getBelong(bid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 修改存物详情
     *
     * @param entity
     */
    @Override
    public void updateBoxopenouts(PoliceBelongEntity entity) {
        // 更新单个物品的提取状态，信息
        this.belongdetMapper.updateBoxopenouts(entity);
        Long s = entity.getId();
        System.err.println("s-----------------:" + s);
        // 反查该物品所属上级编号是否已经全部提取完毕
        int dd = belongMapper.selectbelonginfo(s);
        System.err.println("dd-----------------:" + dd);
        PoliceBelongEntity obj1 = new PoliceBelongEntity();
        obj1.setId(entity.getBelongingsId());
        obj1.setIsGet(1);
        obj1.setGetWay(entity.getGetWay());
        obj1.setGetPerson(entity.getGetPerson());
        obj1.setGetTime(entity.getGetTime());
        obj1.setOpPid(entity.getOpPid());
        obj1.setOpUserId(entity.getOpUserId());
        Long sid1 = belongMapper.selectserialid(obj1);
        // 判断如果全部提取完毕，就更新该专属编号总的提取状态，信息、
        if (dd == 0) {
            PoliceBelongEntity obj = new PoliceBelongEntity();
            obj.setId(entity.getBelongingsId());
            obj.setIsGet(1);
            obj.setGetWay(entity.getGetWay());
            obj.setGetPerson(entity.getGetPerson());
            obj.setGetTime(entity.getGetTime());
            obj1.setOpPid(entity.getOpPid());
            obj1.setOpUserId(entity.getOpUserId());
            this.belongMapper.updateUpperInfo(obj);
            try {
                personLockersService.getBelong(entity.getBelongingsId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        // 开柜记录
        PoliceBelongEntity objs = new PoliceBelongEntity();
        objs.setId(objs.getId());
        objs.setBelongingsId(entity.getBelongingsId());
        objs.setLockerId(entity.getLockerId());
        objs.setGetPerson(entity.getGetPerson());
        objs.setGetWay(entity.getGetWay());
        objs.setGetTime(entity.getGetTime());
        objs.setOpPid(entity.getOpPid());
        objs.setOpUserId(entity.getOpUserId());
        this.belongdetMapper.creatBoxopenouts(objs);
        System.err.println("单个领取sid1" + sid1);
    }

    /**
     * 根据柜门id查看信息
     *
     * @param map
     * @return
     */
    @Override
    public List<PoliceBelongEntity> setSerialId(Map<String, Object> map) {
        return belongdetMapper.setSerialId(map);
    }

    /**
     * 查询存物信息列表
     *
     * @param map
     * @return
     */
    @Override
    public List<PoliceBelongEntity> listAllBelongdet3(Map<String, Object> map) {
        return this.belongdetMapper.listAllBelongdet3(map);
    }

    /**
     * 查询台账内容
     *
     * @param entity
     * @return
     */
    @Override
    public List<PoliceBelongEntity> getDocInfo(PoliceBelongEntity entity) {

        return belongMapper.getDocInfo(entity);
    }

    /**
     * 根据民警入区id查存物信息
     *
     * @param entity
     * @return
     */
    @Override
    public List<PoliceBelongEntity> getDocInfoNoLockers(PoliceBelongEntity entity) {
        return belongMapper.getDocInfoNoLockers(entity);
    }

    /**
     * 增加存物照片
     *
     * @param serialID
     * @param spath
     */
    @Override
    public void creatbelongphoto(String serialID, String spath,String pid,String opUserId) {
        // 使用唯一编号反查BelongingsId
        PoliceBelongEntity dd = belongMapper.selectphotoinfo(serialID);
        PoliceBelongEntity obj = new PoliceBelongEntity();
        obj.setId(obj.getId());
        obj.setBelongingsId(dd.getBelongingsId());
        obj.setAreaId(dd.getAreaId());
        obj.setUrl(spath);
        obj.setCreatedTime(new Date());
        obj.setUpdatedTime(new Date());
        obj.setOpPid(pid);
        obj.setOpUserId(Integer.parseInt(opUserId));
        belongMapper.creatbelongphoto(obj);
    }

    /**
     * 删除一条物品记录，如果所有物品都删除，则删除父记录
     */
    @Override
    public void deleteBelongdetAndBelong(long belongId, int detailId) {
        // 删除物品
        this.belongdetMapper.deleteBelongdet((long) detailId);
        // 查询所有物品
        int dd = belongdetMapper.countByBelongId(belongId);
        if (dd == 0) {
            try {
                personLockersService.removeBelong(belongId);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            belongMapper.deleteBelong((long) belongId);

        }
    }

    /**
     * 根据柜门查看民警入区id
     *
     * @param lockId
     * @return
     */
    @Override
    public long getSerialIdByLockId(String lockId) {
        return belongMapper.getSerialIdByLockId(lockId);
    }

    /**
     * 根据民警入区id查询总数
     *
     * @param s
     * @return
     */
    @Override
    public boolean isGetBelongBySerialId(long s) {
        int dd = belongMapper.isGetBelongBySerialId(s);
        if (dd == 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据存物id查询照片信息
     *
     * @param belongingsId
     * @return
     */
    @Override
    public List<PoliceBelongingsPhotoEntity> selectPhotoByBelongingsId(long belongingsId) {
        return belongMapper.selectPhotoByBelongingsId(belongingsId);
    }

    /**
     * 增加开柜日志
     *
     * @param openCabinetEntity
     */
    @Override
    public void insertOpenboxData(OpenCabinetEntity openCabinetEntity) {
        iopenCabinetMapper.insertSelective(openCabinetEntity);
    }

    /**
     * 查询随身物品开柜记录
     *
     * @param map3
     * @return
     */
    @Override
    public List<PoliceBelongEntity> queryBelongingsCabinetLlog(Map<String, Object> map3) {
        return belongdetMapper.queryBelongingsCabinetLlog(map3);
    }

    /**
     * 随身物品开柜记录总数
     *
     * @param map3
     * @return
     */
    @Override
    public int countBelongingsCabinetLog(Map<String, Object> map3) {
        return belongdetMapper.countBelongingsCabinetLog(map3);
    }

    /**
     * 检查当前柜子是否被其他人占用
     *
     * @param map
     * @return
     */
    @Override
    public PoliceBelongEntity checkOtherPersonUse(Map<String, Object> map) {
        return belongMapper.checkOtherPersonUse(map);
    }

    /**
     * 根据id查询民警存物信息
     *
     * @param id
     * @return
     */
    @Override
    public PoliceBelongEntity getBelongById(Long id) {
        return belongMapper.getBelongById(id);
    }

    /**
     * 根据柜门id查询非领取的总数
     *
     * @param lockerId
     * @return
     */
    @Override
    public int countDetByLockerId(int lockerId) {
        return belongMapper.countDetByLockerId(lockerId);
    }

    /**
     * 导出查询
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<PoliceBelongEntity> getBelongForExport(Map<String, Object> paramMap) {
        return belongMapper.getBelongForExport(paramMap);
    }
    /**
     * 查询柜门下拉框
     *
     * @param params
     * @return
     */
    @Override
    public List<ComboboxEntity> listunUsedbox(Map<String, Object> params) {
        return belongMapper.listunUsedbox(params);
    }
}
