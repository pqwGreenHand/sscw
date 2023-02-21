package com.zhixin.zhfz.bacs.dao.belongtemp;

import com.zhixin.zhfz.bacs.entity.BelongDetailTempEntity;
import com.zhixin.zhfz.bacs.entity.BelongEntity;

import java.util.List;
import java.util.Map;

/**
 * 存物具体物品详情
 */
public interface IBelongDetailTempMapper {

    void saveBelongTemp(BelongDetailTempEntity belongDetailTempEntity);

    List<BelongDetailTempEntity> getListByTempId(Map<String, Object> map);

    List<BelongDetailTempEntity> getByWpUuid(Map<String, Object> map);

    List<BelongDetailTempEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);
}