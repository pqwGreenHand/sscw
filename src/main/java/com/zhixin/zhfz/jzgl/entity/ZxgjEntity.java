package com.zhixin.zhfz.jzgl.entity;

import java.util.Date;


public class ZxgjEntity {
    public String jzbh;
    public String zrr;
    public Integer xxlx;
    public Date gjsj;

    public String getJzbh() {
        return jzbh;
    }

    public void setJzbh(String jzbh) {
        this.jzbh = jzbh;
    }

    public String getZrr() {
        return zrr;
    }

    public void setZrr(String zrr) {
        this.zrr = zrr;
    }

    public Integer getXxlx() {
        return xxlx;
    }

    public void setXxlx(Integer xxlx) {
        this.xxlx = xxlx;
    }

    public Date getGjsj() {
        return gjsj;
    }

    public void setGjsj(Date gjsj) {
        this.gjsj = gjsj;
    }

    @Override
    public String toString() {
        return "ZxgjEntity{" +
                "jzbh='" + jzbh + '\'' +
                ", zrr='" + zrr + '\'' +
                ", xxlx=" + xxlx +
                ", gjsj=" + gjsj +
                '}';
    }
}
