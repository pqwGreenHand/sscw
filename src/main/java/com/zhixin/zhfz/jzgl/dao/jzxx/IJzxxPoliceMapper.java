package com.zhixin.zhfz.jzgl.dao.jzxx;

import java.util.List;
import java.util.Map;

/**
 * table: jzxx_detail
 * 卷宗选择
 * @author xiangdeping
 */
public interface IJzxxPoliceMapper  {
	
	//查询卷宗所有协警
	public List<Map<String, Object>> queryJzxxPoliceByJzxxId(Map<String, Object> map);
	//添加卷宗协警
	public void addJzxxDetailByJzxxId(Map<String, Object> map);
	//删除卷宗协警
	public void deleteJzxxPoliceByjzxxId(Map<String, Object> map);
	
	
	
}