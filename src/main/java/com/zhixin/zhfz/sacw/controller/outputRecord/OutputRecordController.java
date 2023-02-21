package com.zhixin.zhfz.sacw.controller.outputRecord;


import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.*;
import com.zhixin.zhfz.sacw.services.inputRecord.IInputRecordService;
import com.zhixin.zhfz.sacw.services.involved.IinvolvedService;
import com.zhixin.zhfz.sacw.services.label.ILabelService;
import com.zhixin.zhfz.sacw.services.labellog.ILabelLogService;
import com.zhixin.zhfz.sacw.services.outputRecord.IOutputRecordService;
import com.zhixin.zhfz.sacw.services.outrecordphoto.IOutRecordPhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/zhfz/sacw/outputRecord")
public class OutputRecordController {

    private static final Logger logger = LoggerFactory.getLogger(OutputRecordController.class);


    @Autowired
    private IOutputRecordService outputRecordService;
    @Autowired
    private IInputRecordService inputRecordService;
    @Autowired
    private IinvolvedService iinvolvedService;
    @Autowired
    private IOutRecordPhotoService outRecordPhotoService;
    @Autowired
    private IOrganizationService OrganizationService;

    @Autowired
    private ILabelService labelService;

    @Autowired
    private ILabelLogService labelLogService;


