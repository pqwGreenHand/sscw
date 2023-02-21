package com.zhixin.zhfz.sacw.services.unowner;


import com.zhixin.zhfz.sacw.entity.UnOwnerEntity;

import java.util.List;
import java.util.Map;

public interface IUnOwnerService {
    /**
     * 查询及条件查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<UnOwnerEntity> list(Map<String, Object> map) throws Exception;

    /**
     * 分页
     *
     * @param map
     * @return
     * @throws Exception
     */
    int count(Map<String, Object> map) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    void delete(int id) throws Exception;

    /**
     * 修改
     *
     * @param entity
     * @throws Exception
     */
    void update(UnOwnerEntity entity) throws Exception;

    void change(UnOwnerEntity entity) throws Exception;

    /**
     * 插入方法
     *
     * @param entity
     * @throws Exception
     */
    void insert(UnOwnerEntity entity) throws Exception;

    List<UnOwnerEntity> listRecordByInvolvedId(Map<String, Object> map) throws Exception;

    int countRecord(Map<String, Object> map) throws Exception;

    List<UnOwnerEntity> listInRecordPhoto(Map<String, Object> map) throws Exception;

    List<UnOwnerEntity> listOutRecordPhoto(Map<String, Object> map) throws Exception;

}
