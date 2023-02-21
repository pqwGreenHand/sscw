package com.zhixin.zhfz.bacsapp.entity;

import java.io.Serializable;

public class ZbryMenuEntity implements Serializable {

    private int aj; //安全检查

    private int nj; //尿检登记

    private int sw; //随身物品

    private int sa; //涉案物品

    private int gj = 1; //轨迹登记

    private int ax = 1; //案件查询

    public int getAj() {
        return aj;
    }

    public void setAj(int aj) {
        this.aj = aj;
    }

    public int getNj() {
        return nj;
    }

    public void setNj(int nj) {
        this.nj = nj;
    }

    public int getSw() {
        return sw;
    }

    public void setSw(int sw) {
        this.sw = sw;
    }

    public int getSa() {
        return sa;
    }

    public void setSa(int sa) {
        this.sa = sa;
    }

    public int getGj() {
        return gj;
    }

    public void setGj(int gj) {
        this.gj = gj;
    }

    public int getAx() {
        return ax;
    }

    public void setAx(int ax) {
        this.ax = ax;
    }
}
