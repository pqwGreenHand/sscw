package com.zhixin.zhfz.bacs.dao.cabinetConfig;

import com.zhixin.zhfz.bacs.entity.CabinetConfigEntity;

import java.util.List;
import java.util.Map;

public interface ICabinetConfigMapper {

    List<CabinetConfigEntity> list(Map<String, Object> param);

    int count(Map<String, Object> param);

	void insert(CabinetConfigEntity entity);

	void delete(int id);

	void update(CabinetConfigEntity entity);
    /**
     * 查询特定的办案配置
     * wangguhua
     * @param params
     * @return
     * @throws Exception
     */
    List<CabinetConfigEntity> listFn(Map<String,Object> params)throws Exception;
 }
