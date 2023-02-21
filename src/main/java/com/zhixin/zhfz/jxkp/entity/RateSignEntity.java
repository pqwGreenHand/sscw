package com.zhixin.zhfz.jxkp.entity;

import java.io.Serializable;

/**
 * Created by wangl on 2018/11/21.
 */
public class RateSignEntity implements Serializable{
   private String name;
   private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "RateSignEntity{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
