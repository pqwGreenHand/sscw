package com.zhixin.zhfz.bacsapp.services.casequiry;


import com.zhixin.zhfz.bacsapp.dao.casequiry.ICasequiryMapper;
import com.zhixin.zhfz.bacsapp.entity.CaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ICasequiryServiceImpl implements ICasequiryService {

	@Autowired
	private ICasequiryMapper caseQuiryMapper;

	@Override
	public List<CaseEntity> selectCase(Map<String, Object> map) {
		return caseQuiryMapper.selectCase(map);
	}

	@Override
	public Integer selectCaseCount(Map<String, Object> map) {
		return caseQuiryMapper.selectCaseCount(map);
	}

	@Override
	public CaseEntity getCaseById(Map<String, Object> map) throws Exception{
		return caseQuiryMapper.getCaseById(map);
	}
}
