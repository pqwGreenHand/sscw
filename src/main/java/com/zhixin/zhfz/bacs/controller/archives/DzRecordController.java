package com.zhixin.zhfz.bacs.controller.archives;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.bacs.entity.ArchivesTreeEntity;
import com.zhixin.zhfz.bacs.entity.RecordEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.entity.TrajectoryVideoTreeEntity;
import com.zhixin.zhfz.bacs.services.archives.IElecArchivesService;
import com.zhixin.zhfz.bacs.services.record.IRecordService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.common.FileUtil;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.sacw.common.Utils;

/**
 * 电子账单
 * @author xdp
 *
 */
@Controller
@RequestMapping("/zhfz/bacs/dzRecord")
public class DzRecordController {

	@Autowired
	private IElecArchivesService archivesService;
    @Autowired
    private IUserService userService;

    @Autowired
    private ISerialService serialService;
    
    @Autowired
    private IRecordService recordService;
    @Autowired

	private static final Logger logger = LoggerFactory.getLogger(DzRecordController.class);
	
	/**
     * 案件信息树展示
     *
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/listTree")
    @ResponseBody
    public Map<String, Object> listArchivesTree(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( a.cjr=" + ControllerTool.getUserID(request)
                    + " or a.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",', a.xbmj_ids)"
                    +" or b.in_register_user_id = " + ControllerTool.getUserID(request)
                    +" or b.out_register_user_id = " + ControllerTool.getUserID(request)
                    +" or b.send_user_id = " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", "a.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " a.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " a.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( b.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or a.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or a.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( b.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or a.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or a.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( c.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or a.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or a.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<ArchivesTreeEntity> list = null;
        int total = 0;        
        list = archivesService.listCaseTree(map);
        total = archivesService.listCaseTreeCount(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        result.put("total", total);
        return result;
    }

  
    /**
     * 审讯问视频和笔录信息
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/listRecordTree")
    @ResponseBody
    public Map<String, Object> listRecord(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        String path = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //MessageEntity exeResult = new MessageEntity();
        List<TrajectoryVideoTreeEntity> resultList = new ArrayList<TrajectoryVideoTreeEntity>();
        //exeResult.setCallbackData(result);
        // 根据caseId和personId查询所有嫌疑人信息
        List<SerialEntity> list = serialService.selectByCaseAndPerson(params);
        // 遍历list
        if (list != null && list.size() > 0) {
            for (SerialEntity entity : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("serialId", entity.getId());
                // 查询此编号的审讯记录（可能有0到能n条）
                List<RecordEntity> recordList = recordService.selectBySerialId(map);
                // 遍历recordList
                if (recordList != null && recordList.size() > 0) {
                    for (RecordEntity record : recordList) {
                        String startTime = formatter.format(record.getStartTime());
                        if (startTime.endsWith(".0")) {
                            startTime = startTime.substring(0, startTime.length() - 5);
                        }
                        String endTime = formatter.format(record.getEndTime());
                        if (endTime == null || "null".equals(endTime)) {
                            endTime = "当前";
                        }
                        // inquestFileSavePath=D:/ftp/Inquest/
                        path = PropertyUtil.getContextProperty("inquestFileSavePath")
                                + Utils.getDateFromSerialNO(entity.getSerialNo()) + "/"
                                + record.getUuid();
                        LinkedHashMap<String, String> files = FileUtil.listFilesInPath(path);
                        int i = 0;
                        for (String key : files.keySet()) {
                            File file = new File(key);
                            if(file.isDirectory()){
                                System.out.println("是文件夹");
                                LinkedHashMap<String, String> dirFiles = FileUtil.listFilesInPath(key);
                                for(String dirkey : dirFiles.keySet()){
                                    TrajectoryVideoTreeEntity tve = new TrajectoryVideoTreeEntity();
                                    String dirFileName = dirFiles.get(dirkey);
                                    if (dirFileName.endsWith(".mp4")) {
                                        dirFileName = record.getPersonName() + "_第" + record.getCount() + "次.mp4";
                                    }
                                    tve.setRealFilePath(dirkey);
                                    tve.setId(entity.getId() + "_" + record.getId() + "_" + (++i));
                                    tve.setText(dirFileName);
                                    resultList.add(tve);
                                }
                            }
                            if(file.isFile()){
                                System.out.println("是文件");
                                TrajectoryVideoTreeEntity tve = new TrajectoryVideoTreeEntity();
                                String fileName = files.get(key);
                                if (fileName.endsWith(".doc") || fileName.endsWith(".docx")) {
                                    fileName = record.getPersonName() + "_第" + record.getCount() + "次.doc";
                                } else if (fileName.endsWith(".txt")) {
                                    fileName = record.getPersonName() + "_第" + record.getCount() + "次.txt";
                                }else if (fileName.endsWith(".mp4")) {
                                    fileName = record.getPersonName() + "_第" + record.getCount() + "次.mp4";
                                    continue;
                                }
                                tve.setRealFilePath(key);
                                tve.setId(entity.getId() + "_" + record.getId() + "_" + (++i));
                                tve.setText(fileName);
                                resultList.add(tve);
                            }
                        }
                        //exeResult.addCallbackData(result);
                    } // end for
                }
            } // end for
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", resultList);
        return result;
    }

    /**
     * 笔录视频信息
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/listRecordVideo")
    @ResponseBody
    public Map<String, Object> listRecordVideo(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        String path = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //MessageEntity exeResult = new MessageEntity();
        List<TrajectoryVideoTreeEntity> resultList = new ArrayList<TrajectoryVideoTreeEntity>();
        //exeResult.setCallbackData(result);
        // 根据caseId和personId查询所有嫌疑人信息
        List<SerialEntity> list = serialService.selectByCaseAndPerson(params);
        // 遍历list
        if (list != null && list.size() > 0) {
            for (SerialEntity entity : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("serialId", entity.getId());
                // 查询此编号的审讯记录（可能有0到能n条）
                List<RecordEntity> recordList = recordService.selectBySerialId(map);
                // 遍历recordList
                if (recordList != null && recordList.size() > 0) {
                    for (RecordEntity record : recordList) {
                        String startTime = formatter.format(record.getStartTime());
                        if (startTime.endsWith(".0")) {
                            startTime = startTime.substring(0, startTime.length() - 5);
                        }
                        String endTime = formatter.format(record.getEndTime());
                        if (endTime == null || "null".equals(endTime)) {
                            endTime = "当前";
                        }
                        // inquestFileSavePath=D:/ftp/Inquest/
                        path = PropertyUtil.getContextProperty("inquestFileSavePath")
                                + Utils.getDateFromSerialNO(entity.getSerialNo()) + "/"
                                + record.getUuid();
                        LinkedHashMap<String, String> files = FileUtil.listFilesInPath(path);
                        int i = 0;
                        for (String key : files.keySet()) {
                        	TrajectoryVideoTreeEntity child = new TrajectoryVideoTreeEntity();
                        	String fileName = files.get(key);
                        	if (!fileName.endsWith(".mp4")){
								continue;
							}
                        	fileName = record.getPersonName() + "_第" + record.getCount() + "次.mp4";
							child.setId(entity.getId() + "_" +record.getId()+"_"+ (++i));
							child.setRealFilePath(key);
							child.setText(fileName);
							resultList.add(child);
                        }
                    } // end for
                }
            } // end for
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", resultList);
        return result;
    }
    
    
    
    
    
    
    
    
    
    
    // 根据caseId获取主办民警警号
    @RequestMapping(value = "/findPoliceNoByCaseId")
    @ResponseBody
    public MessageEntity findPoliceEntranceByPoliceId(Integer caseId,HttpServletRequest request) {
        UserEntity user = new UserEntity();
        try {
            user = this.userService.findPoliceNoByCaseId(caseId);
        } catch (Exception e) {
            logger.error("获取主办民警警号失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("获取主办民警警号失败!");
        }
        return new MessageEntity().addCode(0).addIsError(false).addTitle("提示").addContent("获取主办民警警号成功!").addCallbackData(user);
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
	/*	List list = archivesService.findFilePath(map);
		if (list.size() > 0) {
			Map map1 = (Map) list.get(0);
			uuid = (String) map1.get("uuid");
		}*/
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

	public String readfile(String filepath, String filename) throws FileNotFoundException, IOException {
		String absolutepath = "";
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
				System.out.println("path=" + file.getPath());
				absolutepath = file.getPath();

			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						if (readfile.getName().equals(filename)) {
							absolutepath = readfile.getPath();

						}
					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i], filename);
					}
				}

			}

		} catch (FileNotFoundException e) {
			logger.error("", e);
		}
		String[] arr = absolutepath.split("=");
		return arr[0];
	}
     
   
}
