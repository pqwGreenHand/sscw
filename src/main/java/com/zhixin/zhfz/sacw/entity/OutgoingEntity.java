package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;
import java.util.Date;

public class OutgoingEntity implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1646452093843010156L;
    private int id;

    private String typeName;

    private Integer counts;

    private Integer count;

    private String name;

    private int wareHouseId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(int wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    @Override
    public String toString() {
        return "OutgoingEntity{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", counts=" + counts +
                ", count=" + count +
                ", name='" + name + '\'' +
                ", wareHouseId=" + wareHouseId +
                '}';
    }
}
