package com.zhixin.zhfz.common.services.operLog;

import com.zhixin.zhfz.bacs.entity.TdOperLogEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;

import java.util.List;
import java.util.Map;

public interface IOperLogService {

	List<OperLogEntity> list(Map<String, Object> params) throws Exception;

	int count(Map<String, Object> params) throws Exception;

	void insertLog(String type, String content, String user, Boolean isSuccess,String systemName);

	void deleteLog(Long id) throws Exception;

	void insertTdLog(Long id, String type, String content,String json);

    List<TdOperLogEntity> listTd(Map<String, Object> map);

	int countTd(Map<String, Object> map);

    void batchClByIds(Map<String, Object> map);
}
