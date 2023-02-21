package com.zhixin.zhfz.jxkp.entity;

import java.io.Serializable;

/**
 * Created by wangly on 2018/5/2.
 */
public class KeyEntity implements Serializable{
    private  String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "KeyEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
