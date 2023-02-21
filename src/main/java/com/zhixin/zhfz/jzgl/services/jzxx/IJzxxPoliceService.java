package com.zhixin.zhfz.jzgl.services.jzxx;


import java.util.List;
import java.util.Map;

/**
 * table: jz_jzxx_police
 * 
 * @author cuichengwei
 */
public interface IJzxxPoliceService  {
	
	//查询卷宗所有协警
	public List<Map<String, Object>> queryJzxxPoliceByJzxxId(Long jzxxId);
	//添加卷宗协警
	public void addJzxxDetailByJzxxId(Long jzxxId,Integer userId);
	//删除卷宗协警
	public void deleteJzxxPoliceByjzxxId(Long jzxxId);
	 
}