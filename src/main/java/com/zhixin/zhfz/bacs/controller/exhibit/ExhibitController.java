package com.zhixin.zhfz.bacs.controller.exhibit;

import com.zhixin.zhfz.bacs.dao.exhibit.IExhibitdetMapper;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.entity.ComboboxEntity;
import com.zhixin.zhfz.bacs.form.BelongAppForm;
import com.zhixin.zhfz.bacs.form.ExhibitForm;
import com.zhixin.zhfz.bacs.services.belong.IBelongService;
import com.zhixin.zhfz.bacs.services.cabinetConfig.ICabinetConfigDetailService;
import com.zhixin.zhfz.bacs.services.combobox.IComboboxService;
import com.zhixin.zhfz.bacs.services.exhibit.IExhibitService;
import com.zhixin.zhfz.bacs.services.openCabinet.IOpenCabinetService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.common.CabinetUtil;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import freemarker.template.TemplateException;
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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/exhibit")
public class ExhibitController {

    private static final Logger logger = LoggerFactory.getLogger(ExhibitController.class);
    @Autowired
    private ICabinetConfigDetailService cabinetConfigDetailService;
    @Autowired
    private IExhibitService exhibitService;
    @Autowired
    private IOperLogService operLogService;
    @Autowired
    private ISerialService serialService;
    @Autowired
    private IBelongService belongService;
    @Autowired
    private IFileConfigService fileConfigService;
    @Autowired
    private IComboboxService comboboxService;
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
        List<CabinetConfigDetailEntity> list = cabinetConfigDetailService.selectRowColSortBySA(Integer.parseInt(areaid));
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
     * 随身储物柜页面显示排序
     *
     * @param areaid
     * @return
     */
    @RequestMapping(value = "/listboxinfoByApp")
    @ResponseBody
    public TreeMap<String, List<CabinetConfigDetailEntity>> listboxinfoByApp(@RequestParam String areaid) {
        List<CabinetConfigDetailEntity> list = cabinetConfigDetailService.selectRowColSortBySAAPP(Integer.parseInt(areaid));
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
     * 涉案储物柜存物查询
     *
     * @param pageMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllExhibitdet2")
    @ResponseBody
    public Map<String, Object> listAllExhibitdet2(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                                  HttpServletResponse response) throws Exception {
        logger.debug("=======================EmployeeController.listAllBelong=======================");
        if (pageMap.get("enpId") == null) {
            return null;
        }
        List<ExhibitEntity> list = new ArrayList<ExhibitEntity>();
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( d.op_user_id=" + ControllerTool.getUserID(request)
                    + " or s.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.send_user_id=" + ControllerTool.getUserID(request)
                    + " or pn.id =" + ControllerTool.getUserID(request)
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
            map.put("dataAuth","  ( d.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or pn.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + ")" );
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth","  ( d.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or s.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or pn.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + ")" );
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","  ( d.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or s.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or pn.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + ")" );
        }
        list = this.exhibitService.listAllExhibitdet2(map);
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
    @RequestMapping(value = "/listExhibitLockerBox")
    @ResponseBody
    public List<ComboboxEntity> listExhibitLockerBox(Long lockerId, HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        logger.info("lockerId:" + lockerId);
        List<ComboboxEntity> list = null;
        if (sessionInfo != null) {
            list = comboboxService.listExhibitLockerBox(sessionInfo.getCurrentArea().getId(), lockerId);
        } else {
            list = new ArrayList<ComboboxEntity>();
        }

        return list;
    }

    /**
     * 增加涉案物品存储
     *
     * @param arr
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/exhibitsave")
    @ResponseBody
    public MessageEntity exhibitsave(@RequestBody Map<String, Object> arr, HttpServletRequest request,
                                     HttpServletResponse response) {
        JSONArray jsonArr = JSONArray.fromObject(arr);
        JSONObject jsonObj = jsonArr.getJSONObject(0);
        JSONArray belongJsonArr = JSONArray.fromObject(jsonObj.get("list"));
        List<ExhibitEntity> list = new ArrayList<ExhibitEntity>();
        JSONArray formJsonArr = JSONArray.fromObject(jsonObj.get("data"));
        ExhibitForm form = (ExhibitForm) JSONObject.toBean(formJsonArr.getJSONObject(0), ExhibitForm.class);
        ExhibitEntity entity = new ExhibitEntity();
        try {
            for (int i = 0; i < belongJsonArr.size(); i++) {
                list.add((ExhibitEntity) JSONObject.toBean(belongJsonArr.getJSONObject(i), ExhibitEntity.class));
            }
            for (ExhibitEntity exhibitEntity : list) {
                entity.setId(entity.getId());
                entity.setSerialId(form.getSerialId());
                entity.setRegisterUserId(ControllerTool.getUserID(request));
                entity.setRegisterTime(new Date());
                entity.setCaseId(form.getCaseId());
                entity.setAreaId(form.getAreaId());
                entity.setLockerId(form.getLockerId());
                entity.setIsGet(0);
                entity.setGetWay("0");
                entity.setExhibitId(form.getId());
                entity.setGetPerson(form.getGetPerson());
                entity.setGetTime(form.getGetTime());
                entity.setCreatedTime(new Date());
                entity.setUpdatedTime(new Date());
                entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
                entity.setDid(form.getDid());
                entity.setName(exhibitEntity.getName());
                entity.setDetailCount(exhibitEntity.getDetailCount());
                entity.setDescription(exhibitEntity.getDescription());
                entity.setUnit(exhibitEntity.getUnit());
                int result = exhibitService.insertExhibit(entity);
                if (result == 0) {// 判断柜子是否被占用，返回提示
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("失败").addContent("该柜已被占用,请选择其他柜!");
                }
            }
            serialService.updateStatusById(form.getSerialId(), 2);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "新增涉案物品(存柜)" + list, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on edit updateArea!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "新增涉案物品" + entity, "system", false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加成功!");
    }

    /**
     * 存储--删除
     *
     * @param
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeExhibitdet")
    @ResponseBody
    public MessageEntity removeExhibitdet(Integer exhibitId, Integer detailId, HttpServletRequest request) {
        try {
            exhibitService.deleteExhibitdetAndExhibit(exhibitId, detailId);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除涉案物品详情" + exhibitId, "system", true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on deleting Enterprise!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除涉案物品详情" + exhibitId, "system", false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("删除成功!");
    }

    /**
     * 修改存物详情
     *
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/editExhibitdet")
    @ResponseBody
    public MessageEntity editExhibitdet(@RequestBody ExhibitForm form, HttpServletRequest request,
                                        HttpServletResponse response) {
        logger.debug("++++++++edit++++++ExhibitForm={}", form);
        ExhibitEntity entity = new ExhibitEntity();
        entity.setId(form.getId());
        entity.setExhibitId(form.getExhibitId());
        entity.setName(form.getName());
        entity.setDetailCount(form.getDetailCount());
        entity.setDescription(form.getDescription());
        try {
            exhibitService.updateExhibitdet(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改涉案物品详情" + entity, "system", true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on edit updateArea!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改涉案物品详情" + entity, "system", false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("修改成功!");
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
            SerialEntity serial = serialService.getSerialByNo1(entity);
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
                            form.setFileType("SA");
                            form.setUuid(serial.getUuid());
                            form.setSysId(serial.getAreaId().toString());
                            form.setFile(file);
                            String filename = "exhibit-" + serial.getSerialNo() + "-" + new Random().nextInt(10000) + "-yt.jpg";
                            form.setFileName(filename);
                            fileConfigService.upload(form);
                            exhibitService.createxhibitphoto(serialID, filename,ControllerTool.getSessionInfo(request).getCurrentOrg().getPid(),ControllerTool.getSessionInfo(request).getUser().getId().toString());
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
     * 涉案物品开柜
     *
     * @param lockid
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exhibitsavebox")
    @ResponseBody
    public MessageEntity exhibitsavebox(@RequestParam long lockid, HttpServletRequest request) throws Exception {
        SessionInfo session = ControllerTool.getSessionInfo(request);
        try {
            openBoxOne(lockid, session, request);
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
    private void openBoxOne(Long lockerId, SessionInfo session, HttpServletRequest request) throws Exception {
        CabinetConfigEntity cabinetConfig = cabinetConfigDetailService.listInParamDetailByOutParamId(lockerId);
        String lockNo = cabinetConfigDetailService.queryBoxNumberById(lockerId.intValue());
        String boxip = cabinetConfig.getIp();
        int boxport = Integer.valueOf(cabinetConfig.getPort());
        String boxgroup = cabinetConfig.getGroup();
        String openips = cabinetConfig.getOpenIp();
        logger.info("### open cabinet ### " + lockNo + "," + boxip + "," + boxgroup + "," + boxport + "[client:"
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

            belongService.insertOpenboxData(openCabinetEntity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if(ControllerTool.getCurrentAreaID(request)==1){
            CabinetUtil util = new CabinetUtil();
            try {
                util.openBoxOne(boxgroup, lockNo, boxip, boxport);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new Exception("连接柜子失败！");
            }
        }else{
            CabinetUtilZhiXin util = new CabinetUtilZhiXin();
            try {
                util.openBoxOne(boxgroup, lockNo, boxip, boxport);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new Exception("连接柜子失败！");
            }
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
    @RequestMapping(value = "/listAllExhibitdetByLockerId")
    @ResponseBody
    public Map<String, Object> listAllExhibitdetByLockerId(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                                           HttpServletResponse response, String lockerId) throws Exception {
        System.err.println("======================================lockerId:" + lockerId);
        List<ExhibitEntity> list = null;
        boolean flag = true;
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        map.put("lockerId", lockerId);
        list = this.exhibitService.listAllExhibitdet3(map);
        System.err.println("list.size=============" + list.size());
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
        return this.exhibitService.getSerialIdByLockId(lockId);
    }

    /**
     * 开柜单保存(单个领取)----out----
     *
     * @param form
     * @param request
     * @param id
     * @param response
     * @return
     */
    @RequestMapping(value = "/editBoxopenouts")
    @ResponseBody
    public MessageEntity editBoxopenouts(@RequestBody ExhibitForm form, HttpServletRequest request, int id,
                                         HttpServletResponse response) {
        logger.debug("++++++++edit++++++BelongForm={}", form);
        System.err.println("1865457:" + id);
        int ss = id;
        ExhibitEntity entity = new ExhibitEntity();
        entity.setId(ss);
        entity.setExhibitId(form.getExhibitId());
        entity.setLockerId(form.getLockerId());
        entity.setIsGet(1);
        entity.setGetWay(form.getGetWay());
        entity.setGetPerson(form.getGetPerson());
        entity.setGetTime(new Date());
        entity.setCreatedTime(new Date());
        entity.setUpdatedTime(new Date());
        entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            exhibitService.updateBoxopenouts(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "填写单个领取开柜单" + entity, "system", true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on edit updateArea!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "填写单个领取开柜单" + entity, "system", false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("领取失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("领取成功!");
    }

    /**
     * 开柜单保存----out---- 全部提取
     *
     * @param form
     * @param request
     * @param id
     * @param response
     * @return
     */
    @RequestMapping(value = "/editBoxopenout")
    @ResponseBody
    public MessageEntity editBoxopenout(@RequestBody ExhibitForm form, HttpServletRequest request, Long id,
                                        HttpServletResponse response) {
        ExhibitEntity entity = new ExhibitEntity();
        Long ss = id;
        entity.setSerialId(ss);
        entity.setId(form.getId());
        entity.setIsGet(1);
        entity.setGetWay(form.getGetWay());
        entity.setGetPerson(form.getGetPerson());
        entity.setGetTime(new Date());
        entity.setCreatedTime(new Date());
        entity.setUpdatedTime(new Date());
        try {
            exhibitService.updateBoxopenout(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "填写全部领取开柜单" + entity, "system", true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on edit updateArea!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "填写全部领取开柜单" + entity, "system", false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("提取失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("提取成功!");
    }

    /**
     * 取物开柜
     *
     * @param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boxoutopen")
    @ResponseBody
    public MessageEntity boxoutopen(@RequestParam long lockid, HttpServletRequest request) throws Exception {
        SessionInfo session = ControllerTool.getSessionInfo(request);
        try {
            openBoxOne(lockid, session, request);
            logger.info("### open belong cabinet 开柜成功 ### ");
        } catch (Exception e) {
            logger.error("开柜异常：", e);
            return new MessageEntity().addIsError(true).addContent(e.getMessage());
        }
        return new MessageEntity().addIsError(false).addContent("开柜成功");
    }

    /**
     * 选择空闲的随身储物柜
     *
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listunUsedbox")
    @ResponseBody
    public List<ComboboxEntity> listunUsedbox(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
        String areaid = request.getParameter("areaid");
        System.err.println("areaid=" + areaid);
        params.put("areaid", areaid);
        params.put("type", 2);
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
    @RequestMapping(value = "/listAllExhibit")
    @ResponseBody
    public Map<String, Object> listAllExhibit(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
        List<ExhibitEntity> list = new ArrayList<ExhibitEntity>();
        int count = 0;
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        map.put("flag", "Belong");
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( b.register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.send_user_id=" + ControllerTool.getUserID(request)
                    + " or dl.op_user_id=" + ControllerTool.getUserID(request)
                    + " or ba.cjr=" + ControllerTool.getUserID(request)
                    + " or ba.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",',ba.xbmj_ids)"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ( b.area_id = " + ControllerTool.getCurrentAreaID(request)
                    + " or s.area_id = " + ControllerTool.getCurrentAreaID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ( b.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " or s.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ( b.area_id  " + sessionInfo.getSuperAndSubAreaInStr()
                    + " or s.area_id " + sessionInfo.getSuperAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth","  ( b.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or dl.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ba.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ba.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + ")" );
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth","  ( b.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or s.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or dl.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ba.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ba.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + ")" );
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","  ( b.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or s.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or dl.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ba.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ba.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + ")" );
        }
        list = this.exhibitService.listAllExhibitByLocker(map);
        count = this.exhibitService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
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
    @RequestMapping(value = "/listAllExhibitcod")
    @ResponseBody
    public Map<String, Object> listAllExhibitcod(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        Map<String, Object> map3 = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map3.put("dataAuth", " ( bcl.opener=" + ControllerTool.getUserID(request)
                    + " or bcl.op_user_id=" + ControllerTool.getUserID(request)
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
        List<ExhibitEntity> list = this.exhibitService.queryExhibitCabinetLlog(map3);
        List<ExhibitEntity> resultList = new ArrayList<ExhibitEntity>();
        for (ExhibitEntity exhibitEntity : list) {
            ExhibitEntity resultEntity = new ExhibitEntity();
            resultEntity.setLogId(exhibitEntity.getLogId());
            resultEntity.setLogBelongId(exhibitEntity.getLogBelongId());
            resultEntity.setLogBelongLockerId(exhibitEntity.getLogBelongLockerId());
            resultEntity.setLogBelongOpener(ControllerTool.getSessionInfo(request).getUser().getRealName());
            resultEntity.setLogBelongReason(exhibitEntity.getLogBelongReason());
            resultEntity.setLogCreatedTime(exhibitEntity.getLogCreatedTime());
            resultList.add(resultEntity);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.exhibitService.countyExhibitCabinetLog(map3));
        result.put("rows", resultList);
        return result;
    }

    /**
     * 查询嫌疑人存物历史
     *
     * @param pageMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllExhibitdet")
    @ResponseBody
    public Map<String, Object> listAllExhibitdet(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        if (pageMap.get("enpId") == null) {
            return null;
        }
        Map<String, Object> map2 = ControllerTool.mapFilter(pageMap);
        List<ExhibitEntity> list = this.exhibitService.listAllExhibitdet(map2);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map2.put("dataAuth", " ( d.get_person=" + ControllerTool.getUserID(request)
                    + " or d.op_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        }  else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map2.put("dataAuth","  ( d.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + ")" );
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map2.put("dataAuth","  ( d.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + ")" );
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map2.put("dataAuth","  ( d.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + ")" );
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.exhibitService.count1(map2));
        result.put("rows", list);
        return result;
    }

    /**
     * 获取图片
     */
    @RequestMapping(value = "/getImages")
    @ResponseBody
    public List<ExhibitPhotoEntity> getImages(HttpServletRequest request, Integer exhibitId) {
        // 根据exhibitId,获取照片集合
        List<ExhibitPhotoEntity> photos = exhibitService.selectPhotoByExhibitId(exhibitId);
        return photos;
    }

    /**
     * 查询开柜记录
     */
    @RequestMapping(value = "/openBoxLog")
    @ResponseBody
    public Map<String, Object> openBoxLog(HttpServletRequest request, @RequestParam Map<String, Object> pageMap) {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", openCabinetService.list(map));
        result.put("total", openCabinetService.count(map));
        return result;
    }
    /**
     * 增加涉案物品存储
     *
     * @param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/exhibitsaveApp")
    @ResponseBody
    public MessageEntity exhibitsaveApp(BelongAppForm form, HttpServletRequest request,
                                        HttpServletResponse response) {
        ExhibitEntity entity = new ExhibitEntity();
        try {
            serialService.updateStatusById((long)form.getSerialId(), 2);
            for (int i = 0;i<form.getList().size();i++) {
                entity.setId(entity.getId());
                entity.setSerialId((long)form.getSerialId());
                entity.setRegisterUserId(ControllerTool.getUserID(request));
                entity.setRegisterTime(new Date());
                entity.setCaseId(form.getCaseId());
                entity.setAreaId(form.getAreaId());
                entity.setLockerId(form.getLockerId().toString());
                entity.setIsGet(0);
                entity.setGetWay("0");
                entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
                entity.setCreatedTime(new Date());
                entity.setUpdatedTime(new Date());
                entity.setName(form.getList().get(i).getName());
                entity.setDetailCount(form.getList().get(i).getDetailCount());
                entity.setDescription(form.getList().get(i).getDescription());
                entity.setUnit(form.getList().get(i).getUnit());

                int result = exhibitService.insertExhibit(entity);
                if (result == 0) {// 判断柜子是否被占用，返回提示
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("失败").addContent("该柜已被占用,请选择其他柜!");
                }
            }
            String serialID = form.getSerialId().toString();
            SerialEntity seentity = new SerialEntity();
            entity.setId(form.getSerialId());
            seentity.setId(form.getSerialId().longValue());
            SerialEntity serial = serialService.getSerialByNo1(seentity);
            if(form.getFiles()!=null&&form.getFiles().length>0) {
                //循环获取file数组中得文件
                for (int i = 0; i < form.getFiles().length; i++) {
                    FileUploadForm form1 = new FileUploadForm();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    form1.setSysType("ba");
                    form1.setFileType("SA");
                    form1.setUuid(serial.getUuid());
                    form1.setSysId(form.getAreaId().toString());
                    form1.setFile(form.getFiles()[i]);
                    String filename = "exhibit-" + serial.getSerialNo() + "-" + new Random().nextInt(10000) + "-yt.jpg";
                    form1.setFileName(filename);
                    fileConfigService.upload(form1);
                    exhibitService.createxhibitphoto(serialID, filename,ControllerTool.getSessionInfo(request).getCurrentOrg().getPid(),ControllerTool.getSessionInfo(request).getUser().getId().toString());
                }
            }
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "新增涉案物品(存柜)" + form.getList(), ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on edit updateArea!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "新增涉案物品" + entity, "system", false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加成功!");
    }
}
