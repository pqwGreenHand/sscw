package com.zhixin.zhfz.sacw.dao.outrecordphoto;


import com.zhixin.zhfz.sacw.entity.OutRecordPhotoEntity;

import java.util.List;
import java.util.Map;

public interface IOutRecordPhotoMapper {
    /**
     * 查询出库照片记录
     *
     * @return
     * @throws Exception
     */
    List<OutRecordPhotoEntity> listOutRecordPhoto(Map<String, Object> map) throws Exception;

    /**
     * 插入出库照片记录
     *
     * @throws Exception
     */
    void insertOutRecordPhoto(OutRecordPhotoEntity outRecordPhotoEntity) throws Exception;

    /**
     * 根据id删除出库照片记录
     *
     * @param id
     * @throws Exception
     */
    void deleteOutRecordPhoto(int id) throws Exception;

    /**
     * 修改出库照片记录
     *
     * @throws Exception
     */
    void updateOutRecordPhoto(OutRecordPhotoEntity outRecordPhotoEntity) throws Exception;

    /**
     * @throws Exception
     */
    int count(Map<String, Object> map) throws Exception;

    /**
     * 查询出库照片记录
     *
     * @return
     * @throws Exception
     */
    List<OutRecordPhotoEntity> listOutRecordPhotoById(Map<String, Object> map) throws Exception;


}
