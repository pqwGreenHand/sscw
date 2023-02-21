package com.zhixin.zhfz.jzgl.services.jzgLie;



import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzgEntity;
import com.zhixin.zhfz.jzgl.entity.JzgLieEntity;

/**
 * IJzgMysqlService table: jzg_lie
 * 
 * @author xdp
 */
public interface IJzgLieService  {
 
	/**
	 * 根据jzg_id查询列
	 * @param jzg_id
	 * @return
	 */
   List<JzgLieEntity> queryAllGmLie(Long id);
   
    /**
     * 查询每列列的数量
     * @param map
     * @return
     */
	int queryLieLx(Map<String, Object> map);
	
	void insertAllJzgLie(JzgLieEntity jzgLieEntity);
    
}