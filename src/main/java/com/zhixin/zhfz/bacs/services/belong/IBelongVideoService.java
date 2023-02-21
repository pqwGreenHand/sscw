package com.zhixin.zhfz.bacs.services.belong;

import com.zhixin.zhfz.bacs.entity.BelongVideoEntity;

import java.util.List;
import java.util.Map;

public interface IBelongVideoService {

	List<BelongVideoEntity> list(Map<String, Object> map);

	int count(Map<String, Object> params);

	void insert(BelongVideoEntity entity);

	void delete(Long id);

}
