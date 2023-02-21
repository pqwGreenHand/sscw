package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

/**
 * @program: zhfz
 * @description: 两个类的融合类
 * @author: jzw
 * @create: 2019-03-06 11:01
 **/

public class PersonalConfigFusionEntity implements Serializable {

    private Long areaId;

    private String paramKey;

    private String paramValue;

    private Long configId;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
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
}
