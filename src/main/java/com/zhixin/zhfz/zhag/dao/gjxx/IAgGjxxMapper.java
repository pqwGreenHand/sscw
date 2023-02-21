package com.zhixin.zhfz.zhag.dao.gjxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.zhag.entity.GjxxEntity;

public interface IAgGjxxMapper {
	
	List<GjxxEntity> selectGjxx(Map<String, Object> map);
	
	Integer countGjxx(Map<String, Object> map);

	List<GjxxEntity> jqGjList(Map<String, Object> map);

	Integer countJqGj(Map<String, Object> map);
}
