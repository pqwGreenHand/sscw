package com.zhixin.zhfz.bacs.quartz.getDateJob;

import com.zhixin.zhfz.bacs.daoOracle.IAjxxOracleMapper;
import com.zhixin.zhfz.bacs.entity.JzAjxxEntity;
import com.zhixin.zhfz.bacs.services.ajxx.IAjxxMysqlService;
import com.zhixin.zhfz.bacs.services.jzPerson.IJzRyxxService;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
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
 * 抽取刑事案件数据
 */
public class GetXsAjxxDataOracleToMysqlJob implements IGetDataJob {


    private static Logger logger = LoggerFactory.getLogger(GetXsAjxxDataOracleToMysqlJob.class);
    private static final ExecutorService appSendExecutorService = Executors.newCachedThreadPool();
	@Autowired
	private IAjxxOracleMapper ajxxOracleMapper = null;

	@Autowired
	private IAjxxMysqlService ajxxMysqlService = null;
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IJzRyxxService jzRyxxService;
    @Override
    public void get() {
        Future<Boolean> result = appSendExecutorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() {
            logger.info("---获取刑事案件数据111111111111-----");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			Timestamp ts1 = new Timestamp(System.currentTimeMillis());
			Map<String, Object> map = new HashMap<String, Object>();
			//获取案件状态的map
			Map<String, Object> ajztMap =getAjztMap();
			//获取案警号的map
			Map<String, Object> zhMap =getUserMap();
			// 查询mysql表里面最后slsj
			String slsj = "";
			List<JzAjxxEntity> ajxx = ajxxMysqlService.queryAjxxSlsjMaxXs();
			//获取配置文件时间
			if(ajxx.size()>0){
				for (JzAjxxEntity ajEntity : ajxx) {
					slsj = sdf.format(ajEntity.getSlsj());
				}
			}else{
				slsj = PropertyUtil.getContextProperty("start.time").toString();
			}
			map.put("SLSJ", slsj);
			System.err.println(map);
            List<Map<String, Object>> list = ajxxOracleMapper.getAjxxFromOracleXs(map);
            //logger.info("-----查询成功xs---------"+list.size());
            //logger.info("-----查询成功xs---------"+list);
            try {
            	if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						JzAjxxEntity entity = new JzAjxxEntity();
						try {
						//TODO 根据部门编号查询部门id
						List<OrganizationEntity> listOrgByRegionCode = organizationService.listOrgByRegionCode(Long.valueOf(list.get(i).get("CBDW")+""));
					    //logger.info("-----查询成功xs---------"+listOrgByRegionCode);
						if(listOrgByRegionCode.size()>0){
							entity.setBmId(Long.valueOf(listOrgByRegionCode.get(0).getId()));
							entity.setBadw(listOrgByRegionCode.get(0).getName());
						}
						entity.setAjbh(list.get(i).get("AJBH")+"");
						entity.setAjzt(ajztMap.get(list.get(i).get("AJZT"))+"");
						//行政1  刑事2
						entity.setAjlb(2+"");
						entity.setJqbh(list.get(i).get("JJBH")+"");
						entity.setAjmc(list.get(i).get("AJMC")+"");
						//TODO 根据主办民警查询民警的证件号码
						if(!zhMap.isEmpty()){
							entity.setZbmj(zhMap.get(list.get(i).get("ZH")+"")+"");
						}else {
							entity.setZbmj("");
						}
						entity.setZbmjName(list.get(i).get("ZBMJ")+"");
						 ts = Timestamp.valueOf(list.get(i).get("SLSJ")+"");
						entity.setSlsj(ts);
						entity.setRkzt(String.valueOf(0));
						entity.setJyaq(list.get(i).get("JYAQ")+"");
						entity.setAfdd(list.get(i).get("FADDXZ")+"");
						ts1 = Timestamp.valueOf(list.get(i).get("AFSJ")+"");
						entity.setAfsj(ts1);
						ajxxMysqlService.insert(entity);
						logger.info("-----抽取刑事案件数据插入结束---------");
						} catch (Exception e) {
							// TODO: handle exception
							logger.info(e+"----------");
						}
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
    /**
	 * 获取orgMap
	 * 
	 * @return
	 */
	private Map<String, Object> getOrgMap() {
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> list = jzRyxxService.queryOrgMap();
		if (list != null) {
			for (Map<String, Object> map2 : list) {
				param.put( map2.get("ORGCODE")+"", map2.get("ORGNAME"));
			}
		}
		return param;
	}
	  /**
		 * 获取userMap
		 * 
		 * @return
		 */
		private Map<String, Object> getUserMap() {
			Map<String, Object> param = new HashMap<String, Object>();
			List<Map<String, Object>> list = jzRyxxService.queryUserMap();
			if (list != null) {
				for (Map<String, Object> map2 : list) {
					param.put( map2.get("LOGINID")+"", map2.get("USERCODE"));
				}
			}
			return param;
		}
    /**
	 * 获取ajztMap
	 * 
	 * @return
	 */
	private Map<String, Object> getAjztMap() {
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> list = jzRyxxService.queryAjztMap();
		if (list != null) {
			for (Map<String, Object> map2 : list) {
				param.put( map2.get("CODE")+"", map2.get("CODEDESC"));
			}
		}
		return param;
	}
	

}
