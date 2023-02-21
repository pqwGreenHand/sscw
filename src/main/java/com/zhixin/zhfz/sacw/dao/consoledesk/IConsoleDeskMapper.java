package com.zhixin.zhfz.sacw.dao.consoledesk;


import com.zhixin.zhfz.sacw.entity.ConsoleDeskEntity;
import com.zhixin.zhfz.sacw.entity.OutRecordPhotoEntity;

import java.util.List;
import java.util.Map;

public interface IConsoleDeskMapper {


    /**
     * 按出库方式统计数量
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<ConsoleDeskEntity> listCountByCaseNature(Map<String, Object> map) throws Exception;

    int countInvolve(Map<String, Object> map) throws Exception;

    List<ConsoleDeskEntity> listInvolve(Map<String, Object> map) throws Exception;

    List<ConsoleDeskEntity> listWarehouse(Map<String, Object> map) throws Exception;

    List<OutRecordPhotoEntity> getImages(Map<String, Object> map) throws Exception;


}
