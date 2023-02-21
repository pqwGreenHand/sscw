package com.zhixin.zhfz.bacs.controller.policebelong;

import com.zhixin.zhfz.bacs.entity.ComboboxEntity;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.PoliceBelongForm;
import com.zhixin.zhfz.bacs.services.cabinetConfig.ICabinetConfigDetailService;
import com.zhixin.zhfz.bacs.services.combobox.IComboboxService;
import com.zhixin.zhfz.bacs.services.lawdoc.ILawDocProcessService;
import com.zhixin.zhfz.bacs.services.openCabinet.IOpenCabinetService;
import com.zhixin.zhfz.bacs.services.person.IPoliceLockersService;
import com.zhixin.zhfz.bacs.services.personalconfig.IPersonalConfigService;
import com.zhixin.zhfz.bacs.services.policebelong.IPoliceBelongService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/policebelong")
public class PoliceBelongController {

    private static Logger logger = LoggerFactory.getLogger(PoliceBelongController.class);

    @Autowired
    private IPoliceBelongService belongService;
    @Autowired
    private IOperLogService operLogService;
    @Autowired
    private ISerialService serialService;
    @Autowired
    private IPersonalConfigService personalconfigservice;
    @Autowired
    private ILawDocProcessService lawDocProcessService;
    @Autowired
    private ICabinetConfigDetailService cabinetConfigDetailService;
    @Autowired
    private IPoliceLockersService policeLockersService;
    @Autowired
    private IComboboxService comboboxService;
    @Autowired
    private IFileConfigService fileConfigService;
    @Autowired
    private IOpenCabinetService openCabinetService;
    /**
     * 随身储物柜页面显示排序
     *
     * @param areaid
     * @return
     */
    @RequestMapping(value = "/listboxinfo")
    @ResponseBody
    public TreeMap<String, List<CabinetConfigDetailEntity>> listboxinfo(@RequestParam String areaid) {
        List<CabinetConfigDetailEntity> list = cabinetConfigDetailService.selectRowColSortByPolice(Integer.parseInt(areaid));
        TreeMap<String, List<CabinetConfigDetailEntity>> result = new TreeMap<String, List<CabinetConfigDetailEntity>>();
        for (int i = 0; i < list.size(); i++) {
            if (result.containsKey(list.get(i).getGroup())) {
                result.get(list.get(i).getGroup()).add(list.get(i));
            } else {
                List<CabinetConfigDetailEntity> param = new ArrayList<CabinetConfigDetailEntity>();
                param.add(list.get(i));
                result.put(list.get(i).getGroup(), param);
            }
        }
        return result;
    }

