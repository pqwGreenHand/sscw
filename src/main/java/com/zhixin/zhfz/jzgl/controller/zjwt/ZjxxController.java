package com.zhixin.zhfz.jzgl.controller.zjwt;

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
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.entity.ZjxxEntity;
import com.zhixin.zhfz.jzgl.services.zjwt.IZjxxService;

/**
 * 
 * @author cuichengwei
 */
@Controller
@RequestMapping("/zhfz/jzgl/zjwt")
public class ZjxxController {

	private static Logger logger = Logger.getLogger(ZjxxController.class);

	@Autowired
	private IZjxxService zjxxService;

	@RequestMapping(value = "/listZjxx")
	@ResponseBody
	public Map<String, Object> listZjxx(@RequestParam Map<String, Object> param, HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		map.put("usePage", true);
		if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
		    String sql = " (zj.op_pid like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()+""
		               + " or yh.organization_id "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgInStr()
		               + " or zrr.organization_id "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgInStr()+")";
		    
		    map.put("dataAuth", sql);
		}
		
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
		    String sql = " (zj.op_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()+""
		               + " or yh.organization_id "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgInStr()
		               + " or zrr.organization_id "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgInStr()+")";
		    
		    map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			String sql = " (zj.op_user_id=" + ControllerTool.getUserID(request)
					  + " or zj.yh_id="+ ControllerTool.getUserID(request)
					  + " or zj.zrr_id="+ControllerTool.getUserID(request)+")";
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本部门
			String sql = " (zj.op_pid="+ ControllerTool.getSessionInfo(request).getCurrentOrgPid()
			           + " or yh.organization_id="+ControllerTool.getCurrentOrgID(request)
			           +"  or zrr.organization_id="+ControllerTool.getCurrentOrgID(request)+")";
			map.put("dataAuth", sql);
		} 
		 
		List<ZjxxEntity> datas = this.zjxxService.listZjxx(map);
		int count = this.zjxxService.countZjxx(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", datas);
		return result;
	}
	
	/**
	 * 自检问题新增
	 * 
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addZjxx")
	@ResponseBody
	public MessageEntity addZjxx(@RequestBody ZjxxEntity form, HttpServletRequest request,HttpServletResponse response) {
		try {
			form.setOpUserId(ControllerTool.getUserID(request));
			form.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
			zjxxService.insertZjxx(form);
		} catch (Exception e) {
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("保存失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("保存成功!");
	}
	
	@RequestMapping(value = "/queryJzxx")
	@ResponseBody
	public List<Map<String, String>> queryJzxx(@RequestParam Map<String,Object> param,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		
		if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 上级部门及其下级部门
		    String sql = "(j.op_pid like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()
					+" or EXISTS (SELECT 1 FROM  xt_user xu WHERE  j.user_id=xu.id AND xu.organization_id "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgInStr()+")"
					+" or EXISTS (SELECT 1 FROM  xt_user xu WHERE  j.zrr=xu.id AND xu.organization_id "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgInStr()+")"
					+" )";
		    map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
		    String sql = "(j.op_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()
					+" or EXISTS (SELECT 1 FROM  xt_user xu WHERE  j.user_id=xu.id AND xu.organization_id "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgInStr()+")"
					+" or EXISTS (SELECT 1 FROM  xt_user xu WHERE  j.zrr=xu.id AND xu.organization_id "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgInStr()+")"
					+" )";
		    map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			String sql = "( j.user_id="+ControllerTool.getUserID(request)
			+" or j.zrr="+ControllerTool.getUserID(request)
			+" or j.op_user_id="+ControllerTool.getUserID(request)
			+" or EXISTS(SELECT 1 FROM  jz_jzxx_police jjp WHERE  j.id=jjp.jzxx_id  AND jjp.user_id="+ControllerTool.getUserID(request)+"))";
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本部门
			String sql = "(j.op_pid="+ControllerTool.getSessionInfo(request).getCurrentOrgPid()
					+" or EXISTS (SELECT 1 FROM  xt_user xu WHERE  j.user_id=xu.id AND xu.organization_id="+ControllerTool.getCurrentOrgID(request)+")"
					+" or EXISTS (SELECT 1 FROM  xt_user xu WHERE  j.zrr=xu.id AND xu.organization_id="+ControllerTool.getCurrentOrgID(request)+")"
					+" )";
			map.put("dataAuth", sql);
		}
		return zjxxService.queryJzxx(map);
	}
}