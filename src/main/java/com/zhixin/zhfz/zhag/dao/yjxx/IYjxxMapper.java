package com.zhixin.zhfz.zhag.dao.yjxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.zhag.entity.YjxxEntity;

public interface IYjxxMapper {

	List<YjxxEntity> selectYjxx(Map<String, Object> map);

	Integer countYjxx(Map<String, Object> map);

	List<YjxxEntity> jqYjList(Map<String, Object> map);

	Integer countJqYj(Map<String, Object> map);

}
