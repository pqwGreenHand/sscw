package com.zhixin.zhfz.bacs.services.openCabinet;

import com.zhixin.zhfz.bacs.entity.OpenCabinetEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;

import java.util.List;
import java.util.Map;

public interface IOpenCabinetService {
    /**
     * 插入开柜记录
     *
     * @param record
     * @return
     */
    void insertSelective(OpenCabinetEntity record);

    /**
     * 查询开柜记录
     *
     * @param params
     * @return
     */
    List<OpenCabinetEntity> list(Map<String, Object> params);

    /**
     * 查询开柜总分数
     *
     * @param params
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * 查询开柜记录
     *
     * @param params
     * @return
     */
    List<OpenCabinetEntity> listBySA(Map<String, Object> params);

    /**
     * 查询开柜总分数
     *
     * @param params
     * @return
     */
    int countBySA(Map<String, Object> params);

    /**
     * 查询开柜记录
     *
     * @param params
     * @return
     */
    List<OpenCabinetEntity> listByPolice(Map<String, Object> params);

    /**
     * 查询开柜总分数
     *
     * @param params
     * @return
     */
    int countByPolice(Map<String, Object> params);
}
