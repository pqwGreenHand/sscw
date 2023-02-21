package com.zhixin.zhfz.sacw.dao.sapersonalconfig;


import com.zhixin.zhfz.sacw.entity.SaPersonalConfigEntity;
import com.zhixin.zhfz.sacw.entity.SaPersonalConfigFusionEntity;

import java.util.List;
import java.util.Map;


public interface ISaPersonalConfigMapper {
    /**
     * 查询所有办案场所个性化配置
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<SaPersonalConfigEntity> list(Map<String, Object> params) throws Exception;

    /**
     * 查询特定的办案配置
     * wangguhua
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<SaPersonalConfigEntity> listFn(Map<String, Object> params) throws Exception;

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
     * 分页
     *
     * @param params
     * @return
     * @throws Exception
     */
    int count(Map<String, Object> params) throws Exception;

    /**
     * 嫌犯随物箱查询
     *
     * @param map
     * @return
     */
    List<SaPersonalConfigEntity> listboxinfoPerson(Map<String, Object> map);

    /**
     * 涉案物箱查询
     *
     * @param map
     * @return
     */
    List<SaPersonalConfigEntity> listboxinfoExhibit(Map<String, Object> map);

    /**
     * @return java.util.List<com.zhixin.zhfz.bacs.entity.PersonalConfigFusionEntity>
     * @Author jzw
     * @Description
     * @Date 11:14 2019/3/6
     * @Param [map]
     **/
    List<SaPersonalConfigFusionEntity> getDetailByType(Map<String, Object> map);
}
