package com.zhixin.zhfz.glpt.controller.policePatrol;

import com.zhixin.zhfz.bacs.entity.ExhibitEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.glpt.services.policePatrol.PolicePatrolService;
import com.zhixin.zhfz.jxkp.entity.EvaluationEntity;
import com.zhixin.zhfz.jzgl.entity.JzxxEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/zhfz/glpt/policePatrol")
public class PolicePatrolController {

    private static Logger logger = LoggerFactory.getLogger(PolicePatrolController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private PolicePatrolService policePatrolService;
    /**
     * 民警信息列表
     *
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/listPolice")
    @ResponseBody
    public Map<String, Object> listPolice(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( u.op_user_id=" + ControllerTool.getUserID(request)
                    + " or org.op_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( u.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or org.p_id like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( u.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or org.p_id like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( u.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or org.p_id = " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        Collection<UserEntity> users = new ArrayList<UserEntity>();
        map.put("dataAuth1","u.type is not null");
        users = this.userService.getUserInfo(map);
        int count = this.userService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", users);
        return result;
    }
    /**
     * 关联嫌疑人信息列表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/listPerson")
    @ResponseBody
    public Map<String, Object> listPerson(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        logger.info("++++++++++++++++++++++++++++++++++++listPerson："+pageMap.toString());
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        List<SerialEntity> users = new ArrayList<SerialEntity>();
        Map<String, Object> result = new HashMap<String, Object>();
        int count = 0;
        String policeId = (String)pageMap.get("policeId");
        try{
            //-1的时候为初始化界面
            if(policeId != null && !policeId.equals("-1")){
                users = this.policePatrolService.listPerson(map);
                count = this.policePatrolService.listPersonCount(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("total", count);
        result.put("rows", users);
        return result;
    }

    /**
     * 关联涉案物品列表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/listExhibit")
    @ResponseBody
    public Map<String, Object> listExhibit(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        logger.info("++++++++++++++++++++++++++++++++++++listPerson："+pageMap.toString());
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        List<ExhibitEntity> exhibitEntities = new ArrayList<ExhibitEntity>();
        Map<String, Object> result = new HashMap<String, Object>();
        int count = 0;
        String policeId = (String)pageMap.get("policeId");
        try{
            //-1的时候为初始化界面
            if(policeId != null && !policeId.equals("-1")){
                exhibitEntities = this.policePatrolService.listExhibit(map);
                count = this.policePatrolService.listExhibitCount(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("total", count);
        result.put("rows", exhibitEntities);
        return result;
    }
    @RequestMapping(value = "/getJzxxAndAjxx")
    @ResponseBody
    public Map<String, Object> getJzxxAndAjxx(@RequestParam Map<String,Object> param) throws Exception{
        Map<String, Object> map = ControllerTool.mapFilter(param);
        System.err.println("====------map-----------=========="+map);
        map.put("usePage", true);
        List<JzxxEntity> jzxxList = new ArrayList<JzxxEntity>();
        int count = 0;
        String policeId = (String)param.get("policeId");
        //-1的时候为初始化界面
        if(policeId != null && !policeId.equals("-1")){
            jzxxList = this.policePatrolService.getJzxxAndAjxx(map);
            count = this.policePatrolService.countJzxxAndAjxx(map);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", jzxxList);
        return result;
    }

    @RequestMapping(value = "/listEvaluation")
    @ResponseBody
    public Map<String, Object> listEvaluation(@RequestParam Map<String,Object> param) throws Exception{
        Map<String, Object> map = ControllerTool.mapFilter(param);
        System.err.println("====------map-----------=========="+map);
        List<EvaluationEntity> list = new ArrayList<EvaluationEntity>();
        //-1的时候为初始化界面
        int count = 0;
        String policeId = (String)param.get("policeId");
        if(policeId != null && !policeId.equals("-1")) {
            list = policePatrolService.selectAllEvaluation(map);
            for (int i = 0; i < list.size(); i++) {
                String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getHandleTime());
                String dateStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getExamineTime());
                list.get(i).setHandleTime2(dateStr);
                list.get(i).setExamineTime2(dateStr2);
            }
            count =  policePatrolService.selectAllEvaluationCount(map);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total",count);
        result.put("rows", list);
        return result;
    }
}
