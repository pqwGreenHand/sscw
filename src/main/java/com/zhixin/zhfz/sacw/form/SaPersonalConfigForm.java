package com.zhixin.zhfz.sacw.form;

import java.io.Serializable;

public class SaPersonalConfigForm implements Serializable {

    private static final long serialVersionUID = 6107046760789030997L;

    private int id;
    private String type;
    private int warehouseId;
    private String areaName;
    private String desc;

    private int isEnable;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    @Override
    public String toString() {
        return "SaPersonalConfigForm{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", warehouseId=" + warehouseId +
                ", areaName='" + areaName + '\'' +
                ", desc='" + desc + '\'' +
                ", isEnable='" + isEnable + '\'' +
                '}';
    }
}
