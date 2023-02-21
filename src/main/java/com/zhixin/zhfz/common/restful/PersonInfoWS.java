package com.zhixin.zhfz.common.restful;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.spi.resource.Singleton;
import com.zhixin.zhfz.common.restful.entity.PersonInfoEntity;
import com.zhixin.zhfz.common.restful.entity.ResultEntity;
import com.zhixin.zhfz.common.restful.services.IRestfulService;

@Path("/ring")
@Component
@Scope("request")
@Singleton
public class PersonInfoWS {

	private static Logger logger = Logger.getLogger(PersonInfoWS.class);

	@InjectParam
	private IRestfulService restfulService;
	
	/**
	 * 给标采系统提供嫌疑人接口
	 * http://127.0.0.1:8080/zhfz/restful/ring/getPersonInfo/1111
	 * @param icNo 卡号
	 * @return
	 */
	@GET
	@Path("/getPersonInfo/{icNo}")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultEntity getGoodsList(@PathParam("icNo") String icNo) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			logger.info("ic卡号==="+icNo);  
			PersonInfoEntity entity = restfulService.getPersonInfoById(icNo);
			if(entity!=null) {
				resultEntity.setStatus(true);
				resultEntity.setJsonString(entity.toString()); 
			}else {
				resultEntity.setStatus(false);
				resultEntity.setMessage("通过手环编号找不到该嫌疑人！"); 
			}
		} catch (Exception e) {
			logger.info("标采息系统接口错误"+e.getMessage());
			resultEntity.setStatus(false);
			resultEntity.setMessage(e.getMessage()); 
		}
		return resultEntity;
	}
}
