package com.zhixin.zhfz.common.controller.monitor;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.MonitorEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.form.MonitorForm;
import com.zhixin.zhfz.common.services.monitor.IMonitorService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.MonitorTool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/common/monitor")
public class MonitorController {

	private static Logger logger = Logger.getLogger(MonitorController.class);
	@Autowired
	private IMonitorService monitorService;
	
	@Autowired
	private IOperLogService operLogService;
	
	@RequestMapping("/monitor")
	@ResponseBody
	public Map<String,Object> list(@RequestParam Map<String,Object> params,HttpServletRequest request){
		
		Map<String, Object> param = ControllerTool.mapFilter(params);
		logger.info("------monitor list param-----"+param);
		List<MonitorEntity> list = monitorService.list(param);
		if(list != null && list.size()>0){
			for(MonitorEntity ee : list){
				if(ee != null){
					String mType = ee.getMonitorType();//监控类型 web、ftp、mysql、oracle、host
					String ip = ee.getIp();
					String mWay = ee.getMonitorWay();
					boolean f = isOnline(mType, ip, mWay);
					int currStatus = (f?1:0);
	            	Map<String,Object> map = new HashMap<String,Object>();
	            	map.put("id", ee.getId());
	            	map.put("status", currStatus);
	            	Date updateTime = new Date();
	            	map.put("updateTime", updateTime);
	            	monitorService.update(map);
	            	ee.setStatus(currStatus);
	            	ee.setUpdatedTime(updateTime);
	            	operLogService.insertLog(OperLogEntity.EDIT_TYPE, "系统自监控:id="+ee.getId(), "system", f, "系统");
				}
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", list);
		return resultMap;
	}
	
	
	@RequestMapping("/checkajax")
	@ResponseBody
	public Map<String, Object> check(@RequestParam int id){
		logger.info("-----checkajax-id-----"+id);
		Map<String ,Object> result = null;
		MonitorEntity ee = monitorService.queryById(id);
		if(ee != null){
			String mType = ee.getMonitorType();//监控类型 web、ftp、mysql、oracle、host
			String ip = ee.getIp();
			String mWay = ee.getMonitorWay();
			boolean f = isOnline(mType, ip, mWay);
			int currStatus = (f?1:0);
        	Map<String,Object> param = new HashMap<String,Object>();
        	param.put("id", id);
        	param.put("status", currStatus);
        	Date updateTime = new Date();
        	param.put("updateTime", updateTime);
        	monitorService.update(param);
        	operLogService.insertLog(OperLogEntity.EDIT_TYPE, "系统自监控:id="+ee.getId(), "system", f, "系统");
        	result = new HashMap<String,Object>();
        	result.put("status", currStatus);
        	result.put("updateTime", updateTime);
		}
		return result;
	}
	
	private boolean isOnline(String type, String ip, String param){
		//监控方式 url、ftp用户名密码以空格分割等、数据库用户名密码连接字符串以空格分割
		boolean f = false;
		if("web".equals(type)){
			f = MonitorTool.isHttpOnline(param);
		}
		else if("ftp".equals(type)){
			String[] arr = param.split(" ");
			f = MonitorTool.isFTPOnline(ip, arr[0], arr[1]);
		}
        else if("mysql".equals(type)){
        	String[] arr = param.split(" ");
			f = MonitorTool.isMysqlOnline(ip, arr[0], arr[1]);
		}
        else if("oracle".equals(type)){
        	String[] arr = param.split(" ");
			f = MonitorTool.isOracleOnline(ip, arr[0], arr[1]);
		}
        else if("host".equals(type)){
        	f = MonitorTool.isHostOnline(ip);
		}
        else{}
		return f;
	}


	@RequestMapping(value = "/add")
	@ResponseBody
	public MessageEntity addUser(@RequestBody MonitorForm form, HttpServletRequest request) throws Exception {
		MonitorEntity entity = new MonitorEntity();
		entity.setId(form.getId());
		entity.setHostname(form.getHostname());
		entity.setIp(form.getIp());
		entity.setAppname(form.getAppname());
		entity.setMonitorType(form.getMonitorType());
		entity.setMonitorWay(form.getMonitorWay());
		/*entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrgPid());
		entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());*/
		try {
			monitorService.insertMonitorEntity(entity);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加自监控" + entity, ControllerTool.getLoginName(request), true,"系统配置");
		} catch (Exception e) {
			logger.error("Error on adding user!", e);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加自监控" + entity, ControllerTool.getLoginName(request), false,"系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加自监控失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("添加自监控成功!");
	}


	@RequestMapping(value = "/edit")
	@ResponseBody
	public MessageEntity editUser(@RequestBody MonitorForm form, HttpServletRequest request) throws Exception {
		MonitorEntity entity = new MonitorEntity();
		entity.setId(form.getId());
		entity.setId(form.getId());
		entity.setHostname(form.getHostname());
		entity.setIp(form.getIp());
		entity.setAppname(form.getAppname());
		entity.setMonitorType(form.getMonitorType());
		entity.setMonitorWay(form.getMonitorWay());
		entity.setEnabled(form.getEnabled());
		try {
			monitorService.updateMonitorByID(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改自监控信息" + entity, ControllerTool.getLoginName(request), true,"系统配置");
		} catch (Exception e) {
			logger.error("Error on editing user!", e);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改自监控信息" + entity, ControllerTool.getLoginName(request), false,"系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("修改成功!");
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public MessageEntity deleteUser(@RequestBody MonitorForm form, HttpServletRequest request) throws Exception {
		try {
			monitorService.deleteMonitorByID(form.getId());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除系统自监控" + form.getId(),  ControllerTool.getLoginName(request), true,"系统配置");
		} catch (Exception e) {
			logger.error("Error on deleting user!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除系统自监控" + form.getId(),  ControllerTool.getLoginName(request), false,"系统配置");
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除系统自监控失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("删除系统自监控成功!");
	}
	
}
