package com.zhixin.zhfz.bacs.quartz.getDateJob;

import com.zhixin.zhfz.bacs.daoOracle.ErQiOracleMapper;
import com.zhixin.zhfz.bacs.entity.JzPersonEntity;
import com.zhixin.zhfz.bacs.services.personTemp.IjzPersonService;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 抽取行政人员数据
 */
public class GetXzPersonDataOracleToMysqlJob implements IGetDataJob {


    private static Logger logger = LoggerFactory.getLogger(GetXzPersonDataOracleToMysqlJob.class);
    private static final ExecutorService appSendExecutorService = Executors.newCachedThreadPool();

    @Autowired
	private IjzPersonService personService;

    @Autowired
	private ErQiOracleMapper erQiOracleMapper;

    @Override
    public void get() {
        Future<Boolean> result = appSendExecutorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                logger.info("---获取行政人员数据xz11111111111----");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
    			 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			Timestamp ts = new Timestamp(System.currentTimeMillis());
    			Timestamp lasj = new Timestamp(System.currentTimeMillis());

    			Map<String, Object> map = new HashMap<String, Object>();
    			// 查询mysql表里面最后lrrq
    			String lrrq = "";
				List<JzPersonEntity> person = personService.queryPersonLrrqMaxXz();
				//获取配置文件时间
    			if(person.size()>0){
    				for (JzPersonEntity peEntity : person) {
						lrrq = sdf.format(peEntity.getLrrq());
    				}
    			}else{
					lrrq = PropertyUtil.getContextProperty("start.time").toString();
    			}
    			map.put("LRRQMAX", lrrq);
    			System.err.println(map);
                List<Map<String, Object>> list = erQiOracleMapper.queryOraclePersonXz(map);
                //logger.info("-----查询成功xs---------"+list.size());
                try {
                	if (list.size() > 0) {
    					for (int i = 0; i < list.size(); i++) {
							JzPersonEntity entity = new JzPersonEntity();
							//行政1  刑事2
							entity.setAjlb(1+"");
							entity.setXm(list.get(i).get("XM")+"");
							entity.setXb(list.get(i).get("XBID")+"");
							entity.setZjhm(list.get(i).get("ZJHM")+"");
							if(list.get(i).get("LRRQ") !="" && list.get(i).get("LRRQ")!= null){
								Date afsj = sdf.parse(list.get(i).get("LRRQ")+"");
								entity.setLrrq(afsj);
							}else{
								entity.setLrrq(null);
							}
							entity.setZbdw(list.get(i).get("CBDW")+"");
							entity.setHjd(list.get(i).get("HJDXZ")+"");
							entity.setAjbh(list.get(i).get("AJBH")+"");
							entity.setRybh(list.get(i).get("RYBH")+"");
							personService.insert(entity);
							logger.info("-----抽取行政人员数据插入结束---------");
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

}