    /**
     * 展示一个入区编号的所有柜子及规则所有物品
     *
     * @param pageMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllBelongdet2")
    @ResponseBody
    public Map<String, Object> listAllBelongdet2(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        if (pageMap.get("enpId") == null) {
            return null;
        }
        List<PoliceBelongEntity> list = new ArrayList<PoliceBelongEntity>();
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( b.police_id=" + ControllerTool.getUserID(request)
                    + " or b.register_user_id=" + ControllerTool.getUserID(request)
                    + " or b.op_user_id=" + ControllerTool.getUserID(request)
                    + " or d.op_user_id=" + ControllerTool.getUserID(request)
                    + " or s.police_id =" + ControllerTool.getUserID(request)
                    + " or s.op_user_id=" + ControllerTool.getUserID(request)
                    + " or pn.id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ( s.area_id = " + ControllerTool.getCurrentAreaID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ( s.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ( s.area_id  " + sessionInfo.getSuperAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth","  ( b.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or d.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + ")" );
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth","  ( b.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or s.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or d.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + ")" );
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","  ( b.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or s.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or d.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + ")" );
        }
        if (flag) {
            list = this.belongService.listAllBelongdet2(map);
            if (list != null && list.size() > 0) {
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        return result;
    }

    /**
     * 拼接存物柜下拉框信息
     *
     * @param lockerId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listBelongLockerBox")
    @ResponseBody
    public List<ComboboxEntity> listBelongLockerBox(Long lockerId, HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        logger.info("lockerId:" + lockerId);
        List<ComboboxEntity> list = null;
        if (sessionInfo != null) {
            list = comboboxService.listPoliceBelongLockerBox(sessionInfo.getCurrentArea().getId(), lockerId);
        } else {
            list = new ArrayList<ComboboxEntity>();
        }
        return list;
    }

    /**
     * 随身物品保存随身物品 （新增(存柜时)）
     *
     * @param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/belongsave")
    @ResponseBody
    public MessageEntity belongsave(@RequestBody Map<String, Object> arr, HttpServletRequest request,
                                    HttpServletResponse response) {
        JSONArray jsonArr = JSONArray.fromObject(arr);
        JSONObject jsonObj = jsonArr.getJSONObject(0);
        JSONArray belongJsonArr = JSONArray.fromObject(jsonObj.get("list"));
        List<PoliceBelongEntity> list = new ArrayList<PoliceBelongEntity>();
        JSONArray formJsonArr = JSONArray.fromObject(jsonObj.get("data"));
        PoliceBelongForm form = (PoliceBelongForm) JSONObject.toBean(formJsonArr.getJSONObject(0), PoliceBelongForm.class);
        PoliceBelongEntity entity = new PoliceBelongEntity();
        try {
            for (int i = 0; i < belongJsonArr.size(); i++) {
                list.add((PoliceBelongEntity) JSONObject.toBean(belongJsonArr.getJSONObject(i), PoliceBelongEntity.class));
            }
            for (PoliceBelongEntity belongEntity : list) {
                entity.setId(entity.getId());
                entity.setPoliceId(form.getSerialId());
                entity.setRegisterUserId(ControllerTool.getUserID(request));
                entity.setRegisterTime(new Date());
                entity.setCaseId(form.getCaseId());
                entity.setAreaId(form.getAreaId());
                entity.setLockerId(form.getLockerId());
                entity.setIsGet(0);
                entity.setGetWay("0");
                entity.setBelongingsId(form.getId());
                entity.setGetPerson(form.getGetPerson());
                entity.setGetTime(form.getGetTime());
                entity.setCreatedTime(new Date());
                entity.setUpdatedTime(new Date());
                entity.setDid(form.getDid());
                entity.setName(belongEntity.getName());
                entity.setDetailCount(belongEntity.getDetailCount());
                entity.setDescription(belongEntity.getDescription());
                entity.setUnit(belongEntity.getUnit());
                entity.setSaveMethod(form.getSaveMethod());
                entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
                int result = this.belongService.insertBelong(entity);
                if (result == 0) {// 判断柜子是否被占用，返回提示
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("失败").addContent("该柜已被占用,请选择其他柜!");
                }
            }
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "新增随身物品(存柜)" + list, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.info("" + e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "新增随身物品(存柜)" + list, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加成功!");
    }

    /**
     * 修改具体存物信息
     *
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/editBelongdet")
    @ResponseBody
    public MessageEntity editBelongdet(@RequestBody PoliceBelongForm form, HttpServletRequest request,
                                       HttpServletResponse response) {
        PoliceBelongEntity entity = new PoliceBelongEntity();
        entity.setId(form.getId());
        entity.setBelongingsId(form.getBelongingsId());
        entity.setName(form.getName());
        entity.setDetailCount(form.getDetailCount());
        entity.setDescription(form.getDescription());
        try {
            belongService.updateBelongdet(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改随身物品详情" + entity, ControllerTool.getUser(request).getCertificateNo(), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on edit updateArea!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改随身物品详情" + entity, ControllerTool.getUser(request).getCertificateNo(), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("修改成功!");
    }

    /**
     * 随身物品存入时删除一条物品记录（存柜时）
     *
     * @param
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeBelongdet")
    @ResponseBody
    public MessageEntity removeBelongdet(HttpServletRequest request, Integer belongingsId, Integer detailId) {
        try {
            // 删除一条物品记录，如果所有的物品记录都删除了则删除存柜记录
            belongService.deleteBelongdetAndBelong(belongingsId, detailId);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除随身物品详情" + detailId, ControllerTool.getUser(request).getCertificateNo(), true, "办案场所");
        } catch (Exception e) {
            logger.error("删除失败!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除随身物品详情" + detailId, ControllerTool.getUser(request).getCertificateNo(), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("删除成功!");
    }

    /**
     * 存物图片上传
     *
     * @param params1
     * @param request
     * @param serialid
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/outgetpicture")
    @ResponseBody
    public void outgetpicture(@RequestParam Map<String, Object> params1, HttpServletRequest request, String serialid,
                              HttpServletResponse response) throws Exception {
        try {
            // 图片开始
            String serialID = serialid;
            SerialEntity entity = new SerialEntity();
            entity.setId(Long.valueOf(serialid));
            SerialEntity serial = serialService.getSerialByNo(entity);
            // 创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            FileUploadForm form = new FileUploadForm();
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                // 转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    // 取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        // 取得当前上传文件的文件名称
                        String myFileName = file.getOriginalFilename();
                        if (!"".equals(myFileName.trim())) {
                            form.setSysType("ba");
                            form.setFileType("MJ");
                            form.setUuid(serial.getUuid());
                            form.setSysId(serial.getAreaId().toString());
                            form.setFile(file);
                            String filename = "policebelong-" + serial.getSerialNo() + "-" + new Random().nextInt(10000) + "-yt.jpg";
                            form.setFileName(filename);
                            fileConfigService.upload(form);
                            belongService.creatbelongphoto(serialID, filename,ControllerTool.getSessionInfo(request).getCurrentOrg().getPid(),ControllerTool.getSessionInfo(request).getUser().getId().toString());
                        }
                    }
                }
            } else {
                logger.info("========================图片上传错误 isNotMultipart=");
            }
            // 图片结束
        } catch (Exception e) {
            logger.error("### 图片上传错误  ###", e);
        }
    }

    /**
     * 打开储物柜
     *
     * @param lockid
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/belongsavebox")
    @ResponseBody
    public MessageEntity belongsavebox(@RequestParam long lockid, HttpServletRequest request) throws Exception {
        SessionInfo session = ControllerTool.getSessionInfo(request);
        try {
            openBoxOne(lockid, session);
            logger.info("### open belong cabinet 开柜成功 ### ");
        } catch (Exception e) {
            logger.error("", e);
            return new MessageEntity().addIsError(true).addContent(e.getMessage());
        }
        return new MessageEntity().addIsError(false).addContent("开柜成功");
    }

    /**
     * 查询开柜配置并连接打开柜门
     *
     * @param lockerId
     * @param session
     * @throws Exception
     */
    private void openBoxOne(Long lockerId, SessionInfo session) throws Exception {
        CabinetConfigEntity cabinetConfig = cabinetConfigDetailService.listInParamDetailByOutParamId(lockerId);
        String lockNo = cabinetConfigDetailService.queryBoxNumberById(lockerId.intValue());
        String boxip = cabinetConfig.getIp();
        int boxport = Integer.valueOf(cabinetConfig.getPort());
        String boxgroup = cabinetConfig.getGroup();
        String openips = cabinetConfig.getOpenIp();
        logger.info("### open belong cabinet ### " + lockNo + "," + boxip + "," + boxgroup + "," + boxport + "[client:"
                + session.getClientIP() + "]" + "，ips:[" + openips + "].");
        if (openips.indexOf(session.getClientIP()) < 0) {
            logger.info("user ip:" + session.getClientIP());
            logger.info("openips:" + openips);
            throw new Exception("您的IP不可以开柜！");
        }
        //记录开柜操作
        try {
            OpenCabinetEntity openCabinetEntity = new OpenCabinetEntity();
            openCabinetEntity.setId(openCabinetEntity.getId());
            openCabinetEntity.setCreatedTime(new Date());
            openCabinetEntity.setLockerId(lockerId.toString());
            openCabinetEntity.setAreaId(session.getCurrentArea().getId());
            openCabinetEntity.setOpener(session.getUser().getRealName());
            openCabinetEntity.setOpPid(session.getCurrentOrg().getPid());
            openCabinetEntity.setOpUserId(session.getUser().getId());
            belongService.insertOpenboxData(openCabinetEntity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        CabinetUtilZhiXin util = new CabinetUtilZhiXin();
        try {
            util.openBoxOne(boxgroup, lockNo, boxip, boxport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception("连接柜子失败！");
        }
    }

    /**
     * 根据柜门id查询柜门存储信息
     *
     * @param pageMap
     * @param request
     * @param response
     * @param lockerId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllBelongdetByLockerId")
    @ResponseBody
    public Map<String, Object> listAllBelongdetByLockerId(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                                          HttpServletResponse response, String lockerId) throws Exception {
        List<PoliceBelongEntity> list = new ArrayList<PoliceBelongEntity>();
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        map.put("lockerId", lockerId);
        map.put("flag", "Belong");
        list = belongService.listAllBelongdet3(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        return result;
    }

    /**
     * 通过lockId 查询 serial_id
     */
    @RequestMapping(value = "/getSerialIdByLockId")
    @ResponseBody
    public long getSerialIdByLockId(String lockId) {
        return belongService.getSerialIdByLockId(lockId);
    }

    // 随身物品开柜单保存(单个领取)----out----
    @RequestMapping(value = "/editBoxopenouts")
    @ResponseBody
    public MessageEntity editBoxopenouts(@RequestBody PoliceBelongForm form, HttpServletRequest request, int id,
                                         HttpServletResponse response) {
        logger.info("++++++++edit++++++BelongForm=" + form);
        System.err.println("1865457:" + id);
        long ss = id;
        PoliceBelongEntity entity = new PoliceBelongEntity();
        entity.setId(ss);
        entity.setBelongingsId(form.getBelongingsId());
        entity.setLockerId(form.getLockerId());
        entity.setIsGet(1);
        entity.setGetWay(form.getGetWay());
        entity.setGetPerson(form.getGetPerson());
        entity.setGetTime(new Date());
        entity.setCreatedTime(new Date());
        entity.setUpdatedTime(new Date());
        entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            belongService.updateBoxopenouts(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "填写单个领取开柜单" + entity, ControllerTool.getUser(request).getCertificateNo(), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on edit updateArea!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "填写单个领取开柜单" + entity, ControllerTool.getUser(request).getCertificateNo(), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("领取失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("领取成功!");
    }

    /**
     * 取物开柜
     *
     * @param lockid
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boxoutopen")
    @ResponseBody
    public MessageEntity boxoutopen(@RequestParam long lockid, HttpServletRequest request) throws Exception {
        SessionInfo session = ControllerTool.getSessionInfo(request);
        try {
            openBoxOne(lockid, session);
            logger.info("### open belong cabinet 开柜成功 ### ");
        } catch (Exception e) {
            logger.error("开柜异常：", e);
            return new MessageEntity().addIsError(true).addContent(e.getMessage());
        }
        return new MessageEntity().addIsError(false).addContent("开柜成功");
    }

    /**
     * 随身物品开柜单保存----out---- 全部提取
     *
     * @param form
     * @param request
     * @param id
     * @param response
     * @return
     */
    @RequestMapping(value = "/editBoxopenout")
    @ResponseBody
    public MessageEntity editBoxopenout(@RequestBody PoliceBelongForm form, HttpServletRequest request, Long id,
                                        HttpServletResponse response) {
        logger.info("++++++++edit++++++PoliceBelongForm=" + form);
        PoliceBelongEntity entity = new PoliceBelongEntity();
        Long ss = id;
        entity.setPoliceId(ss);
        entity.setId(form.getId());
        entity.setIsGet(1);
        entity.setGetWay(form.getGetWay());
        entity.setGetPerson(form.getGetPerson());
        entity.setGetTime(new Date());
        entity.setCreatedTime(new Date());
        entity.setUpdatedTime(new Date());
        try {
            belongService.updateBoxopenout(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "填写全部领取开柜单" + entity, ControllerTool.getUser(request).getCertificateNo(), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on edit updateArea!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "填写全部领取开柜单" + entity, ControllerTool.getUser(request).getCertificateNo(), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("提取失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("提取成功!");
    }

    /**
     * 选择空闲的随身储物柜
     *
     * @param params
     * @param request
     * @param response
     * @throws Exception
     * @returnlistAllBelong
     */
    @RequestMapping(value = "/listunUsedbox")
    @ResponseBody
    public List<ComboboxEntity> listunUsedbox(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
        String areaid = request.getParameter("areaid");
        System.err.println("areaid=" + areaid);
        params.put("areaid", areaid);
        params.put("type", 3);
        return this.belongService.listunUsedbox(params);
    }

    /**
     * 随身物品管理 list
     *
     * @param pageMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllBelong")
    @ResponseBody
    public Map<String, Object> listAllBelong(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( b.op_user_id=" + ControllerTool.getUserID(request)
                    + " or s.op_user_id=" + ControllerTool.getUserID(request)
                    + " or pn.op_user_id=" + ControllerTool.getUserID(request)
                    + " or pn.id=" + ControllerTool.getUserID(request)
                    + " or dl.op_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ( b.area_id=" + ControllerTool.getCurrentAreaID(request)
                +" or s.area_id = " + ControllerTool.getCurrentAreaID(request)
            +" ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "( b.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
                    +" or s.area_id = " + sessionInfo.getCurrentAndSubAreaInStr()
            +" )");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ( b.area_id  " + sessionInfo.getSuperAndSubAreaInStr()
                    +" or s.area_id = " + sessionInfo.getCurrentAndSubAreaInStr()
            +" )");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( b.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or s.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or pn.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or dl.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( b.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or s.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or pn.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or dl.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( b.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or s.op_pid like " +  sessionInfo.getCurrentOrgPid()
                    + " or pn.op_pid like " +  sessionInfo.getCurrentOrgPid()
                    + " or dl.op_pid like " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<PoliceBelongEntity> list = new ArrayList<PoliceBelongEntity>();
        int count = 0;
        map.put("flag", "Belong");
        list = this.belongService.listAllBelong(map);
        count = this.belongService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", list);
        return result;
    }

    /**
     * 查询民警存物历史
     *
     * @param pageMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllBelongdet")
    @ResponseBody
    public Map<String, Object> listAllBelongdet(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        if (pageMap.get("enpId") == null) {
            return null;
        }
        Map<String, Object> map2 = ControllerTool.mapFilter(pageMap);
        List<PoliceBelongEntity> list = this.belongService.listAllBelongdet(map2);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.belongService.count1(map2));
        result.put("rows", list);
        return result;
    }

    /**
     * 查询随身物品开柜记录
     *
     * @param pageMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllBelongcod")
    @ResponseBody
    public Map<String, Object> listAllBelongcod(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        Map<String, Object> map3 = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map3.put("dataAuth", " ( bcl.opener=" + ControllerTool.getUserID(request)
                    + " or pcl.op_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map3.put("dataAuth", " bcl.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map3.put("dataAuth", " bcl.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map3.put("dataAuth", " bcl.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map3.put("dataAuth"," ( bcl.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map3.put("dataAuth"," ( bcl.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map3.put("dataAuth"," ( bcl.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<PoliceBelongEntity> list = this.belongService.queryBelongingsCabinetLlog(map3);
        List<BelongEntity> resultList = new ArrayList<BelongEntity>();
        for (PoliceBelongEntity belongEntity : list) {
            BelongEntity resultEntity = new BelongEntity();
            resultEntity.setLogId(belongEntity.getLogId());
            resultEntity.setLogBelongId(belongEntity.getLogBelongId());
            resultEntity.setLogBelongLockerId(belongEntity.getLogBelongLockerId());
            resultEntity.setLogBelongOpener(ControllerTool.getSessionInfo(request).getUser().getRealName());
            resultEntity.setLogBelongReason(belongEntity.getLogBelongReason());
            resultEntity.setLogCreatedTime(belongEntity.getLogCreatedTime());
            resultList.add(resultEntity);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.belongService.countBelongingsCabinetLog(map3));
        result.put("rows", resultList);
        return result;
    }

    /**
     * 查询开柜记录
     */
    @RequestMapping(value = "/openBoxLog")
    @ResponseBody
    public Map<String, Object> openBoxLog(HttpServletRequest request, @RequestParam Map<String, Object> pageMap) {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        Map<String, Object> result = new HashMap<String, Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( pcl.opener=" + ControllerTool.getUserID(request)
                    + " or pcl.op_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " pcl.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " pcl.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " pcl.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( pcl.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( pcl.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( pcl.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        result.put("rows", openCabinetService.list(map));
        result.put("total", openCabinetService.count(map));
        return result;
    }

    /**
     * 获取图片
     */
    @RequestMapping(value = "/getImages")
    @ResponseBody
    public List<PoliceBelongingsPhotoEntity> getImages(HttpServletRequest request, Integer belongingsId) {
        //根据belongingsId,获取照片集合
        List<PoliceBelongingsPhotoEntity> photos = belongService.selectPhotoByBelongingsId(belongingsId);
        return photos;
    }
}