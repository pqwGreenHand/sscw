package com.zhixin.zhfz.bacs.controller.archives;

import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.services.archives.IElecArchivesService;
import com.zhixin.zhfz.bacs.services.clue.ICaseDataClueService;
import com.zhixin.zhfz.bacs.services.record.IRecordService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.common.FileUtil;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.sacw.common.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/archives")
public class ElecArchivesController {

	@Autowired
	private IElecArchivesService archivesService;
    @Autowired
    private IUserService userService;

    @Autowired
    private ISerialService serialService;
    
    @Autowired
    private IRecordService recordService;
    @Autowired
    private ICaseDataClueService service;


    private static final Logger logger = LoggerFactory.getLogger(ElecArchivesController.class);
	
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
            map.put("dataAuth", " b.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " b.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " b.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
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

    @RequestMapping(value="/onlineupload", method = RequestMethod.POST)
    @ResponseBody
    public String onlineupload(@RequestParam("onlinefilename") MultipartFile file, @RequestParam("personId") Long personId, @RequestParam("fileType") String fileType,@RequestParam("caseId") Integer caseId,
                               HttpServletRequest request) {
//        String contentType = file.getContentType();
        Map<String,String> map=new HashMap<>();
        String myFileName = file.getOriginalFilename();
        String status = "success";
        try {
            String path = PropertyUtil.getContextProperty("exhibitImageFileSavePath").toString() + "/";
            FileUtil.createDir(path);
            String spath = path + myFileName;
            File localFile = new File(path, myFileName);
            file.transferTo(localFile);
            CaseDataClueEntity entity = new CaseDataClueEntity();
            entity.setFileName(myFileName);
            entity.setFileUrl(spath);
            entity.setUploadUserId(ControllerTool.getUserID(request));
            entity.setOpPid(ControllerTool.getUser(request).getOp_Pid());
            entity.setOpUserId(ControllerTool.getUserID(request)+"");
            entity.setLawCaseId(caseId);
            entity.setFileDesc(fileType);
            entity.setPersonId(personId);
            try {
                service.insert(entity);
            } catch (Exception e) {
                logger.error("Error on adding user!", e);
            }
            status = "success";
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("涉案财物图片上传异常"+e);
            status="error";
        }
        if(file==null){
            status="error";
        }
        return status;
    }


	// 光盘刻录查询
	@RequestMapping(value = "/listBurn")
	@ResponseBody
	public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( l.op_user_id=" + ControllerTool.getUserID(request)
                    +" or l.burn_user_id = " +  ControllerTool.getUserID(request)
                    +" or a.op_user_id = " + ControllerTool.getUserID(request)
                    +" or c.cjr = " + ControllerTool.getUserID(request)
                    +" or u.op_user_id = " + ControllerTool.getUserID(request)
                    +" or u.id = " + ControllerTool.getUserID(request)
                    +" or p.op_user_id = " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", "( l.area_id=" + ControllerTool.getCurrentAreaID(request)
                    +" or a.id = " + ControllerTool.getCurrentAreaID(request)
                    +" or p.area_id = " + ControllerTool.getCurrentAreaID(request)
                    +" ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "( l.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
                    +" or p.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
                    +" or a.id " + sessionInfo.getCurrentAndSubAreaInStr()
                    +" ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", "( l.area_id   " + sessionInfo.getSuperAndSubAreaInStr()
                    +" or p.area_id " + sessionInfo.getSuperAndSubAreaInStr()
                    +" or a.id " + sessionInfo.getSuperAndSubAreaInStr()
                    +" ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( l.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or a.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    +" or c.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    +" or u.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    +" or p.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( l.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or a.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or c.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or u.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or p.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( l.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or a.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or c.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or u.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or p.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
		List<BurnLogEntity> list = archivesService.list(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", archivesService.count(map));
		result.put("rows", list);
		return result;
	}
	
	/**
     * 全部光盘领取情况
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/listByAll")
    @ResponseBody
    public Map<String, Object> listByAll(@RequestParam Map<String, Object> params,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(params);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( l.register_user_id=" + ControllerTool.getUserID(request) + " or l.op_user_id="
                    + ControllerTool.getUserID(request)
                    +" or a.op_user_id = " + ControllerTool.getUserID(request)
                    +" or c.cjr = " + ControllerTool.getUserID(request)
                    +" or u.op_user_id = " + ControllerTool.getUserID(request)
                    +" or u.id = " + ControllerTool.getUserID(request)
                    +" or p.op_user_id = " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", "( l.area_id=" + ControllerTool.getCurrentAreaID(request)
            +" or a.id =" + ControllerTool.getCurrentAreaID(request)
                    +" or p.area_id =" + ControllerTool.getCurrentAreaID(request)
            +" ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ( a.id  " + sessionInfo.getCurrentAndSubAreaInStr()
                +" or l.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
                    +" or p.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
            +" ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ( a.id  " + sessionInfo.getSuperAndSubAreaInStr()
                +" or l.area_id " + sessionInfo.getSuperAndSubAreaInStr()
                    +" or p.area_id " + sessionInfo.getSuperAndSubAreaInStr()
            +" ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( l.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or a.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or c.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or u.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or p.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()

                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( l.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or a.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or c.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or u.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or p.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( l.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or a.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or c.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or u.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or p.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<DvdLogEntity> list = archivesService.listByAll(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        result.put("total", archivesService.countDvdLog(map));
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
     * 光盘领取
     *
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/insertDvdLog")
    @ResponseBody
    public MessageEntity insert(@RequestBody DvdLogEntity form, HttpServletRequest request,
                                HttpServletResponse response) {
        try {
            DvdLogEntity entity = new DvdLogEntity();
            entity.setReceiveUser(form.getReceiveUser());
            entity.setAreaId(form.getAreaId());
            entity.setCaseId(form.getCaseId());
            entity.setRegisterUserId(ControllerTool.getUserID(request));// 在session中取
            entity.setPersonId(form.getPersonId());
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            archivesService.insertDvdLog(entity);
        } catch (Exception e) {
            logger.error("Error on insertDvdLog !", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("光盘领取失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("光盘领取成功!");
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




}
