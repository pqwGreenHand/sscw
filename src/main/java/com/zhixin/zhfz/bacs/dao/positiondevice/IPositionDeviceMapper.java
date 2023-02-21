package com.zhixin.zhfz.bacs.dao.positiondevice;

import com.zhixin.zhfz.bacs.entity.PositionDeviceEntity;

import java.util.List;
import java.util.Map;

public interface IPositionDeviceMapper {
    /**
     * 查询所有定位设备
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<PositionDeviceEntity> listAll(Map<String, Object> params) throws Exception;

    /**
     * 计数(定位设备)
     *
     * @param params
     * @return
     * @throws Exception
     */
    int count(Map<String, Object> params) throws Exception;

    /**
     * 添加定位设备
     *
     * @param positionDevice
     * @return
     */
    int insert(PositionDeviceEntity positionDevice);

    /**
     * 修改定位设备
     *
     * @param positionDevice
     * @return
     */
    int update(PositionDeviceEntity positionDevice);

    /**
     * 删除定位设备
     *
     * @param id
     * @return
     */
    int delete(Integer id);
}