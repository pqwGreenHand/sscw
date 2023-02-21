package com.zhixin.zhfz.bacs.services.serialVideoMapping;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.bacs.dao.serialVideoMapping.ISerialVideoMappingMapper;
import com.zhixin.zhfz.bacs.entity.SerialVideoMappingEntity;

@Service
public class SerialVideoMappingServiceImpl implements ISerialVideoMappingService {

	@Autowired
	private ISerialVideoMappingMapper mapper;

	@Override
	public List<SerialVideoMappingEntity> list(Map<String, Object> params) throws Exception {
		return mapper.list(params);
	}

	@Override
	public void insert(SerialVideoMappingEntity entity) throws Exception {
		mapper.insert(entity);
	}

	@Override
	public List<SerialVideoMappingEntity> getServerConfig(Map<String, Object> params) throws Exception {
		return mapper.getServerConfig(params);
	}

}
