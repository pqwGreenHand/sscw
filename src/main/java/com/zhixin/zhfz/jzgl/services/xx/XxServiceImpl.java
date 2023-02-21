package com.zhixin.zhfz.jzgl.services.xx;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.xx.IXxMapper;
import com.zhixin.zhfz.jzgl.entity.XxEntity;

@Service
public class XxServiceImpl implements IXxService {
	
	@Autowired
	private IXxMapper xxMapper;

	@Override
	public void insertXx(XxEntity xxEntity) {
		xxMapper.insertXX(xxEntity);
	}

	@Override
	public List<XxEntity> listYjTj(Map<String, Object> map) {
		return xxMapper.listYjTj(map);
	}

}
