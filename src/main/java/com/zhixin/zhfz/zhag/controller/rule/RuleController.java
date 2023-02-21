package com.zhixin.zhfz.zhag.controller.rule;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.services.role.IRoleService;
import com.zhixin.zhfz.zhag.entity.AbstractTree;
import com.zhixin.zhfz.zhag.entity.RuleInfoEntity;
import com.zhixin.zhfz.zhag.form.RuleInfoForm;
import com.zhixin.zhfz.zhag.services.rule.IAgRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/zhfz/zhag/rule")
public class RuleController {

	private static final Logger logger = LoggerFactory.getLogger(RuleController.class);
	@Autowired
	private IAgRuleService ruleService;
	@Autowired
	private IRoleService roleService;
	/**
	 * 列表展示
	 * @param params
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public List<AbstractTree> list(@RequestParam Map<String, Object> params,HttpServletResponse response,HttpServletRequest request) {
//		Map<String, Object> result= new HashMap<String, Object>();
		
		List<AbstractTree> list = ruleService.listAllRule();
		for(AbstractTree obj : list){
			System.out.println("------list-------"+obj.getId());	
		}
		response.setCharacterEncoding("UTF-8");
	
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			Writer writer = response.getWriter();
//			JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(writer);
//			jsonGenerator.writeObject(list);
//			jsonGenerator.flush();
//			jsonGenerator.close();
//			writer.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return list;
		
//		JSONObject  result = new JSONObject();
//		//查询父节点
//		List<RuleInfoEntity> list = ruleService.listAllRule();
//		System.err.println(list);
//		for (RuleInfoEntity ruleEntity : list) {
//			System.out.println(ruleEntity);
//			result.put("text", ruleEntity.getRuleName());
//			result.put("isFule",0);
//			result.put("children", CreateChildTree(ruleEntity.getId()));
//			
//		}
//		System.err.println(result);
	
//		return result;
	}
	//查询规则name
	@RequestMapping(value = "/listAllRule")
	@ResponseBody
	public List<RuleInfoEntity> listAllRule(@RequestParam Map<String, Object> params, HttpServletResponse response, HttpServletRequest request) {
		List<RuleInfoEntity> list = ruleService.listRule(params);
		return list;
	}
	//查询rolename
	@RequestMapping(value = "/listAllRole")
	@ResponseBody
	public Collection<RoleEntity> listAllRole(@RequestParam Map<String, Object> params, HttpServletResponse response, HttpServletRequest request) {
		RoleEntity  entity = new RoleEntity();
		entity.setId(999);
		entity.setName("本人");
		Collection<RoleEntity> list=new ArrayList<RoleEntity>();
		try{
			list = roleService.getAllRoles();
			list.add(entity);
		}catch (Exception ex){

		}
		System.err.println("list----------"+list);
		return list;
	}

	@RequestMapping(value="/editTXContent")
	@ResponseBody
	public MessageEntity editTXContent(@RequestBody RuleInfoForm form, HttpServletRequest request){
		try{
			Map<String ,Object> map = new HashMap<>();
			map.put("editId",form.getId());
			map.put("editContent",form.getTxnr());
            ruleService.editTXContent(map);
		}
		catch (Exception e){
                return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("修改成功!");
	}
}
