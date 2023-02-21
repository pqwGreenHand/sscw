package com.zhixin.zhfz.jzgl.controller.jdrd;

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
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.entity.GjxxEntity;
import com.zhixin.zhfz.jzgl.entity.GjxxLogEntity;
import com.zhixin.zhfz.jzgl.entity.XxEntity;
import com.zhixin.zhfz.jzgl.services.jdrd.GjxxService;
import com.zhixin.zhfz.jzgl.services.xx.IXxService;

@Controller
@RequestMapping("/zhfz/jzgl/jdrd")
public class GjxxController {

	private static Logger logger = Logger.getLogger(GjxxController.class);

	@Autowired
	private GjxxService gjxxService;

	/**
	 * 监督认定列表
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listGjxx")
	@ResponseBody
	public Map<String, Object> listGjxx(@RequestParam Map<String, Object> param) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		map.put("usePage", true);
		List<GjxxEntity> list = this.gjxxService.listGjxx(map);
		int count = this.gjxxService.countGjxx(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", list);
		return result;
	}


	/**
	 * 认定，审批，取消，回复操作
	 * 
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addGjxxLog")
	@ResponseBody
	public MessageEntity addGjxxLog(@RequestBody GjxxLogEntity form, HttpServletRequest request,HttpServletResponse response) {
		try {
			form.setUserId(Long.valueOf(ControllerTool.getUserID(request)));
			gjxxService.insertGjxxLog(form);
			GjxxEntity entity = new GjxxEntity();
			entity.setId(form.getGjxxId());
			entity.setGjzt(form.getGjzt());			
			gjxxService.updateGjxx(entity);
		} catch (Exception e) {
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("操作失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("操作成功!");
	}
	
	/**
	 * 记录列表
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listGjxxLogById")
	@ResponseBody
	public Map<String, Object> listGjxxLogById(@RequestParam Map<String, Object> param) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		map.put("usePage", true);
		List<GjxxLogEntity> list = this.gjxxService.listGjxxLogById(map);
		int count = this.gjxxService.countGjxxLog(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", list);
		return result;
	}

}