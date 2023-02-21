package com.zhixin.zhfz.common.dao.notice;

import com.zhixin.zhfz.common.entity.InformEntity;

import java.util.List;
import java.util.Map;

public interface IInformMapper {

	public List<InformEntity> list(Map<String, Object> map);

	public int count(Map<String, Object> map);


	public void insert(InformEntity entity) throws Exception;

	public List<InformEntity> listhistory(Map<String, Object> map);

	public int listhistoryCount(Map<String, Object> map);

	public void updatInformById(Long id);

	public int isInform(InformEntity entity);

	public void changeInformOvertime();

	public void cleanInform();

}
