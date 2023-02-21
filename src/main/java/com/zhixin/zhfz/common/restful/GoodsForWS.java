package com.zhixin.zhfz.common.restful;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.zhixin.zhfz.common.restful.entity.GoodsEntity;
import com.zhixin.zhfz.common.restful.entity.ResultEntity;
import com.zhixin.zhfz.common.restful.services.IRestfulService; 

@Path("/GoodsService")
@Component
@Scope("request")
@Singleton
public class GoodsForWS {
	
	private static Logger logger = Logger.getLogger(GoodsForWS.class);
	
	@InjectParam
	private IRestfulService service;
	
	 
	/**
	 * 随身物品
	 * http://127.0.0.1:8090/zhfz/restful/GoodsService/getBelongingsList
	 * @param rybh
	 * @return
	 */
	@GET
	@Path("/getBelongingsList/{rybh}")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultEntity getGoodsList(@PathParam("rybh") String rybh) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rybh", rybh);
			List<GoodsEntity> list = service.getBelongingsList(map);
			resultEntity.setStatus(true);
			resultEntity.setJsonString(list.toString());
			logger.info("GoodsService getBelongingsList：" + resultEntity);
		} catch (Exception e) {
			logger.info("GoodsService getBelongingsList异常：" + e.getMessage());
			resultEntity.setStatus(false);
			resultEntity.setMessage(e.getMessage());
		}
		return resultEntity;
	}
	
	/**
	 * 涉案物品
	 * http://127.0.0.1:8090/zhfz/restful/GoodsService/getExhibitList
	 * @param rybh
	 * @return
	 */
	@GET
	@Path("/getExhibitList/{rybh}")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultEntity getExhibitList(@PathParam("rybh") String rybh) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rybh", rybh);
			List<GoodsEntity> list = service.getExhibitList(map);
			resultEntity.setStatus(true);
			resultEntity.setJsonString(list.toString());
			logger.info("GoodsService getExhibitList：" + resultEntity);
		} catch (Exception e) {
			logger.info("GoodsService getExhibitList异常：" + e.getMessage());
			resultEntity.setStatus(false);
			resultEntity.setMessage(e.getMessage());
		}
		return resultEntity;
	}
 
}
