package com.zhixin.zhfz.bacs.dao.exhibit;

import com.zhixin.zhfz.bacs.entity.ExhibitEntity;
import java.util.List;
import java.util.Map;

public interface IExhibitcodMapper {

	List<ExhibitEntity> listAllExhibitcod(Map<String, Object> map3);

	int count3(Map<String, Object> params);

	ExhibitEntity selectcodinfo(Long ss);

	void insertExhibitcod(ExhibitEntity obj);

	
   
}