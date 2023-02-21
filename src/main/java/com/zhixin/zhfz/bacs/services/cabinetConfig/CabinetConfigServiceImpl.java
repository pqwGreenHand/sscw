package com.zhixin.zhfz.bacs.services.cabinetConfig;

import com.zhixin.zhfz.bacs.dao.cabinetConfig.ICabinetConfigMapper;
import com.zhixin.zhfz.bacs.entity.CabinetConfigEntity;
import com.zhixin.zhfz.common.dao.authortity.IAuthorityMapper;
import com.zhixin.zhfz.common.entity.AbstractTreeEntity;
import com.zhixin.zhfz.common.entity.AuthorityEntity;
import com.zhixin.zhfz.common.utils.PowerCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CabinetConfigServiceImpl implements ICabinetConfigService {

	@Autowired
	private ICabinetConfigMapper cabinetConfigMapper;

	@Override
	public List<CabinetConfigEntity> list(Map<String, Object> param) {
		return cabinetConfigMapper.list(param);
	}

	@Override
	public int count(Map<String, Object> param) {
		return cabinetConfigMapper.count(param);
	}

	@Override
	public void insertCabinetConfig(CabinetConfigEntity entity) {
		// TODO Auto-generated method stub
		cabinetConfigMapper.insert(entity);
	}

	@Override
	public void update(CabinetConfigEntity entity) {
		// TODO Auto-generated method stub
		cabinetConfigMapper.update(entity);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		cabinetConfigMapper.delete(id);
	}
}
