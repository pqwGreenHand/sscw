package com.zhixin.zhfz.bacs.services.serial;

import com.zhixin.zhfz.bacs.dao.serial.ISerialStatusMapper;
import com.zhixin.zhfz.bacs.entity.SerialStatusEntity;
import com.zhixin.zhfz.bacs.entity.SerialStatusTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SerialServiceStatusImpl implements ISerialStatusService {

	@Autowired
	private ISerialStatusMapper serialStatusMapper;

	/**
	 * 嫌疑人瞬时状态
	 * @param serialId
	 * @return
	 */
	@Override
	public SerialStatusTypeEntity checkStatus(Long serialId) {
		return serialStatusMapper.checkStatus(serialId);
	}

	@Override
	public void insert(SerialStatusEntity status) {
		serialStatusMapper.insert(status);
	}

	@Override
	public int queryPersonStatus(Long serialId) {
		int count = 0;
		//查询审讯状态
		count = serialStatusMapper.selectRecordStatus(serialId);
		if(count > 0){
			return 1;
		}
		//查询看押状态
		count = serialStatusMapper.selectWaitingStatus(serialId);
		if(count > 0){
			return 2;
		}
		//查询随身物品状态
		count = serialStatusMapper.selectBelongsStatus(serialId);
		if(count > 0){
			return 3;
		}
		return 0;
	}
}
