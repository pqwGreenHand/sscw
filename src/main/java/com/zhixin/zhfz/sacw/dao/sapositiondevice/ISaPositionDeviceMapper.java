package com.zhixin.zhfz.sacw.dao.sapositiondevice;


import com.zhixin.zhfz.sacw.entity.SaPositionDeviceEntity;

import java.util.List;
import java.util.Map;

public interface ISaPositionDeviceMapper {
    /**
     * 查询所有定位设备
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<SaPositionDeviceEntity> list(Map<String, Object> params) throws Exception;

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
     * @param PositionData
     * @return
     */
    int insert(SaPositionDeviceEntity PositionData);

    /**
     * 修改定位设备
     *
     * @param PositionData
     * @return
     */
    int update(SaPositionDeviceEntity PositionData);

    /**
     * 删除定位设备
     *
     * @param id
     * @return
     */
    int delete(Integer id);
}