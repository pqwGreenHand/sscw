package com.zhixin.zhfz.jzgl.dao.jzxx;

import java.util.List;
import java.util.Map;

/**
 * table: jzxx_detail
 * 卷宗选择
 * @author yangyk
 */
public interface IJzxxDetailMapper  {
	//查询用户所选卷宗
	public List<Map<String, Object>> selectJzxxDetailByYhId(Map<String, Object> map);
	//添加用户所选卷宗
	public void insertJzxxDetailByYhId(Map<String, Object> map);
	//删除用户所选卷宗
	public void deleteJzxxDetailByYhId(Map<String, Object> map);
	//根据卷宗id删除用户所选卷宗
	public void deleteJzxxDetailByJzxxId(Map<String, Object> map);
}