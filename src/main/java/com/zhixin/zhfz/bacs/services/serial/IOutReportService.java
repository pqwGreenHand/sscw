package com.zhixin.zhfz.bacs.services.serial;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhixin.zhfz.bacs.entity.LawDocProcessEntity;
import com.zhixin.zhfz.bacs.entity.OutReportEntity;

public interface IOutReportService {
	LawDocProcessEntity getDocData(Long serialId, HttpServletRequest request);

}