package com.zhixin.zhfz.bacs.services.belong;

import com.zhixin.zhfz.bacs.dao.belong.IBelongMapper;
import com.zhixin.zhfz.bacs.dao.belong.IBelongdetMapper;
import com.zhixin.zhfz.bacs.dao.belong.IOpenCabinetMapper;
import com.zhixin.zhfz.bacs.dao.belongtemp.IBelongDetailTempMapper;
import com.zhixin.zhfz.bacs.dao.belongtemp.IBelongTempMapper;
import com.zhixin.zhfz.bacs.dao.serial.ISerialMapper;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.services.belongtemp.IBelongDetailTempService;
import com.zhixin.zhfz.bacs.services.belongtemp.IBelongTempService;
import com.zhixin.zhfz.bacs.services.person.IPersonLockersService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.common.HttpClientUtil;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class BelongServiceImpl implements IBelongService {

    private static Logger logger = LoggerFactory.getLogger(BelongServiceImpl.class);
    private static String kh_base_url = "http://14.112.65.153:8090";

    @Resource
    private IBelongMapper belongMapper;
    @Resource
    private IBelongdetMapper belongdetMapper;
    @Resource
    private ISerialMapper serialMapper;
    @Resource
    private ISerialService serialSrevie;
    @Resource
    private IOpenCabinetMapper openCabinetMapper;
    @Resource
    private IPersonLockersService personLockersService;
    @Resource
    private IBelongTempMapper belongTempService;
    @Resource
    private IBelongDetailTempMapper belongDetailTempService;
    @Resource
    private IOrganizationService organizationService;

    /**
     * 查询某存物柜的存物信息
     *
     * @param map
     * @return
     */
    @Override
    public List<BelongEntity> listAllBelongdet(Map<String, Object> map) {
        return belongdetMapper.listAllBelongdet(map);
    }


    /**
     * 增加存物信息
     *
     * @param entity
     * @return
     */
    @Override
    public int insertBelong(BelongEntity entity,HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("serialId", entity.getSerialId());
        paramMap.put("lockerId", entity.getLockerId());
        // 查看柜子占用情况
        BelongEntity ss = belongMapper.selectinfo(paramMap);
        if (ss == null) {// 无占用
            // 查看该专属编号是否已经存物
            BelongEntity dd = belongMapper.selectSidnfo(paramMap);
            // 如果没有存过物品则创建belong表
            if (dd == null) {
                // 添加随身物品
                this.belongMapper.insertBelong(entity);
                // 添加随身物品详情
                BelongEntity obj = new BelongEntity();
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
                obj.setWpUuid(entity.getWpUuid());
                this.belongdetMapper.insertBelongdet(obj);
               /* try {
                    personLockersService.addBelong(entity);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }*/
            } else {
                // 如果之前存过物品，只添加随身物品详情，只更新belongdetail表
                if (dd.getLockerId() != entity.getLockerId()) {
                    // 添加随身物品
                    this.belongMapper.insertBelong(entity);
                }
                BelongEntity obj = new BelongEntity();
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
                obj.setWpUuid(entity.getWpUuid());
                this.belongdetMapper.insertBelongdet(obj);
            }

        } else {// 占用时，判断是否同一个入区编号占用，是则插入
            if (ss.getSerialId().longValue() == entity.getSerialId().longValue()) {
                // 只添加随身物品详情
                BelongEntity obj = new BelongEntity();
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
                obj.setWpUuid(entity.getWpUuid());
                this.belongdetMapper.insertBelongdet(obj);
            } else {
               //  看守所可以一个柜子里面保存多个
                if(ControllerTool.getSessionInfo(request).getCurrentArea().getId()==45){
                   // 添加随身物品
                    this.belongMapper.insertBelong(entity);
                    // 添加随身物品详情
                    BelongEntity obj = new BelongEntity();
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
                    obj.setWpUuid(entity.getWpUuid());
                    this.belongdetMapper.insertBelongdet(obj);
                }else{
                    return 0;// 被占用
                }
            }
        }
        return 1;// 成功
    }

    /**
     * 修改存物详情
     *
     * @param entity
     */
    @Override
    public void updateBelongdet(BelongEntity entity) {
        this.belongdetMapper.updateBoxopenouts(entity);
    }

    /**
     * 删除一条物品记录，如果所有物品都删除，则删除父记录
     */
    @Override
    public void deleteBelongdetAndBelong(int belongId, int detailId) {
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
     * 创建存物拍照记录
     *
     * @param serialID
     * @param spath
     */
    @Override
    public void creatbelongphoto(String serialID, String spath, String pid, String opUserId) {
        // 使用唯一编号反查BelongingsId
        BelongEntity dd = belongMapper.selectphotoinfonew(serialID);
        BelongEntity obj = new BelongEntity();
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
     * 根据柜门id查询柜门存储信息
     *
     * @param map
     * @return
     */
    @Override
    public List<BelongEntity> listAllBelongdetByLockerId(Map<String, Object> map) {
        return belongdetMapper.listAllBelongdetByLockerId(map);
    }

    /**
     * 通过lockId 查询 serial_id
     *
     * @param lockId
     * @return
     */
    @Override
    public long getSerialIdByLockId(String lockId) {
        return belongMapper.getSerialIdByLockId(lockId);
    }

    /**
     * 随身物品开柜单保存(单个领取)----out----
     *
     * @param entity
     */
    @Override
    public void updateBoxopenouts(BelongEntity entity) throws Exception {
        // 更新单个物品的提取状态，信息
        this.belongdetMapper.updateBoxopenouts(entity);
        Integer s = entity.getId();
        // 反查该物品所属上级编号是否已经全部提取完毕
        int dd = belongMapper.selectbelonginfo(s);
        BelongEntity obj1 = new BelongEntity();
        obj1.setId(entity.getBelongingsId());
        obj1.setIsGet(1);
        obj1.setGetWay(entity.getGetWay());
        obj1.setGetPerson(entity.getGetPerson());
        obj1.setGetTime(entity.getGetTime());
        Long sid1 = belongMapper.selectserialid(obj1);
        // 判断如果全部提取完毕，就更新该专属编号总的提取状态，信息、
        if (dd == 0) {
            BelongEntity obj = new BelongEntity();
            obj.setId(entity.getBelongingsId());
            obj.setIsGet(1);
            obj.setGetWay(entity.getGetWay());
            obj.setGetPerson(entity.getGetPerson());
            obj.setGetTime(entity.getGetTime());
            this.belongMapper.updateUpperInfo(obj);
            // 修改入区状态为8物品已领取
            Long sid = belongMapper.selectserialid(obj);
            SerialEntity state = new SerialEntity();
            state.setId(sid);
            state.setStatus(8);
            state.setStatusChangeTime(new Date());
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("status", 8);
            this.serialMapper.updateStatusById(map);
            try {
                personLockersService.getBelong(entity.getBelongingsId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        // 开柜记录
        BelongEntity objs = new BelongEntity();
        objs.setId(objs.getId());
        objs.setBelongingsId(entity.getBelongingsId());
        objs.setLockerId(entity.getLockerId());
        objs.setGetPerson(entity.getGetPerson());
        objs.setGetWay(entity.getGetWay());
        objs.setGetTime(entity.getGetTime());
        objs.setOpPid(entity.getOpPid());
        objs.setOpUserId(entity.getOpUserId());
        this.belongdetMapper.creatBoxopenouts(objs);
//        try {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("status", 8);
//            this.serialMapper.updateStatusById(map);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 增加开柜日志
     *
     * @param openCabinetEntity
     */
    @Override
    public void insertOpenboxData(OpenCabinetEntity openCabinetEntity) {
        openCabinetMapper.insertSelective(openCabinetEntity);
    }

    /**
     * 全部提取
     *
     * @param entity
     */
    @Override
    public void updateBoxopenout(BelongEntity entity, HttpServletRequest request) {
        Long s = entity.getSerialId();
        // 查询所有占用记录
        List<BelongEntity> list = belongMapper.selectloginfo(s);
        Integer bid = null;
        Integer tempId=0;
        if (list != null) {
            for (BelongEntity be : list) {
                BelongEntity obj = new BelongEntity();
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
            // 息更新该专属编号总的提取状态，信
            this.belongMapper.updateBoxopenout(entity);
            System.err.println("领取全部随身物品=");

        }
//            获取所有数据详情信息
        int count = 0;
        List<BelongEntity> belongEntityList = belongMapper.getBelongDetailById(bid);
        for (BelongEntity aaa : belongEntityList) {
            //对接宽和领取接口
            if ("5".equals(aaa.getGetWay())) {//移交
                if (entity.getJsdwId() != null) {
//                对接并且更新接收单位
                    Map<String, Object> map = new HashMap<>();
                    map.put("wpUuid", aaa.getWpUuid());
                    List<BelongDetailTempEntity> listByTempId = belongDetailTempService.getListByTempId(map);
                    if (listByTempId.size() > 0) {
                        BelongTempEntity temEntity = new BelongTempEntity();
                        temEntity.setId(listByTempId.get(0).getTempId());
                        temEntity.setJsdwId(entity.getJsdwId());
                        temEntity.setYjdwId(organizationService.getOrganizationById(ControllerTool.getSessionInfo(request).getCurrentOrg().getId()).getOrgCode());
                        belongTempService.updateTempYjyj(temEntity);
                    } else {
                        BelongTempEntity temEntity = new BelongTempEntity();
                        List<BelongDetailTempEntity> entityById = belongDetailTempService.getByWpUuid(map);
                        if(entityById.size()==0){
                            temEntity.setBelongId(bid);
                            temEntity.setPersonId(aaa.getSerialId());
                            temEntity.setCaseId(aaa.getCaseId());
                            temEntity.setJsdwId(entity.getJsdwId());
                            temEntity.setXm(aaa.getPersonname());
                            temEntity.setSfzh(aaa.getCertificateNo());
                            temEntity.setYjdwId(organizationService.getOrganizationById(ControllerTool.getSessionInfo(request).getCurrentOrg().getId()).getOrgCode());
//                            temEntity.setYjjg("Y");
                            belongTempService.saveBelong(temEntity);
                            tempId=temEntity.getId();
                        }else{
                            tempId = entityById.get(0).getTempId();
                        }
                        BelongDetailTempEntity belongDetailTempEntity = new BelongDetailTempEntity();
                        belongDetailTempEntity.setTempId(tempId);
                        belongDetailTempEntity.setWpUuid(aaa.getWpUuid());
                        belongDetailTempEntity.setName(aaa.getName());
                        belongDetailTempEntity.setUnit(aaa.getUnit());
                        belongDetailTempEntity.setDescription(aaa.getDescription());
                        belongDetailTempEntity.setSaveMethod(aaa.getSaveMethod());
                        belongDetailTempEntity.setDetailCount(aaa.getDetailCount());
                        belongDetailTempService.saveBelongTemp(belongDetailTempEntity);
                       if(count==0){
                           //移交后图片 插入到对应的表中
                           Map<String, Object> fileMap = new HashMap<>();
                           fileMap.put("areaId", 1);
                           Map<String, Object> serverUrl = belongMapper.selectImage(fileMap);
                           BelongingsPhotoTempEntity photoTempEntity = new BelongingsPhotoTempEntity();
                           if (!serverUrl.isEmpty()) {
                               List<BelongingsPhotoEntity> list1 = belongMapper.selectPhotoByBelongingsId(bid);
                               for (BelongingsPhotoEntity photoEntity : list1) {
                                   photoTempEntity.setTempId(tempId);
                                   photoTempEntity.setUrl(serverUrl.get("web_url") + "?path=" + photoEntity.getUrl());
                                   belongTempService.insertBelongphotoTemp(photoTempEntity);
                                   count++;
                               }
                           }
                       }
                    }
//                    try {
//                        JSONObject json = new JSONObject();
//                        json.put("userZH", ControllerTool.getLoginName(request));
//                        json.put("wbwpbh", aaa.getWpUuid());
//                        json.put("yjdwid", organizationService.getOrganizationById(ControllerTool.getSessionInfo(request).getCurrentOrg().getId()).getOrgCode());
//                        json.put("jsdwid", entity.getJsdwId());
//                        String url1 = kh_base_url + "/sscwjk/yiJiao";
//                        String result = HttpClientUtil.getAllInfoFromHTTPResponse(HttpClientUtil.post(url1, "", json.toString()));
//                        logger.info("随身物品移交数据接收接口(" + url1 + ")，参数为：" + json.toString() + "，结果：" + result);
//                    } catch (Exception e) {
//                        logger.info("随身物品移交数据接收接口" + e);
//                    }
                }
            }else if ("4".equals(aaa.getGetWay())) {//转涉案财物
//                try {
//                    JSONObject json = new JSONObject();
//                    json.put("userZH", ControllerTool.getLoginName(request));
//                    json.put("wbwpbh", aaa.getWpUuid());
//                    String url1 = kh_base_url + "/sscwjk/zhuanSACW";
//                    String result = HttpClientUtil.getAllInfoFromHTTPResponse(HttpClientUtil.post(url1, "", json.toString()));
//                    logger.info("随身物品转涉案财物数据接收接口(" + url1 + ")，参数为：" + json.toString() + "，结果：" + result);
//                } catch (Exception e) {
//                    logger.info("随身物品转涉案财物数据接收接口" + e);
//                }
            }else{
//                try {
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("userZH", ControllerTool.getLoginName(request));
//                    jsonObject.put("wbwpbh", aaa.getWpUuid());
//                    jsonObject.put("lhrxm", aaa.getGetPerson());
//                    String url = kh_base_url + "/sscwjk/lingHui";
//                    String resultObj = HttpClientUtil.getAllInfoFromHTTPResponse(HttpClientUtil.post(url, "", jsonObject.toString()));
//                    logger.info("调用随身物品接口(" + url + ")，参数为：" + jsonObject.toString() + "，结果：" + resultObj);
//
//                } catch (Exception e) {
//                    logger.info("调用随身物品接口" + e);
//                }
            }
        }
        try {
            personLockersService.getBelong(bid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 统计一次存物的所有物品总数
     *
     * @param params
     * @return
     */
    @Override
    public int count1(Map<String, Object> params) {
        return belongdetMapper.count1(params);
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

    /**
     * 查询所有存物信息
     *
     * @param map
     * @return
     */
    @Override
    public List<BelongEntity> listAllBelong(Map<String, Object> map) {
        return belongMapper.listAllBelong(map);
    }

    /**
     * 查询所有存物信息的数量
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public int count(Map<String, Object> params) throws Exception {
        return belongMapper.count(params);
    }

    /**
     * 查询随身物品开柜记录
     *
     * @param map3
     * @return
     */
    @Override
    public List<BelongEntity> queryBelongingsCabinetLlog(Map<String, Object> map3) {
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

    @Override
    public List<BelongEntity> listAllBelongdet2(Map<String, Object> map) {
        return belongdetMapper.listAllBelongdet2(map);
    }

    /**
     * 查询照片
     *
     * @param belongingsId
     * @return
     */
    @Override
    public List<BelongingsPhotoEntity> selectPhotoByBelongingsId(long belongingsId) {
        return belongMapper.selectPhotoByBelongingsId(belongingsId);
    }

    @Override
    public BelongEntity selectBelonginfo(Map<String, Object> map) {
        //int b = new Long(ss).intValue();
        BelongEntity s = belongMapper.selectBelonginfo(map);
        BelongEntity obj = new BelongEntity();
        obj.setAname(s.getAname());
        obj.setPname(s.getPname());
        obj.setCname(s.getCname());
        obj.setCertificateNo(s.getCertificateNo());
        Calendar dt = Calendar.getInstance();
        int y = dt.get(Calendar.YEAR);
        int m = dt.get(Calendar.MONTH) + 1;
        int d = dt.get(Calendar.DAY_OF_MONTH);
        int h = dt.get(Calendar.HOUR_OF_DAY);
        int mm = dt.get(Calendar.MINUTE);
        int ds = dt.get(Calendar.SECOND);
        int ms = dt.get(Calendar.MILLISECOND);

        String Y = String.valueOf(y);
        String M = String.valueOf(m);
        String D = String.valueOf(d);
        String H = String.valueOf(h);
        String N = String.valueOf(mm);
        String S = String.valueOf(ds);
        String MS = String.valueOf(ms);
        String ms2 = "";
        ms2 = MS + "";
        if (ms < 10) {
            ms2 = "00" + MS;
        }
        if ((ms < 100) && (ms >= 10)) {
            ms2 = "0" + MS;
        }
        String mm2 = "";
        mm2 = N + "";
        if (mm < 10) {
            mm2 = "0" + N;
        }
        String ds2 = "";
        ds2 = S + "";
        if (ds < 10) {
            ds2 = "0" + S;
        }
        String h2 = "";
        h2 = H + "";
        if (h < 10) {
            h2 = "0" + H;
        }
        String m2 = "";
        m2 = M + "";
        if (m < 10) {
            m2 = "0" + M;
        }
        String D2 = "";
        D2 = D + "";
        if (d < 10) {
            D2 = "0" + D;
        }
        String cod = "SSWP" + Y + m2 + D2 + h2 + mm2 + ds2 + ms2;
        obj.setBurcode(cod);
        obj.setId(obj.getId());
        obj.setCreatedTime(new Date());
        obj.setBelongingsId(s.getBelongingsId());
        return obj;
    }

    @Override
    public List<BelongEntity> getDocInfo(BelongEntity entity) {
        return belongMapper.getDocInfo(entity);
    }

    @Override
    public BelongEntity getBelongdetById(Integer id) {
        return belongdetMapper.getById(id);
    }

    @Override
    public Long selectSerialid(Integer belongingsId) {
        BelongEntity entity = new BelongEntity();
        entity.setId(belongingsId);
        return belongMapper.selectserialid(entity);
    }

    @Override
    public BelongEntity getBelongdetByWpUuid(Map<String, Object> map) {
        return belongMapper.getBelongdetByWpUuid(map);
    }

    /**
     * 查询所有存物信息
     *
     * @param areaId,str
     * @return
     */
    @Override
    public List<BelongEntity> getListByBelongDetail(Integer areaId, String str) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("areaId",areaId);
        map.put("dataAuth",str);
        List<BelongEntity> list = belongMapper.getListByBelongDetail(map);
        return list;
    }

    @Override
    public Map<String, Object> selectImage(Map<String, Object> fileMap) {
        return belongMapper.selectImage(fileMap);
    }

    @Override
    public List<BelongingsPhotoTempEntity> selectPhotoByTempId(Integer tempId) {
        return belongTempService.selectPhotoByTempId(tempId);
    }

    @Override
    public List<BelongEntity> addBelongcodNew(Map<String, Object> fileMap) {
        List<BelongEntity> s = belongMapper.selectcodinfoNew(fileMap);
        return s;
    }

}
