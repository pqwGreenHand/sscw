package com.zhixin.zhfz.sacw.controller.common;

import com.zhixin.zhfz.common.common.FileUtil;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.sacw.common.Utils;
import com.zhixin.zhfz.sacw.entity.InRecordPhotoEntity;
import com.zhixin.zhfz.sacw.entity.OutRecordPhotoEntity;
import com.zhixin.zhfz.sacw.services.inrecordphoto.IInRecordPhotoService;
import com.zhixin.zhfz.sacw.services.outrecordphoto.IOutRecordPhotoService;
import com.zhixin.zhfz.sacw.services.unowner.IUnOwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/sacw")
public class InvImageController {

    @Autowired
    private IUnOwnerService unOwnerService;

    @Autowired
    private IInRecordPhotoService inRecordPhotoService;

    @Autowired
    private IFileConfigService fileConfigService;

    @Autowired
    private IOutRecordPhotoService outRecordPhotoService;

    private static final Logger logger = LoggerFactory.getLogger(InvImageController.class);

    @RequestMapping(value = "/imageshow")
    @ResponseBody
    public void showImage(@RequestParam Map<String, Object> params, HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        try {
            logger.info("======================params.get path========================" + params.get("path"));
            String uuid = request.getParameter("uuid");
            String fileName = request.getParameter("fileName");
            String warehouseId = request.getParameter("warehouseId");


            FileUploadForm form = new FileUploadForm();
            form.setSysType("sa");
            form.setFileType("sa");
            form.setUuid(uuid);
            form.setSysId(warehouseId);
            form.setFileName(fileName);
            fileConfigService.download(form);


        } catch (Exception e) {
            logger.error("查询照片异常：", e);
        }
    }

    @RequestMapping(value = "/getpicture")
    @ResponseBody
    public void getpicture(@RequestParam Map<String, Object> params1, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("===================getpicture=====图片上传 =");
        long involvedId = 0;
        long recordId = 0;
        int type = 0;
        String warehouseId = null;
        String uuid = null;
        if (request.getParameter("involvedId") != null) {
            involvedId = Long.parseLong(request.getParameter("involvedId"));
        }
        if (request.getParameter("recordId") != null) {
            recordId = Long.parseLong(request.getParameter("recordId"));
        }
        if (request.getParameter("type") != null) {
            type = Integer.parseInt(request.getParameter("type"));
        }
        if (request.getParameter("uuid") != null) {
            uuid = request.getParameter("uuid");
        }
        if (request.getParameter("warehouseId") != null) {
            warehouseId = request.getParameter("warehouseId");
        }

        try {
            logger.info("========================图片上传 involvedId=" + involvedId + ";recordId=" + recordId + ";type="
                    + type);
            // 创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            FileUploadForm form = new FileUploadForm();
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                // 转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    // 记录上传过程起始时的时间，用来计算上传时间
                    int pre = (int) System.currentTimeMillis();
                    // 取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        // 取得当前上传文件的文件名称
                        String myFileName = file.getOriginalFilename();
                        logger.info("========================图片上传 myFileName=" + myFileName);
                        // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                        if (!"".equals(myFileName.trim())) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
							/*String path = "";
							if (type == 1) {// 入库
								path = PropertyUtil.getContextProperty("inputRecordPhotoSavePath").toString()
										+ Utils.getDatePath() + "/";
							} else {
								path = PropertyUtil.getContextProperty("outputRecordPhotoSavePath").toString()
										+ Utils.getDatePath() + "/";
							}*/


                            //	FileUtil.createDir(path);


                            String filename = "";
                            if (type == 1) {// 入库
                                filename = "input-" + dateFormat.format(new java.util.Date()) + ".jpg";
                            } else {
                                filename = "output-" + dateFormat.format(new java.util.Date()) + ".jpg";
                            }
							/*String spath = path + filename;
							logger.info("========================图片上传spath=" + spath);
							File localFile = new File(path, filename);
							file.transferTo(localFile);
							logger.info("========================图片上传create file=");*/
                            form.setSysType("sa");
                            form.setFileType("sa");
                            form.setUuid(uuid);
                            form.setSysId(warehouseId);
                            form.setFileName(filename);
                            form.setFile(file);
                            fileConfigService.upload(form);
                            if (type == 1) {// 插入一笔拍照记录
                                InRecordPhotoEntity inRecordPhotoEntity = new InRecordPhotoEntity();
                                inRecordPhotoEntity.setInvolvedId(involvedId);
                                inRecordPhotoEntity.setInputRecordId(recordId);
                                inRecordPhotoEntity.setUuid(uuid);
                                inRecordPhotoEntity.setFileName(filename);
                                inRecordPhotoEntity.setInputRecordId(recordId);
                                inRecordPhotoEntity.setUrl("3333");
                                inRecordPhotoEntity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                                inRecordPhotoEntity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());

                                inRecordPhotoService.insertinRecordPhoto(inRecordPhotoEntity);
                            } else {
                                OutRecordPhotoEntity outRecordPhotoEntity = new OutRecordPhotoEntity();
                                outRecordPhotoEntity.setInvolvedId(involvedId);
                                outRecordPhotoEntity.setFileName(filename);
                                outRecordPhotoEntity.setUuid(uuid);
                                outRecordPhotoEntity.setOutputRecordId(recordId);
                                outRecordPhotoEntity.setUrl("3333");
                                outRecordPhotoEntity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                                outRecordPhotoEntity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
                                outRecordPhotoService.insertOutRecordPhoto(outRecordPhotoEntity);
                            }

                        }
                    }
                    // 记录上传该文件后的时间
                    int finaltime = (int) System.currentTimeMillis();
                    System.out.println(finaltime - pre);
                }

            } else {
                logger.info("========================图片上传错误 isNotMultipart=");
            }
            // 图片结束
        } catch (Exception e) {
            logger.error("### 图片上传错误  ###", e);
        }
    }
}




