package com.zhixin.zhfz.zhag.services.xsajxx;

import com.zhixin.zhfz.zhag.dao.xzxsajxx.CriminalAndAdministrationCaseMapper;
import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CriminalCaseServiceImpl implements CriminalCaseService {
    @Autowired
    private CriminalAndAdministrationCaseMapper criminalAndAdministrationCaseMapper;

    @Override
    public List<CriminalAndAdministrationCaseEntity> listCriminalCase(Map<String, Object> map) throws Exception {
        return criminalAndAdministrationCaseMapper.listCriminalCase(map);
    }

    @Override
    public int listCriminalCount(Map<String, Object> map) throws Exception {
        return criminalAndAdministrationCaseMapper.listCriminalCount(map);
    }

	@Override
	public List<CriminalAndAdministrationCaseEntity> xsajList(Map<String, Object> map) throws Exception {
		return criminalAndAdministrationCaseMapper.xsajList(map);
	}

	@Override
	public int xsajCount(Map<String, Object> map) throws Exception {
		return criminalAndAdministrationCaseMapper.xsajCount(map);
	}
}
