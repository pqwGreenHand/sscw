package com.zhixin.zhfz.jzgl.controller.jzg;

import java.text.ParseException;
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
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.entity.JzgGxEntity;
import com.zhixin.zhfz.jzgl.services.jzgGmxx.IJzgGmxxService;
import com.zhixin.zhfz.jzgl.services.jzgGmxx.IJzgGxService;

/**
 * 实体类 table: jz_jzg_gx 柜门与用户的分配关系
 */
@Controller
@RequestMapping("/zhfz/jzgl/jzgGmxx")
public class JzgGxController {
	
	private static Logger logger = Logger.getLogger(JzgGxController.class);
	@Autowired
	private IJzgGmxxService jzgGmxxService;
	@Autowired
	private IJzgGxService jzgGxService;
	@Autowired
	private IOperLogService operLogService;

	@RequestMapping(value = "/queryAllUser")
	@ResponseBody
	public Map<String, Object> listAllJzg(@RequestParam Map<String, Object> param) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = ControllerTool.mapFilter(param);
		logger.info(map.get("id") + "===============");
		map.put("usePage", true);
		map.put("Id", map.get("id"));
		List<JzgGxEntity> list = jzgGxService.queryAllUserGmxx(map);
		System.out.println("----------"+list + "===============");
		int count = jzgGxService.countAllUserGmxx(map);
		result.put("total", count);
		result.put("rows", list);
		return result;
	}
	
	/**
	 * 添加
	 * 
	 * @param entity
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/addJzgGx")
	@ResponseBody
	public MessageEntity addJzgGx(@RequestBody JzgGxEntity entity, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		logger.info(entity + "==============");
		String userIds = entity.getUserIds();
		// 插入前进行删除根据getJzgGmxxId进行删除
		jzgGxService.deleteGxByjzgGmxxId(entity.getJzgGmxxId());
		this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "柜门分配删除用户" + entity, ControllerTool.getLoginName(request), true,OperLogEntity.SYSTEM_JZGL);
		if(userIds!=null && !"".equals(userIds.trim())){
			String[] str = userIds.split(",");
			try {
				for (int i = 0; i < str.length; i++) {
					if(str[i]!=null && !"".equals(str[i])){
						Long userId = Long.parseLong(str[i]);
						entity.setUserId(userId);
						if(i==str.length-1){
							entity.setGmxs(1);
						}else {
							entity.setGmxs(0);
						}
						jzgGxService.insertJzgGx(entity);
						this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "柜门分配添加用户" + entity, ControllerTool.getLoginName(request), true,OperLogEntity.SYSTEM_JZGL);
					}
				}
			} catch (Exception e) {
				logger.fatal("Error on adding AjxxMysqlEntity!", e);
				return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("添加失败!");
			}
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteJzgGx")
	@ResponseBody
	public MessageEntity deleteJzgGx(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) {
		System.err.println("--------------" + id);
		try {
			jzgGxService.deletejzgGxById(id);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "柜门分配删除用户ID:" + id, ControllerTool.getLoginName(request), true,OperLogEntity.SYSTEM_JZGL);
		} catch (Exception e) {
			logger.fatal("Error on delete UserGmxxMysqlEntity!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("删除成功!");
	}

	/**
	 * 设置
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/setJzgUserName")
	@ResponseBody
	public MessageEntity setJzgUserName(@RequestParam Long id,@RequestParam Long jzgGmxxId, HttpServletRequest request,
			HttpServletResponse response) {
		System.err.println("--------------" + id);
		try {
			JzgGxEntity userGmxx = new JzgGxEntity();
			userGmxx.setJzgGmxxId(jzgGmxxId);
			userGmxx.setGmxs(1);
			userGmxx.setId(id);
			jzgGxService.updateJzgGxGmxs(userGmxx);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "柜门分配设置" + userGmxx, ControllerTool.getLoginName(request), true, OperLogEntity.SYSTEM_JZGL);
		} catch (Exception e) {
			logger.fatal("Error on delete UserGmxxMysqlEntity!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("设置失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("设置成功!");
	}

}
