package com.zhixin.zhfz.common.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;


/**
 * 
 * @author Richard
 *
 */
public class ConfigJsonUtil {

	private static final Logger logger = LoggerFactory.getLogger(ConfigJsonUtil.class);

	private static final String encoding = "utf-8";

	public static JSONArray readJsonArray(String fileName) {
		StringBuilder sb=readFile(fileName);
		return JSON.parseArray(sb.toString());
	}
	
	public static JSONObject readJsonObject(String fileName) {
		StringBuilder sb=readFile(fileName);
		return JSON.parseObject(sb.toString());
	}
	
	private static synchronized StringBuilder readFile(String fileName){
		URL url= ConfigJsonUtil.class.getClassLoader().getResource(fileName);
		InputStreamReader read=null;
		StringBuilder sb=new StringBuilder();
		try {
			File file = new File(url.toURI());
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					sb.append(lineTxt);
				}
			}else{
				System.out.println(file.exists());
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			ex.printStackTrace();
		} finally {

			if (read != null) {
				try {
					read.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return sb;
	}

	public static void main(String[] args) {
		// AsynUtil.getInstance().createFTPDirectory("dajskdlsajldsajkdsajlkdsajlkdsaj");
		System.out.println(readJsonObject("common/beijing-geo.txt"));
		
	}

}
