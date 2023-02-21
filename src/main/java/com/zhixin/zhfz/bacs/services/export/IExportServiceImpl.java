package com.zhixin.zhfz.bacs.services.export;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.entity.OrderRequestEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.bacs.dao.export.IExprotMapper;
import com.zhixin.zhfz.bacs.entity.AskManagementExportEntity;
import com.zhixin.zhfz.bacs.entity.ExportEntity;
import com.zhixin.zhfz.bacs.entity.WaitingRecordEntity;

@Service
public class IExportServiceImpl implements IExportService {

    private static Logger logger = Logger.getLogger(IExportServiceImpl.class);

    @Autowired
    private IExprotMapper exprotMapper;


    @Override
    public List<AskManagementExportEntity> listWaitingRecord(Map<String, Object> map) {
        return exprotMapper.listWaitingRecord(map);
    }

    @Override
    public List<AskManagementExportEntity> listInterrogateRecord(Map<String, Object> map) {
        return exprotMapper.listInterrogateRecord(map);
    }

    @Override
    public List<AskManagementExportEntity> listInterrogate0(Map<String, Object> map) {
        return exprotMapper.listInterrogate0(map);
    }

    @Override
    public List<AskManagementExportEntity> listInterrogateElse(Map<String, Object> map) {
        return exprotMapper.listInterrogateElse(map);
    }

    @Override
    public List<AskManagementExportEntity> listTempOut(Map<String, Object> map) {
        return exprotMapper.listTempOut(map);
    }

    @Override
    public List<WaitingRecordEntity> listRecord(Map<String, Object> map) {
        return exprotMapper.listRecord(map);
    }

    @Override
    public List<ExportEntity> list(Map<String, Object> map) {
        return exprotMapper.list(map);
    }

    public List<ExportEntity> listquery(Map<String, Object> map) {
        return exprotMapper.listquery(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return exprotMapper.count(map);
    }

    @Override
    public List<ExportEntity> listother1(Map<String, Object> map) {
        return exprotMapper.listother1(map);
    }

    @Override
    public List<ExportEntity> listother2(Map<String, Object> map) {
        return exprotMapper.listother2(map);
    }

    @Override
    public List<ExportEntity> listother(Map<String, Object> map) {
        return exprotMapper.listother(map);
    }

	@Override
	public List<ExportEntity> accountList(Map<String, Object> map) {
		return exprotMapper.accountList(map);
	}

	@Override
	public int accountCount(Map<String, Object> map) {
		return exprotMapper.accountCount(map);
	}

	@Override
	public List<ExportEntity> accountExport(Map<String, Object> map) {
		return exprotMapper.accountExport(map);
	}

    @Override
    public List<OrderRequestEntity> listOrderExport(Map<String, Object> map) {
        return exprotMapper.listOrderExport(map);
    }

    @Override
    public List<Map<String, Object>> listByExport(Map<String, Object> map) {
        return exprotMapper.listByExport(map);
    }

    @Override
    public int listByExportCount(Map<String, Object> map) {
        return exprotMapper.listByExportCount(map);
    }

    @Override
    public List<Map<String, Object>> listBelongDetailByPersonId(Long personId) {
        return exprotMapper.listBelongDetailByPersonId(personId);
    }
}
