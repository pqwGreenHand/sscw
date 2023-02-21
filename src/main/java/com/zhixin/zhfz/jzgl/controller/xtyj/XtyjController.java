package com.zhixin.zhfz.jzgl.controller.xtyj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.entity.XtyjEntity;
import com.zhixin.zhfz.jzgl.entity.XxEntity;
import com.zhixin.zhfz.jzgl.services.xtyj.IXtyjService;
import com.zhixin.zhfz.jzgl.services.xx.IXxService;


@Controller
@RequestMapping("/zhfz/jzgl/xtyj")
public class XtyjController {

	private static Logger logger = Logger.getLogger(XtyjController.class);

	@Autowired
	private IXtyjService xtyjService;
	
	@Autowired
	private IXxService xxService;
	
	
    /**
     * 预警管理列表
     * @param param
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/listXtyj")
	@ResponseBody
	public Map<String, Object> listXtyj(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		map.put("usePage", true);
		if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
		    String sql = " x.op_pid like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()+"";
		    map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
		    String sql = " x.op_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()+"";
		    map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			map.put("dataAuth", " x.op_user_id= " + ControllerTool.getUser(request).getId());
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本部门
			String sql = " x.op_pid="+ ControllerTool.getSessionInfo(request).getCurrentOrgPid();
			map.put("dataAuth", sql);
		} 
		List<XtyjEntity> list = this.xtyjService.listXtyj(map);
		int count = this.xtyjService.countXtyj(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", list);
		return result;
	}
	
	
	/**
	 * 催办
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addXx")
	@ResponseBody
	public MessageEntity addXx(@RequestBody XxEntity form, HttpServletRequest request,HttpServletResponse response) {
		try {	
			form.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
			form.setOpUserId( ControllerTool.getUser(request).getId());
			xxService.insertXx(form);
		} catch (Exception e) {
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("催办失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("催办成功!");
	}
	
}