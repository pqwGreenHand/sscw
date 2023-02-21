package com.zhixin.zhfz.bacs.quartz.getDateJob;

import com.zhixin.zhfz.bacs.daoOracle.ErQiOracleMapper;
import com.zhixin.zhfz.bacs.daoOracle.IAjxxOracleMapper;
import com.zhixin.zhfz.bacs.entity.RycljdMysqlEntity;
import com.zhixin.zhfz.bacs.services.jzPerson.IJzRyxxService;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 抽取刑事人员数据
 */
public class GetRyxxCljdDataOracleToMysqlJob implements IGetDataJob {


    private static Logger logger = LoggerFactory.getLogger(GetRyxxCljdDataOracleToMysqlJob.class);
    private static final ExecutorService appSendExecutorService = Executors.newCachedThreadPool();
	@Autowired
	private IJzRyxxService jzRyxxService;
	@Autowired
	private ErQiOracleMapper jzPersonOracleMapper = null;
	@Autowired
	private IAjxxOracleMapper ajxxOracleMapper = null;

	@Override
	public void get() {
		Future<Boolean> result = appSendExecutorService.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() {
				logger.info("---获取人员处理决定----");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Timestamp ts = new Timestamp(System.currentTimeMillis());
				Timestamp lasj = new Timestamp(System.currentTimeMillis());
				//获取案件状态的map
				Map<String, Object> cljdMap =getCljdMap();
				logger.info("cljdMap-----"+cljdMap);
				Map<String, Object> map = new HashMap<String, Object>();
				// 查询mysql表里面最后lrrq
				String pzsj = "";
				List<RycljdMysqlEntity> cRycljd = jzRyxxService.queryCljdPzsjMaxXs();
				logger.info("批准时间----------"+cRycljd.size());
				//获取配置文件时间
				if(cRycljd.size()>0){
						pzsj = sdf.format(cRycljd.get(0).getPzsj());
				}else{
					pzsj = PropertyUtil.getContextProperty("start.time").toString();
				}
				map.put("pzsj", pzsj);
				List<Map<String, Object>> list = ajxxOracleMapper.queryOracleRycljd(map);
				logger.info("-----查询成功处理决定---------"+list.size());
				try {
					if (list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							RycljdMysqlEntity entity = new RycljdMysqlEntity();
							//行政1  刑事2
							
							entity.setCljd(cljdMap.get(list.get(i).get("CLJD")) + "");
							entity.setRyid(list.get(i).get("RYID")+"");
							entity.setRybh(list.get(i).get("RYBH")+"");
							entity.setPzr(list.get(i).get("PZR")+"");
							if(list.get(i).get("CZSJ") !="" && list.get(i).get("CZSJ")!= null){
								entity.setCzsj(sdf.parse(list.get(i).get("CZSJ")+""));
								}else{
									entity.setCzsj(null);
								}
							if(list.get(i).get("PZSJ") !="" && list.get(i).get("PZSJ")!= null){
								entity.setPzsj(sdf.parse(list.get(i).get("PZSJ")+""));
							}else{
								entity.setPzsj(null);
							}
							jzRyxxService.insertRYCLJD(entity);
							logger.info("-----抽取人员处理决定数据插入结束---------");
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
					logger.info("---抽取异常---" + e);
				}

				return true;
			}
		});
		try {
			result.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//查询处理 决定
	protected Map<String, Object> getCljdMap() {
	List<Map<String, Object>> list=	jzRyxxService.queryCljdMap();
	Map<String, Object> cljdMap= new HashMap<String, Object>();
	for (Map<String, Object> map : list) {
		cljdMap.put(map.get("CODE")+"", map.get("CODEDESC"));
	}
		return cljdMap;
	}

}
