package com.zhixin.zhfz.sacw.services.sapositiondevice;


import com.zhixin.zhfz.sacw.entity.SaPositionDeviceEntity;

import java.util.List;
import java.util.Map;

/**
 * 涉案定位标签
 */
public interface ISaPositionDeviceService {
    /**
     * 查询所有涉案定位标签
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<SaPositionDeviceEntity> list(Map<String, Object> params) throws Exception;

    /**
     * 定位设备计数
     *
     * @param params
     * @return
     * @throws Exception
     */
    int count(Map<String, Object> params) throws Exception;

    /**
     * 插入定位标签管理
     *
     * @param positionDevice
     * @return
     */
    int insert(SaPositionDeviceEntity positionDevice);

    /**
     * 更新定位标签
     *
     * @param positionDevice
     * @return
     */
    int update(SaPositionDeviceEntity positionDevice);

    /**
     * 根据id删除定位标签
     *
     * @param id
     * @return
     */
    int delete(Integer id);
}
