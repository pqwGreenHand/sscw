package com.zhixin.zhfz.zhag.controller.xzajxx;

import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;
import com.zhixin.zhfz.zhag.services.xzajxx.AdministrationCaseService;
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

/**
 *行政案件统计
 */
@Controller
@RequestMapping("/zhfz/zhag/xzajxx")
public class AdministrationCaseController {
    @Autowired
    private AdministrationCaseService  administrationCaseService;

    // 查询历史案件
    @RequestMapping(value = "/listCase")
    @ResponseBody
    public Map<String, Object> listCase(@RequestParam Map<String, Object> params, HttpServletRequest request)
            throws Exception {
        System.out.println("+++++++++++++listCase+++++ Start+++++++++++++++");
        Map<String, Object> map = ControllerTool.mapFilter(params);
//        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " (c.cjr=" + ControllerTool.getUserID(request) + " or org.op_user_id=" + ControllerTool.getUserID(request)
                    + " or ct.op_user_id=" + ControllerTool.getUserID(request)
                    + " or xu.op_user_id =" + ControllerTool.getUserID(request)
                    + " or xbmln.op_user_id = " + ControllerTool.getUserID(request)
                    + " ) ");
        } /*else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " t.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "t.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        }
        else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", "t.area_id " + ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        }*/ else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth","( c.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    +" or org.p_id like " + sessionInfo.getCurrentAndSubOrgPid()
                    +" or ct.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    +" or xu.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    +" or xbmln.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    +")");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth","( c.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    +" or org.p_id like " + sessionInfo.getSuperAndSubOrgPid()
                    +" or ct.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    +" or xu.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    +" or xbmln.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    +")");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","( c.op_pid = " + sessionInfo.getCurrentOrgPid()
                    +" or org.p_id = " + sessionInfo.getCurrentOrgPid()
                    +" or ct.op_pid = " + sessionInfo.getCurrentOrgPid()
                    +" or xu.op_pid = " + sessionInfo.getCurrentOrgPid()
                    +" or xbmln.op_pid = "+ sessionInfo.getCurrentOrgPid()
                    +")");
        }
        List<CriminalAndAdministrationCaseEntity> datas = new ArrayList<CriminalAndAdministrationCaseEntity>();
        int count = 0;
        try {
            //当zbmjId为-1时代表初始化表格
            if(map.get("zbmjId") == null || map.get("zbmjId").equals("") || !map.get("zbmjId").equals("-1")){
                datas = administrationCaseService.listAdministrationCase(map);
                count = administrationCaseService.listAdministrationCount(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", datas);
        System.out.println("+++++++++++++result+++++ Start+++++++++++++++"+result);
        return result;
    }
}
