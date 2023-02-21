package com.zhixin.zhfz.jxkp.entity;

public class CopoliceEntity {
	private String login_name;
	private int mjid;
	private String mjxm;
	private String mjdw;
	private int xbsl;
	private int ajsl;
	private int jqsl;
	private int px;
	
	
	
	public int getPx() {
		return px;
	}
	public void setPx(int px) {
		this.px = px;
	}
	public int getJqsl() {
		return jqsl;
	}
	public void setJqsl(int jqsl) {
		this.jqsl = jqsl;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public int getAjsl() {
		return ajsl;
	}
	public void setAjsl(int ajsl) {
		this.ajsl = ajsl;
	}
	public int getMjid() {
		return mjid;
	}
	public void setMjid(int mjid) {
		this.mjid = mjid;
	}
	public String getMjxm() {
		return mjxm;
	}
	public void setMjxm(String mjxm) {
		this.mjxm = mjxm;
	}
	public String getMjdw() {
		return mjdw;
	}
	public void setMjdw(String mjdw) {
		this.mjdw = mjdw;
	}
	public int getXbsl() {
		return xbsl;
	}
	public void setXbsl(int xbsl) {
		this.xbsl = xbsl;
	}
	


	public CopoliceEntity(String login_name, int mjid, String mjxm, String mjdw, int xbsl, int ajsl, int jqsl, int px) {
		super();
		this.login_name = login_name;
		this.mjid = mjid;
		this.mjxm = mjxm;
		this.mjdw = mjdw;
		this.xbsl = xbsl;
		this.ajsl = ajsl;
		this.jqsl = jqsl;
		this.px = px;
	}
	public CopoliceEntity() {};
}
