package com.zhixin.zhfz.bacs.controller.rpc;

import com.zhixin.zhfz.bacs.entity.RecognizeFlagConfigEntity;
import com.zhixin.zhfz.bacs.entity.RecognizePersonConfigEntity;
import com.zhixin.zhfz.bacs.services.rpc.IRecognizeConfigService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.form.FileDownloadForm;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 人员图片管理
 * @author: jzw
 * @create: 2019-02-26 10:55
 **/

@Controller
@RequestMapping("/zhfz/bacs/rpc")
public class RecognizeConfigController {

    private static final Logger logger = LoggerFactory.getLogger(RecognizeConfigController.class);

    @Autowired
    private IRecognizeConfigService service;

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IFileConfigService fileConfigService;



    /**
     * @Author jzw
     * @Description
     * @Date 11:45 2019/2/21
     * @Param [param]
     * @return java.util.Map<java.lang.String , java.lang.Object>
     **/
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String,Object> list(@RequestParam Map<String,Object> param)throws Exception{
        Map<String, Object> map = ControllerTool.mapFilter(param);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.service.personCount(map));
        result.put("rows", service.personList(map));
        return result;
    }

    /**
     * @Author jzw
     * @Description 增加
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/imgAdd")
    @ResponseBody
    public MessageEntity add(@RequestParam(value="files",required=false) MultipartFile[] files, HttpServletRequest request) throws IOException {
        FileDownloadForm form = new FileDownloadForm();
        form.setSysType("br");
        form.setSysId("0");
        String url = fileConfigService.download(form) + "/BRUploadPhoto";
        String error = "出错文件:";
        for (MultipartFile file : files){
            try {
                service.insert(file,url);
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加人员图片管理：人员相片"  , ControllerTool.getLoginName(request), true,"办案场所");
            } catch (Exception e) {
                logger.error("Error on adding RecognizePersonConfig!", e);
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加人员图片管理：人员相片"  , ControllerTool.getLoginName(request), false,"办案场所");
                error += "\n" + file.getOriginalFilename();
            }
        }
        if(!error.equals("出错文件:")){
            return MessageEntity.error(error);
        }
        return MessageEntity.success("添加成功!");
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
    public MessageEntity edit( RecognizePersonConfigEntity entity, HttpServletRequest request){
        logger.info("++++++++edit++++++" + entity);
        try {
            service.personUpdate(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改人员图片管理：" + entity , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on editing RecognizePersonConfig!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改人员图片管理：" + entity , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("更新失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("更新成功!");
    }

    /**
     * @Author jzw
     * @Description 删除
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/remove")
    @ResponseBody
    public MessageEntity remove(Long id,HttpServletRequest request){
        logger.info("++++++++remove++++++RecognizePersonConfigEntityId=" + id);
        try {
            service.personDelete(id);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除人员图片管理：" + id , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on removing RecognizePersonConfig!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除人员图片管理：" + id , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("删除成功!");
    }

    /**
     * 下拉
     * @return
     */
    @RequestMapping("/selectFlagAll")
    @ResponseBody
    public List<RecognizeFlagConfigEntity> selectFlagAll(Map<String,Object> params){

        return service.selectFlagAll(params);

    }



}
