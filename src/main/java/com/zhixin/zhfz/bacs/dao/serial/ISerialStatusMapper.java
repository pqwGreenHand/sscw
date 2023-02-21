package com.zhixin.zhfz.bacs.dao.serial;

import com.zhixin.zhfz.bacs.entity.SerialStatusEntity;
import com.zhixin.zhfz.bacs.entity.SerialStatusTypeEntity;
import org.apache.ibatis.annotations.Param;

public interface ISerialStatusMapper {

	/**
	 * 嫌疑人瞬时状态
	 * @param serialId
	 * @return
	 */
	SerialStatusTypeEntity checkStatus( Long serialId);

    void insert(SerialStatusEntity status);

	int selectRecordStatus(Long serialId);

	int selectWaitingStatus(Long serialId);

	int selectBelongsStatus(Long serialId);
}