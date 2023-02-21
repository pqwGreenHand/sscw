package com.zhixin.zhfz.zhag.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * table ag_log
 * @author Admin
 *
 */
public class LogEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5211099721536620854L;

	private Long id;
	private String lx;// 类型
	private String nr;// 日志内容
	private String ajbh;// 案件编号
	private String jqbh;// 警情编号
	private Integer yjid;// 预警id
	private Integer gjid;// 告警id
	private Timestamp cjsj;// 创建时间
	private String cjr;// 创建人
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLx() {
		return lx;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
	public String getNr() {
		return nr;
	}
	public void setNr(String nr) {
		this.nr = nr;
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
	public Integer getYjid() {
		return yjid;
	}
	public void setYjid(Integer yjid) {
		this.yjid = yjid;
	}
	public Integer getGjid() {
		return gjid;
	}
	public void setGjid(Integer gjid) {
		this.gjid = gjid;
	}
	public Timestamp getCjsj() {
		return cjsj;
	}
	public void setCjsj(Timestamp cjsj) {
		this.cjsj = cjsj;
	}
	public String getCjr() {
		return cjr;
	}
	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	@Override
	public String toString() {
		return "LogEntity [id=" + id + ", lx=" + lx + ", nr=" + nr + ", ajbh=" + ajbh + ", jqbh=" + jqbh + ", yjid="
				+ yjid + ", gjid=" + gjid + ", cjsj=" + cjsj + ", cjr=" + cjr + "]";
	}
	
	
}
