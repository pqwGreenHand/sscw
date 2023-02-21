package com.zhixin.zhfz.bacs.services.belongtemp;

import com.zhixin.zhfz.bacs.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 随身物品对接
 */
public interface IBelongDetailTempService {

    void saveBelongTemp(BelongDetailTempEntity belongDetailTempEntity);

    List<BelongDetailTempEntity> getListByTempId(Map<String, Object> map);

    List<BelongDetailTempEntity> getByWpUuid(Map<String, Object> map);

    List<BelongDetailTempEntity> getListByBelongDetail(Integer areaId, String str);

    List<BelongDetailTempEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);
}
