package com.zhixin.zhfz.common.controller.operLog;

import com.zhixin.zhfz.bacs.entity.TdOperLogEntity;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.form.IDForm;
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
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/common/operLog")
public class OperLogController {

	private static final Logger logger = LoggerFactory.getLogger(OperLogController.class);

	@Autowired
	private IOperLogService operLogService;

	/**
	 * 批量处理
	 *
	 * @param(111,222,333)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchClByIds")
	@ResponseBody
	public MessageEntity batchConfirmByIds(@RequestParam Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			logger.info("嫌疑人批量处理params=="+params);
			Map<String, Object> map = ControllerTool.mapFilter(params);
//			map.put("ids",map.get("ids[]"));
			operLogService.batchClByIds(map);
			return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("批量操作成功!");
		}catch (Exception e){
			e.printStackTrace();
			return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("批量操作失败!").addCallbackData(e.getMessage());
		}
	}

	@RequestMapping(value = "/listTd")
	@ResponseBody
	public Map<String, Object> listTd(@RequestParam Map<String, Object> params,HttpServletRequest request) throws Exception {
		int total=0;
		List<TdOperLogEntity> datas=new ArrayList<TdOperLogEntity>();
		Map<String, Object> map = ControllerTool.mapFilter(params);
		boolean flag = true;

		if (flag) {
			datas = this.operLogService.listTd(map);
			total=this.operLogService.countTd(map);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", datas);
		return result;
	}


	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam Map<String, Object> params,HttpServletRequest request) throws Exception {
		int total=0;
		List<OperLogEntity> datas=new ArrayList<OperLogEntity>();
		Map<String, Object> map = ControllerTool.mapFilter(params);
		boolean flag = true;

		if (flag) {
			datas = this.operLogService.list(map);
			total=this.operLogService.count(map);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", datas);
		return result;
	}
	@RequestMapping(value = "/remove")
	@ResponseBody
	public MessageEntity remove(@RequestBody IDForm form, HttpServletRequest request) {
		logger.info("++++++++remove++++++id=  " + form.getId());
		try {
			this.operLogService.deleteLog(form.getLongID());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE,"删除操作日志" + form.getLongID(), "system", true,"日志操作");
		} catch (Exception e) {
			logger.error("Error on deleting operlog!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE,"删除操作日志" + form.getLongID(), "system", false,"日志操作");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败！");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("删除成功！");
	}
	
}
