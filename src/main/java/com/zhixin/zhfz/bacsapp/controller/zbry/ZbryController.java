package com.zhixin.zhfz.bacsapp.controller.zbry;

import com.zhixin.zhfz.bacsapp.entity.ZbryMenuEntity;
import com.zhixin.zhfz.bacsapp.services.entrance.IEntranceService;
import com.zhixin.zhfz.bacsapp.services.zbry.IZbryService;
import com.zhixin.zhfz.bacsapp.vo.PageResponse;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacsapp/zbry")
public class ZbryController {

    @Autowired
    private IZbryService service;

    @ResponseBody
    @RequestMapping("/list")
    public PageResponse list(@RequestParam  Map<String,Object> params,HttpServletRequest request){
        Map<String, Object> map = ControllerTool.mapFilter(params);

        Map<String, Object> map1 = getAuthMethod(request);

        if ("true".equals(map1.get("flag"))){
            map.put("dataAuth",map1.get("dataAuth"));
        }
        return service.personPage(map);

    }

    public  Map<String,Object> getAuthMethod(HttpServletRequest request){

        Map<String,Object> map = new HashMap<String,Object>();

        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " (s.in_register_user_id=" + ControllerTool.getUserID(request) + " or s.out_register_user_id="
                    + ControllerTool.getUserID(request) + " ) ");
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " s.area_id=" + ControllerTool.getCurrentAreaID(request));
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "s.area_id=" + sessionInfo.getCurrentAndSubAreaInStr());
            map.put("flag","true");
        }
        else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " s.area_id=" + ControllerTool.getSessionInfo(request).getSuperAndSubAreaInStr());
            map.put("flag","true");
        }  else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth","s.op_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid());
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth","s.op_pid like " + ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid());
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","s.op_pid like " + sessionInfo.getCurrentOrgPid());
            map.put("flag","true");
        } else {
            map.put("flag","false");
        }

        return map;
    }

    @ResponseBody
    @RequestMapping("/menu")
    public ZbryMenuEntity menu(@RequestParam  Map<String,Object> params){
        return service.menu(params);
    }

}
