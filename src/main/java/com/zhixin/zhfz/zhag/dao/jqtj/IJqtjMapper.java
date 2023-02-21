package com.zhixin.zhfz.zhag.dao.jqtj;

import com.zhixin.zhfz.zhag.entity.JqxxEntity;
import java.util.List;
import java.util.Map;

public interface IJqtjMapper {
	/**
	 * 查询多少天内接警处警折线图
	 * @param map
	 * @return
	 */
	List<JqxxEntity> selectJCJByDay(Map<String, Object> map);


	//处理结果查询
	List<JqxxEntity> selectHandleResultByDay(Map<String, Object> map);

	//警情类别查询
	List<JqxxEntity> quyJQTypeByDay(Map<String, Object> map);

	//接警方式查询
	List<JqxxEntity> quyJJMethedByDay(Map<String, Object> map);

	//受理单位查询
	List<JqxxEntity> quySLCompanyByDay(Map<String, Object> map);

	//总警情数
	List<JqxxEntity> quyAllJQNumberByDay(Map<String, Object> map);

	//处警单位查询
	List<JqxxEntity> quyCJCompanyByDay(Map<String, Object> map);

	/**
	 * 查询多少天内处理未处理折线图
	 * @param map
	 * @return
	 */
	List<JqxxEntity> selectCLByDay(Map<String, Object> map);

}
