package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;

/**
 * @program: zhfz
 * @description: 两个类的融合类
 * @author: jzw
 * @create: 2019-03-06 11:01
 **/

public class SaPersonalConfigFusionEntity implements Serializable {

    private Long warehouseId;

    private String paramKey;

    private String paramValue;

    private Long configId;


    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    @Override
    public String toString() {
        return "SaPersonalConfigFusionEntity{" +
                "warehouseId=" + warehouseId +
                ", paramKey='" + paramKey + '\'' +
                ", paramValue='" + paramValue + '\'' +
                ", configId=" + configId +
                '}';
    }
}
