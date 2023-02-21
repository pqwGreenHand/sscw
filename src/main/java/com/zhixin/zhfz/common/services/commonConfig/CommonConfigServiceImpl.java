package com.zhixin.zhfz.common.services.commonConfig;

import com.zhixin.zhfz.common.dao.commonConfig.ICommonConfigDetailMapper;
import com.zhixin.zhfz.common.dao.commonConfig.ICommonConfigMapper;
import com.zhixin.zhfz.common.entity.CommonConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommonConfigServiceImpl implements ICommonConfigService {

	@Autowired
	private ICommonConfigMapper dao;
	@Autowired
	private ICommonConfigDetailMapper Detaildao;

	@Override
	public List<CommonConfigEntity> list(Map<String, Object> params) throws Exception {
		return dao.list(params);
	}

	@Override
	public void insert(CommonConfigEntity entity) throws Exception {
		dao.insert(entity);
	}

	@Override
	public void update(CommonConfigEntity entity) throws Exception {
		dao.update(entity);
	}

	@Override
	public void delete(int id) throws Exception {
		dao.delete(id);
		Detaildao.deleteByConfigId(id);
	}

	@Override
	public int count(Map<String, Object> params) throws Exception {
		return dao.count(params);
	}

}
