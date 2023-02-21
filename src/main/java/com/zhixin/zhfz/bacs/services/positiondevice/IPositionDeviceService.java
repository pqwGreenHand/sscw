package com.zhixin.zhfz.bacs.services.positiondevice;

import com.zhixin.zhfz.bacs.entity.PositionDeviceEntity;

import java.util.List;
import java.util.Map;

public interface IPositionDeviceService {
    /**
     * 查询所有定位设备
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<PositionDeviceEntity> list(Map<String, Object> params) throws Exception;

    /**
     * 定位设备计数
     *
     * @param params
     * @return
     * @throws Exception
     */
    int count(Map<String, Object> params) throws Exception;

    /**
     * 插入定位设备管理
     *
     * @param positionDevice
     * @return
     */
    int insert(PositionDeviceEntity positionDevice);

    /**
     * 更新定位设备
     *
     * @param positionDevice
     * @return
     */
    int update(PositionDeviceEntity positionDevice);

    /**
     * 根据id删除定位设备
     *
     * @param id
     * @return
     */
    int delete(Integer id);
}
