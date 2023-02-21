package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.util.Date;

public class GrdbEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8805025118121299606L;
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
    
    

}
