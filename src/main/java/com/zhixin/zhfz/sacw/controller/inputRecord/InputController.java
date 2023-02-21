package com.zhixin.zhfz.sacw.controller.inputRecord;

import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.*;
import com.zhixin.zhfz.sacw.form.InputForm;
import com.zhixin.zhfz.sacw.form.UserNoSearchForm;
import com.zhixin.zhfz.sacw.services.input.IInputService;
import com.zhixin.zhfz.sacw.services.inputRecord.IInputRecordService;
import com.zhixin.zhfz.sacw.services.involved.IinvolvedService;
import com.zhixin.zhfz.sacw.services.label.ILabelService;
import com.zhixin.zhfz.sacw.services.labellog.ILabelLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/sacw/inputStock")
public class InputController {

    private static final Logger logger = LoggerFactory.getLogger(InputController.class);

    @Autowired
    private IInputService service;

    @Autowired
    private IUserService useService;

    @Autowired
    private IinvolvedService involvedService;

    @Autowired
    private IOrganizationService OrganizationService;

    @Autowired
    private IInputRecordService inputRecordService;

    @Autowired
    private ILabelService labelService;


    @Autowired
    private ILabelLogService labelLogService;


    /**
     * 查所有及分页及条件查询
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/inputlist")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        List<InputEntity> list = new ArrayList<>();
        Map<String, Object> result = new HashMap<String, Object>();
        int count = 0;
        Map<String, Object> map = ControllerTool.mapFilter(param);
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", "(locate('," + ControllerTool.getUserID(request) + ",', ilc.xbmj_ids) or  ilc.zbmj_id="
                    + ControllerTool.getUserID(request) + " or l.register_user_id = " + ControllerTool.getUserID(request) + " or l.police_id1 = " + ControllerTool.getUserID(request) + ")");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " l.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " l.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " l.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", "t.p_id like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "t.p_id like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "t.p_id like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }

        list = service.list(map);
        count = service.count(map);
        result.put("total", count);
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/inputInsert")
    @ResponseBody
    public MessageEntity inputInsert(@RequestBody InputForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        logger.debug("InterrogateSerialForm=" + form);
        InputEntity entity = new InputEntity();
        try {
            entity.setInvolvedId(form.getInvolvedId());
            entity.setRegisterUserId(form.getRegisterUserId());
            entity.setShelfId(form.getShelfId());
            entity.setWarehouseId(form.getWarehouseId());
            entity.setLocationId(form.getLocationId());
            entity.setLawCaseId(form.getLawCaseId());
            entity.setInvolvedTypeId(form.getInvolvedTypeId());
            entity.setInputTypeId(form.getInputTypeId());
            entity.setPoliceId1(form.getPoliceId());
            entity.setLabelId(form.getLabelId());
            entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());

            //通过移交民警ID找到移交民警所在的单位
            List<OrganizationEntity> userOrganizationEntities =
                    OrganizationService.getOrgByUserId(form.getPoliceId());
            if (userOrganizationEntities != null && userOrganizationEntities.size() > 0) {
                entity.setOrganizationId(userOrganizationEntities.get(0).getId());
            }
            entity.setAreaId(form.getAreaId());
            service.insert(entity);

            //根据invovedId改变物品的状态
            int involvedId = entity.getInvolvedId();
            Map<String, Object> map = new HashMap<>();
            map.put("id", involvedId);
            map.put("status", 2);//已入库
            map.put("warehouseId", form.getWarehouseId());//仓库id更新
            map.put("labelId", form.getLabelId());//标签
            involvedService.updateStatus(map);

            try {
                //更新标签的状态
                LabelEntity labelEntity = new LabelEntity();
                labelEntity.setStatus(1);
                labelEntity.setId(form.getLabelId());
                labelService.update(labelEntity);

                //激活标签，进行定位检测
                //  RfidServerServiceImpl.activeLabelMap.put(Integer.parseInt(form.getLabelNo()), labelEntity);

                //插入标签发放的log
                LabelLogEntity labelLogEntity = new LabelLogEntity();
                labelLogEntity.setLabelId(form.getLabelId());
                labelLogEntity.setType(0);//绑定
                labelLogEntity.setDescription("入库操作，标签绑定，物品ID=" + form.getInvolvedId());
                labelLogService.insert(labelLogEntity);
            } catch (Exception e) {
                logger.info("更新标签状态异常：" + e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("服务器内部错误！");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("入库成功!").addCallbackData(entity.getId());
    }


    @RequestMapping(value = "/searchUser")
    @ResponseBody
    public MessageEntity searchUser(@RequestBody UserNoSearchForm form) throws Exception {
        UserEntity user = useService.getUserByCertificateNo(form.getUserNo());
        if (user != null) {
            return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒")
                    .addContent("警号" + form.getUserNo() + "有效!").addCallbackData(user.getId());
        } else {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误")
                    .addContent("警号" + form.getUserNo() + "不存在!");
        }
    }

    /**
     * 列表展示
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/listInputRecord")
    @ResponseBody
    public Map<String, Object> listInputRecord(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        logger.info("-----------------InputController.list-----------------");
        Map<String, Object> map = ControllerTool.mapFilter(params);
        int count = 0;
        boolean flag = true;
        List<InputRecordEntity> list = null;

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", "(locate('," + ControllerTool.getUserID(request) + ",', l.xbmj_ids) or  l.zbmj_id="
                    + ControllerTool.getUserID(request) + " or i.register_user_id = " + ControllerTool.getUserID(request) + ")");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " i.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " i.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " i.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", "( t.p_id like " + sessionInfo.getSuperAndSubOrgPid()
                + " or lb.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                + " or i.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                + " or l.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                + " or cd.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                + " or iw.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
            +" )");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "( t.p_id like  " + sessionInfo.getSuperAndSubOrgPid()
            + " or lb.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or i.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or l.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or cd.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or iw.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
            +" )");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "( t.p_id like " + sessionInfo.getCurrentAndSubOrgPid()
                +" or lb.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                +" or i.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                +" or l.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                +" or cd.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                +" or iw.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()

            +" )");
        } else {
            flag = false;
            // return null
        }
        if (flag) {
            list = inputRecordService.listInputRecord(map);
            if (list != null && list.size() > 0) {
                for (InputRecordEntity inputRecordEntity : list) {
                    Map<String, Object> mapParam = new HashMap<>();
                    mapParam.put("involvedId", inputRecordEntity.getInvolvedId());
                    List<InputRecordEntity> listRecord = inputRecordService.listLastRecord(mapParam);
                    if (listRecord != null && listRecord.size() > 0) {
                        inputRecordEntity.setId(listRecord.get(0).getId());
                        inputRecordEntity.setLocation(listRecord.get(0).getLocation());
                        inputRecordEntity.setDept(listRecord.get(0).getDept());
                    }
                }
            }

            count = inputRecordService.countInputRecord(map);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", list);
        return result;
    }

    /**
     * 获取入库图片
     */
    @RequestMapping(value = "/getImages")
    @ResponseBody
    public List<InRecordPhotoEntity> getImages(HttpServletRequest request) {
        List<String> result = new ArrayList<String>();
        Map<String, Object> map = new HashMap<>();
        List<InRecordPhotoEntity> photos = null;
        if (request.getParameter("invId") != null) {
            map.put("invId", request.getParameter("invId"));
            try {
                photos = inputRecordService.listInRecordPhotoById(map);
            } catch (Exception ex) {
            }

        }

        return photos;

    }
}
