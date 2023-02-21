package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;

public class GmxxEntity implements Serializable {

	private static final long serialVersionUID = 8253154596581408257L;

	private Integer gmsCount;// 总柜门数
	private Integer gmsKx;// 空闲柜门数
	private String gmsSyl;// 柜门数使用率
	private Integer jzsYc;// 卷总数已存
	private Integer jzsGjs;// 卷宗告警数
	private String jzGjl;// 卷宗告警率

	public Integer getGmsCount() {
		return gmsCount;
	}

	public void setGmsCount(Integer gmsCount) {
		this.gmsCount = gmsCount;
	}

	public Integer getGmsKx() {
		return gmsKx;
	}

	public void setGmsKx(Integer gmsKx) {
		this.gmsKx = gmsKx;
	}

	public String getGmsSyl() {
		return gmsSyl;
	}

	public void setGmsSyl(String gmsSyl) {
		this.gmsSyl = gmsSyl;
	}

	public Integer getJzsYc() {
		return jzsYc;
	}

	public void setJzsYc(Integer jzsYc) {
		this.jzsYc = jzsYc;
	}

	public Integer getJzsGjs() {
		return jzsGjs;
	}

	public void setJzsGjs(Integer jzsGjs) {
		this.jzsGjs = jzsGjs;
	}

	public String getJzGjl() {
		return jzGjl;
	}

	public void setJzGjl(String jzGjl) {
		this.jzGjl = jzGjl;
	}

	@Override
	public String toString() {
		return "GmxxEntity{" + "gmsCount=" + gmsCount + ", gmsKx=" + gmsKx + ", gmsSyl='" + gmsSyl + '\'' + ", jzsYc="
				+ jzsYc + ", jzsGjs=" + jzsGjs + ", jzGjl='" + jzGjl + '\'' + '}';
	}
}
