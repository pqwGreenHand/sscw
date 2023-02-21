package com.zhixin.zhfz.bacsapp.controller.common;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhixin.zhfz.bacs.entity.RoomEntity;
import com.zhixin.zhfz.bacsapp.entity.SerialAppEntity;
import com.zhixin.zhfz.bacsapp.services.common.ICommonService;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacsapp/common")
public class CommonAppController {

	private static final Logger logger = LoggerFactory.getLogger(CommonAppController.class);

	@Autowired
	private ICommonService commonService;
	@Autowired
	private IFileConfigService fileConfigService;

	// 查询所有入区嫌疑人
	@RequestMapping(value = "/listAllPerson")
	@ResponseBody
	public List<SerialAppEntity> listAllPerson(@RequestParam Map<String, Object> params,
											   HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer areaId = ControllerTool.getCurrentAreaID(request);
		Map<String, Object> map = ControllerTool.mapFilter(params);
//		map.put("areaId", areaId);

		Map<String, Object>  map1 = getAuthMethod(request);

		List<SerialAppEntity> list =null;

		if ("true".equals(map1.get("flag"))){
			map.put("dataAuth",map1.get("dataAuth"));
		}
		list = commonService.listAllPerson(map);

		return list;
	}


	public  Map<String,Object> getAuthMethod(HttpServletRequest request){

		Map<String,Object> map = new HashMap<String,Object>();

		boolean flag = true;
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
			// 办案人员-本人
			map.put("dataAuth", "(a.in_register_user_id=" + ControllerTool.getUserID(request) +
					" or a.out_register_user_id=" + ControllerTool.getUserID(request) + " ) ");
			map.put("flag","true");
		} else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 办案场所-本办案场所
			map.put("dataAuth", " a.area_id=" + ControllerTool.getCurrentAreaID(request));
			map.put("flag","true");
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 管理员 -本办案场所及下级办案场所
			map.put("dataAuth", "a.area_id" + sessionInfo.getCurrentAndSubAreaInStr());
			map.put("flag","true");
		}
		else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 上级办案场所及其下级办案场所
			map.put("dataAuth", " a.area_id" + ControllerTool.getSessionInfo(request).getSuperAndSubAreaInStr());
			map.put("flag","true");
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 公安领导-本部门及下级部门
			map.put("dataAuth","a.op_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid());
			map.put("flag","true");
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 法制人员-上级部门及下级部门
			map.put("dataAuth","a.op_pid like " + ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid());
			map.put("flag","true");
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
			// 本部门
			map.put("dataAuth","a.op_pid =" + sessionInfo.getCurrentOrgPid());
			map.put("flag","true");
		} else {
			map.put("flag","false");
		}

		return map;
	}


	// 查询所有房间
	@RequestMapping(value = "/listAllRoom")
	@ResponseBody
	public List<RoomEntity> listAllRoom(@RequestParam Map<String, Object> params,
										HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("-----------------listAllRoom-----------------");
		Integer areaId = ControllerTool.getCurrentAreaID(request);
		Map<String, Object> map = ControllerTool.mapFilter(params);

//		map.put("interrogateAreaId", areaId);

		Map<String, Object> map1 =getAuthMethod1(request);

		List<RoomEntity> list = null;

		if ("true".equals(map1.get("flag"))){
			map.put("dataAuth",map1.get("dataAuth"));
		}
		list = commonService.listAllRoom(map);

		return list;
	}

	public  Map<String,Object> getAuthMethod1(HttpServletRequest request){

		Map<String,Object> map = new HashMap<String,Object>();

		boolean flag = true;
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
			// 办案人员-本人
			map.put("dataAuth", "(i.op_user_id=" + ControllerTool.getUserID(request) +
							" or t.op_user_id=" + ControllerTool.getUserID(request) +
					" ) ");
			map.put("flag","true");
		} else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 办案场所-本办案场所
			map.put("dataAuth", " i.area_id=" + ControllerTool.getCurrentAreaID(request));
			map.put("flag","true");
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 管理员 -本办案场所及下级办案场所
			map.put("dataAuth", "i.area_id" + sessionInfo.getCurrentAndSubAreaInStr());
			map.put("flag","true");
		}
		else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 上级办案场所及其下级办案场所
			map.put("dataAuth", " i.area_id" + ControllerTool.getSessionInfo(request).getSuperAndSubAreaInStr());
			map.put("flag","true");
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 公安领导-本部门及下级部门
			map.put("dataAuth","i.op_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()
					+" or t.op_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid());
			map.put("flag","true");
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 法制人员-上级部门及下级部门
			map.put("dataAuth","i.op_pid like " + ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()
					+" or t.op_pid like " + ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid());
			map.put("flag","true");
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
			// 本部门
			map.put("dataAuth","i.op_pid =" + sessionInfo.getCurrentOrgPid()
					+" or t.op_pid =" + sessionInfo.getCurrentOrgPid());
			map.put("flag","true");
		} else {
			map.put("flag","false");
		}

		return map;
	}

	@RequestMapping(value = "/getpicture")
	@ResponseBody
	public void getpicture(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) throws Exception {
		logger.info("===================getpicture=====图片上传 =");

		String uuid = request.getParameter("uuid");
		String areaId = request.getParameter("areaId");
		String type = request.getParameter("type");

			try {
				//判断file数组不能为空并且长度大于0
				if(files!=null&&files.length>0){
					//循环获取file数组中得文件
					for(int i = 0;i<files.length;i++){
//						MultipartFile file = files[i];
						FileUploadForm form = new FileUploadForm();
						String filename = "";
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
						filename = "App"+type+"-" + dateFormat.format(new java.util.Date()) + i+".jpg";
						form.setSysType("ba");
						form.setFileType(type);
						form.setUuid(uuid);
						form.setSysId(areaId);
						form.setFileName(filename);
						form.setFile(files[i]);
						fileConfigService.upload(form);
					}
				}
			// 图片结束
		} catch (Exception e) {
			logger.error("### 图片上传错误  ###", e);
		}
	}



}
