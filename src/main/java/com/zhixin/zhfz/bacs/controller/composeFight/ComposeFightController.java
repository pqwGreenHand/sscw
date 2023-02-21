package com.zhixin.zhfz.bacs.controller.composeFight;

import com.zhixin.zhfz.bacs.controller.arraign.ArraignController;
import com.zhixin.zhfz.bacs.controller.serial.Face1v1Result;
import com.zhixin.zhfz.bacs.controller.serial.Face1vNResult;
import com.zhixin.zhfz.bacs.entity.OneToOneForm;
import com.zhixin.zhfz.bacs.entity.ResultEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.entity.SerialVideoMappingEntity;
import com.zhixin.zhfz.bacs.form.ComposRepsForm;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.common.HttpClientUtil;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.sacw.common.ImageUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @program: zhfz
 * @description: 合成作战
 * @author: xcf
 * @create: 2019-05-27 17:56
 **/
@Controller
@RequestMapping("/zhfz/bacs/composeFight")
public class ComposeFightController {

    private static Logger logger = LoggerFactory.getLogger(ArraignController.class);

    @Autowired
    private ISerialService serialService;
    @Autowired
    private IFileConfigService fileConfigService;

    @RequestMapping("/1v1Compare")
    @ResponseBody
    public Face1v1Result oneToOneCompare(@RequestBody OneToOneForm form, HttpServletRequest request) {

        try {
            // 然后调用人像接口进行1:1比对
            String path = (String) PropertyUtil.getContextProperty("combinePhotoSavePath");
            String queryImageBase64 = "";
            String dataImageBase64 = "";
            try {
                queryImageBase64 = ImageUtil.yituGetImageStr(path + form.getPath1());
                dataImageBase64 = ImageUtil.yituGetImageStr(path + form.getPath2());
                System.err.println(path + form.getPath1());
                System.err.println(path + form.getPath2());
            } catch (Exception e) {
                logger.info("找不到照片");
                throw e;
            }

            logger.info("合成queryImageBase64----------" + queryImageBase64);
            logger.info("合成dataImageBase64----------" + dataImageBase64);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("image_base64_1", "--query_base_64--");// 查询图片(JPG)的Base64编码
            jsonObject.put("image_base64_2", "--database_base_64--");// 登记照片(JPG)的Base64编码
            String json_1v1 = jsonObject.toString();
            json_1v1 = json_1v1.replaceAll("--query_base_64--", queryImageBase64);
            json_1v1 = json_1v1.replaceAll("--database_base_64--", dataImageBase64);
            logger.info("### 人像1V1 faceImgOneToOne json_input= " + json_1v1);
            String resultJson = HttpClientUtil.faceImgOneToOne(json_1v1);
            logger.info("### 人像1V1 faceImgOneToOne reponse= " + resultJson);
            JSONObject json = JSONObject.fromObject(resultJson);
            String message = json.getString("message");
            Face1v1Result face1v1Result = new Face1v1Result();
            if ("OK".equals(message)) {
                String similarity = json.getString("similarity");// 相似度
                face1v1Result.setSimilarity(similarity);
                logger.info("### 人像1V1 face1v1Result= " + face1v1Result);
            } else {
                face1v1Result.setSimilarity("0");
            }
            return face1v1Result;
        } catch (Exception e) {
            Face1v1Result face1v1Result = new Face1v1Result();
            face1v1Result.setSimilarity("0");
            logger.info("### 人像1V1 face1v1Result= " + face1v1Result);
            e.printStackTrace();
            return face1v1Result;

        }

    }


