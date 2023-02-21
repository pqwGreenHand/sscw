package com.zhixin.zhfz.common.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;

public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static final String separator = File.separator;

	public static boolean isExist(String filePath) {
		File file = null;
		boolean boo = false;
		try {
			file = new File(filePath);
			boo = file.exists();
			System.out.println("getName: " + file.getName());
			System.out.println("AbsolutePath: " + file.getAbsolutePath());
			System.out.println("getPath: " + file.getPath());
		} catch (Exception e) {
			boo = false;
			logger.error("", e);
		}
		return boo;
	}

	public static LinkedHashMap<String, String> listFilesInPath(String filePath) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		File file = null;
		try {
			file = new File(filePath);
			if (file.exists()) {
				File[] fss = file.listFiles();
				for (File child : fss) {
					if (child.isFile()) {
						map.put(child.getAbsolutePath(), child.getName());
					}
					if (child.isDirectory()) {
						map.putAll(listFilesInPath(child.getAbsolutePath()));
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return map;
	}

	public static void deleteTempFiles(String filePath) {
		long currentTime = System.currentTimeMillis();
		File file = null;
		try {
			file = new File(filePath);
			if (file.exists()) {
				File[] fss = file.listFiles();
				for (File child : fss) {
					if (child.getName().indexOf(".files") > 0 || child.getName().indexOf(".doc") > 0
							|| child.getName().indexOf(".doc.html") > 0 || child.getName().indexOf(".html") > 0) {
						if ((currentTime - child.lastModified()) > 1000L) {
							child.delete();
							// TODO 文件
						}
					}

					if (child.getName().indexOf(".doc.files") > 0) {
						// TODO 目录
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	//创建目录
	public static void createDir(String dirPath){
		if (!isExist(dirPath)){
			File file = new File(dirPath);
			file.mkdirs();
		}
	}

	/**
	 * 删除单个文件
	 * @param   sPath    被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * @param   sPath 被删除目录的文件路径
	 * @return  目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		boolean flag = false;
		//如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		//如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		//删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			//删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) break;
			} //删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) break;
			}
		}
		if (!flag) return false;
		//删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除目录（文件夹）以及目录下的文件的文件 
	 * @param   sPath 被删除目录的文件路径
	 * @return  目录删除成功返回true，否则返回false
	 */
	public static boolean deleteFileInDirectoryByPassHour(String sPath,int hour) {
		boolean flag = false;
		//如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		//如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		//删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		long currTime=System.currentTimeMillis()-hour*60*60*1000L;
		for (int i = 0; i < files.length; i++) {
			String name=files[i].getName();
			long mTime=files[i].lastModified();
			
			if(!name.equalsIgnoreCase("readme.txt") && mTime<currTime ){
				//删除子文件
				if (files[i].isFile()) {
					flag = deleteFile(files[i].getAbsolutePath());
					if (!flag) break;
				} //删除子目录
				else {
					flag = deleteDirectory(files[i].getAbsolutePath());
					if (!flag) break;
				}
			}
			

		}
		if (!flag) {
			return false;
		}else{
			return true;
		}

	}


	/**
	 * @Author jzw
	 * @Description multipartFile 转File
	 * @Date 17:08 2019/3/11
	 * @Param [file]
	 * @return java.io.File
	 **/
	public static File multipartFileToFile(MultipartFile file ) throws Exception {
		if(!file.equals("")&& file.getSize()>=0){
			return inputStreamToFile(file.getInputStream(),file.getOriginalFilename());
		}
		return null;
	}
	public static File inputStreamToFile(InputStream inputStream, String fileName ) throws Exception {
		File toFile = null;
		toFile = new File(fileName);
		OutputStream os = new FileOutputStream(toFile);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		inputStream.close();
		return toFile;
	}



		public static void main(String[] args) throws Exception {
		// System.out.println(separator);
		// isExist("E:\\InterrogateCenter\\eclipse");
		// isExist("E:\\InterrogateCenter\\eclipse\\eclipse.exe");
		// LinkedHashMap<String, String> map =
		// listFilesInPath("E:\\2015-05-05测试案件11\\审讯流程");
		// for (String key : map.keySet()) {
		// System.out.println(key + " - " + map.get(key));
		// }
//		deleteTempFiles(
//				"E:\\InterrogateCenter\\workspace\\INTERROGATE_CENTER_PLATFORM\\interrogate-web\\src\\main\\webapp\\officefile");
		System.out.println(deleteFileInDirectoryByPassHour("D:/temp/test",5));
	}

}
