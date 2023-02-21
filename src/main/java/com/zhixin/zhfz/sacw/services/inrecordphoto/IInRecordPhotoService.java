package com.zhixin.zhfz.sacw.services.inrecordphoto;


import com.zhixin.zhfz.sacw.entity.InRecordPhotoEntity;

import java.util.List;
import java.util.Map;

public interface IInRecordPhotoService {
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
     * @param InRecordPhotoForm
     * @throws Exception
     */
    void insertinRecordPhoto(InRecordPhotoEntity inRecordPhotoEntity) throws Exception;

    /**
     * 根据id删除入库照片记录
     *
     * @param id
     * @throws Exception
     */
    void deleteInRecord(int id) throws Exception;

    /**
     * 修改入库照片记录
     *
     * @param InRecordPhotoForm
     * @throws Exception
     */
    void updateInRecordPhoto(InRecordPhotoEntity inRecordPhotoEntity) throws Exception;

    int count(Map<String, Object> map) throws Exception;
}
