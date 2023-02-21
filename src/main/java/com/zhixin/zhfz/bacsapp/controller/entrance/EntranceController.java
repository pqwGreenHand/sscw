package com.zhixin.zhfz.bacsapp.controller.entrance;

import com.zhixin.zhfz.bacs.controller.serial.SerialController;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.SerialForm;
import com.zhixin.zhfz.bacs.services.person.IPersonService;
import com.zhixin.zhfz.bacs.services.policeEntrance.IPoliceEntranceService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacsapp.entity.EntranceEntity;
import com.zhixin.zhfz.bacsapp.entity.InformationEntity;
import com.zhixin.zhfz.bacsapp.form.EntranceForm;
import com.zhixin.zhfz.bacsapp.services.Information.IInformationService;
import com.zhixin.zhfz.bacsapp.services.entrance.IEntranceService;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.services.notice.IMyNoticeService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: zhfz
 * @description: 入区登记
 * @author: cuicchengwei
 * @create: 2019-04-02
 **/
@Controller
@RequestMapping("/zhfz/bacsapp/entrance")
public class EntranceController {

    private static Logger logger = LoggerFactory.getLogger(EntranceController.class);

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private IEntranceService entranceService;

    @Autowired
    private ISerialService serialService;

    @Autowired
    private IPoliceEntranceService policeEntranceService;

    @Autowired
    private IFileConfigService fileConfigService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IInformationService iInformationService;

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IPersonService personService;

    @RequestMapping(value = "/entranceList")
    @ResponseBody
    public Map<String, Object> entranceList(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( a.send_user_id=" + ControllerTool.getUserID(request)
                    + " or a.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or a.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or bc.zbmj_id= "+ ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",', bc.xbmj_ids)"
                    + " or bc.cjr="+ ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " a.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " a.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        }
        else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " a.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( a.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or bc.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or bc.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( a.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or bc.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or bc.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( a.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or bc.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or bc.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<EntranceEntity> list = null;
        int total = 0;
        list = entranceService.entranceList(map);
        total = entranceService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }

