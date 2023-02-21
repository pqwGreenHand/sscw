package com.zhixin.zhfz.zhag.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * table ag_cscf
 * @author Admin
 *
 */
public class CscfEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4277182269900473341L;
	private long id;
	private long total;
	private String ajbh;// 案件编号
	private String jqbh;// 警情编号
	private String rybh;// 人员编号
	private String cszt;// 强制措施名称
	private Integer csztdm;// 案件状态即强制措施状态代码
	private String csztdmmc;//案件状态 名称
	private Integer lx;//0强制措施，1行政处罚
	private String lxmc;//类型名称
	private String zxsj;// 执行时间
	private Timestamp xgsj;// 修改时间
	private Timestamp cjsj;// 创建时间
	private String tj_result;// 行政处罚/强制措施
	private String ab;// 行政处罚/强制措施
	private String abmc;// 行政处罚/强制措施
	private long caseId; //案件id
	public String getAb() {
		return ab;
	}

	public void setAb(String ab) {
		this.ab = ab;
	}

	public String getAbmc() {
		return abmc;
	}

	public void setAbmc(String abmc) {
		this.abmc = abmc;
	}

	public String getTj_result() {
		return tj_result;
	}

	public void setTj_result(String tj_result) {
		this.tj_result = tj_result;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getRybh() {
		return rybh;
	}
	public void setRybh(String rybh) {
		this.rybh = rybh;
	}
	public String getCszt() {
		return cszt;
	}
	public void setCszt(String cszt) {
		this.cszt = cszt;
	}
	public Integer getCsztdm() {
		return csztdm;
	}
	public void setCsztdm(Integer csztdm) {
		this.csztdm = csztdm;
	}
	
	public Integer getLx() {
		return lx;
	}
	public void setLx(Integer lx) {
		this.lx = lx;
	}
	public String getZxsj() {
		return zxsj;
	}
	public void setZxsj(String zxsj) {
		this.zxsj = zxsj;
	}
	public Timestamp getXgsj() {
		return xgsj;
	}
	public void setXgsj(Timestamp xgsj) {
		this.xgsj = xgsj;
	}
	public Timestamp getCjsj() {
		return cjsj;
	}
	public void setCjsj(Timestamp cjsj) {
		this.cjsj = cjsj;
	}
	
	public String getCsztdmmc() {
		return csztdmmc;
	}
	public void setCsztdmmc(String csztdmmc) {
		this.csztdmmc = csztdmmc;
	}
	public String getLxmc() {
		return lxmc;
	}
	public void setLxmc(String lxmc) {
		this.lxmc = lxmc;
	}
	
	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	@Override
	public String toString() {
		return "CscfEntity [id=" + id + ", ajbh=" + ajbh + ", jqbh=" + jqbh + ", rybh=" + rybh + ", cszt=" + cszt
				+ ", csztdm=" + csztdm + ", csztdmmc=" + csztdmmc + ", lx=" + lx + ", lxmc=" + lxmc + ", zxsj=" + zxsj
				+ ", xgsj=" + xgsj + ", cjsj=" + cjsj + "]";
	}
	 
	 
	
	
}
