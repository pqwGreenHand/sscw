package com.zhixin.zhfz.glpt.controller.involvedPatrol;


import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.ConsoleDeskEntity;
import com.zhixin.zhfz.sacw.entity.OutRecordPhotoEntity;
import com.zhixin.zhfz.sacw.services.consoledesk.IConsoleDeskService;
import com.zhixin.zhfz.sacw.services.involved.IinvolvedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/zhfz/glpt/involvedPatrol")
public class involvedPatrolController {

	private static final Logger logger = LoggerFactory.getLogger(involvedPatrolController.class);

	@Autowired
	private IOperLogService operLogService;

	@Autowired
	private IConsoleDeskService service;
	@Autowired
	private IinvolvedService involvedService;

	/**
	 *
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/listInvolved")
	@ResponseBody
	public Map<String,Object> listInvolvedDetail(@RequestParam Map<String,Object> param, HttpServletRequest request) throws Exception{
        List<ConsoleDeskEntity> list =new ArrayList<>();
        Map<String, Object> result = new HashMap<String, Object>();
        int count=0;
        boolean flag = true;
        //map.put("usePage", true);
        Map<String, Object> map = ControllerTool.mapFilter(param);
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
			// 办案人员-本人
            map.put("dataAuth", " ( a.register_user_id=" + ControllerTool.getUserID(request)
                    + " or l.cjr=" + ControllerTool.getUserID(request)
                    + " or l.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",',l.xbmj_ids) )" );
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
			// 公安领导-本部门及下级部门
			map.put("dataAuth","  ( a.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or l.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or l.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + ")" );
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForINV(request))) {
			// 法制人员-上级部门及下级部门
            map.put("dataAuth","  ( a.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or l.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or l.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + ")" );
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
			// 本部门
            map.put("dataAuth","  ( a.op_pid =" + sessionInfo.getCurrentOrgPid()
                    + " or l.zbmj_pid =" + sessionInfo.getCurrentOrgPid()
                    + " or l.op_pid =" + sessionInfo.getCurrentOrgPid()
                    + ")" );
		}else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
			// 全部
			map.put("dataAuth","" );
		}else {
			flag = false;
		}

        if (flag) {
            list = service.listInvolve(map);
            count = service.countInvolve(map);
        }
        result.put("total", count);
        result.put("rows", list);
        return result;
		}

	@RequestMapping("/getRecordByTime")
	@ResponseBody
	public Map<String, Object> getRecordByTime(@RequestParam Map<String, Object> param) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = ControllerTool.mapFilter(param);
		result.put("rows", involvedService.getRecordByTime(map));
		return result;
	}


}
