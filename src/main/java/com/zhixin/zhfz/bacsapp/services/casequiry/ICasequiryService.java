package com.zhixin.zhfz.bacsapp.services.casequiry;


import com.zhixin.zhfz.bacsapp.entity.CaseEntity;

import java.util.List;
import java.util.Map;

public interface ICasequiryService {

	List<CaseEntity> selectCase(Map<String,Object> map);

	Integer selectCaseCount(Map<String,Object> map);

	CaseEntity getCaseById(Map<String,Object> map) throws Exception;

}
