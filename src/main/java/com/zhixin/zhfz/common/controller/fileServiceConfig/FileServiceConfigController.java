package com.zhixin.zhfz.common.controller.fileServiceConfig;

import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.FileDownloadForm;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 文件服务
 * @author: jzw
 * @create: 2019-02-26 10:55
 **/

@Controller
@RequestMapping("/zhfz/common/fileServiceConfig")
public class FileServiceConfigController {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceConfigController.class);

    @Autowired
    private IFileConfigService service;

    @Autowired
    private IOperLogService operLogService;



    /**
     * @Author jzw
     * @Description
     * @Date 11:45 2019/2/21
     * @Param [param]
     * @return java.util.Map<java.lang.String , java.lang.Object>
     **/
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String,Object> list(@RequestParam Map<String,Object> param, HttpServletRequest request)throws Exception{
        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<FileServiceConfigEntity> list = new ArrayList<FileServiceConfigEntity>();
        int total = 0;
        boolean flag = true;

        /*SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 本部门及下级部门
            map.put("dataAuth","f.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 上级部门及下级部门
            map.put("dataAuth","f.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 本部门
            map.put("dataAuth","f.op_pid = " + sessionInfo.getCurrentOrgPid());
        } else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForXtConf(request))){
            //全部
        }else if(RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForXtConf(request))){
            //本人
            map.put("dataAuth","f.op_user_id = " + ControllerTool.getUserID(request));
        }else{
            flag = false;
        }*/

        if (flag) {
//            map.put("usePage", true);
            list = this.service.list(map);
            total = this.service.count(map);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", list);
        return result;
    }

    /**
     * @Author jzw
     * @Description 增加
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/add")
    @ResponseBody
    public MessageEntity add(FileServiceConfigEntity entity, HttpServletRequest request){
        logger.info("++++++++add++++++" + entity);
        try {
//            entity.setRegisterUserId(Long.valueOf(ControllerTool.getUserID(request)));
            entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
            service.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加文件服务：" + entity , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on adding FileServiceConfig!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加文件服务：" + entity , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
    }

    /**
     * @Author jzw
     * @Description 修改
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/edit")
    @ResponseBody
    public MessageEntity edit( FileServiceConfigEntity entity, HttpServletRequest request){
        logger.info("++++++++edit++++++" + entity);
        try {
            service.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改文件服务：" + entity , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on editing FileServiceConfig!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改文件服务：" + entity , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("更新失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("更新成功!");
    }
    /**
     * @Author jzw
     * @Description 修改
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/updEnable")
    @ResponseBody
    public MessageEntity updEnable(Long id,Integer enable, HttpServletRequest request){
        logger.info("++++++++updEnable++++++" );
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("valid",enable);
        try {
            service.updateEnable(map);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改文件服务可用状态：" + map , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on editing FileServiceConfig!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改文件服务可用状态：" + map , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("更新失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("更新成功!");
    }

    /**
     * @Author jzw
     * @Description 问题删除
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/remove")
    @ResponseBody
    public MessageEntity remove(Long id,HttpServletRequest request){
        logger.info("++++++++remove++++++FileServiceConfigEntityId=" + id);
        try {
            service.delete(id);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除文件服务：" + id , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on removing FileServiceConfig!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除文件服务：" + id , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("删除失败!");
        }
        return new MessageEntity().addCode(0).addIsError(false).addTitle("提示").addContent("删除成功!");
    }

    @RequestMapping("/url")
    @ResponseBody
    public MessageEntity getUrl(FileDownloadForm form,HttpServletRequest request){
        String url =  service.download(form);
        return new MessageEntity().addCode(0).addIsError(false).addTitle("提示").addContent("文件路径获取成功!").addCallbackData(url);
    }

    /**
     * 首页文件下载
     * @param filename
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/download")
    public void  download(@RequestParam String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String filePath = (String) PropertyUtil.getContextProperty("plugins.download.path") + File.separator + filename;
        File file = new File(filePath);
        if (file.exists()) {
            OutputStream out = null;
            try {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition",
                        "attachment; " + "filename=" + URLEncoder.encode(filename, "UTF-8"));
                out = response.getOutputStream();
                out.write(FileUtils.readFileToByteArray(new File(filePath)));
                out.flush();

            } catch (Exception e) {
                logger.error("", e);
                throw new Exception("下载失败！请联系管理员！"+e);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                }
            }
        }else{
            throw new Exception("文件不存在，下载失败！请联系管理员！");
        }
    }
}
