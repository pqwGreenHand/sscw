package com.zhixin.zhfz.bacs.services.positiondevice;

import com.zhixin.zhfz.bacs.dao.positiondevice.IPositionDeviceMapper;
import com.zhixin.zhfz.bacs.entity.PositionDeviceEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class PositionDeviceServiceImpl implements IPositionDeviceService {

    @Resource
    private IPositionDeviceMapper positionDeviceMapper;

    // 查询
    @Override
    public List<PositionDeviceEntity> list(Map<String, Object> params) throws Exception {
        return positionDeviceMapper.listAll(params);
    }

    // 总数
    @Override
    public int count(Map<String, Object> params) throws Exception {
        return this.positionDeviceMapper.count(params);
    }

    //新增
    @Override
    public int insert(PositionDeviceEntity positionDevice) {
        return this.positionDeviceMapper.insert(positionDevice);
    }

    //修改
    @Override
    public int update(PositionDeviceEntity positionDevice) {
        return this.positionDeviceMapper.update(positionDevice);
    }

    //删除
    @Override
    public int delete(Integer id) {
        return this.positionDeviceMapper.delete(id);
    }
}
