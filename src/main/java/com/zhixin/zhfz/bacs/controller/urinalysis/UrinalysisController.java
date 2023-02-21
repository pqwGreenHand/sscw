package com.zhixin.zhfz.bacs.controller.urinalysis;

import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.UrinalysisForm;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacs.services.serial.ISerialStatusService;
import com.zhixin.zhfz.bacs.services.urinalysis.UrinalysisDetailService;
import com.zhixin.zhfz.bacs.services.urinalysis.UrinalysisPhotoService;
import com.zhixin.zhfz.bacs.services.urinalysis.UrinalysisService;
import com.zhixin.zhfz.common.common.Base64Util;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.common.BaseConfig;
import com.zhixin.zhfz.sacw.common.FreemarkerUtil;
import com.zhixin.zhfz.sacw.common.Utils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/urinalysis")
public class UrinalysisController {
    @Autowired
    private UrinalysisService urinalysisService = null;
    @Autowired
    private UrinalysisPhotoService urinalysisPhotoService;
    @Autowired
    private UrinalysisDetailService urinalysisDetailService;
    @Autowired
    private ISerialStatusService serialStatusService;
    @Autowired
    private ISerialService serialService;
    @Autowired
    private IUserService userService;
    private static Logger logger = Logger.getLogger(UrinalysisController.class);

