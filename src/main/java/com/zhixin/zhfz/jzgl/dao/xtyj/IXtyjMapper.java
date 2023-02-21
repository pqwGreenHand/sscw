package com.zhixin.zhfz.jzgl.dao.xtyj;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.XtyjEntity;

public interface IXtyjMapper {
	
	public List<XtyjEntity> listXtyj(Map<String, Object> map) throws Exception;
	
	public int countXtyj(Map<String, Object> map) throws Exception;

	public XtyjEntity queryYjxxSame(XtyjEntity yj);

	public void insert(XtyjEntity yj);
	
}
