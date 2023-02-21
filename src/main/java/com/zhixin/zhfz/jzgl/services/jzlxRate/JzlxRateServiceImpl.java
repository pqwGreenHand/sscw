package com.zhixin.zhfz.jzgl.services.jzlxRate;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.jzlxRate.IJzlxRateMapper;
import com.zhixin.zhfz.jzgl.entity.JzlxRateEntity;
import com.zhixin.zhfz.jzgl.entity.ZxgjEntity;

@Service
public class JzlxRateServiceImpl implements IJzlxRateService {
    @Autowired
    IJzlxRateMapper jzlxRateMapper;
    @Override
    public JzlxRateEntity selectCountOfJzlx(Map map) {
        return jzlxRateMapper.selectCountOfJzlx(map);
    }
	@Override
	public JzlxRateEntity selectCountOfJzlxXs(Map map) {
		return jzlxRateMapper.selectCountOfJzlxXs(map);
	}
	@Override
	public List<ZxgjEntity> selectListZxgj(Map map) {
		return jzlxRateMapper.selectListZxgj(map);
	}
	@Override
	public Integer selectCountOfAlarmJz(Map map) {
		return jzlxRateMapper.selectCountOfAlarmJz(map);
	}
	@Override
	public Integer selectCountOfJz(Map map) {
		return jzlxRateMapper.selectCountOfJz(map);
	}
	@Override
	public Integer selectAllGmCount(Map map) {
		return jzlxRateMapper.selectAllGmCount(map);
	}
	@Override
	public Integer selectUsedGmCount(Map map) {
		return jzlxRateMapper.selectUsedGmCount(map);
	}

   


}
