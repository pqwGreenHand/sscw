package com.zhixin.zhfz.jzgl.controller.warnInfo;

import java.util.ArrayList;
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
import com.zhixin.zhfz.jzgl.entity.RuleConfigEntity;
import com.zhixin.zhfz.jzgl.entity.RuleInfoEntity;
import com.zhixin.zhfz.jzgl.entity.RuleTree;
import com.zhixin.zhfz.jzgl.services.warnInfo.RuleConfigServiceImpl;
import com.zhixin.zhfz.jzgl.services.warnInfo.RuleInfoServiceImpl;

@Controller
@RequestMapping("/zhfz/jzgl/warnInfo")
public class WarnConfigController {

	private static Logger logger = Logger.getLogger(WarnConfigController.class);

	@Autowired
	private RuleInfoServiceImpl ruleInfoService = null;

	@Autowired
	private RuleConfigServiceImpl ruleConfigService = null;

	@RequestMapping(value = "/addRuleConfig")
	@ResponseBody
	public MessageEntity addRuleConfig(@RequestBody RuleConfigEntity form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String sendRoleNames = "";
			String str = form.getSendRoleIds();
			if (str != null && !"".equals(str.trim())) {
				String[] s = str.split(",");
				for (int i = 0; i < s.length; i++) {
					if ("1".equals(s[i])) {
						sendRoleNames += "民警" + ",";
					} else if ("2".equals(s[i])) {
						sendRoleNames += "卷宗管理员" + ",";
					} else if ("3".equals(s[i])) {
						sendRoleNames += "所属领导" + ",";
					}
				}
				sendRoleNames = sendRoleNames.substring(0, sendRoleNames.length() - 1);
				form.setSendRoleNames(sendRoleNames);
				System.out.println(form.toString());
				form.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
				form.setOpUserId(ControllerTool.getUserID(request));
				ruleConfigService.insertRuleConfig(form);
			}
		} catch (Exception e) {
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("保存失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("保存成功!");
	}

	@RequestMapping(value = "/getRuleConfig")
	@ResponseBody
	public Map<String, Object> getRuleConfigByRuleId(@RequestParam Map<String, Object> param,  HttpServletRequest request) throws Exception {
		
		if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJzglConf(request))) {
			// 公安领导-本部门及下级部门
		    String sql = "  op_pid like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid();
		    param.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJzglConf(request))) {
			// 公安领导-本部门及下级部门
		    String sql = "  op_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid();
		    param.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJzglConf(request))) {
			// 办案人员-本人
			param.put("dataAuth", " op_user_id= " + ControllerTool.getUser(request).getId());
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJzglConf(request))) {
			// 办案人员-本部门
			String sql = " op_pid= "+ControllerTool.getSessionInfo(request).getCurrentOrgPid();
			param.put("dataAuth", sql);
		}
		
		List<RuleConfigEntity> ruleConfigs = this.ruleConfigService.getRuleConfigByRuleId(param);
		int count = this.ruleConfigService.countRuleConfig(param);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", ruleConfigs);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/getRuleTree")
	public List<RuleTree> getRuleTree() throws Exception {
		List<RuleTree> treeList = new ArrayList<RuleTree>();
		List<RuleTree> treeList2 = new ArrayList<RuleTree>();
		// 所有根节点
		List<RuleInfoEntity> allRoot = ruleInfoService.getRuleAllRoot();
		// 遍历所有根节点
		for (RuleInfoEntity root : allRoot) {
			RuleTree ruleTree = new RuleTree();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parentRuleId", root.getId());
			// 每一个根节点下的所有孩子节点
			List<RuleInfoEntity> ruleChildren = ruleInfoService.getRuleChildrenByRuleId(map);
			// 遍历每一个根节点下的所有孩子节点
			for (RuleInfoEntity children : ruleChildren) {
				RuleTree ruleTree2 = new RuleTree();
				ruleTree2.setId(children.getId());
				ruleTree2.setText(children.getRuleName());
				treeList2.add(ruleTree2);
			}
			ruleTree.setId(root.getId());
			ruleTree.setText(root.getRuleName());
			ruleTree.setChildren(treeList2);
			treeList.add(ruleTree);
		}
		System.out.println(treeList);
		return treeList;
	}

}