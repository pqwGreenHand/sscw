package com.zhixin.zhfz.sacw.services.inputRecord;


import com.zhixin.zhfz.sacw.entity.InRecordPhotoEntity;
import com.zhixin.zhfz.sacw.entity.InputRecordEntity;

import java.util.List;
import java.util.Map;

public interface IInputRecordService {

    List<InputRecordEntity> listInputRecord(Map<String, Object> map) throws Exception;

    Integer countInputRecord(Map<String, Object> map) throws Exception;

    InputRecordEntity getInputRecordById(Integer id) throws Exception;

    List<InRecordPhotoEntity> listInRecordPhotoById(Map<String, Object> map) throws Exception;


    //查询物品的最后一条入库记录
    List<InputRecordEntity> listLastRecord(Map<String, Object> map) throws Exception;

}
