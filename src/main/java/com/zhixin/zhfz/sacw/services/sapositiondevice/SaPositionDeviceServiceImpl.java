package com.zhixin.zhfz.sacw.services.sapositiondevice;

import com.zhixin.zhfz.sacw.dao.sapositiondevice.ISaPositionDeviceMapper;
import com.zhixin.zhfz.sacw.entity.SaPositionDeviceEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SaPositionDeviceServiceImpl implements ISaPositionDeviceService {

    @Resource
    private ISaPositionDeviceMapper positionDeviceMapper;

    // 查询
    @Override
    public List<SaPositionDeviceEntity> list(Map<String, Object> params) throws Exception {
        return positionDeviceMapper.list(params);
    }

    // 总数
    @Override
    public int count(Map<String, Object> params) throws Exception {
        return this.positionDeviceMapper.count(params);
    }

    //添加
    @Override
    public int insert(SaPositionDeviceEntity PositionData) {
        return this.positionDeviceMapper.insert(PositionData);
    }

    //修改
    @Override
    public int update(SaPositionDeviceEntity PositionData) {
        return this.positionDeviceMapper.update(PositionData);
    }


    //删除
    @Override
    public int delete(Integer id) {
        return this.positionDeviceMapper.delete(id);
    }
}