    @RequestMapping(value = "/listUrinalysis")
    @ResponseBody
    public Map<String, Object> listUrinalysis(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( u.main_user_id=" + ControllerTool.getUserID(request) + " or u.op_user_id="
                    + ControllerTool.getUserID(request)
                    +" or u.check_user_id = " + ControllerTool.getUserID(request)
                    +" or c.cjr = " + ControllerTool.getUserID(request)
                    +" or c.zbmj_id = " + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",', c.xbmj_ids)"
                    +" or s.in_register_user_id = " + ControllerTool.getUserID(request)
                    +" or s.out_register_user_id = " + ControllerTool.getUserID(request)
                    +" or s.send_user_id = " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", "( s.area_id=" + ControllerTool.getCurrentAreaID(request)
            +" ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "( s.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
            +" )");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ( s.area_id  " + sessionInfo.getSuperAndSubAreaInStr()
            +" ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( u.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or c.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( u.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or c.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( u.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or c.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<UrinalysisEntity> datas = this.urinalysisService.listUrinalysis(map);
        int count = this.urinalysisService.countUrinalysis(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", datas);
        return result;
    }
    /**
     * 获得嫌疑人当前状态
     */
    @RequestMapping("/getCurrentStatus")
    @ResponseBody
    //@RequestBody WaitingRecordForm
    public MessageEntity getCurrentStatus(@RequestParam long serialId, HttpServletRequest request) {

        try {
            SerialStatusTypeEntity entity = serialStatusService.checkStatus(serialId);
            return new MessageEntity().addCode(1).addIsError(false).addCallbackData(entity);
        } catch (Exception e) {
            logger.error("Error", e);
            return new MessageEntity().addCode(1).addIsError(true);
        }
    }
    /**
     * 新增尿检信息
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/addUrinalysis")
    @ResponseBody
    public MessageEntity addUrinalysis(@RequestBody UrinalysisForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        System.err.print(form);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (sessionInfo == null) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("请重新登陆!");
        }
       SerialEntity serialEntity = null;
        if (form.getSerialId() != null) {
            serialEntity = serialService.queryById(form.getSerialId());
        } else {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("查无此人!");
        }
        UserEntity user = userService.getUserByCertificateNo(form.getCheckUserNo());
        if (user == null) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("民警编号错误!");
        }
        UrinalysisEntity entity = new UrinalysisEntity();
        try {
            PropertyUtils.copyProperties(entity, form);
            entity.setAreaId(serialEntity.getAreaId());
            if (user != null) {
                entity.setCheckUserId(user.getId());
            }
            if (serialEntity != null) {
                entity.setMainUserId(serialEntity.getInUserId1());
                entity.setCaseId(serialEntity.getCaseId());
            }
            Map<String, Object> map = new HashMap<>();
            map.put("serialId", entity.getSerialId());
            entity.setCount(urinalysisService.count(map));
            entity.setUrinalysisTime(new Date());
            entity.setCreatedTime(new Date());
            //添加opPid    opUserId
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            logger.info(entity.toString());
            //插入尿检表
            this.urinalysisService.insertUrinalysis(entity);
            //插入尿检详情表
            if(form.getResult() != null){
                String [] results = form.getResult().split(";");
                List<UrinalysisDetailEntity> urinalysisDetailEntityList = new ArrayList<UrinalysisDetailEntity>();
                if(results != null && results.length >0){
                    for (String reString:results) {
                        UrinalysisDetailEntity urinalysisDetail = new UrinalysisDetailEntity();
                        urinalysisDetail.setUrinalysisId(entity.getId());
                        urinalysisDetail.setCheckUserId(entity.getCheckUserId());
                        urinalysisDetail.setCheckItem( reString.split("呈")[0]);
                        urinalysisDetail.setItemResult( reString.split("呈")[1]);
                        urinalysisDetail.setCreatedTime(new Date());
                        urinalysisDetail.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                        urinalysisDetail.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
                        //添加新增加的字段
                        entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                        entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
                        urinalysisDetailEntityList.add(urinalysisDetail);
                    }
                    if(urinalysisDetailEntityList != null && urinalysisDetailEntityList.size() >0){
                        urinalysisDetailService.insertList(urinalysisDetailEntityList);
                    }
                }
            }
            SerialStatusEntity iss = new SerialStatusEntity();
            iss.setInterrogateSerialId(entity.getSerialId());
            iss.setStatusName("已尿检-"+entity.getResult());
            //添加新字段
            iss.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            iss.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            serialStatusService.insert(iss);
        } catch (Exception e) {
            logger.fatal("Error on adding UrinalysisEntity!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("保存失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("保存成功!").addCallbackData(entity.getId());
    }

    /**
     * 新增尿检照片信息
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/addUrinalysisPhoto")
    @ResponseBody
    public MessageEntity addUrinalysisPhoto(@RequestParam(value="file",required=false) MultipartFile file, Long urinalysisId, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        logger.info("### addUrinalysisPhoto ImageForm= " + urinalysisId);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (sessionInfo == null) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("请重新登陆!");
        }
        if(urinalysisId == null){
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("数据错误，请刷新页面!");
        }
        UrinalysisPhotoEntity urinalysisPhotoEntity = new UrinalysisPhotoEntity();
        String filename =System.currentTimeMillis() + "-nj.jpg";
        try {
            if (StringUtils.isNotEmpty(filename)) {
                urinalysisPhotoEntity.setUrl(filename);
            }
            urinalysisPhotoEntity.setUrinalysisId(urinalysisId);
            urinalysisPhotoEntity.setCreatedTime(new Date());
            //添加新增加的字段
            urinalysisPhotoEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            urinalysisPhotoEntity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            urinalysisPhotoService.insert(urinalysisPhotoEntity,file);
        }catch (Exception e){
            e.printStackTrace();
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("保存失败!"+e);
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("保存成功!");
    }

    /**
     * 修改尿检信息
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/editUrinalysis")
    @ResponseBody
    public MessageEntity editUrinalysis(@RequestBody UrinalysisForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        System.err.print(form);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (sessionInfo == null) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("请重新登陆!");
        }
        SerialEntity serialEntity = null;
        if (form.getSerialId() != null) {
            serialEntity = serialService.queryById(form.getSerialId());
        } else {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("查无此人!");
        }
        UserEntity user = userService.getUserByCertificateNo(form.getCheckUserNo());
        if (user == null) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("民警编号错误!");
        }
        UrinalysisEntity entity = new UrinalysisEntity();
        try {
            PropertyUtils.copyProperties(entity, form);
            entity.setAreaId(serialEntity.getAreaId());
            if (user != null) {
                entity.setCheckUserId(user.getId());
            }
            if (serialEntity != null) {
                entity.setMainUserId(serialEntity.getInUserId1());
                entity.setCaseId(serialEntity.getCaseId());
            }
            Map<String, Object> map = new HashMap<>();
            map.put("serialId", entity.getSerialId());
            entity.setCount(urinalysisService.count(map));
            entity.setUrinalysisTime(new Date());
            entity.setCreatedTime(new Date());
            logger.info(entity.toString());
            //更新尿检表
            this.urinalysisService.updateUrinalysis(entity);
            //插入尿检详情表
            if(form.getResult() != null){
                String [] results = form.getResult().split(";");
                List<UrinalysisDetailEntity> urinalysisDetailEntityList = new ArrayList<UrinalysisDetailEntity>();
                if(results != null && results.length >0){
                    for (String reString:results) {
                        UrinalysisDetailEntity urinalysisDetail = new UrinalysisDetailEntity();
                        urinalysisDetail.setUrinalysisId(entity.getId());
                        urinalysisDetail.setCheckUserId(entity.getCheckUserId());
                        urinalysisDetail.setCheckItem( reString.split("呈")[0]);
                        urinalysisDetail.setItemResult( reString.split("呈")[1]);
                        urinalysisDetail.setCreatedTime(new Date());
                        urinalysisDetail.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                        urinalysisDetail.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
                        urinalysisDetailEntityList.add(urinalysisDetail);
                    }

                    if(urinalysisDetailEntityList != null && urinalysisDetailEntityList.size() >0){
                        urinalysisDetailService.insertList(urinalysisDetailEntityList);
                    }
                }
            }
            SerialStatusEntity iss = new SerialStatusEntity();
            iss.setInterrogateSerialId(entity.getSerialId());
            iss.setStatusName("已尿检-"+entity.getResult());
            //新加字段
            iss.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            iss.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            serialStatusService.insert(iss);
        } catch (Exception e) {
            logger.fatal("Error on adding UrinalysisEntity!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("保存失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("保存成功!");
    }

    //删除尿检信息
    @RequestMapping(value = "/deleteUrinalysis")
    @ResponseBody
    public MessageEntity deleteUrinalysis(@RequestParam("urinalysisId") Long urinalysisId) throws Exception{
        try {
            //删除尿检采集表
            this.urinalysisService.deleteUrinalysisById(urinalysisId);
            //删除尿检详情表
            this.urinalysisDetailService.deleteByUrinalysisId(urinalysisId);
            //删除尿检照片表
            this.urinalysisPhotoService.deleteByUrinalysisId(urinalysisId);
        } catch (Exception e) {
            logger.fatal("Error on delete UrinalysisEntity!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("删除成功!");
    }

    /**
     * 台账下载
     * @param id
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/download")
    public void export(@RequestParam("id") long id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        UrinalysisDocProcessEntity result = urinalysisService.getProcessData(id, request);
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");

        String downFileName = result.getDownFileName();
        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
        } else {
            downFileName = URLEncoder.encode(downFileName, "UTF-8");
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + downFileName);
        String xmlFileName = result.getXmlFileName();
        PrintWriter out = response.getWriter();
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String templatePath = url.getPath() + "template";
        System.out.println("111getData" + result.getData());
        FreemarkerUtil.process(templatePath, xmlFileName, result.getData(), out);
    }
    /**
     * 台账下载base64
     * @param id
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/downloadBase64")
    @ResponseBody
    public MessageEntity exportBase64(@RequestParam("id") long id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        UrinalysisDocProcessEntity result = urinalysisService.getProcessData(id, request);
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");

        String downFileName = result.getDownFileName();
        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
        } else {
            downFileName = URLEncoder.encode(downFileName, "UTF-8");
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + downFileName);
        String xmlFileName = result.getXmlFileName();
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        System.out.println("exportReport getData=" + result.getData());
        String officeDir = BaseConfig.getInstance().getOfficeFileDir();
        String officeFile = officeDir + result.getDownFileName();
        String templatePath = url.getPath() + "template";
        FreemarkerUtil.process(templatePath, xmlFileName, officeFile,result.getData());
        return new MessageEntity().addCallbackData(Base64Util.encodeBase64File(officeFile));
    }
    /**
     * 文件上传
     * @param request
     * @param response
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public MessageEntity upload(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("filedata") MultipartFile file) {
        try{
            Long urinalysisId = Long.parseLong(request.getParameter("urinalysisId"));
            System.err.print(urinalysisId);
            if (null == file || file.isEmpty())
                return new MessageEntity().addIsError(true).addCode(1).addTitle("不能上传空文件！");

            if(null == urinalysisId || urinalysisId.equals(0)){
                return new MessageEntity().addIsError(true).addCode(1).addTitle("上传空文件时，同时上传urinalysisId！");
            }
            String uuid =  Utils.getUniqueId();
            UrinalysisPhotoEntity urinalysisPhotoEntity = new UrinalysisPhotoEntity();
            String filename =System.currentTimeMillis() + "-nj.jpg";
            if (StringUtils.isNotEmpty(filename)) {
                urinalysisPhotoEntity.setUrl(filename);
            }
            urinalysisPhotoEntity.setUrinalysisId(urinalysisId);
            urinalysisPhotoEntity.setCreatedTime(new Date());
            //添加新的两个字段
            urinalysisPhotoEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            urinalysisPhotoEntity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            urinalysisPhotoService.insert(urinalysisPhotoEntity,file);

        } catch (Exception e) {
                e.printStackTrace();
                return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("保存失败!"+e);
        }
            return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("保存成功!");
    }
    /**
     * 获取尿检图片List
     * @param urinalysisId
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPhotoList")
    @ResponseBody
    public List<UrinalysisPhotoEntity> getPhotoList(@RequestParam("urinalysisId") long urinalysisId, HttpServletRequest request) {
        try{
            List<UrinalysisPhotoEntity> urinalysisPhotoEntities = urinalysisPhotoService.getPhotoByUrinalysisId(urinalysisId);
            return urinalysisPhotoEntities;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除尿检图片
     * @param photoId
     * @param request
     * @return
     */
    @RequestMapping(value = "/deletePhotoByPhotoId")
    @ResponseBody
    public MessageEntity deletePhotoByPhotoId(@RequestParam("photoId") long photoId, HttpServletRequest request) {
        try{
            urinalysisPhotoService.deletePhotoById(photoId);
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageEntity().addIsError(true).addCode(1).addTitle("提示").addContent("删除失败"+e);
        }
        return new MessageEntity().addIsError(false).addCode(1).addTitle("提示").addContent("删除成功!");
    }
}
