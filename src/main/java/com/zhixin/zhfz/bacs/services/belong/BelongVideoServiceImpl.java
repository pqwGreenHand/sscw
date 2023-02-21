package com.zhixin.zhfz.bacs.services.belong;

import com.zhixin.zhfz.bacs.dao.belong.IBelongVideoMapper;
import com.zhixin.zhfz.bacs.entity.BelongVideoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class BelongVideoServiceImpl implements IBelongVideoService {

	private static Logger logger = LoggerFactory.getLogger(BelongVideoServiceImpl.class);
	
	@Autowired
	private IBelongVideoMapper belongVideoMapper;

	@Override
	@Transactional
	public void delete(Long id) {
		this.belongVideoMapper.delete(id);
	}

	@Override
	@Transactional
	public int count(Map<String, Object> params) {
		return this.belongVideoMapper.count(params);
	}

	@Override
	@Transactional
	public void insert(BelongVideoEntity entity) {
		this.belongVideoMapper.insert(entity);
	}

	@Override
	@Transactional
	public List<BelongVideoEntity> list(Map<String, Object> map) {
		return this.belongVideoMapper.list(map);
	}
}
