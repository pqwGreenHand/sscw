package com.zhixin.zhfz.jzgl.services.ajxx;


import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.AjxxEntity;

/**
 * 
 * @author xdp
 */
public interface IAjxxService  {
	
	/**
	 * 案件查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AjxxEntity> listAjxx(Map<String, Object> map) throws Exception;
	/**
	 * 案件总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer countAjxx(Map<String, Object> map) throws Exception;
	
	/**
     * 入库修改
     * @param entity
     * @throws Exception
     */
    public void updateRkAjxx(AjxxEntity entity) throws Exception;
	
}