package com.zhixin.zhfz.jzgl.services.jzxx;


import java.util.List;
import java.util.Map;

/**
 * IJzxxDetailMysqlService table: jzxx_detail
 * 
 * @author yangyk
 */
public interface IJzxxDetailService  {
	//查询用户所选卷宗
	public List<Map<String, Object>> queryJzxxDetailByYhId(Long jzxx_id, Integer jzlb);
	//添加用户所选卷宗
	public void addJzxxDetailByYhId(Long jzxx_id, Long jz_id, Integer jzlb);
	//删除用户所选卷宗
	public void removeJzxxDetailByYhId(Long jzxx_id, Integer jzlb);
	//根据卷宗id删除用户所选卷宗
	public void removeJzxxDetailByJzxxId(Long jzxx_id);
}