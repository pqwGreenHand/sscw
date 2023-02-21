package com.zhixin.zhfz.common.dao.operLog;


import com.zhixin.zhfz.bacs.entity.TdOperLogEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;

import java.util.List;
import java.util.Map;

public interface IOperLogMapper {

	List<OperLogEntity> list(Map<String, Object> params) throws Exception;

	int count(Map<String, Object> params) throws Exception;

	void insert(OperLogEntity log) throws Exception;

	void delete(Long id) throws Exception;

    void insertTdLog(TdOperLogEntity log);

    List<TdOperLogEntity> listTd(Map<String, Object> map);

	int countTd(Map<String, Object> map);

	void batchClByIds(Map<String, Object> map);
}
