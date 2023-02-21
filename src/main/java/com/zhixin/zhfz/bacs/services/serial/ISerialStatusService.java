package com.zhixin.zhfz.bacs.services.serial;


import com.zhixin.zhfz.bacs.entity.SerialStatusEntity;
import com.zhixin.zhfz.bacs.entity.SerialStatusTypeEntity;

public interface ISerialStatusService {

    /**
     * 嫌疑人瞬时状态
     * @param serialId
     * @return
     */
    SerialStatusTypeEntity checkStatus(Long serialId);

    void insert(SerialStatusEntity status);

    int queryPersonStatus(Long interrogateSerialId);
}