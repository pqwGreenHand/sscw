package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * gjxx
 * 
 * @author cuichengwei
 *
 */
public class GjxxEntity implements Serializable {

	private static final long serialVersionUID = 3279873445597693769L;

	private Long id; // 主键id

	private String ajxxId; // 案件id

	private String jzxxId; // 卷宗id

	private Integer gjlx; // 告警类型

	private Integer gjzt; // 告警状态

	private Date cjDate; // 创建时间

	private Date startTime; // 开始时间

	private Date endTime; // 结束时间

	private String ajmc; // 案件编号

	private String ajbh; // 案件名称

	private String jzbh; // 卷宗编号

	private String xsbh; // 柜门号

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAjxxId() {
		return ajxxId;
	}

	public void setAjxxId(String ajxxId) {
		this.ajxxId = ajxxId;
	}

	public String getJzxxId() {
		return jzxxId;
	}

	public void setJzxxId(String jzxxId) {
		this.jzxxId = jzxxId;
	}

	public Integer getGjlx() {
		return gjlx;
	}

	public void setGjlx(Integer gjlx) {
		this.gjlx = gjlx;
	}

	public Integer getGjzt() {
		return gjzt;
	}

	public void setGjzt(Integer gjzt) {
		this.gjzt = gjzt;
	}

	public Date getCjDate() {
		return cjDate;
	}

	public void setCjDate(Date cjDate) {
		this.cjDate = cjDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public String getAjbh() {
		return ajbh;
	}

	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}

	public String getJzbh() {
		return jzbh;
	}

	public void setJzbh(String jzbh) {
		this.jzbh = jzbh;
	}

	public String getXsbh() {
		return xsbh;
	}

	public void setXsbh(String xsbh) {
		this.xsbh = xsbh;
	}

	@Override
	public String toString() {
		return "GjxxEntity [id=" + id + ", ajxxId=" + ajxxId + ", jzxxId=" + jzxxId + ", gjlx=" + gjlx + ", gjzt="
				+ gjzt + ", cjDate=" + cjDate + ", startTime=" + startTime + ", endTime=" + endTime + ", ajmc=" + ajmc
				+ ", ajbh=" + ajbh + ", jzbh=" + jzbh + ", xsbh=" + xsbh + "]";
	}

}
