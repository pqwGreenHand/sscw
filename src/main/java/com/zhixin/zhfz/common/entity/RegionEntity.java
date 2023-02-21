package com.zhixin.zhfz.common.entity;

import java.io.Serializable;

public class RegionEntity implements  Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer code;

    private Integer parentId;

    private String name;

    private Boolean level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getLevel() {
        return level;
    }

    public void setLevel(Boolean level) {
        this.level = level;
    }
}