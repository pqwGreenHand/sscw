package com.zhixin.zhfz.sacw.controller.remotelook;

import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.notice.IMyNoticeService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.Md5Util;
import com.zhixin.zhfz.common.utils.SMSSender;
import com.zhixin.zhfz.sacw.entity.InvolvedEntity;
import com.zhixin.zhfz.sacw.entity.RemoteLookEntity;
import com.zhixin.zhfz.sacw.form.RemoteLookForm;
import com.zhixin.zhfz.sacw.services.involved.IinvolvedService;
import com.zhixin.zhfz.sacw.services.remotelook.IRemoteLookService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 远程示证
 */
@Controller
@RequestMapping("/zhfz/sacw/remotelook")
public class RemoteLookController {

    private static final Logger logger = LoggerFactory.getLogger(RemoteLookController.class);

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IRemoteLookService service;

    @Autowired
    private IUserService userService;

    @Autowired
    private IMyNoticeService noticeService;

    @Autowired
    private ICaseService lawCaseService;

    @Autowired
    private IinvolvedService iinvolvedService;


    /**
     * 查所有及分页及条件查询
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        List<RemoteLookEntity> list = new ArrayList<>();
        Map<String, Object> result = new HashMap<String, Object>();
        int count = 0;

        Map<String, Object> map = ControllerTool.mapFilter(param);
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案人员-本人
            map.put("dataAuth", "(locate('," + ControllerTool.getUserID(request) + ",', ba.xbmj_ids) or  ba.zbmj_id="
                    + ControllerTool.getUserID(request) + " or r.register_user_id = " + ControllerTool.getUserID(request) + " or r.request_police_id = " + ControllerTool.getUserID(request) + ")");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " r.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " r.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " r.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 本部门
            map.put("dataAuth", "r.op_pid like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "r.op_pid like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "r.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }

        if (flag) {
            list = service.list(map);
            count = service.count(map);
        }
        result.put("total", count);
        result.put("rows", list);
        return result;
    }

    /**
     * 新增远程示证
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public MessageEntity add(@RequestBody RemoteLookForm form, HttpServletRequest request) {
        RemoteLookEntity entity = new RemoteLookEntity();
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            entity.setCaseId(form.getCaseId());
            entity.setInvolvedId(form.getInvolvedId());
            entity.setRequestPoliceId(form.getRequestPoliceId());
            entity.setFailDesc(form.getFailDesc());
            entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
            entity.setDescription(form.getDescription());
            if (form.getShowDate() != null && form.getShowDate() != "") {
                entity.setShowDate(sdf.parse(form.getShowDate()));
            }
            entity.setDescription(form.getDescription());
            entity.setRegisterUserId(ControllerTool.getUserID(request));
            //查询warehouseId仓库id
            InvolvedEntity involvedEntity = iinvolvedService.getInvolvedById(Long.parseLong(form.getInvolvedId().toString()));
            entity.setWarehouseId(involvedEntity.getWarehouseId());
            service.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加远程示证记录信息:" + entity, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");


     /*       //查询请求远程示证民进信息及请求物品
            Map<String, Object> param = new HashMap<>();
            param.put("id", entity.getId());
            List<RemoteLookEntity> list = service.queryRequestInvolved(param);
            RemoteLookEntity requestPolice = null;
            if (list != null && list.size() > 0) {
                requestPolice = list.get(0);
                *//* 添加通知和待办事项 *//*
                Map<String, Object> map = new HashMap<>();
                map.put("roleId", 9);
                List<UserEntity> users = userService.getUserByRoleId(map);
                String content = requestPolice.getOrganizationName() + "的民警：" + requestPolice.getRequestPoliceName()
                        + ",请求远程示证物品：" + requestPolice.getInvolvedName() + ",请审核！";
                if (users != null && users.size() > 0) {
                    for (int i = 0; i < users.size(); i++) {
                        *//* 添加待办事项 *//*

                        ScheduleEntity schedule = new ScheduleEntity();
                        schedule.setSenderId(ControllerTool.getSessionInfo(request).getUser().getId());
                        schedule.setReceiverId(users.get(i).getId());
                        schedule.setTitle("请求远程示证通知");
                        schedule.setContent(content);
                        ;
                        schedule.setType(1);//表示远程示证的通知
                        schedule.setStatus("0");//
                        noticeService.insertSchedule(schedule);

                        *//* 添加通知 *//*
                        InformEntity inform = new InformEntity();
                        inform.setSenderId(ControllerTool.getSessionInfo(request).getUser().getId());
                        inform.setReceiverId(users.get(i).getId());
                        inform.setTitle("请求远程示证通知");
                        inform.setContent(content);
                        inform.setType(1);//表示远程示证的通知
                        inform.setIsRead(0);
                        noticeService.insertInform(inform);

                        String mobile = "";
                        if (users.get(i).getMobile() != null && !"".equals(users.get(i).getMobile().trim())) {
                            mobile = users.get(i).getMobile();
                            try {
                                logger.info("###发送短信：手机号码【" + mobile + "】,内容【" + content + "】.");
                                SMSSender.sendSMS(new String[]{mobile}, content);
                            } catch (Exception ex) {
                                logger.info("发送短信异常：" + ex.getMessage());
                            }
                        }


                    }
                }
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加远程示证记录信息:" + entity, ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加远程示证记录失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加远程示证记录成功!");
    }

    /**
     * 根据id删除
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public MessageEntity delete(@RequestBody IDForm form, HttpServletRequest request) {
        try {
            service.delete(form.getIntID());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除远程示证记录信息:" + form.getId(), ControllerTool.getUser(request).getLoginName(), true, "涉案财物");

        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除远程示证记录信息:" + form.getId(), ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除远程示证记录失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除远程示证记录成功!");
    }

    /**
     * 根据id更新
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public MessageEntity update(@RequestBody RemoteLookForm form, HttpServletRequest request) {
        RemoteLookEntity entity = new RemoteLookEntity();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("id", form.getId());

            List<RemoteLookEntity> list = service.listById(map);
            if (list != null && list.size() > 0) {
                RemoteLookEntity remoteLookEntity = list.get(0);
                if (remoteLookEntity.getStatus() != 1) {
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("该申请已审批!");
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            entity.setId(form.getId());
            entity.setCaseId(form.getCaseId());
            entity.setInvolvedId(form.getInvolvedId());
            entity.setRequestPoliceId(form.getRequestPoliceId());
            if (form.getShowDate() != null && form.getShowDate() != "") {
                entity.setShowDate(sdf.parse(form.getShowDate()));
            }
            entity.setDescription(form.getDescription());
            entity.setRegisterUserId(ControllerTool.getUserID(request));
            InvolvedEntity involvedEntity = iinvolvedService.getInvolvedById(Long.parseLong(form.getInvolvedId().toString()));
            entity.setWarehouseId(involvedEntity.getWarehouseId());
            service.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "更新远程示证记录信息:" + entity, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "更新远程示证记录信息:" + entity, ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("更新远程示证记录失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("更新远程示证记录成功!");
    }


    /**
     * 根据id更新
     * 远程示证审核
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/audit")
    @ResponseBody
    public MessageEntity audit(@RequestBody RemoteLookForm form, HttpServletRequest request) {
        RemoteLookEntity entity = new RemoteLookEntity();
        try {
            if (form.getStatus() == 2) {
                //审核通过，产生随机密码
                entity.setRandomPasswd(Md5Util.getRandNum(1, 999999) + "");
            }
            entity.setId(form.getId());
            entity.setStatus(form.getStatus());
            entity.setFailDesc(form.getFailDesc());
            service.update(entity);


           /* //查询请求远程示证民进信息及请求物品
            Map<String, Object> param = new HashMap<>();
            param.put("id", entity.getId());
            List<RemoteLookEntity> list = service.queryRequestInvolved(param);
            RemoteLookEntity requestPolice = null;
            if (list != null && list.size() > 0) {
                requestPolice = list.get(0);


                String content = "您申请的远程示证物品：" + requestPolice.getInvolvedName() + ",审核" + (form.getStatus() == 2 ? ("通过,验证码是：" + entity.getRandomPasswd()) : "未通过") + ",请查看！";
                *//* 添加通知 *//*
                InformEntity inform = new InformEntity();
                inform.setSenderId(ControllerTool.getSessionInfo(request).getUser().getId());
                inform.setReceiverId(requestPolice.getRequestPoliceId());
                inform.setTitle("远程示证审核通知");
                inform.setContent(content);
                inform.setType(1);//表示远程示证的通知
                inform.setIsRead(1);
                noticeService.insertInform(inform);

                String mobile = "";
                if (requestPolice.getMobile() != null && !"".equals(requestPolice.getMobile().trim())) {
                    mobile = requestPolice.getMobile();
                    try {
                        logger.info("###发送短信：手机号码【" + mobile + "】,内容【" + content + "】.");
                        SMSSender.sendSMS(new String[]{mobile}, content);
                    } catch (Exception ex) {
                        logger.info("发送短信异常：" + ex.getMessage());
                    }
                }

            }*/

            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "审核远程示证记录信息:" + entity, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "审核远程示证记录信息:" + entity, ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("审核远程示证记录失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("审核远程示证记录成功!");
    }


}
