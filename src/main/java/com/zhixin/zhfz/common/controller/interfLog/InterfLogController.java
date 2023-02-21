package com.zhixin.zhfz.common.controller.interfLog;

import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.interfLog.IInterfLogService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/common/interfLog")
public class InterfLogController {

	private static final Logger logger = LoggerFactory.getLogger(InterfLogController.class);
	
	@Autowired
	private IInterfLogService interLogService;
	
	@Autowired
	private IOperLogService operLogService;
	
	@RequestMapping(value = "/listInterfaceLog")
	@ResponseBody
	public Map<String, Object> listInterfaceLog(@RequestParam Map<String, Object> params,HttpServletRequest request) throws Exception {
		int total=0;
		boolean flag = true;
		List<InterfLogEntity> datas=null;
		logger.info("-----------------InterfLogController.listInterfaceLog-----------------");
		Map<String, Object> map = ControllerTool.mapFilter(params);

		if (flag) {
			datas = this.interLogService.listInterfaceLog(map);
			total=this.interLogService.countInterfaceLog(map);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", datas);
		return result;
	}

	@RequestMapping(value = "/removeInterfaceLog")
	@ResponseBody
	public MessageEntity removeInterfaceLog(@RequestBody IDForm form, HttpServletRequest request) throws Exception {
		logger.info("++++++++remove++++++id=  " + form.getId());
		try {
			this.interLogService.deleteInterfaceLog(form.getLongID());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除接口日志" + form.getLongID(), ControllerTool.getUser(request).getLoginName(), true, "系统配置");
		} catch (Exception e) {
			logger.error("Error on deleting InterfaceLog!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除接口日志" + form.getLongID(), ControllerTool.getUser(request).getLoginName(), true, "系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败！");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("删除成功！");
	}

}
