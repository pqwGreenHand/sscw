package com.zhixin.zhfz.jzgl.dao.JzgLie;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzgLieEntity;

/**
 * 实体类IJzgMysqlMapper table: jzg
 * 
 * @author xdp
 */
public interface IJzgLieMapper {

	public List<JzgLieEntity> queryAllGmLie(Long id);

	int queryLieLx(Map<String, Object> map);

	List<JzgLieEntity> queryJzgLie(Map<String, Object> map);
	
	void insertJzgLie(JzgLieEntity entity);

	void updateJzgLie(JzgLieEntity entity);
	
	// 一键插入列
	void insertAllJzgLie(JzgLieEntity jzgLieEntity);

}