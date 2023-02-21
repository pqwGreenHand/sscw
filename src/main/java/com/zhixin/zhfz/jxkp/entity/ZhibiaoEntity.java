package com.zhixin.zhfz.jxkp.entity;

public class ZhibiaoEntity {
	private int id;
	private String xlh;
	private String zbbz;
	private int ajsl;
	private int cxcs;
	private int kfzz;
	private String kfzb;
	private String kfzddw;
	private int px;
	

	public int getPx() {
		return px;
	}
	public void setPx(int px) {
		this.px = px;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getXlh() {
		return xlh;
	}
	public void setXlh(String xlh) {
		this.xlh = xlh;
	}
	public String getZbbz() {
		return zbbz;
	}
	public void setZbbz(String zbbz) {
		this.zbbz = zbbz;
	}
	public int getAjsl() {
		return ajsl;
	}
	public void setAjsl(int ajsl) {
		this.ajsl = ajsl;
	}
	public int getCxcs() {
		return cxcs;
	}
	public void setCxcs(int cxcs) {
		this.cxcs = cxcs;
	}
	public int getKfzz() {
		return kfzz;
	}
	public void setKfzz(int kfzz) {
		this.kfzz = kfzz;
	}
	public String getKfzb() {
		return kfzb;
	}
	public void setKfzb(String kfzb) {
		this.kfzb = kfzb;
	}
	public String getKfzddw() {
		return kfzddw;
	}
	public void setKfzddw(String kfzddw) {
		this.kfzddw = kfzddw;
	}
	public ZhibiaoEntity(int id,String xlh, String zbbz, int ajsl, int cxcs, int kfzz, String kfzb, String kfzddw,int px) {
		super();
		this.id = id;
		this.xlh = xlh;
		this.zbbz = zbbz;
		this.ajsl = ajsl;
		this.cxcs = cxcs;
		this.kfzz = kfzz;
		this.kfzb = kfzb;
		this.kfzddw = kfzddw;
		this.px = px;
	}
	public ZhibiaoEntity() {};
}
