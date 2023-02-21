package com.zhixin.zhfz.glpt.controller.jzwgdc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.entity.ZjxxEntity;
import com.zhixin.zhfz.jzgl.services.zjwt.IZjxxService;

/**
 * 卷宗违规督查
 * @author xdp
 *
 */
@Controller
@RequestMapping("/zhfz/glpt/jzwgdc")
public class JzwgdcController {
	
	private static Logger logger = LoggerFactory.getLogger(JzwgdcController.class);
	

	@Autowired
	private IZjxxService zjxxService;

	@RequestMapping(value = "/listAlarm")
	@ResponseBody
	public Map<String, Object> listZjxx(@RequestParam Map<String, Object> param, HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			map.put("dataAuth", " ( zj.yh_id=" + ControllerTool.getUserID(request)
					+ " or zj.zrr_id=" + ControllerTool.getUserID(request)
					+ " or zj.op_user_id=" + ControllerTool.getUserID(request)
					+ " or jz.user_id=" + ControllerTool.getUserID(request)
					+ " or jz.zrr=" + ControllerTool.getUserID(request)
					+ " or jz.op_user_id=" + ControllerTool.getUserID(request)
					+ " or aj.cjr=" + ControllerTool.getUserID(request)
					+ " or aj.zbmj_id=" + ControllerTool.getUserID(request)
					+ " or locate('," + ControllerTool.getUserID(request) + ",',aj.xbmj_ids)"
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
			map.put("dataAuth","  ( zj.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
					+ " or jz.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
					+ " or aj.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
					+ " or aj.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
					+ ")" );
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 法制人员-上级部门及下级部门
			map.put("dataAuth","  ( zj.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
					+ " or jz.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
					+ " or aj.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
					+ " or aj.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
					+ ")" );
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 本部门
			map.put("dataAuth","  ( zj.op_pid =" + sessionInfo.getCurrentOrgPid()
					+ " or jz.op_pid =" + sessionInfo.getCurrentOrgPid()
					+ " or aj.zbmj_pid =" + sessionInfo.getCurrentOrgPid()
					+ " or aj.op_pid =" + sessionInfo.getCurrentOrgPid()
					+ ")" );
		}
		logger.info("参数==="+map);
		map.put("usePage", true);
		List<ZjxxEntity> datas = this.zjxxService.listZjxx(map);
		int count = this.zjxxService.countZjxx(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", datas);
		return result;
	}
	
    
	
}