    /**
     * 列表展示
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/listOutputRecord")
    @ResponseBody
    public Map<String, Object> listOutputRecord(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        logger.info("-----------------OutputRecordController.list-----------------");

        List<OutputRecordEntity> list = null;
        Map<String, Object> result = new HashMap<String, Object>();
        int count = 0;
        Map<String, Object> map = ControllerTool.mapFilter(params);
        boolean flag = true;

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", "(locate('," + ControllerTool.getUserID(request) + ",', cases.xbmj_ids) or  cases.zbmj_id="
                    + ControllerTool.getUserID(request) + " or ord.register_user_id = " + ControllerTool.getUserID(request) +" or ord.police_id = " + ControllerTool.getUserID(request)+ ")");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ord.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " ord.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ord.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", "d.p_id like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "d.p_id like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "d.p_id like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }

        if (flag) {
            list = outputRecordService.listOutputRecord(map);
            count = outputRecordService.countOutputRecord(map);
        }

        result.put("total", count);
        result.put("rows", list);
        return result;
    }

    /**
     * 移交检察院
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendJianchayuan")
    @ResponseBody
    public MessageEntity sendJianchayuan(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        logger.info("-----------------OutputRecordController.sendJianchayuan-----------------");
        OutputRecordEntity outputRecordEntity = new OutputRecordEntity();
        try {
            Long involvedId = Long.parseLong(params.get("involvedId").toString());
            String handPerson = params.get("handPerson").toString();
            int count = Integer.parseInt(params.get("count").toString());
            String description = params.get("description").toString();
            String telephone = params.get("telephone").toString();
            int policeId = Integer.parseInt(params.get("policeId").toString());
            int organizationId = Integer.parseInt(params.get("organizationId").toString());

            InvolvedEntity involvedEntity = iinvolvedService.getInvolvedById(involvedId);
            int restCount = Integer.parseInt(involvedEntity.getDetail_count()) - Integer.parseInt(involvedEntity.getOutputCount());


            if (count <= restCount) {
                //操作的入库记录
                InputRecordEntity inputRecordEntity = inputRecordService.getInputRecordById(Integer.parseInt(involvedId + ""));

                outputRecordEntity.setInvolvedId(inputRecordEntity.getInvolvedId());
                outputRecordEntity.setRegisterUserId(ControllerTool.getUserID(request));
                outputRecordEntity.setShelfId(inputRecordEntity.getShelfId());
                outputRecordEntity.setLocationId(inputRecordEntity.getLocationId());
                outputRecordEntity.setWarehouseId(inputRecordEntity.getWarehouseId());
                outputRecordEntity.setLawCaseId(inputRecordEntity.getLawCaseId());
                outputRecordEntity.setInvolvedTypeId(inputRecordEntity.getInvolvedTypeId());
                outputRecordEntity.setOutputTypeId(9);//移交检察院
                outputRecordEntity.setInterrogateAreaId(inputRecordEntity.getInterrogateAreaId());
                outputRecordEntity.setOrganizationId(organizationId);//嘉祥检察院
                outputRecordEntity.setPoliceId(policeId);
                outputRecordEntity.setDescription(description);
                outputRecordEntity.setCount(count);
                outputRecordEntity.setTelephone(telephone);
                outputRecordEntity.setHandlerPerson(handPerson);
                outputRecordEntity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                outputRecordEntity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
                //if(outputRecordService.getOutputRecordByInvolvedId(outputRecordEntity.getInvolvedId()).size()==0){
                outputRecordService.insertOutputRecord(outputRecordEntity);
//				}else{
//
//				}

                Map<String, Object> map = new HashMap<>();
                map.put("id", involvedId);
                map.put("outputCount", 0);
                //map.put("outputCount", Integer.parseInt(involvedEntity.getOutputCount())+count);
                if (count == restCount) {
                    map.put("status", 4);//与总数相等，变为已移交检察院
                } else {
                    //状态保持不变
                }
                iinvolvedService.updateStatus(map);
            } else {
                return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("移交数量大于剩余数量!");
            }
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("移交失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("移交成功!").addCallbackData(outputRecordEntity.getId());


    }

    /**
     * 出库
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/outputCommon")
    @ResponseBody
    public MessageEntity outputCommon(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        logger.info("-----------------OutputRecordController.outputCommon-----------------");
        OutputRecordEntity outputRecordEntity = new OutputRecordEntity();
        try {
            Long involvedId = Long.parseLong(params.get("involvedId").toString());
            int policeId = Integer.parseInt(params.get("policeId").toString());
            int count = Integer.parseInt(params.get("count").toString());
            int outputType = Integer.parseInt(params.get("outputType").toString());
            String description = params.get("description").toString();
            String telephone = params.get("telephone").toString();

            InvolvedEntity involvedEntity = iinvolvedService.getInvolvedById(involvedId);
            int restCount = Integer.parseInt(involvedEntity.getDetail_count()) - Integer.parseInt(involvedEntity.getOutputCount());

            if (count <= restCount) {
                //操作的入库记录
                InputRecordEntity inputRecordEntity = inputRecordService.getInputRecordById(Integer.parseInt(involvedId + ""));

                outputRecordEntity.setInvolvedId(inputRecordEntity.getInvolvedId());
                outputRecordEntity.setRegisterUserId(ControllerTool.getUserID(request));
                outputRecordEntity.setShelfId(inputRecordEntity.getShelfId());
                outputRecordEntity.setLocationId(inputRecordEntity.getLocationId());
                outputRecordEntity.setWarehouseId(inputRecordEntity.getWarehouseId());
                outputRecordEntity.setLawCaseId(inputRecordEntity.getLawCaseId());
                outputRecordEntity.setInvolvedTypeId(inputRecordEntity.getInvolvedTypeId());
                outputRecordEntity.setOutputTypeId(outputType);
                outputRecordEntity.setInterrogateAreaId(inputRecordEntity.getInterrogateAreaId());
                outputRecordEntity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                outputRecordEntity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());

                Collection<OrganizationEntity> organizationEntities = OrganizationService.getOrganizationByUserId(policeId);
                if (organizationEntities != null && organizationEntities.size() > 0) {
                    outputRecordEntity.setOrganizationId(organizationEntities.iterator().next().getId());
                }

                outputRecordEntity.setPoliceId(policeId);
                outputRecordEntity.setDescription(description);
                outputRecordEntity.setCount(count);
                outputRecordEntity.setTelephone(telephone);
                outputRecordService.insertOutputRecord(outputRecordEntity);

                Map<String, Object> map = new HashMap<>();
                map.put("id", involvedId);
                map.put("outputCount", Integer.parseInt(involvedEntity.getOutputCount()) + count);
                if (count == restCount && outputRecordEntity.getOutputTypeId() != 6) {
                    map.put("status", 3);//与总数相等，变为已出库
                } else {
                    if (outputRecordEntity.getOutputTypeId() == 6) {
                        map.put("outputCount", 0);
                        map.put("status", 6);//借出，临时出库
                    }
                    //状态保持不变
                }
                iinvolvedService.updateStatus(map);

                if (outputRecordEntity.getOutputTypeId() != 6) {
                    try {
                        //更新标签的状态
                        LabelEntity labelEntity = new LabelEntity();
                        labelEntity.setStatus(0);
                        labelEntity.setId(involvedEntity.getLabelId());
                        labelService.update(labelEntity);

                        //解除标签，取消定位检测
                        //		RfidServerServiceImpl.activeLabelMap.remove(Integer.parseInt(involvedEntity.getLabelNo()));

                        //插入标签发放的log
                        LabelLogEntity labelLogEntity = new LabelLogEntity();
                        labelLogEntity.setLabelId(involvedEntity.getLabelId());
                        labelLogEntity.setType(1);//解绑
                        labelLogEntity.setDescription("出库操作，标签解绑，物品ID=" + involvedEntity.getId());
                        labelLogService.insert(labelLogEntity);
                    } catch (Exception e) {
                        logger.info("更新标签状态异常：" + e.getMessage());
                        e.printStackTrace();
                    }
                }
            } else {
                return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("出库数量大于剩余数量!");
            }
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("出库失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("出库成功!").addCallbackData(outputRecordEntity.getId());


    }

    /**
     * 获取图片
     */
    @RequestMapping(value = "/getImages")
    @ResponseBody
    public List<OutRecordPhotoEntity> getImages(HttpServletRequest request) {
        List<String> result = new ArrayList<String>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", request.getParameter("id"));
        map.put("invId", request.getParameter("invId"));

        List<OutRecordPhotoEntity> photos = null;
        try {
            photos = outRecordPhotoService.listOutRecordPhotoById(map);
        } catch (Exception ex) {
        }


        return photos;

    }


}
