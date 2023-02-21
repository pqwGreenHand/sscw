package com.zhixin.zhfz.bacsapp.dao.belong;

import com.zhixin.zhfz.bacs.entity.BelongEntity;
import com.zhixin.zhfz.bacs.entity.BelongingsPhotoEntity;
import com.zhixin.zhfz.bacs.entity.ComboboxEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IBelongAppMapper {
    /**
     * 查询嫌疑人存取物信息
     *
     * @param map
     * @return
     */
    List<BelongEntity> selectBelongByAreaId(Map<String, Object> map);

    /**
     * 查询嫌疑人涉案存取物信息
     *
     * @param map
     * @return
     */
    List<BelongEntity> selectExhibitByAreaId(Map<String, Object> map);
}