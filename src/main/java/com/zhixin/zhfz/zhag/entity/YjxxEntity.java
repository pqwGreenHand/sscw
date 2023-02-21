package com.zhixin.zhfz.zhag.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * table ag_gjxx
 * @author Admin
 *
 */
public class YjxxEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3919406353418172248L;

	private Long id;
	private Timestamp yjsj;// 预警时间
	private String yjjb;// 预警级别
	private String yjlx;// 预警类型
	private Integer yjly;// 预警来源 0警情1行政2刑事3强制措施4行政处罚
	private String yjnr;// 预警内容
	private String ajbh;// 案件编号
	private String jqbh;// 警情编号
	private String yjzt;// 预警状态
	private Timestamp cjsj;// 创建时间
	private Timestamp xgsj;// 修改时间

	//关联表
	private String abmc;//案别名称
	private String ajzt;//案件状态

	public String getAbmc() {
		return abmc;
	}

	public void setAbmc(String abmc) {
		this.abmc = abmc;
	}

	public String getAjzt() {
		return ajzt;
	}

	public void setAjzt(String ajzt) {
		this.ajzt = ajzt;
	}

	private Date startTime;
	private Date endTime;

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

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getYjsj() {
		return yjsj;
	}
	public void setYjsj(Timestamp yjsj) {
		this.yjsj = yjsj;
	}
	public String getYjjb() {
		return yjjb;
	}
	public void setYjjb(String yjjb) {
		this.yjjb = yjjb;
	}
	public String getYjlx() {
		return yjlx;
	}
	public void setYjlx(String yjlx) {
		this.yjlx = yjlx;
	}
	public Integer getYjly() {
		return yjly;
	}
	public void setYjly(Integer yjly) {
		this.yjly = yjly;
	}
	public String getYjnr() {
		return yjnr;
	}
	public void setYjnr(String yjnr) {
		this.yjnr = yjnr;
	}
	public String getAjbh() {
		return ajbh;
	}
	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}
	public String getJqbh() {
		return jqbh;
	}
	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}
	public String getYjzt() {
		return yjzt;
	}
	public void setYjzt(String yjzt) {
		this.yjzt = yjzt;
	}
	public Timestamp getCjsj() {
		return cjsj;
	}
	public void setCjsj(Timestamp cjsj) {
		this.cjsj = cjsj;
	}
	public Timestamp getXgsj() {
		return xgsj;
	}
	public void setXgsj(Timestamp xgsj) {
		this.xgsj = xgsj;
	}
	@Override
	public String toString() {
		return "YjxxEntity [id=" + id + ", yjsj=" + yjsj + ", yjjb=" + yjjb + ", yjlx=" + yjlx + ", yjly=" + yjly
				+ ", yjnr=" + yjnr + ", ajbh=" + ajbh + ", jqbh=" + jqbh + ", yjzt=" + yjzt + ", cjsj=" + cjsj
				+ ", xgsj=" + xgsj + "]";
	}

	
}
