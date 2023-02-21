package com.zhixin.zhfz.zhag.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * table ag_gjxx
 * 
 * @author Admin
 *
 */
public class GjxxEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4960092334796355503L;

	private Long id;
	private Timestamp gjsj;// 告警时间
	private String wtlx;// 问题类型
	private String wtlxdm;// 问题类型代码
	private String gjly;// 告警来源 0警情1行政2刑事3强制措施4行政处罚
	private String ajbh;// 案件编号
	private String jqbh;// 警情编号
	private String gjzt;// 告警状态
	private Timestamp cjsj;// 创建时间
	private Timestamp xgsj;// 修改时间
	private String cjz;//
	private String tj_gjlx;// 告警类型
	private long total;//总数

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String getTj_gjlx() {
		return tj_gjlx;
	}

	public void setTj_gjlx(String tj_gjlx) {
		this.tj_gjlx = tj_gjlx;
	}

	//case表
	private Long caseId;//案件id
	private String abmc;//案别名称
	private String ajzt;//案件状态
	//jq表
	private String sldwmc;//受立单位名称
	private String cjrxm;//处警人姓名

	private Date startTime;
	private Date endTime;

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
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

	public String getSldwmc() {
		return sldwmc;
	}

	public void setSldwmc(String sldwmc) {
		this.sldwmc = sldwmc;
	}

	public String getCjrxm() {
		return cjrxm;
	}

	public void setCjrxm(String cjrxm) {
		this.cjrxm = cjrxm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getGjsj() {
		return gjsj;
	}

	public void setGjsj(Timestamp gjsj) {
		this.gjsj = gjsj;
	}

	public String getWtlx() {
		return wtlx;
	}

	public void setWtlx(String wtlx) {
		this.wtlx = wtlx;
	}

	public String getWtlxdm() {
		return wtlxdm;
	}

	public void setWtlxdm(String wtlxdm) {
		this.wtlxdm = wtlxdm;
	}

	public String getGjly() {
		return gjly;
	}

	public void setGjly(String gjly) {
		this.gjly = gjly;
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

	public String getGjzt() {
		return gjzt;
	}

	public void setGjzt(String gjzt) {
		this.gjzt = gjzt;
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

	public String getCjz() {
		return cjz;
	}

	public void setCjz(String cjz) {
		this.cjz = cjz;
	}

	@Override
	public String toString() {
		return "GjxxEntity [id=" + id + ", gjsj=" + gjsj + ", wtlx=" + wtlx + ", wtlxdm=" + wtlxdm + ", gjly=" + gjly
				+ ", ajbh=" + ajbh + ", jqbh=" + jqbh + ", gjzt=" + gjzt + ", cjsj=" + cjsj + ", xgsj=" + xgsj
				+ ", cjz=" + cjz + "]";
	}

}
