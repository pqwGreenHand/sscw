package com.zhixin.zhfz.jzgl.services.xtyj;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.xtyj.IXtyjMapper;
import com.zhixin.zhfz.jzgl.entity.XtyjEntity;

/**
 * 
 * @author cuichengwei
 */
@Service
public class XtyjServiceImpl implements IXtyjService {

	private static Logger logger = Logger.getLogger(XtyjServiceImpl.class);

	@Autowired
	private IXtyjMapper xtyjMapper;

	@Override
	public List<XtyjEntity> listXtyj(Map<String, Object> map) throws Exception {
		return xtyjMapper.listXtyj(map);
	}

	@Override
	public int countXtyj(Map<String, Object> map) throws Exception {
		return xtyjMapper.countXtyj(map);
	}

	public XtyjEntity queryYjxxSame(XtyjEntity yj) {
		return xtyjMapper.queryYjxxSame(yj);
	}

	public void insert(XtyjEntity yj) {
		xtyjMapper.insert(yj);
	}

	

}