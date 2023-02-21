package com.zhixin.zhfz.common.services.commonConfig;

import com.zhixin.zhfz.common.dao.commonConfig.ICommonConfigDetailMapper;
import com.zhixin.zhfz.common.entity.CommonConfigDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class CommonConfigDetailServiceImpl implements ICommonConfigDetailService{
	
	@Autowired
	private ICommonConfigDetailMapper dao;
	/**
	 * 查询所有通用配置
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<CommonConfigDetailEntity> list(Map<String,Object> params)throws Exception{
		return dao.list(params);
	}
	/**
	 * 插入
	 * @param entity
	 * @throws Exception
	 */
	public void insert(CommonConfigDetailEntity entity)throws Exception{
		dao.insert(entity);
	}
	/**
	 * 更新通用配置
	 * @param entity
	 * @throws Exception
	 */
	public void update(CommonConfigDetailEntity entity)throws Exception{
		dao.update(entity);
	}
	/**
	 * 根据id删除通用配置
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id)throws Exception{
		dao.delete(id);
	}
	/**
	 * 分页
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int count(Map<String,Object> params)throws Exception{
		return dao.count(params);
	}
	public String listDetailsByName(String parm1,String parm2) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("type", parm1);
		map.put("param_key", parm2);
		return dao.listDetailsByName(map);
	}
	@Override
	public void updateByConfigIdAndKey(CommonConfigDetailEntity entity) throws Exception {	
		dao.updateByConfigIdAndKey(entity);
	}

	@Override
	public CommonConfigDetailEntity queryDetailByType(String type) throws Exception {
		return dao.queryDetailByType(type);
	}
}
