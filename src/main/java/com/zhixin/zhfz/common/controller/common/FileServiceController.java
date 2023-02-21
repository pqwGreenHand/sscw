package com.zhixin.zhfz.common.controller.common;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.form.FileDownloadForm;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * @program: zhfz
 * @description:
 * @author: jzw
 * @create: 2019-03-11 17:58
 **/
@Controller
@RequestMapping("/zhfz/common/file")
public class FileServiceController  {

    @Autowired
    private IFileConfigService service;


    @RequestMapping("/upload")
    public MessageEntity upload(FileUploadForm form){
        return null;
    }

    @RequestMapping("/download")
    public String download(FileDownloadForm form){
        return service.download(form);
    }

}
