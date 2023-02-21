package com.zhixin.zhfz.bacs.controller.iriscollection;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacs/iriscollection")
public class ImageShowController {


    @RequestMapping(value = "/imageshow")
    @ResponseBody
    public void imageshow(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response){
        try {
            String filepath = null;
            String path = params.get("path").toString();
//            String path = "D:/test.jpg";
            File file = new File(path);
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            if (!file.exists()) {
                System.err.println("无此照片");
            } else {
//                fis = new FileInputStream(file);
//                int i = fis.available(); // 得到文件大小
//                System.out.println(i);
//                byte data[] = new byte[i];
//                fis.read(data); // 读数据
//                fis.close();
//                response.setContentType("image/*"); // 设置返回的文件类型
//                OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
//                toClient.write(data); // 输出数据
//                toClient.close();


                String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
                try {
                    response.setContentType("application/force-download");// 设置强制下载不打开            
                    response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                    byte[] buffer = new byte[1024];
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream outputStream = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        outputStream.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
