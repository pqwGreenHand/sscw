package com.zhixin.zhfz.zhag.services.jqtj;

import com.zhixin.zhfz.zhag.entity.JqxxEntity;

import java.util.List;

public interface IJqtjService {

	/**
	 * 查询多少天内接警处警折线图
	 * @param day
	 * @return
	 */
	List<JqxxEntity> selectJCJByDay(Integer day);

	/**
	 * 处理结果
	 *
	 * @param day
	 * @return
	 * @throws Exception
	 */
	List<JqxxEntity> quyHandleResultByDay(Integer day) throws Exception;

	/**
	 * 警情类别
	 *
	 * @param day
	 * @return
	 * @throws Exception
	 */
	List<JqxxEntity> quyJQTypeByDay(Integer day) throws Exception;

	/**
	 * 接警方式
	 *
	 * @param day
	 * @return
	 * @throws Exception
	 */
	List<JqxxEntity> quyJJMethedByDay(Integer day) throws Exception;

	/**
	 * 受理单位
	 *
	 * @param day
	 * @return
	 * @throws Exception
	 */
	List<JqxxEntity> quySLCompanyByDay(Integer day) throws Exception;

	/**
	 * 总警情数
	 *
	 * @param day
	 * @return
	 * @throws Exception
	 */
	List<JqxxEntity> quyAllJQNumberByDay(Integer day) throws Exception;

	/**
	 * 处警单位
	 *
	 * @param day
	 * @return
	 * @throws Exception
	 */
	List<JqxxEntity> quyCJCompanyByDay(Integer day) throws Exception;

	/**
	 * 查询多少天内处理未处理折线图
	 * @param day
	 * @return
	 */
	List<JqxxEntity> selectCLByDay(Integer day);

}
