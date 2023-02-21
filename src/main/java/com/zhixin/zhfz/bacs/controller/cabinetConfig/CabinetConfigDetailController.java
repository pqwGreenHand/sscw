package com.zhixin.zhfz.bacs.controller.cabinetConfig;

import com.zhixin.zhfz.bacs.entity.CabinetConfigDetailEntity;
import com.zhixin.zhfz.bacs.form.CabinetDetailConfigForm;
import com.zhixin.zhfz.bacs.services.cabinetConfig.ICabinetConfigDetailService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/zhfz/bacs/cabinetConfigDetail")
public class CabinetConfigDetailController {

	private static final Logger logger = LoggerFactory.getLogger(CabinetConfigDetailController.class);

	@Autowired
	private ICabinetConfigDetailService cabinetConfigDetailService;
	@Autowired
	private IOperLogService operLogService;
	/**
	 * 查所有及分页及条件查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String,Object> list(@RequestParam Map<String,Object> param)throws Exception{
		Map<String, Object> map = ControllerTool.mapFilter(param);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", cabinetConfigDetailService.count(map));
		result.put("rows", cabinetConfigDetailService.list(map));
		return result;
	}
	@RequestMapping(value = "/editDetail")
	@ResponseBody
	public MessageEntity edit(@RequestBody CabinetDetailConfigForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CabinetConfigDetailEntity entity = new CabinetConfigDetailEntity();
		entity.setId(form.getId());
		entity.setType(form.getType());
		try {
			cabinetConfigDetailService.update(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改柜子" + entity, ControllerTool.getLoginName(request), true, "办案场所");
		} catch (Exception e) {
			logger.error("Error on edit Organization!", e);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改柜子" + entity, ControllerTool.getLoginName(request), false, "办案场所");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改柜子失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("修改柜子成功!");
	}
}
