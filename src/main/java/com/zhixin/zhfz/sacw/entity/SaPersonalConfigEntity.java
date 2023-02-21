package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;

public class SaPersonalConfigEntity implements Serializable {

    private static final long serialVersionUID = 6107046760789030997L;

    private int id;
    private String type;
    private String desc;
    private int warehouseId;
    private String warehouseName;
    private int isEnable;
    private String op_pid;
    private Integer op_user_id;

    public String getOp_pid() {
        return op_pid;
    }

    public void setOp_pid(String op_pid) {
        this.op_pid = op_pid;
    }

    public Integer getOp_user_id() {
        return op_user_id;
    }

    public void setOp_user_id(Integer op_user_id) {
        this.op_user_id = op_user_id;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }


    @Override
    public String toString() {
        return "SaPersonalConfigEntity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", isEnable=" + isEnable +
                '}';
    }
}