    /**
     * 人像对比
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/personIngetpicture")
    @ResponseBody
    public MessageEntity personIngetpicture(@RequestBody SerialEntity serial, HttpServletRequest request
            , HttpServletResponse response) throws Exception {
        //Map<String, Object> map = ControllerTool.mapFilter(param);
        Map<String,Object> reaultMap = new HashMap<String,Object>();
        try{

            // 首先查询在逃前科库，进行1:N比对,1:N结束后页面显示搜索结果
            List<Face1vNResult> templist = new ArrayList<Face1vNResult>();
            try {
                Face1vNResult renKouKuResult = new Face1vNResult();
                Face1vNResult qianKeKuResult = new Face1vNResult();
                logger.info("### 人口库与前科库比对Start= " + serial.getCertificateTypeId());
                if (serial != null && serial.getCertificateTypeId() == 111) {
                    String token = PropertyUtil.getContextProperty("population.token").toString();
                    renKouKuResult = renKouKu(serial.getCertificateNo(), token, request);
                    logger.info("### 人口库结果= " + renKouKuResult);
                    qianKeKuResult = qianKeKu(serial);
                    logger.info("### 前科库结果= " + qianKeKuResult);
                }
                try {
                    //在逃库比对
//                    templist = faceImgOneToN(serial.getSerialNo(), request,serial);
                } catch (Exception e) {
                    logger.info("-------", e);
                    //人口库
                    if(renKouKuResult != null){
                        logger.info("进入人口库------1-----");
                        reaultMap.put("personWarehouse",renKouKuResult);
                        //获取人口库图片 和入区照片比对
                        String renkouImage = renKouKuResult.getFace_image_url();
                        logger.info("----人口照片renkouImage---"+renkouImage);
                        String queryImageBase64 = fileConfigService.getFileBase64(FileUploadForm.of("ba", FileUploadForm.FILETYPRRQ, serial.getUuid(), serial.getAreaId(), serial.getInPhotoId()));
                        logger.info("----人口照片11queryImageBase64---"+queryImageBase64.replaceAll("\r|\n","").replaceAll("\n","").replaceAll("\r",""));
                        logger.info("----人口照片22queryImageBase64---"+queryImageBase64.replaceAll("\\r\\n","").replaceAll("\\n","").replaceAll("\\r",""));
                        Face1v1Result oneToOneCompare = oneToOneCompare1(renkouImage,queryImageBase64);
                        logger.info("人口入区对比成功--1---"+oneToOneCompare);
                        reaultMap.put("onevOneResult",oneToOneCompare);
                    }
                    //前科库
                    if(qianKeKuResult != null){
                        reaultMap.put("oldWarehouse",qianKeKuResult);
                    }
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("提醒").addContent("照片比对失败！").addCallbackData(reaultMap);
                }
                //人口库
                if(renKouKuResult != null){
                    logger.info("进入人口库------2-----");
                    reaultMap.put("personWarehouse",renKouKuResult);
                    //获取人口库图片 和入区照片比对
                    logger.info("进入人口库------3-----");
                    String renkouImage = renKouKuResult.getFace_image_url();
                    logger.info("----人口照片1renkouImage1---"+renkouImage);
                    String queryImageBase64 = fileConfigService.getFileBase64(FileUploadForm.of("ba", FileUploadForm.FILETYPRRQ, serial.getUuid(), serial.getAreaId(), serial.getInPhotoId()));
                    logger.info("----人口照片11queryImageBase64---"+queryImageBase64.replaceAll("\r|\n","").replaceAll("\n","").replaceAll("\r",""));
                    logger.info("----人口照片22queryImageBase64---"+queryImageBase64.replaceAll("\\r\\n","").replaceAll("\\n","").replaceAll("\\r",""));
                    Face1v1Result oneToOneCompare = oneToOneCompare1(renkouImage,queryImageBase64);
                    logger.info("人口入区对比成功--2---"+oneToOneCompare);
                    reaultMap.put("onevOneResult",oneToOneCompare);
                }
                //前科库
                if(qianKeKuResult != null){
                    reaultMap.put("oldWarehouse",qianKeKuResult);
                }
                //在逃库
                if(templist != null && templist.size() > 0){
                    reaultMap.put("largeWarehouse",qianKeKuResult);
                }
                if (reaultMap.size() > 0) {// 成功返回比对结果
                    return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("照片比对成功！").addCallbackData(reaultMap);
                } else {
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("照片比对失败!") ;
                }
            } catch (Exception e) {
                logger.info(e + "");
                return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("照片比对失败!") ;
            }
        }catch (Exception e){
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("照片比对失败!") ;
        }
    }


    public Face1v1Result oneToOneCompare1(String renkouImage , String queryImageBase64){

        try {

            logger.info("进入1v1比对信息------");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("image_base64_1", "--query_base_64--");// 查询图片(JPG)的Base64编码
            jsonObject.put("image_base64_2", "--database_base_64--");// 登记照片(JPG)的Base64编码
            String json_1v1 = jsonObject.toString();
            json_1v1 = json_1v1.replaceAll("--query_base_64--", renkouImage.substring(22, renkouImage.length()));
            json_1v1 = json_1v1.replaceAll("--database_base_64--", queryImageBase64.replaceAll("\\r\\n",""));
            logger.info("### 人像1V1 faceImgOneToOne json_input= " + json_1v1);
            String resultJson = HttpClientUtil.faceImgOneToOne(json_1v1);
            logger.info("### 人像1V1 faceImgOneToOne reponse= " + resultJson);
            JSONObject json = JSONObject.fromObject(resultJson);
            String message = json.getString("message");
            Face1v1Result face1v1Result = new Face1v1Result();
            if ("OK".equals(message)) {
                String similarity = json.getString("similarity");// 相似度
                face1v1Result.setSimilarity(similarity);
                logger.info("### 人像1V1 face1v1Result= " + face1v1Result);
            }else{
                face1v1Result.setSimilarity("0");
            }
            return face1v1Result;
        } catch (Exception e) {
            Face1v1Result face1v1Result = new Face1v1Result();
            face1v1Result.setSimilarity("0");
            logger.info("### 人像1V1 face1v1Result= " + face1v1Result);
            e.printStackTrace();
            return face1v1Result;

        }

    }

    private Face1vNResult qianKeKu(SerialEntity serial) throws Exception {
        String url = PropertyUtil.getContextProperty("qianke.url") + "?sfzh=" + serial.getCertificateNo();
        logger.info("前科库 url= " + url);
        Face1vNResult person = new Face1vNResult();
        try {
            String resultJson = HttpClientUtil.query(url);
            logger.info("### 前科库 reponse= " + resultJson);
            if (resultJson != null && !"".equals(resultJson)) {
                person.setGender((serial.getSex() + 1) + "");
                person.setName(serial.getName());
                person.setPerson_id(serial.getCertificateNo());
                person.setFace_image_url(resultJson + "&id=0");
                person.setBorn_year(serial.getCertificateNo().substring(6, 10));
                person.setSimilarity("100");
                logger.info("前科库 person= " + person);
            } else {
                throw new Exception("前科库反馈非正确结果" + resultJson);
            }
            return person;
        } catch (Exception e) {
            logger.info("前科库反馈非正确结果" + person);
            return person;
        }
    }


    private Face1vNResult renKouKu(String certificateNo, String token, HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sfzh", certificateNo);
        jsonObject.put("token", token);

        String json_1vn = jsonObject.toString();
        logger.info("### 人口库renKouKu json_input= " + json_1vn);
        String url = PropertyUtil.getContextProperty("population.url") + "?sfzh=" + certificateNo + "&token=" + token;
        // String
        // url="http://10.11.229.141:8080/wsquery-web/wsquery?sfzh="+certificateNo+"&token="+token;
        logger.info("人口库renKouKu url= " + url);
        Face1vNResult person = new Face1vNResult();
        try {
            String resultJson = HttpClientUtil.renKouKu(url, "");

            logger.info("### 人口库renKouKu reponse= " + resultJson);
            if (resultJson != null && !"".equals(resultJson)) {
                JSONObject json = JSONObject.fromObject(resultJson);
                String name = json.getString("name");
                String sex = json.getString("sex");
                String birthDate = json.getString("birthDate");
                String address = json.getString("address");
                String imgData = "data:image/png;base64," + json.getString("imgData");

                person.setGender(sex);
                person.setName(name);
                person.setPerson_id(certificateNo);
                person.setFace_image_url(imgData);
                person.setBorn_year(certificateNo.substring(6, 10));
                person.setAddress(address);

                logger.info("人口库renKouKu person= " + person);
            } else {
                throw new Exception("人口库反馈非正确结果" + resultJson);
            }
            return person;
        } catch (Exception e) {
            logger.info("人口库反馈非正确结果" + person);
            return person;
        }
    }

    //获取前台图片
    @RequestMapping(value = "/uploadImage")
    @ResponseBody
    public ComposRepsForm uploadPicture1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String certificateNo = request.getParameter("personalcertificateNo");
        System.err.println("=====上传图片11111111111111111111111111111=====");
        String finalPath="";
        String filename1 = "";
        ComposRepsForm composRepsForm = new  ComposRepsForm();
        // 图片开始
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            //while (iter.hasNext()) {
            // 记录上传过程起始时的时间，用来计算上传时间
            int pre = (int) System.currentTimeMillis();
            // 取得上传文件
            MultipartFile file = multiRequest.getFile("file");
            MultipartFile file1 = multiRequest.getFile("file1");
            if (file != null) {
                // 取得当前上传文件的文件名称
                String myFileName = file.getOriginalFilename();
                // 如果名称不为“”,说明该文件存在，否则说明该文件不存在

                if (null!= myFileName && !"".equals(myFileName.trim())) {
                    System.out.println("myFileName1=" + myFileName);
                    // 重命名上传后的文件名
                    //serialNo = myFileName.substring(0, 16);
                    String myNewFileName = "imageFirst"+System.currentTimeMillis()+".jpg";
                    String path = (String)PropertyUtil.getContextProperty("combinePhotoSavePath");
                    File localFile = new File(path, myNewFileName);
                    file.transferTo(localFile);
                    finalPath=path+myNewFileName;
                    filename1= myNewFileName;
                    composRepsForm.setImgSrc(filename1);
                }
            }

        }

        System.err.println("==========myFileName========="+finalPath);
        return composRepsForm;
    }

    //获取前台图片
    @RequestMapping(value = "/uploadImageOne")
    @ResponseBody
    public ComposRepsForm uploadPictureOne(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.err.println("=====上传图片11111111111111111111111111111=====");
        String finalPath="";
        String filename2 = "";
        ComposRepsForm composRepsForm = new  ComposRepsForm();
        // 图片开始
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            //while (iter.hasNext()) {
            // 记录上传过程起始时的时间，用来计算上传时间
            int pre = (int) System.currentTimeMillis();
            // 取得上传文件
            MultipartFile file1 = multiRequest.getFile("file1");
            if (file1 != null) {
                // 取得当前上传文件的文件名称
                String myFileName = file1.getOriginalFilename();
                // 如果名称不为“”,说明该文件存在，否则说明该文件不存在

                if (null!= myFileName && !"".equals(myFileName.trim())) {
                    System.out.println("myFileName2=" + myFileName);
                    // 重命名上传后的文件名
                    String myNewFileName = "imageSecond"+System.currentTimeMillis()+".jpg";
                    String path = (String)PropertyUtil.getContextProperty("combinePhotoSavePath");
                    File localFile = new File(path, myNewFileName);
                    file1.transferTo(localFile);
                    finalPath=path+myNewFileName;
                    filename2= myNewFileName;
                    composRepsForm.setImgSrc(filename2);
                }
            }

        }

        System.err.println("==========myFileName========="+finalPath);
        return composRepsForm;
    }


    @RequestMapping(value = "/imageshow")
    @ResponseBody
    public void showImage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            if("combinePhotoSavePath".equals(params.get("path"))){
                System.err.println("-----combinePhotoSavePath------");
                FileInputStream is = new FileInputStream((PropertyUtil.getContextProperty("combinePhotoSavePath")).toString()+params.get("image"));
                int i = is.available(); // 得到文件大小
                System.out.println(i);
                byte data[] = new byte[i];
                is.read(data); // 读数据
                is.close();
                response.setContentType("image/*"); // 设置返回的文件类型
                OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
                toClient.write(data); // 输出数据
                toClient.close();
            }
            if("imageComparePhotoSavePath".equals(params.get("path"))){
                System.err.println("-----imageComparePhotoSavePath------");
                FileInputStream is = new FileInputStream((PropertyUtil.getContextProperty("imageComparePhotoSavePath")).toString()+params.get("image"));
                int i = is.available(); // 得到文件大小
                System.out.println(i);
                byte data[] = new byte[i];
                is.read(data); // 读数据
                is.close();
                response.setContentType("image/*"); // 设置返回的文件类型
                OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
                toClient.write(data); // 输出数据
                toClient.close();
            }

        } catch (Exception e) {
            logger.error("", e);
        }
    }

}



