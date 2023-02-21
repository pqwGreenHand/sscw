package com.zhixin.zhfz.common.controller.signfile;

import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.common.entity.SignFileEntity;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.services.signfile.SignFileService;
import com.zhixin.zhfz.common.entity.MessageEntity;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/common/signfile")
public class SignFileController {
    private static Logger logger = LoggerFactory.getLogger(SignFileController.class);
    @Autowired
    private SignFileService signFileService;
    @Autowired
    private ISerialService serialService;
    @Autowired
    private IFileConfigService fileConfigService;

    /**
     * PDF上传
     * @param file
     * @param serialId
     * @param fileType
     * @param response
     * @return
     */
    @RequestMapping(value = "/uploadPdf")
    @ResponseBody
    public MessageEntity uploadPdf(@RequestParam(value="file",required=false) MultipartFile file, Integer serialId,Integer fileType, HttpServletRequest request
            , HttpServletResponse response){
        if(serialId == null || serialId == 0 ){
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("serialId 不能为0");
        }
        String fileName = "";
        if(fileType == 0){
            //入区台账
            fileName ="违法犯罪嫌疑人进入办案场所凭证" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".pdf";
        }else if(fileType == 1){
            //入区五合一台账
            fileName ="办案区使用情况登记表" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".pdf";
        }else if(fileType == 2){
            //出区台账
            fileName ="涉案人员进出办案区登记台账" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".pdf";
        } else if(fileType == 3){
            //安检台账
            fileName ="违法犯罪嫌疑人人身安全检查情况登记表" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".pdf";
        } else if(fileType == 4){
            //尿检登记台账
            fileName ="尿检登记台账" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".pdf";
        } else if(fileType == 5){
            //随身物品存物台账
            fileName ="随身物品登记" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".pdf";
        }else if(fileType == 6){
            //涉案物品存物台账
            fileName ="涉案物品登记" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".pdf";
        }else if(fileType == 7){
            //民警物品存物台账
            fileName ="民警随身物品登记" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".pdf";
        }else if(fileType == 8){
            //随身物品取物台账
            fileName ="随身物品登记" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".pdf";
        }else if(fileType == 9){
            //涉案物品取物台账
            fileName ="涉案物品登记" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".pdf";
        }else if(fileType == 10){
            //民警物品取物台账
            fileName ="民警随身物品登记" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".pdf";
        }else if(fileType == 11){
            //涉案人员五合一台账
            fileName ="涉案人员五合一台账" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".pdf";
        }
        SerialEntity serial = new SerialEntity();
        serial.setId(Long.parseLong(serialId+""));
        serial = this.serialService.getSerialById(serial);
        try{
            SignFileEntity serialFileEntity = new SignFileEntity();
            serialFileEntity.setFileName(fileName);
            serialFileEntity.setSerialId(serialId);
            serialFileEntity.setFileType(fileType);
            logger.info("++++++++SerialFileEntity:++++++++="+serialFileEntity.toString());
            signFileService.insertSerialFile(serialFileEntity);
            fileConfigService.upload(FileUploadForm.of("ba", FileUploadForm.FILETYPRQZ, serial.getUuid(), serial.getAreaId(), fileName, file));
            return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("文件上传成功!");
        }catch (Exception e){
            e.printStackTrace();
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("文件上传失败!").addCallbackData(e.getMessage());
        }
    }

    /**
     * 获取最新的PDF文件
     * @param serialId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getNewPdf")
    @ResponseBody
    public MessageEntity getNewPdf(@RequestParam Integer serialId, HttpServletRequest request, Integer fileType,HttpServletResponse response) throws Exception {
        SignFileEntity record = new SignFileEntity();
        record.setSerialId(serialId);
        record.setFileType(fileType);
        SignFileEntity serialFileEntity = signFileService.getLastFileBySerialAndType(record);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            if(serialFileEntity != null && serialFileEntity.getId() != null ){
                resultMap.put("entity",serialFileEntity);
                SerialEntity serialEntity = new SerialEntity();
                serialEntity.setId(Long.parseLong(serialId + ""));
                serialEntity = serialService.getSerialById(serialEntity);
                if(serialEntity != null && serialEntity.getId() != null){
                    try {
                        String fileBase64 = fileConfigService.getFileBase64(FileUploadForm.of("ba", FileUploadForm.FILETYPRQZ, serialEntity.getUuid(), serialEntity.getAreaId(), serialFileEntity.getFileName()));
                        resultMap.put("fileBase64",fileBase64);
                    } catch( Exception e){
                        System.err.println("无此文件");
                    }
                }
                return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("文件获取成功!").addCallbackData(resultMap);
            } else{
                return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("未进行签字，请先在设备上签字并提交");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("系统错误!"+e);
        }
    }

    /**
     * 获取签章图片
     * @param serialId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSignImage")
    @ResponseBody
    public MessageEntity getSignImage(@RequestParam Long serialId) throws Exception {

//        SignFileEntity serialFileEntity = signFileService.getLastFileBySerialAndType(serialId);
//        try{
////            if(serialFileEntity != null && serialFileEntity.getId() != null ){
////                return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("文件获取成功!").addCallbackData(serialFileEntity);
////            } else{
////                return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("未进行签字，请先在设备上签字并提交");
////            }
//        } catch (Exception e){
//            e.printStackTrace();
//            return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("系统错误!"+e);
//        }
        return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("系统错误!");
    }


    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> map = ControllerTool.mapFilter(params);
        List<SignFileEntity> signFileEntityList = signFileService.getSignatureList(map);
//        int count = signFileService.getSignatureListCount(map);
        result.put("total", signFileEntityList.size());
        result.put("rows", signFileEntityList);
        return result;
    }
}
