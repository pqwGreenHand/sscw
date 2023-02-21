package com.zhixin.zhfz.bacs.controller.lawdoc;


import com.zhixin.zhfz.bacs.entity.InfoCollectionEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacs/lawdoc")
public class SerialLawDocController {

    @Autowired
    private ISerialService serialService;


    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String,Object> list(@RequestParam Map<String,Object> param, HttpServletRequest request){
        Map<String, Object> map = ControllerTool.mapFilter(param);
        Map<String,Object> result = new HashMap<>();
        List<SerialEntity> list = new ArrayList<SerialEntity>();
        int total = 0;
        boolean flag = true;
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " (  bs.in_register_user_id="
                    + ControllerTool.getUserID(request) + " or bs.out_register_user_id="
                    + ControllerTool.getUserID(request) + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " bs.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", ControllerTool.getAreasInSql("bs.area_id",
                    ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
            map.put("dataAuth",
                    " ( bs.in_register_user_id " + sql
                            + " or bs.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getSuperAndSubOrg());
            map.put("dataAuth",
                    " ( bs.in_register_user_id " + sql
                            + " or bs.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentOrgs());
            map.put("dataAuth",
                    " ( bs.in_register_user_id " + sql
                            + " or bs.out_register_user_id " + sql + " ) ");
        } else {
            flag = true;
        }

        if (flag) {
            list = this.serialService.listAllLawDoc(map);
            total = this.serialService.countAllLawDoc(map);
        }
        /*List<SerialEntity> list = serialService.listAllLawDoc(map);
        int count = serialService.countAllLawDoc(map);*/
        result.put("rows",list);
        result.put("total",total);
        return result;
    }
}
