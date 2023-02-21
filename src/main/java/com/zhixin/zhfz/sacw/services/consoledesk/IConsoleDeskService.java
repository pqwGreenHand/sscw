package com.zhixin.zhfz.sacw.services.consoledesk;


import com.zhixin.zhfz.sacw.entity.ConsoleDeskEntity;
import com.zhixin.zhfz.sacw.entity.OutRecordPhotoEntity;

import java.util.List;
import java.util.Map;

public interface IConsoleDeskService {
    /**
     * 查询按案件性质统计数量
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<ConsoleDeskEntity> listTotalByCaseNature(Map<String, Object> map) throws Exception;

    List<ConsoleDeskEntity> listInvolve(Map<String, Object> map) throws Exception;

    List<OutRecordPhotoEntity> getImages(Map<String, Object> map) throws Exception;

    int countInvolve(Map<String, Object> map) throws Exception;

    /**
     * 查看有涉案财物仓库的办案场所
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<ConsoleDeskEntity> listWarehouse(Map<String, Object> map) throws Exception;
}
