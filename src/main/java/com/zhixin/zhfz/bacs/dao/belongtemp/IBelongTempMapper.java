package com.zhixin.zhfz.bacs.dao.belongtemp;

import com.zhixin.zhfz.bacs.entity.BelongTempEntity;
import com.zhixin.zhfz.bacs.entity.BelongingsPhotoTempEntity;

import java.util.List;
import java.util.Map;

/**
 * 存物具体物品详情
 */
public interface IBelongTempMapper {

    void saveBelong(BelongTempEntity belongTempEntity);

    List<BelongTempEntity> getListBySfzh(Map<String, Object> sfzh);

    void updateTempYjyj(BelongTempEntity updateTempYjyj);

    List<BelongTempEntity> getEntityById(Map<String, Object> map);

    List<BelongTempEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    void insertBelongphotoTemp(BelongingsPhotoTempEntity photoTempEntity);

    List<BelongingsPhotoTempEntity> selectPhotoByTempId(Integer tempId);
}