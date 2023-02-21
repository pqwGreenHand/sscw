package com.zhixin.zhfz.bacs.services.cabinetConfig;

import com.zhixin.zhfz.bacs.entity.CabinetConfigEntity;
import com.zhixin.zhfz.common.entity.AbstractTreeEntity;
import com.zhixin.zhfz.common.entity.AuthorityEntity;

import java.util.List;
import java.util.Map;

public interface ICabinetConfigService {

	List<CabinetConfigEntity> list(Map<String, Object> param);

	int count(Map<String, Object> param);

	void insertCabinetConfig(CabinetConfigEntity entity);

	void update(CabinetConfigEntity entity);

	void delete(int intID);

}
