package com.zhixin.zhfz.bacsapp.vo;

import java.util.List;

public class PageResponse<T> {

    List<T> list;

    int count;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public PageResponse(List list,int count){
        this.count = count;
        this.list = list;
    }

    public static PageResponse of(List list,int count){
        return new PageResponse(list,count);
    }
}
