package com.zhixin.zhfz.glpt.vo;

import java.util.List;

public class PageResponse<T> {

    List<T> rows;

    Integer total;


    public PageResponse(){}

    public PageResponse(List<T> rows, Integer total){
        this.total = total;
        this.rows = rows;
    }

    public static PageResponse of(List rows,Integer total){
        return new PageResponse(rows,total);
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
