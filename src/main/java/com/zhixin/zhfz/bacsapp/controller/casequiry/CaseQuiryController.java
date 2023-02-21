package com.zhixin.zhfz.bacsapp.controller.casequiry;
import com.zhixin.zhfz.bacsapp.entity.CaseEntity;
import com.zhixin.zhfz.bacsapp.services.casequiry.ICasequiryService;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacsapp/casequiry")
public class CaseQuiryController {
    private static Logger logger = LoggerFactory.getLogger(CaseQuiryController.class);
    @Autowired
    private ICasequiryService casequiryService;

    @RequestMapping(value = "/caseList")
    @ResponseBody
    public Map<String, Object> entranceList(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        List<CaseEntity> list = null;
        int total = 0;

        Map<String, Object> map1 =getAuthMethod(request);

        if ("true".equals(map1.get("flag"))){
            map.put("dataAuth",map1.get("dataAuth"));
        }
        list = casequiryService.selectCase(map);
        total = casequiryService.selectCaseCount(map);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }

    @RequestMapping(value = "/getCaseById")
    @ResponseBody
    public CaseEntity getCaseById(@RequestParam Long caseId, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {

        Map<String, Object> map1 =getAuthMethod(request);

        CaseEntity caseEntities =null;

        map1.put("caseId",caseId);

        if ("true".equals(map1.get("flag"))){
            map1.put("dataAuth",map1.get("dataAuth"));
        }

        caseEntities = casequiryService.getCaseById(map1);

        return caseEntities;
    }


    public  Map<String,Object> getAuthMethod(HttpServletRequest request){

        Map<String,Object> map = new HashMap<String,Object>();

        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", "( bcase.zbmj_id="+ ControllerTool.getUserID(request) +" or locate('," + ControllerTool.getUserID(request) + ",', bcase.xbmj_ids)" +
                    " or bcase.cjr="+ ControllerTool.getUserID(request)+" ) ");
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth","bcase.op_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()
                    +" or bcase.zbmj_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid());
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth","bcase.op_pid like " + ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()
                    +" or bcase.zbmj_pid like " + ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid());
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","bcase.op_pid = " + sessionInfo.getCurrentOrgPid()
                    +" or bcase.zbmj_pid = " + sessionInfo.getCurrentOrgPid());
            map.put("flag","true");
        } else {
            map.put("flag","false");
        }

        return map;

    }


}
