package com.zhixin.zhfz.jzgl.dao.jzg;

import com.zhixin.zhfz.jzgl.entity.JzgEntity;

import java.util.List;
import java.util.Map;

/**
 * 实体类IJzgMysqlMapper table: jzg
 * 
 * @author xdp
 */
public interface IJzgMapper {
 
	// 查询 柜门信息
	public List<JzgEntity> queryAllJzgGmxx(Map<String, Object> map);

	// 查询 柜门数量
	public int countAllJzgGmxx(Map<String, Object> map);
	
	List<JzgEntity> queryAllGm(Map<String, Object> map);
 

}