package com.zhixin.zhfz.bacs.services.personalconfig;

import com.zhixin.zhfz.bacs.entity.PersonalConfigDetailEntity;

import java.util.List;
import java.util.Map;


public interface IPersonalConfigDetailService {
    /**
     * 查询所有办案场所个性化配置
     *
     * @param params
     * @return
     * @throws Exception
     */
    public List<PersonalConfigDetailEntity> list(Map<String, Object> params) throws Exception;

    /**
     * 插入
     *
     * @param entity
     * @throws Exception
     */
    public void insert(PersonalConfigDetailEntity entity) throws Exception;

    /**
     * 更新通用配置
     *
     * @param entity
     * @throws Exception
     */
    public void update(PersonalConfigDetailEntity entity) throws Exception;

    /**
     * 根据id删除通用配置
     *
     * @param id
     * @throws Exception
     */
    public void delete(int id) throws Exception;

    /**
     * 分页
     *
     * @param params
     * @return
     * @throws Exception
     */
    public int count(Map<String, Object> params) throws Exception;

    /**
     * 分页
     *
     * @param name
     * @param id
     * @return
     * @throws Exception
     */
    public List<PersonalConfigDetailEntity> listDetailsByNameAndInterrogateAreaId(String name, int id) throws Exception;

    public List<PersonalConfigDetailEntity> checkurl(String areaid);

    /**
     * 根据储物柜id查询储物柜变编号
     *
     * @param lockid
     * @return
     */
    public String queryBoxNumberById(int lockid);


    /**
     * 根据 codeKey paramKey areaId 查找指定的detail信息
     *
     * @param codeKey
     * @param paramKey
     * @param areaId
     * @return
     * @throws Exception
     */
    public List<PersonalConfigDetailEntity> listDetailByCodeAndParam(String codeKey, String paramKey, Long areaId) throws Exception;

    /**
     * 根据外参ID获取内参LIST
     *
     * @param id
     * @return
     * @throws Exception
     */
    public List<PersonalConfigDetailEntity> listInParamDetailByOutParamId(Long id) throws Exception;

    /**
     * 查询个性化配置
     *
     * @return
     */
    Map<String, String> listConfigDetailByAreaAndName(int areaId, String name);
}
