package com.zhixin.zhfz.common.common;

import com.zhixin.zhfz.common.utils.PropertyUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FTPUtil {

	private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

	private static String ftpIp = "192.168.201.53";
	private static String ftpUser = "tiandy_test";
	private static String ftpPwd = "tiandy_test";
	private static int ftpPort = 21;

	private static void init() {
		ftpIp = (String) PropertyUtil.getContextProperty("ftp.ip");
		ftpUser = (String) PropertyUtil.getContextProperty("ftp.user");
		ftpPwd = (String) PropertyUtil.getContextProperty("ftp.pwd");
		ftpPort = Integer.parseInt(PropertyUtil.getContextProperty("ftp.port").toString());
	}

	/**
	 * 获取FTPClient对象
	 * @return
	 */
	private static FTPClient getFTPClient() {
		FTPClient ftpClient = null;
		try {
			init();
			//System.err.println("### ftp info [ftpIp:"+ftpIp+"][ftpUser:"+ftpUser+"][ftpPwd:"+ftpPwd+"][ftpPort:"+ftpPort+"] ###");
			logger.info("### ftp info [ftpIp:"+ftpIp+"][ftpUser:"+ftpUser+"][ftpPwd:"+ftpPwd+"][ftpPort:"+ftpPort+"] ###");
			ftpClient = new FTPClient();
			ftpClient.connect(ftpIp, ftpPort);// 连接FTP服务器
			ftpClient.login(ftpUser, ftpPwd);
		} catch (Exception e) {
			logger.error("FTP的端口错误,请正确配置。", e);
		}
		return ftpClient;
	}

	// 新建目录
	public static void createDirectory(String dirPath) {
		FTPClient ftpClient = getFTPClient();
		try {
			ftpClient.makeDirectory(dirPath);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	// 删除目录
	public static void deleteDirectory(String dirPath) {
		FTPClient ftpClient = getFTPClient();
		try {
			ftpClient.removeDirectory(dirPath);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	/**
	 * 查看文件夹直接子集文件名
	 * 
	 * @param dirPath
	 * @return 文件名集合
	 */
	public static List<String> listSubFiles(String dirPath) {
		FTPClient ftpClient = getFTPClient();
		List<String> result = new ArrayList<>();
		try {
			FTPFile[] files = ftpClient.listFiles(dirPath);
			for (FTPFile file : files) {
				if (file.isFile()) {
					String name = (new String(file.getName().getBytes("iso-8859-1"), "GBK")).trim();
					result.add(name);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return result;
	}

	public static void main(String[] args) {
//		System.out.println("------------");
//		createDirectory("/测试");
//		deleteDirectory("/dhc_dir");
//		System.out.println(listSubFiles("/dhc_test"));
//		// createDirectory("/测试");
//		// deleteDirectory("/dhc_dir");
//		System.out.println(listSubFiles("/dhc_test"));
		createDirectory("/test_d");
		createDirectory("/test_d/dhc");
	}
}
