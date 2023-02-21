package com.zhixin.zhfz.common.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * 
 * @author Richard
 *
 */
public class AsynUtil {

	private static final Logger logger = LoggerFactory.getLogger(AsynUtil.class);

	private static LinkedHashSet<String> queue = null;
	private static Map<String, Integer> map = new HashMap<String, Integer>();
	private boolean isActive = true;
	private static AsynUtil instance = null;

	private AsynUtil() {
		new Worker().start();
	}

	public static synchronized AsynUtil getInstance() {
		if (instance == null) {
			queue = new LinkedHashSet<String>();
			instance = new AsynUtil();
		}
		return instance;
	}

	public void createFTPDirectory(String dirPath) {
		synchronized (queue) {
			try {
				logger.info("### AsynUtil queue size ### " + queue.size());
				if (null != dirPath && !"".equals(dirPath.trim())) {
					queue.add(dirPath);
					queue.notifyAll();
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		} // end synchronized
	}

	public void createLocalDir(String dirPath) {
		// 判断是否已创建过，如果创建了就返回
		Integer isCreated = map.get(dirPath);
		if (null != isCreated && isCreated.intValue() > 0) {
			logger.info("### AsynUtil already created ### " + dirPath);
		} else {
			logger.info("### AsynUtil createLocalDir ### " + dirPath);
			System.out.println("### AsynUtil createLocalDir ### " + dirPath);
			FileUtil.createDir(dirPath);
			map.put(dirPath, 1);
		}
	}

	private class Worker extends Thread {

		public Worker() {
			super("AsynUtil.Worker");
			this.setPriority(Thread.NORM_PRIORITY);
		}

		public void run() {
			while (isActive) {
				try {
					synchronized (queue) {
						if (queue.size() > 0) {
							Iterator<String> it = queue.iterator();
							while (it.hasNext()) {
								String path = it.next();
								try {
									logger.info("### AsynUtil.Worker createFTPDirectory ### " + path);
									System.out.println("### AsynUtil.Worker createFTPDirectory ### " + path);
									FTPUtil.createDirectory(path);
								} catch (Exception e) {
									logger.error("", e);
								}
								it.remove();
							}
						} else {
							try {
								queue.wait();
							} catch (Exception e) {
								logger.error("", e);
							}
						}
					} // synchronized
				} catch (Exception e) {
					logger.error("", e);
				}
			} // end while
		}

	}

	public static void main(String[] args) {
		//AsynUtil.getInstance().createFTPDirectory("dajskdlsajldsajkdsajlkdsajlkdsaj");
	}

}
