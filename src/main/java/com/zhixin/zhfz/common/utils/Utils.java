package com.zhixin.zhfz.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     *
     * @param filePath
     */
    public static List<String> readTxtFile(String filePath) {
        List<String> result = new ArrayList<>();
        try {
            String encoding = "Utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (lineTxt != null && !"".equals(lineTxt)) {
                        result.add(lineTxt);
                    }
                }
                read.close();
            } else {
                logger.info("找不到指定的文件");
            }
        } catch (Exception e) {
            logger.error("读取文件内容出错", e);
        }
        return result;
    }

    public static String encodeStr(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
        return null;
    }

    public static String getUUId() {
        return UUID.randomUUID().toString();
    }

    public static final String getUniqueId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static final String getDatePath() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static final String getDateFromSerialNO(String serialNo) {
        if (serialNo.startsWith("XYR")) {
            return serialNo.substring(3, 11);
        }
        return serialNo.substring(2, 10);
    }

    public static void main(String[] args) {
        String path = null;
        try {
            path = Utils.class.getClassLoader().getResource("").toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        List<String> lawDocList = Utils.readTxtFile(path + "lawdoc_list.txt");
        for (String str : lawDocList) {
            System.out.println(str);
        }

//        System.out.println(getUUId().replace("-", ""));
        //System.out.println(getUniqueId());
//    	System.out.println(getDateFromSerialNO("XYR20150929141800896"));
//    	System.out.println(getDateFromSerialNO("zq20150929141800896"));
    }
}