    // 嫌疑人出区
    @RequestMapping(value = "/updateOutTime")
    @ResponseBody
    public MessageEntity updateOutTime(@RequestBody SerialForm form,HttpServletRequest request) {
        // 当前登录用户id
        Integer userId = ControllerTool.getSessionInfo(request).getUser().getId();
        try {
            SerialEntity serialEntity = new SerialEntity();
            serialEntity.setId(form.getId());
            serialEntity.setOutType(form.getOutType());
            serialEntity.setOutReason(form.getOutReason());
            serialEntity.setConfirmResult(form.getConfirmResult());
            serialEntity.setConfirmTime(new Date());
            serialEntity.setStatus(11);
            serialEntity.setOutRegisterUserId(ControllerTool.getUserID(request));
            this.serialService.updateSerialById(serialEntity);
            // 给主办民警添加通知
            UserEntity userInform = userService.getUserByID(form.getZbmjId());
            InformationEntity informationEntity = new InformationEntity();
            informationEntity.setSenderId(userId.longValue());
            informationEntity.setReceiverId(form.getZbmjId().longValue());
            informationEntity.setTitle("出区通知");
            informationEntity.setContent(userInform.getRealName()+"出区成功，出区时间"+df.format(new Date()));
            informationEntity.setSendTime(new Date());
            informationEntity.setIsRead(0);
            informationEntity.setSystemName("BaAPP");
            informationEntity.setAjmc(form.getAjmc());
            informationEntity.setAjbh(form.getAjbh());
            informationEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            informationEntity.setOpUserId(userId);
            this.iInformationService.insertInform(informationEntity);
        } catch (Exception e) {
            logger.error("出区失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("出区失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("出区成功!");
    }

    /**
     * 预约案件下拉列表
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/orderList")
    @ResponseBody
    public List<EntranceEntity> orderList(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            params.put("dataAuth", "( b.zbmj_id= "+ ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",', b.xbmj_ids)"
                    + " or b.cjr="+ ControllerTool.getUserID(request)
                    + " or a.order_user_id=" + ControllerTool.getUserID(request)
                    + " or a.noter_id=" + ControllerTool.getUserID(request)
                    +  " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            params.put("dataAuth", " a.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            params.put("dataAuth", " a.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        }
        else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            params.put("dataAuth", " a.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            params.put("dataAuth"," ( a.op_pid like " + sessionInfo.getCurrentAndSubOrgPid() +
                    " or b.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid() +
                    " or b.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            params.put("dataAuth"," ( a.op_pid like " + sessionInfo.getSuperAndSubOrgPid() +
                    " or b.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid() +
                    " or b.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            params.put("dataAuth"," ( a.op_pid = " + sessionInfo.getCurrentOrgPid() +
                    " or b.zbmj_pid = " + sessionInfo.getCurrentOrgPid() +
                    " or b.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<EntranceEntity> list = new ArrayList<EntranceEntity>();
        list = entranceService.orderListNew(params);
        return list;
    }

    /**
     * 人员姓名下拉列表
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/personList")
    @ResponseBody
    public List<EntranceEntity> personList(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<EntranceEntity> list = new ArrayList<EntranceEntity>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            params.put("dataAuth", " a.op_user_id="+ ControllerTool.getUserID(request));
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            params.put("dataAuth", " b.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            params.put("dataAuth", " b.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        }
        else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            params.put("dataAuth", " b.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            params.put("dataAuth"," a.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            params.put("dataAuth"," a.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            params.put("dataAuth"," a.op_pid = " + sessionInfo.getCurrentOrgPid());
        }
        list = entranceService.personList(params);
        return list;
    }


    @RequestMapping(value = "/add")
    @ResponseBody
    public MessageEntity add(EntranceForm form, HttpServletRequest request)throws Exception {
        logger.info("EntranceForm=" + form);
        // 当前登录用户id
        Integer userId = ControllerTool.getSessionInfo(request).getUser().getId();
        String uuid = java.util.UUID.randomUUID().toString();
        try {
            SerialEntity entity = new SerialEntity();
            // 根据时间生成编号
            String no = "QTR" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            entity.setSerialNo(no);
            entity.setUuid(uuid);
            entity.setCaseId(form.getCaseId());
            entity.setOrderId(form.getOrderRequestId());
            entity.setPersonId(Long.valueOf(form.getPersonId()));
            entity.setAreaId(ControllerTool.getSessionInfo(request).getCurrentArea().getId());
            entity.setInRegisterUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            entity.setInTime(df.parse(form.getInTime()));
            entity.setInPhotoId("rq-" + no + "-yt.jpg");
            entity.setEntranceProcedure(form.getEntranceProcedure());
            // 设置默认值
            entity.setType(0); //0嫌疑人
            entity.setStatus(0);//0已入区
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            // 嫌疑人新增
            serialService.insert(entity);
            // 上传嫌疑人照片
            fileConfigService.upload(FileUploadForm.of("ba", FileUploadForm.FILETYPRRQ, entity.getUuid(), entity.getAreaId(), entity.getInPhotoId(), form.getFile()));
            // 主办民警新增
            PoliceEntranceEntity zbmjEntity = new PoliceEntranceEntity();
            zbmjEntity.setCaseId(form.getCaseId());
            zbmjEntity.setAreaId(ControllerTool.getSessionInfo(request).getCurrentArea().getId());
            zbmjEntity.setPoliceId(Long.valueOf(form.getZbmjId()));
            zbmjEntity.setCuffId(0);
            zbmjEntity.setPoliceType(0);
            zbmjEntity.setInTime(df.parse(form.getInTime()));
            zbmjEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            zbmjEntity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            policeEntranceService.addPoliceEntrance(zbmjEntity);
            // 协办民警新增
            PoliceEntranceEntity xbmjEntity = new PoliceEntranceEntity();
            xbmjEntity.setCaseId(form.getCaseId());
            xbmjEntity.setAreaId(ControllerTool.getSessionInfo(request).getCurrentArea().getId());
            xbmjEntity.setPoliceId(Long.valueOf(form.getXbmjId()));
            xbmjEntity.setCuffId(0);
            xbmjEntity.setPoliceType(1);
            xbmjEntity.setInTime(df.parse(form.getInTime()));
            xbmjEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            xbmjEntity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            policeEntranceService.addPoliceEntrance(xbmjEntity);
            // 给主办民警添加通知
            UserEntity userInform = userService.getUserByID(form.getZbmjId());
            InformationEntity informationEntity = new InformationEntity();
            informationEntity.setSenderId(userId.longValue());
            informationEntity.setReceiverId(form.getZbmjId().longValue());
            informationEntity.setTitle("入区通知");
            informationEntity.setContent(userInform.getRealName()+"入区成功，入区时间"+df.format(zbmjEntity.getInTime()));
            informationEntity.setSendTime(new Date());
            informationEntity.setIsRead(0);
            informationEntity.setSystemName("BaAPP");
            informationEntity.setAjmc(form.getAjmc());
            informationEntity.setAjbh(form.getAjbh());
            informationEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            informationEntity.setOpUserId(userId);
            this.iInformationService.insertInform(informationEntity);
            // 操作日志
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "app入区登记信息" + entity,
                    ControllerTool.getSessionInfo(request).getUser().getRealName(), true, "app出入登记");
        } catch (Exception e) {
            logger.error("", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("保存失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("保存成功!");
    }

    @RequestMapping(value = "/updateEntrance")
    @ResponseBody
    public MessageEntity updateEntrance(EntranceForm form, HttpServletRequest request)throws Exception {
        logger.info("EntranceForm=" + form);
        try {

            SerialEntity entity = new SerialEntity();

            entity.setId(Long.valueOf(form.getSerialId()));
            entity.setEntranceProcedure(form.getEntranceProcedure());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            entity.setInTime(sdf.parse(form.getInTime()));
            serialService.updateAppSerialById(entity);
            PersonEntity personEntity = new PersonEntity();
            personEntity.setId(Long.valueOf(form.getPersonId()));
            personEntity.setCertificateNo(form.getCertificateNo());
            personEntity.setName(form.getPersonName());
            personEntity.setSex(form.getSex());
            personEntity.setCertificateTypeId(form.getCertificateTypeId());

            //修改人员信息
            personService.update(personEntity);

            // 上传嫌疑人照片
            fileConfigService.upload(FileUploadForm.of("ba", "xyrcrq", entity.getUuid(), entity.getAreaId(), entity.getInPhotoId(), form.getFile()));

            // 操作日志
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "app入区修改信息" + entity,
                    ControllerTool.getSessionInfo(request).getUser().getRealName(), true, "app出入登记");
        } catch (Exception e) {
            logger.error("", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("保存失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("保存成功!");
    }

}
