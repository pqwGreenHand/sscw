package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;


public class JzPersonEntity implements Serializable {
	/** Serial UID */
	private static final long serialVersionUID = 1L;

	/** id */
	private String id;

	private String xm; // 姓名

	private String xb; // 性别

	private String ajbh; // 案件编号

	private String rybh; // 人员编号

	private Date lrrq; // 录入时间

	private String zbdw; // 主办单位

	private String ajlb; // 案件类别

	private String zjhm; // 证件号码

	private String hjd; // 户籍地址


	private String ryxm;
	private Date lrsj;
	private String zjlx;
	private String ajmc;
	private String xzdxz;

	public String getRyxm() {
		return ryxm;
	}

	public void setRyxm(String ryxm) {
		this.ryxm = ryxm;
	}

	public Date getLrsj() {
		return lrsj;
	}

	public void setLrsj(Date lrsj) {
		this.lrsj = lrsj;
	}

	public String getZjlx() {
		return zjlx;
	}

	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public String getXzdxz() {
		return xzdxz;
	}

	public void setXzdxz(String xzdxz) {
		this.xzdxz = xzdxz;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public String getAjbh() {
		return ajbh;
	}

	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}

	public String getRybh() {
		return rybh;
	}

	public void setRybh(String rybh) {
		this.rybh = rybh;
	}

	public Date getLrrq() {
		return lrrq;
	}

	public void setLrrq(Date lrrq) {
		this.lrrq = lrrq;
	}

	public String getZbdw() {
		return zbdw;
	}

	public void setZbdw(String zbdw) {
		this.zbdw = zbdw;
	}

	public String getAjlb() {
		return ajlb;
	}

	public void setAjlb(String ajlb) {
		this.ajlb = ajlb;
	}

	public String getZjhm() {
		return zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	public String getHjd() {
		return hjd;
	}

	public void setHjd(String hjd) {
		this.hjd = hjd;
	}

	@Override
	public String toString() {
		return "JzPersonEntity{" +
				"id='" + id + '\'' +
				", xm='" + xm + '\'' +
				", xb='" + xb + '\'' +
				", ajbh='" + ajbh + '\'' +
				", rybh='" + rybh + '\'' +
				", lrrq=" + lrrq +
				", zbdw='" + zbdw + '\'' +
				", ajlb='" + ajlb + '\'' +
				", zjhm='" + zjhm + '\'' +
				", hjd='" + hjd + '\'' +
				", ryxm='" + ryxm + '\'' +
				", lrsj=" + lrsj +
				", zjlx='" + zjlx + '\'' +
				", ajmc='" + ajmc + '\'' +
				", xzdxz='" + xzdxz + '\'' +
				'}';
	}
}