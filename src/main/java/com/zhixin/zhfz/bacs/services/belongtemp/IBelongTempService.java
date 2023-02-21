package com.zhixin.zhfz.bacs.services.belongtemp;

import com.zhixin.zhfz.bacs.entity.BelongDetailTempEntity;
import com.zhixin.zhfz.bacs.entity.BelongTempEntity;
import com.zhixin.zhfz.bacs.entity.BelongingsPhotoTempEntity;
import com.zhixin.zhfz.bacs.entity.LawEntity;

import java.util.List;
import java.util.Map;

/**
 * 随身物品对接
 */
public interface IBelongTempService {

    void saveBelong(BelongTempEntity belongTempEntity);

    List<BelongTempEntity> getListBySfzh(Map<String, Object> sfzh);

    void updateTempYjyj(BelongTempEntity entity);

    List<BelongTempEntity> getEntityById(Map<String, Object> map);

    List<BelongTempEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    void insertBelongphotoTemp(BelongingsPhotoTempEntity photoTempEntity);
}
