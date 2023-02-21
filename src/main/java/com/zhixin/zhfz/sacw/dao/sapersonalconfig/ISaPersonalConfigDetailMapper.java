package com.zhixin.zhfz.sacw.dao.sapersonalconfig;

import com.zhixin.zhfz.sacw.entity.SaPersonalConfigDetailEntity;

import java.util.List;
import java.util.Map;


public interface ISaPersonalConfigDetailMapper {
    /**
     * 删除personConfig级联删除personalConfigDetail表
     *
     * @param personalConfigId
     * @throws Exception
     */
    void deleteByPersonalConfigId(int personalConfigId) throws Exception;

    /**
     * 查询所有办案场所个性化配置
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<SaPersonalConfigDetailEntity> list(Map<String, Object> params) throws Exception;

    /**
     * 插入
     *
     * @param entity
     * @throws Exception
     */
    void insert(SaPersonalConfigDetailEntity entity) throws Exception;

    /**
     * 更新通用配置
     *
     * @param entity
     * @throws Exception
     */
    void update(SaPersonalConfigDetailEntity entity) throws Exception;

    /**
     * 根据id删除通用配置
     *
     * @param id
     * @throws Exception
     */
    void delete(int id) throws Exception;

    /**
     * 分页
     *
     * @param params
     * @return
     * @throws Exception
     */
    int count(Map<String, Object> params) throws Exception;

    /**
     * 分页
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<SaPersonalConfigDetailEntity> listDetailsByNameAndInterrogateAreaId(Map<String, Object> params) throws Exception;


    List<SaPersonalConfigDetailEntity> checkurl(String areaid);

    /**
     * 查询个性化配置
     *
     * @param param
     * @return
     */
    List<SaPersonalConfigDetailEntity> listConfigDetailByAreaAndName(Map<String, Object> param);

    String queryBoxNumberById(int lockid);

    SaPersonalConfigDetailEntity listDetailByCodeAndParam(Map<String, Object> params) throws Exception;

    List<SaPersonalConfigDetailEntity> listInParamDetailByOutParamId(Long id) throws Exception;


}
