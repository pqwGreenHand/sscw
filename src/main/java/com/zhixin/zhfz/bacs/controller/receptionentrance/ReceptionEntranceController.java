package com.zhixin.zhfz.bacs.controller.receptionentrance;

import com.zhixin.zhfz.bacs.controller.policeEntrance.PoliceEntranceController;
import com.zhixin.zhfz.bacs.entity.ReceptionEntranceEntity;
import com.zhixin.zhfz.bacs.services.receptionentrance.IReceptionEntranceService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacs/receptionentrance")
public class ReceptionEntranceController {

    private static Logger logger = LoggerFactory.getLogger(PoliceEntranceController.class);

    @Autowired
    private IReceptionEntranceService receptionEntranceService;

    @RequestMapping(value = "/list2")
    @ResponseBody
    public Map<String,Object> list2(@RequestParam Map<String,Object> param, HttpServletRequest request){
        Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " s.op_user_id=" + ControllerTool.getUserID(request));
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " s.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " s.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " s.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," s.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," s.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," s.op_pid = " +  sessionInfo.getCurrentOrgPid());
        }
          List<ReceptionEntranceEntity> list = receptionEntranceService.list2(map);
          int count = receptionEntranceService.count(map);
          Map<String,Object> result = new HashMap<>();
          result.put("rows",list);
          result.put("total",count);
          return result;
    }

    @RequestMapping(value = "/receptionInsert")
    @ResponseBody
    public MessageEntity receptionInsert(@RequestBody ReceptionEntranceEntity data, HttpServletRequest request, HttpServletResponse response){
        try {
            data.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            data.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            receptionEntranceService.receptionInsert(data);
        } catch (Exception e) {
            logger.error("登记失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("登记失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("登记成功!");
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<ReceptionEntranceEntity> list(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = new HashMap<String, Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " re.op_user_id=" + ControllerTool.getUserID(request));
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " re.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " re.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " re.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," re.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," re.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," re.op_pid = " +  sessionInfo.getCurrentOrgPid());
        }
        return   receptionEntranceService.list(map);
    }


    // 出区
    @RequestMapping(value = "/updateOutTime")
    @ResponseBody
    public MessageEntity updateOutTime(HttpServletRequest request) {
        try {
            String id =  request.getParameter("receptionEntranceId");

            receptionEntranceService.updateOutTime(id);
        } catch (Exception e) {
            logger.error("出区失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("出区失败!");
        }

        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("出区成功!");
    }

    @RequestMapping(value = "/receptionHistoryList")
    @ResponseBody
    public Map<String,Object> receptionHistoryList(@RequestParam Map<String,Object> param,HttpServletRequest request){
        Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " re.op_user_id=" + ControllerTool.getUserID(request));
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " re.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " re.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " re.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," re.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," re.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," re.op_pid = " +  sessionInfo.getCurrentOrgPid());
        }
        List<ReceptionEntranceEntity> list = receptionEntranceService.receptionHistoryList(map);
        int count = receptionEntranceService.count2(map);
        Map<String,Object> result = new  HashMap<>();
        result.put("rows",list);
        result.put("total",count);
        return result;
    }

    @RequestMapping(value = "/findById")
    @ResponseBody
    public List<ReceptionEntranceEntity> findById(HttpServletRequest request){
        return receptionEntranceService.findById(request.getParameter("receptionEntranceId"));
    }

    @RequestMapping(value = "/updateById")
    @ResponseBody
    public MessageEntity updateById(@RequestBody ReceptionEntranceEntity data, HttpServletRequest request, HttpServletResponse response){
        try {
           receptionEntranceService.updateById(data);
        } catch (Exception e) {
            logger.error("修改失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("修改成功!");
    }

}
