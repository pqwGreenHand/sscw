package com.zhixin.zhfz.jzgl.services.jzxx;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.XtrzEntity;

public interface IXtrzService {

	public List<XtrzEntity> listAllXtrz() throws Exception;

	public Integer countAllXtrz() throws Exception;

	public List<XtrzEntity> listXtrz(Map<String, Object> map) throws Exception;

	public Integer countXtrz(Map<String, Object> map) throws Exception;

	public XtrzEntity getXtrzById(Long id) throws Exception;

	public void insertXtrz(XtrzEntity entity) throws Exception;

}