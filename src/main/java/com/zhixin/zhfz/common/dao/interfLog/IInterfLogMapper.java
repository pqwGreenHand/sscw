package com.zhixin.zhfz.common.dao.interfLog;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.common.entity.InterfLogEntity;

public interface IInterfLogMapper {

	List<InterfLogEntity> listInterfaceLog(Map<String, Object> params) throws Exception;

	int countInterfaceLog(Map<String, Object> params) throws Exception;

	void insertInterfaceLog(InterfLogEntity log) throws Exception;

	void deleteInterfaceLog(Long id) throws Exception;

	void cleanInterfaceLog() throws Exception;

}
