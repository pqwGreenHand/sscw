package com.zhixin.zhfz.jxkp.entity;

public class BJTJEntity {
	private String ajlx;
	private int ajlxid;
	private int ajzs;
	private int kpzs;
	private float avg;
	private float kfsum;

	public float getKfsum() {
		return kfsum;
	}

	public void setKfsum(float kfsum) {
		this.kfsum = kfsum;
	}

	public int getAjlxid() {
		return ajlxid;
	}
	public void setAjlxid(int ajlxid) {
		this.ajlxid = ajlxid;
	}
	public String getAjlx() {
		return ajlx;
	}
	public void setAjlx(String ajlx) {
		this.ajlx = ajlx;
	}
	public int getAjzs() {
		return ajzs;
	}
	public void setAjzs(int ajzs) {
		this.ajzs = ajzs;
	}
	public int getKpzs() {
		return kpzs;
	}
	public void setKpzs(int kpzs) {
		this.kpzs = kpzs;
	}
	public float getAvg() {
		return avg;
	}
	public void setAvg(float avg) {
		this.avg = avg;
	}

	@Override
	public String toString() {
		return "BJTJEntity{" +
				"ajlx='" + ajlx + '\'' +
				", ajlxid=" + ajlxid +
				", ajzs=" + ajzs +
				", kpzs=" + kpzs +
				", avg=" + avg +
				", kfsum=" + kfsum +
				'}';
	}

	public BJTJEntity() {};
	
}
