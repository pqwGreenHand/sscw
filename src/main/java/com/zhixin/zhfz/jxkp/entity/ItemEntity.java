package com.zhixin.zhfz.jxkp.entity;

/**
 * Created by wangly on 2018/5/2.
 */
public class ItemEntity {
    private  Integer id;
    private String item;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", item='" + item + '\'' +
                '}';
    }
}
