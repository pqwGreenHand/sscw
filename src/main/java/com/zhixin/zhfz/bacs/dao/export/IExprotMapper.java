package com.zhixin.zhfz.bacs.dao.export;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.entity.AskManagementExportEntity;
import com.zhixin.zhfz.bacs.entity.ExportEntity;
import com.zhixin.zhfz.bacs.entity.OrderRequestEntity;
import com.zhixin.zhfz.bacs.entity.WaitingRecordEntity;

/*
 * 数据导出
 * */
public interface IExprotMapper {

	List<AskManagementExportEntity> listWaitingRecord(Map<String, Object> map);

	List<AskManagementExportEntity> listInterrogateRecord(Map<String, Object> map);
	
	List<AskManagementExportEntity> listInterrogate0(Map<String, Object> map);
	
	List<AskManagementExportEntity> listInterrogateElse(Map<String, Object> map);
	
	List<AskManagementExportEntity> listTempOut(Map<String, Object> map);

	List<WaitingRecordEntity> listRecord(Map<String, Object> map);

	List<ExportEntity> list(Map<String, Object> map);

	List<ExportEntity> listquery(Map<String, Object> map);

	int count(Map<String, Object> map);

	List<ExportEntity> listother1(Map<String, Object> map);

	List<ExportEntity> listother2(Map<String, Object> map);

	List<ExportEntity> listother(Map<String, Object> map);
	
	
    List<ExportEntity> accountList(Map<String, Object> map);
    
	int accountCount(Map<String, Object> map);
	
 	List<ExportEntity> accountExport(Map<String, Object> map);

    List<OrderRequestEntity> listOrderExport(Map<String, Object> map);


	List<Map<String, Object>> listByExport(Map<String, Object> map);

	int listByExportCount(Map<String, Object> map);

	List<Map<String, Object>> listBelongDetailByPersonId(Long personId);
}
