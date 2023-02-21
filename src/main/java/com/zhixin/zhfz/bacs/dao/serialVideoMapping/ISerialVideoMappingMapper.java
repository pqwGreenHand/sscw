package com.zhixin.zhfz.bacs.dao.serialVideoMapping;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.entity.SerialVideoMappingEntity;

public interface ISerialVideoMappingMapper {

	List<SerialVideoMappingEntity> list(Map<String, Object> params) throws Exception;

	void insert(SerialVideoMappingEntity entity) throws Exception;

	List<SerialVideoMappingEntity> getServerConfig(Map<String, Object> params) throws Exception;

}
