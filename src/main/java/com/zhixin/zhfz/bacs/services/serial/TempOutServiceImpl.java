package com.zhixin.zhfz.bacs.services.serial;

import com.zhixin.zhfz.bacs.dao.serial.TempOutMapper;
import com.zhixin.zhfz.bacs.entity.TempOutEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempOutServiceImpl implements TempOutService {
    @Autowired
    private TempOutMapper tempOutMapper;

    @Override
    public void insert(TempOutEntity entity) throws Exception {
        tempOutMapper.insertSelective(entity);
    }

    @Override
    public void update(TempOutEntity entity) throws Exception {
        tempOutMapper.update(entity);
    }

	@Override
	public List<TempOutEntity> queryBySerialId(Integer serialId) { 
		return tempOutMapper.queryBySerialId(serialId);
	}
}
