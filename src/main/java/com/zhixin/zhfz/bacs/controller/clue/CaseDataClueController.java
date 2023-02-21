package com.zhixin.zhfz.bacs.controller.clue;


import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.CaseDataClueForm;
import com.zhixin.zhfz.bacs.services.archives.IElecArchivesService;
import com.zhixin.zhfz.bacs.services.clue.ICaseDataClueService;
import com.zhixin.zhfz.bacs.services.record.IRecordService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.common.FileUtil;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.sacw.common.Utils;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
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
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/clue")
public class CaseDataClueController {

	private static final Logger logger = LoggerFactory.getLogger(CaseDataClueController.class);

	
	@Autowired
	private IOperLogService operLogService;

	@Autowired
	private ICaseDataClueService service;

	@Autowired
	private ISerialService serialService;

	@Autowired
	private IRecordService recordService;

	@Autowired
	private IElecArchivesService archivesService;
	/**
	 * 查所有及分页及条件查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listCaseDataClue")
	@ResponseBody
	public Map<String,Object> list(@RequestParam Map<String,Object> param, HttpServletRequest request,
                                   HttpServletResponse response)throws Exception{
		Map<String, Object> map = ControllerTool.mapFilter(param);
		//map.put("usePage", true);
		logger.info("++++++++login++++++param----------------------------"+param+"--------------");
		List<CaseDataClueEntity> list=service.list(map);
		logger.info("++++++++login++++++param----------------------------"+param+"--------------");

		Map<String, Object> result = new HashMap<String, Object>();
		logger.info("++++++++login++++++listAllCaseDataClue----------------------------"+list+"--------------");
		result.put("total", this.service.count(map));
		result.put("rows", list);
		return result;
	}
	/**
	 * 插入证件类型
	 * @param certificateTypeEntity
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCaseDataClue")
	@ResponseBody
	public MessageEntity add(@RequestBody CaseDataClueForm form){
		System.out.println("++++++++add++++++UserForm=" + form);
		CaseDataClueEntity entity = new CaseDataClueEntity();
		entity.setFileName(form.getFileName());
		entity.setFileUrl(form.getFileUrl());
		entity.setUploadUserId(form.getUploadUserId());
		entity.setLawCaseId(form.getLawCaseId());
		entity.setFileDesc(form.getFileDesc());
		try {
			service.insert(entity);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加证件类型" + entity, "system", true,OperLogEntity.SYSTEM_BACS);
		} catch (Exception e) {
			logger.error("Error on adding user!", e);
			this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加证件类型" + entity, "system", false,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("Add failure!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("Add success!");
	}
	/**
	 * 根据id删除证件类型
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeCaseDataClue")
	@ResponseBody
	public MessageEntity remove(@RequestBody IDForm form) {
		System.out.println("++++++++remove++++++id=  " + form.getId());
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", Integer.parseInt(form.getId()));
			CaseDataClueEntity entity = service.selectOneById(map);
			System.out.println(entity);
			String filePath = entity.getFileUrl();
			File file = new File(filePath);
			if (file.exists() && file.isFile()) {
				FileUtils.forceDelete(file);
			}
			service.delete(form.getIntID());
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除证件类型" + form.getId(), "system", true,OperLogEntity.SYSTEM_BACS);
		} catch (Exception e) {
			logger.error("Error on deleting user!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除证件类型" + form.getId(), "system", false,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("Delete failure!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("Delete success!");
	}
	
	/*
	 * @RequestMapping(value="/delFile") public @ResponseBody ReturnDatas
	 * delete(@RequestParam("path") String path,HttpServletRequest request){
	 * ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas(); String
	 * filePath = path; String uploadDir =
	 * ConfigHelper.getString("fileSavePath"); FileUtil.deleteFile(uploadDir +
	 * "/" + filePath); returnObject.setMessage("删除成功"); return returnObject; }
	 * 先删除文件，在删除数据库
	 */
	@RequestMapping(value = "/delFile")
	@ResponseBody
	public MessageEntity delete(@RequestParam("path") String path) {
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("fileUrl", path);
			CaseDataClueEntity entity=service.selectOneById(map);
			File file =new File(entity.getFileUrl());
			if (file.exists() && file.isFile()) {
				FileUtils.forceDelete(file);
			}
			service.delete(entity.getId());
		} catch (Exception e) {
			e.getMessage();
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("delete failure!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("delete 111111111 success!");
	}
	/*
	 * @RequestMapping(value="/delFile") public @ResponseBody ReturnDatas
	 * delete(@RequestParam("path") String path,HttpServletRequest request){
	 * ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas(); String
	 * filePath = path; String uploadDir =
	 * ConfigHelper.getString("fileSavePath"); FileUtil.deleteFile(uploadDir +
	 * "/" + filePath); returnObject.setMessage("删除成功"); return returnObject; }
	 * 先删除文件，在删除数据库
	 */
	@RequestMapping(value = "/delAllFile")
	@ResponseBody
	public MessageEntity deleteAll(@RequestParam("path") String path) {
		try {
			String[] paths=path.split(",");
			for(int i=0;i<paths.length;i++){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("fileUrl", paths[i]);
				CaseDataClueEntity entity=service.selectOneById(map);
				if(entity==null)continue;
				File file =new File(entity.getFileUrl());
				if (file.exists() && file.isFile()) {
					FileUtils.forceDelete(file);
				}
				service.delete(entity.getId());
			}
			
		} catch (Exception e) {
			e.getMessage();
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("delete failure!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("delete 111111111 success!");
	}
	
	
	@RequestMapping("/download")
	public void download(@RequestParam Map<String,Object> map , HttpServletRequest request , HttpServletResponse response){
		try {
			logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!download!!!!!!!"+map.toString());
			CaseDataClueEntity entity = service.selectOneById(map);
			File file = new File(entity.getFileUrl());
			response.reset();
			response.setContentType("application/x-download");
			response.setCharacterEncoding("UTF-8");
			String fileName = entity.getFileName();
			if(request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0){
	            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
	        }else{
	            fileName = URLEncoder.encode(fileName, "UTF-8");
	        }
			response.addHeader("Content-Disposition", "attachment;filename=" +fileName);
			OutputStream output = response.getOutputStream();
			FileUtils.copyFile(file, output);
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id删除证件类型
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateByAuto")
	@ResponseBody
	public MessageEntity updateByAuto(@RequestParam Map<String,Object> map , HttpServletRequest request) {
		try{
			String path=request.getParameter("path");
			String interrogateSerialIdStr=request.getParameter("interrogateSerialId");
			Long interrogateSerialId=null;
			if(interrogateSerialIdStr!=null&&!"".equals(interrogateSerialIdStr)){
				interrogateSerialId=Long.parseLong(interrogateSerialIdStr);
			}
			Map<String,Object> map1=new HashMap<String,Object>();
			map1.put("fileUrl", path);
			CaseDataClueEntity entity=service.selectOneById(map1);
			entity.setInterrogateSerialId(interrogateSerialId);
			service.update(entity);
		} catch (Exception e) {
			logger.error("Error on deleting user!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除证件类型" , "system", false,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("Delete failure!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("Delete success!");
	}
	/**
	 * 根据id删除证件类型
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateByAutoAll")
	@ResponseBody
	public MessageEntity updateByAutoAll(@RequestParam Map<String,Object> map , HttpServletRequest request) {
		try{
			String pathStr=request.getParameter("path");
			String interrogateSerialIdStr=request.getParameter("interrogateSerialId");
			Long interrogateSerialId=Long.parseLong(interrogateSerialIdStr);
			String[] pathList=pathStr.split(",");
			for (int i = 0; i < pathList.length; i++) {
				Map<String,Object> map1=new HashMap<String,Object>();
				map1.put("fileUrl", pathList[i]);
				CaseDataClueEntity entity=service.selectOneById(map1);
				entity.setInterrogateSerialId(interrogateSerialId);
				service.update(entity);
			}
			
		} catch (Exception e) {
			logger.error("Error on deleting user!", e);
			this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除证件类型" , "system", false,OperLogEntity.SYSTEM_BACS);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("Delete failure!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("Delete success!");
	}

	/**
	 * 轨迹视频
	 *
	 * @param pageMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/listVideoTree")
	@ResponseBody
	public void listVideo(@RequestParam Map<String, Object> params, HttpServletRequest request,
						  HttpServletResponse response) throws Exception {
		// 获取此案件的全部入区编号（可能有0到能n条）
		List<SerialEntity> list = serialService.selectByCaseAndPerson(params);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<TrajectoryVideoTreeEntity> result = new ArrayList<TrajectoryVideoTreeEntity>();
		if (list != null) {
			// 遍历编号
			for (SerialEntity entity : list) {
				System.err.println("id=" + entity.getId());
				TrajectoryVideoTreeEntity tvEntity = new TrajectoryVideoTreeEntity();
				tvEntity.setId(entity.getId() + "");
				String intime = sdf.format(entity.getInTime());
				String outime = null;
				if (entity.getOutTime() == null || "null".equals(entity.getOutTime())) {
					outime = "当前";
				} else {
					outime = sdf.format(entity.getOutTime());
				}
				tvEntity.setText(intime + " 至 " + outime);
				logger.info("File list begin" + "-----------" + entity.getUuid() + System.currentTimeMillis());
				// 从本地获取文件，因为FTP的目录已被mount到本地
				// List<String> fileList =
				// FTPUtil.listSubFiles("/Ring/"+entity.getUuid()+"/");
				// LinkedHashMap<String, String> map =
				// FileUtil.listFilesInPath("D:\\ringFileSavePath\\07a0d239-8f7e-4549-8787-47dc69ca9c60");
				LinkedHashMap<String, String> map = FileUtil
						.listFilesInPath(PropertyUtil.getContextProperty("ringFileSavePath")
								+ Utils.getDateFromSerialNO(entity.getSerialNo()) + "/" + entity.getUuid()
								+ File.separator);
				logger.info("File list end --------------" + System.currentTimeMillis());
				System.err.println("file size " + map.size());
				int i = 0;
				for (String key : map.keySet()) {
					TrajectoryVideoTreeEntity childEntity = new TrajectoryVideoTreeEntity();
					childEntity.setId(entity.getId() + "_" + (++i));
					childEntity.setRealFilePath(key);
					childEntity.setText(map.get(key));
					tvEntity.addChildren(childEntity);
				}
				result.add(tvEntity);
			}
		}
		response.setCharacterEncoding("UTF-8");
		Writer writer = response.getWriter();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(writer);
		JsonGenerator jsonGenerator2 = objectMapper.getJsonFactory().createJsonGenerator(System.out);
		jsonGenerator2.writeObject(result);
		jsonGenerator.writeObject(result);
	}

	/**
	 * 笔录信息
	 *
	 * @param pageMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/listRecordTree")
	@ResponseBody
	public void listRecord(@RequestParam Map<String, Object> params, HttpServletRequest request,
						   HttpServletResponse response) throws Exception {
		System.err.println("---------listRecordTree---------" + params);
		List<InterrogateRecordTreeEntity> result = new ArrayList<InterrogateRecordTreeEntity>();
		// 获取此案件的全部入区编号（可能有0到能n条）
		List<SerialEntity> list = serialService.selectByCaseAndPerson(params);
		System.err.println("---------list---------" + list.size());
		if (list != null && list.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			// 遍历编号
			for (SerialEntity entity : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("serialId", entity.getId());
				// 查询此编号的审讯记录（可能有0到能n条）
				List<RecordEntity> recordList = recordService.selectBySerialId(map);
				System.err.println("---------recordList---------" + recordList.size());
				if (recordList != null && recordList.size() > 0) {
					for (RecordEntity record : recordList) {
						InterrogateRecordTreeEntity ent = new InterrogateRecordTreeEntity();
						ent.setId(entity.getId() + "_" + record.getId());
						String startTime = (record.getStartTime()).toString();
						if (startTime.endsWith(".0")) {
							startTime = startTime.substring(0, startTime.length() - 5);
						}
						String endTime = record.getEndTime().toString();
						if (endTime == null || "null".equals(endTime)) {
							endTime = "当前";
						}
						ent.setName(startTime + " 至 " + endTime);
						logger.info("File list begin" + "-----------" + record.getUuid() + System.currentTimeMillis());

						String path=PropertyUtil.getContextProperty("inquestFileSavePath")
								+ Utils.getDateFromSerialNO(entity.getSerialNo()) + "/"
								+ record.getUuid();
						logger.info("%%%%%%%%%%%  s  %%%%%%%%%%%55 "+path);
						logger.info("%%%%%%%%%%%  s  %%%%%%%%%%%55 "+path);
						LinkedHashMap<String, String> files = FileUtil
								.listFilesInPath(path);
						logger.info("File list end --------------" + System.currentTimeMillis());
						System.err.println("file size " + files.size());
						int i = 0;
						for (String key : files.keySet()) {
							InterrogateRecordTreeEntity child = new InterrogateRecordTreeEntity();
							child.setId(entity.getId() + "_" +record.getId()+"_"+ (++i));
							child.setRealFilePath(key);
							String fileName = files.get(key);
							if (fileName.endsWith(".doc")){
								fileName = record.getPersonName()+"_第"+record.getCount()+"次.doc";
							} else if(fileName.endsWith(".txt")){
								fileName = record.getPersonName()+"_第"+record.getCount()+"次.txt";
							} else if(fileName.endsWith(".mp4")){
								continue;
							}
							child.setName(fileName);
							ent.addChildren(child);
						}

						result.add(ent);
					} // end for
				}
			} // end for
		}

		response.setCharacterEncoding("UTF-8");
		Writer writer = response.getWriter();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(writer);
		JsonGenerator jsonGenerator2 = objectMapper.getJsonFactory().createJsonGenerator(System.out);
		jsonGenerator2.writeObject(result);
		jsonGenerator.writeObject(result);
	}

	/**
	 * 笔录视频信息
	 *
	 * @param params
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/listRecordVideo")
	@ResponseBody
	public void listRecordVideo(@RequestParam Map<String, Object> params, HttpServletRequest request,
								HttpServletResponse response) throws Exception {
		System.err.println("---------listRecordTree---------" + params);
		List<InterrogateRecordTreeEntity> result = new ArrayList<InterrogateRecordTreeEntity>();
		// 获取此案件的全部入区编号（可能有0到能n条）
		List<SerialEntity> list = serialService.selectByCaseAndPerson(params);
		System.err.println("---------list---------" + list.size());
		if (list != null && list.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			// 遍历编号
			for (SerialEntity entity : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("serialId", entity.getId());
				// 查询此编号的审讯记录（可能有0到能n条）
				List<RecordEntity> recordList = recordService.selectBySerialId(map);
				System.err.println("---------recordList---------" + recordList.size());
				if (recordList != null && recordList.size() > 0) {
					for (RecordEntity record : recordList) {
						InterrogateRecordTreeEntity ent = new InterrogateRecordTreeEntity();
						ent.setId(entity.getId() + "_" + record.getId());
						String startTime = record.getStartTime().toString();
						if (startTime.endsWith(".0")) {
							startTime = startTime.substring(0, startTime.length() - 5);
						}
						String endTime = record.getEndTime().toString();
						if (endTime == null || "null".equals(endTime)) {
							endTime = "当前";
						}
						ent.setName(startTime + " 至 " + endTime);
						logger.info("File list begin" + "-----------" + record.getUuid() + System.currentTimeMillis());
						String path=PropertyUtil.getContextProperty("inquestFileSavePath")
								+ Utils.getDateFromSerialNO(entity.getSerialNo()) + "/"
								+ record.getUuid();
						logger.info("%%%%%%%%%%%  s  %%%%%%%%%%%55 "+path);
						LinkedHashMap<String, String> files = FileUtil
								.listFilesInPath(path);
						System.err.println("file size " + files.size());
						int i = 0;
						for (String key : files.keySet()) {
							InterrogateRecordTreeEntity child = new InterrogateRecordTreeEntity();
							child.setId(entity.getId() + "_" +record.getId()+"_"+ (++i));
							child.setRealFilePath(key);
							String fileName = files.get(key);
							if (!fileName.endsWith(".mp4")){
								continue;
							}
							child.setName(fileName);
							ent.addChildren(child);
						}

						result.add(ent);
					} // end for
				}
			} // end for
		}

		response.setCharacterEncoding("UTF-8");
		Writer writer = response.getWriter();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(writer);
		JsonGenerator jsonGenerator2 = objectMapper.getJsonFactory().createJsonGenerator(System.out);
		jsonGenerator2.writeObject(result);
		jsonGenerator.writeObject(result);
	}

	/**
	 * 笔录信息
	 *
	 * @param pageMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadfile")
	@ResponseBody
	public void downfile(@RequestParam Map<String, Object> params, HttpServletRequest request,
						 HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		map.put("interrogate_case_id", params.get("interrogate_case_id"));
		map.put("type", params.get("type"));

		String uuid = "";
		List list = archivesService.findFilePath(map);
		if (list.size() > 0) {
			Map map1 = (Map) list.get(0);
			uuid = (String) map1.get("uuid");
		}
		String realName = params.get("name").toString();
		logger.info("下载文件名 realName= " + realName);
		// String inquestFileSavePath =
		// PropertyUtil.getContextProperty("inquestFileSavePath").toString();
		// String ringFileSavePath =
		// PropertyUtil.getContextProperty("ringFileSavePath").toString();
		/*
		 * String realPath = ""; if (params.get("type").equals("recordtree")) {
		 * realPath = inquestFileSavePath + uuid + "/"; } if
		 * (params.get("type").equals("videotree")) { realPath =
		 * ringFileSavePath + uuid + "/"; }
		 */

		String realPathName = params.get("realFilePath").toString();
		// System.err.println("===============realPathName================"+realPathName);
		logger.info("下载文件地址 realPathName= " + realPathName);
		// String realPathName = readfile(realPath, realName);
		OutputStream os = response.getOutputStream();
		try {
			response.setContentType("binary/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment; " + "filename=" + URLEncoder.encode(realName, "UTF-8"));
			// new String(realName.getBytes("gb2312"), "iso8859-1")
			os.write(FileUtils.readFileToByteArray(new File(realPathName)));
			os.flush();
		} finally {
			if (os != null) {
				os.close();
			}
		}

	}

}
