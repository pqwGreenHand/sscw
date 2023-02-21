package com.zhixin.zhfz.common.services.interfLog;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.common.dao.interfLog.IInterfLogMapper;
import com.zhixin.zhfz.common.entity.InterfLogEntity;

@Service
public class InterfLogServiceImpl implements IInterfLogService {

	@Autowired
	private IInterfLogMapper interLogMapper;

	@Override
	public List<InterfLogEntity> listInterfaceLog(Map<String, Object> params) throws Exception {
		return interLogMapper.listInterfaceLog(params);
	}

	@Override
	public int countInterfaceLog(Map<String, Object> params) throws Exception {
		return interLogMapper.countInterfaceLog(params);
	}

	@Override
	public void insertInterfaceLog(InterfLogEntity log) throws Exception {
		interLogMapper.insertInterfaceLog(log);
	}

	@Override
	public void deleteInterfaceLog(Long id) throws Exception {
		interLogMapper.deleteInterfaceLog(id);
	}

	@Override
	public void cleanOperLog() throws Exception {
		interLogMapper.cleanInterfaceLog();
	}

	@Override
	public void cleanInterfaceLog() throws Exception {
		interLogMapper.cleanInterfaceLog();
	}

}
