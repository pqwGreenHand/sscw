package com.zhixin.zhfz.jzgl.common;

 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhixin.zhfz.jzgl.dao.gmxxPz.IGmxxPzMapper;
import com.zhixin.zhfz.jzgl.entity.GmxxPzEntity;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PowerCacheUtilForJZ implements InitializingBean, Runnable {

	private static final Logger logger = LoggerFactory.getLogger(PowerCacheUtilForJZ.class);

	@Autowired
	private IGmxxPzMapper gmxxPzMapper;
 

	private final static ReadWriteLock rwl = new ReentrantReadWriteLock();
	private final static Lock readLock = rwl.readLock();
	private final static Lock writeLock = rwl.writeLock();
 
	// 柜门配置(key:根据lx和显示编号，value 配置集合)
	private static Map<String, String> pzMap = null;

	// 最后更新时间map(key:项目名,value:time)
	private static long lastLoadTime = 0L;

	public void refreshCache() {
		loadCacheData();
	}

	public void loadCacheData() {
		long statTime = System.currentTimeMillis();
		logger.info("###################加载jz_gmxx_pz数据缓存(" + statTime + ")#####################");
		loadPzMap();
		lastLoadTime = System.currentTimeMillis();
		logger.info("###################加载jz_gmxx_pz数据缓存 end all cost" + (System.currentTimeMillis() - statTime)
				+ " ms#####################");
	}

	private void loadPzMap() {
		long statTime = System.currentTimeMillis();
		logger.info("--------------loadPzMap start(" + statTime + ")------------------------");
		writeLock.lock();
		try {
			List<GmxxPzEntity> list = gmxxPzMapper.queryAllPz();
			logger.info("====gmxx_pz=====list+++++++"+list);
			pzMap = new HashMap<String, String>();
			if (list != null) {
				for (GmxxPzEntity jzgGmxxPzMysqlEntity : list) {
					pzMap.put(jzgGmxxPzMysqlEntity.getXsbh() + "_" + jzgGmxxPzMysqlEntity.getLx(),
							jzgGmxxPzMysqlEntity.getMlbh());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			writeLock.unlock();
		}
		logger.info("--------------loadPzMap end cost" + (System.currentTimeMillis() - statTime)
				+ " ms------------------------");
	}
	
	// 根据lx 获取数据
	public String getPz(String pz) {
		readLock.lock();
		try {
			String mlbh = pzMap.get(pz);
			return mlbh;
		} finally {
			readLock.unlock();
		}
	}
		
	@Override
	public void run() {
		loadCacheData();
	}

	private static boolean isLoad() {
		if (lastLoadTime >= System.currentTimeMillis() - 24 * 60 * 60 * 1000L) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 异步方式刷新缓存
	 */
	public void refreshCacheAsyc() {
		new Thread(this).start();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//refreshCacheAsyc();
	}

}
