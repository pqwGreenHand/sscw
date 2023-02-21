package com.zhixin.zhfz.jzgl.dao.xx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.XxEntity;

public interface IXxMapper {

	void insertXX(XxEntity xxEntity);
	
	List<XxEntity> listYjTj(Map<String, Object> map);

}
