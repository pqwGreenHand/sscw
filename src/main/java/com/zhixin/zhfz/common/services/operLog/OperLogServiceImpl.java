package com.zhixin.zhfz.common.services.operLog;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.entity.TdOperLogEntity;
import com.zhixin.zhfz.common.dao.operLog.IOperLogMapper;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperLogServiceImpl implements IOperLogService {

	private static final Logger logger = LoggerFactory.getLogger(OperLogServiceImpl.class);

	@Autowired
	private IOperLogMapper operLogMapper;

	@Override
	public List<OperLogEntity> list(Map<String, Object> params) throws Exception {
		return this.operLogMapper.list(params);
	}

	@Override
	public void deleteLog(Long id) throws Exception {
		this.operLogMapper.delete(id);
	}

	@Override
	public void insertTdLog(Long id, String type, String content,String json) {
		try {
			TdOperLogEntity log = new TdOperLogEntity();
			log.setType(type);
			log.setContent(content);
			log.setSerialId(id);
			log.setParams(json);
			log.setSfcl(0);
			this.operLogMapper.insertTdLog(log);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public List<TdOperLogEntity> listTd(Map<String, Object> map) {
		return this.operLogMapper.listTd(map);
	}

	@Override
	public int countTd(Map<String, Object> map) {
		return this.operLogMapper.countTd(map);
	}

	@Override
	public void batchClByIds(Map<String, Object> map) {
		this.operLogMapper.batchClByIds(map);
	}

	@Override
	public void insertLog(String type, String content, String user, Boolean isSuccess,String systemName ) {
		try {
			OperLogEntity log = new OperLogEntity();
			log.setType(type);
			log.setContent(content);
			log.setUser(user);
			log.setIsSuccess(isSuccess);
			log.setSystemName(systemName);
			this.operLogMapper.insert(log);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public int count(Map<String, Object> params) throws Exception {
		return this.operLogMapper.count(params);
	}

}
