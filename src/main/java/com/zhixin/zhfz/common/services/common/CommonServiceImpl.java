package com.zhixin.zhfz.common.services.common;

import com.zhixin.zhfz.common.dao.common.ICommonCommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements ICommonService {
	
	@Autowired
	ICommonCommonMapper dao;

	@Override
	public Boolean checkIp(String ip) throws Exception {
		Map<Object, Object> map = dao.queryIpFilterByIp(ip);
		return map!=null?true:false;
	}

	@Override
	public List<Map<String, Object>> listTimeoutRecode() {
		return dao.listTimeoutRecode();
	}

	@Override
	public void updateTimeoutRecode(Integer id) {
		dao.updateTimeoutRecode(id);
	}

	@Override
	public List<Map<String, Object>> listBaqOutAlarmData(Map<String, Object> param) {
		return dao.listBaqOutAlarmData(param);
	}

	@Override
	public void updateBaqOutAlarmById(Integer id) {
		dao.updateBaqOutAlarmById(id);
	}

	@Override
	public Map<String, Object> queryUserByCuffNo(String cuffNo) {
		return dao.queryUserByCuffNo(cuffNo);
	}

	@Override
	public List<Map<String, Object>> querySerailByUser12(Map<String, Object> param) {
		return dao.querySerailByUser12(param);
	}


}
