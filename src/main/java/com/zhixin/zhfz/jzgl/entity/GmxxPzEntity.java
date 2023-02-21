package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;

/**
 * jz_gmxx_pz
 * @author xdp
 *
 */
public class GmxxPzEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8698391581167408642L;
	/** id */
	private Long id;
	// 0 16进制值 1 长字符串值
	private Integer lx;
	// 显示编号
	private String xsbh;
	// 命令编号
	private String mlbh;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLx() {
		return lx;
	}

	public void setLx(Integer lx) {
		this.lx = lx;
	}

	public String getXsbh() {
		return xsbh;
	}

	public void setXsbh(String xsbh) {
		this.xsbh = xsbh;
	}

	public String getMlbh() {
		return mlbh;
	}

	public void setMlbh(String mlbh) {
		this.mlbh = mlbh;
	}

	@Override
	public String toString() {
		return "GmxxPzEntity [id=" + id + ", lx=" + lx + ", xsbh=" + xsbh + ", mlbh=" + mlbh + "]";
	}
}
