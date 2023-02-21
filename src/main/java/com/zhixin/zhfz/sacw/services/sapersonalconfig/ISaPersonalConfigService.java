package com.zhixin.zhfz.sacw.services.sapersonalconfig;


import com.zhixin.zhfz.sacw.entity.SaPersonalConfigEntity;
import com.zhixin.zhfz.sacw.entity.SaPersonalConfigFusionEntity;

import java.util.List;
import java.util.Map;

public interface ISaPersonalConfigService {
    /**
     * 查询所有办案场所个性化配置
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<SaPersonalConfigEntity> list(Map<String, Object> params) throws Exception;


    /**
     * 分页
     *
     * @param params
     * @return
     * @throws Exception
     */
    int count(Map<String, Object> params) throws Exception;

    /**
     * 插入
     *
     * @param entity
     * @throws Exception
     */
    void insert(SaPersonalConfigEntity entity) throws Exception;

    /**
     * 更新通用配置
     *
     * @param entity
     * @throws Exception
     */
    void update(SaPersonalConfigEntity entity) throws Exception;

    /**
     * 根据id删除通用配置
     *
     * @param id
     * @throws Exception
     */
    void delete(int id) throws Exception;

    /**
     * 初始化区域
     *
     * @param warehouseId
     */
    void initArea(int warehouseId);


    List<SaPersonalConfigFusionEntity> getDetailByType(Map<String, Object> map);

}
