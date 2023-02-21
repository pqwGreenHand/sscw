package com.zhixin.zhfz.sacw.dao.inrecordphoto;


import com.zhixin.zhfz.sacw.entity.InRecordPhotoEntity;

import java.util.List;
import java.util.Map;


public interface IInRecordPhotoMapper {
    /**
     * 查询入库照片记录
     *
     * @return
     * @throws Exception
     */
    List<InRecordPhotoEntity> listInRecordPhoto(Map<String, Object> map) throws Exception;

    /**
     * 插入入库照片记录
     *
     * @throws Exception
     */
    void insertInRecordPhoto(InRecordPhotoEntity inRecordPhotoEntity) throws Exception;

    /**
     * 根据id删除入库照片记录
     *
     * @param id
     * @throws Exception
     */
    void deleteInRecordPhoto(int id) throws Exception;

    /**
     * 修改入库照片记录
     *
     * @throws Exception
     */
    void updateInRecordPhoto(InRecordPhotoEntity inRecordPhotoEntity) throws Exception;

    /**
     * @throws Exception
     */
    int count(Map<String, Object> map) throws Exception;


    List<InRecordPhotoEntity> listInRecordPhotoById(Map<String, Object> map) throws Exception;

}
