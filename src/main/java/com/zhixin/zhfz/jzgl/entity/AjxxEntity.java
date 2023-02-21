package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 实体类: jz_ajxx
 * 
 * @author xdp
 */
public class AjxxEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 911035684202826515L;

	/** id */
	private Long id;

	/** ajmc 案件名称 */
	private String ajmc;

	/** ajbh 案件编号 */
	private String ajbh;

	/** ajlb 案件类别 */
	private String ajlb;

	/** ajzt 案件状态 */
	private String ajzt;

	/** zbmj 主办民警 */
	private String zbmj;

	/** slsj 受理时间 */
	private Timestamp slsj;

	/** lasj 立案时间 */
	private Date lasj;

	/** orgId 部门ID */
	private Long orgId;

	/** cjsj 创建时间 */
	private Date cjsj;

	/** bmmc 部门名称 */
	private String bmmc;

	/** badw 办案单位 */
	private String badw;

	/** jqzt 警情编号 */
	private String jqbh;

	/** rkzt 入库状态 */
	private String rkzt;

	/** resource 来源 */
	private String resource;

	/** ajlbz 案件类别字典值 */
	private String ajlbz;

	/** ajztz 案件状态字典值 */
	private String ajztz;

	/** count 统计图的数量 */
	private Integer count;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAjlb() {
		return ajlb;
	}

	public void setAjlb(String ajlb) {
		this.ajlb = ajlb;
	}

	public String getAjzt() {
		return ajzt;
	}

	public void setAjzt(String ajzt) {
		this.ajzt = ajzt;
	}

	public String getZbmj() {
		return zbmj;
	}

	public void setZbmj(String zbmj) {
		this.zbmj = zbmj;
	}

	public Timestamp getSlsj() {
		return slsj;
	}

	public void setSlsj(Timestamp slsj) {
		this.slsj = slsj;
	}

	public Date getLasj() {
		return lasj;
	}

	public void setLasj(Date lasj) {
		this.lasj = lasj;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getBadw() {
		return badw;
	}

	public void setBadw(String badw) {
		this.badw = badw;
	}

	public String getJqbh() {
		return jqbh;
	}

	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}

	public String getRkzt() {
		return rkzt;
	}

	public void setRkzt(String rkzt) {
		this.rkzt = rkzt;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getAjlbz() {
		return ajlbz;
	}

	public void setAjlbz(String ajlbz) {
		this.ajlbz = ajlbz;
	}

	public String getAjztz() {
		return ajztz;
	}

	public void setAjztz(String ajztz) {
		this.ajztz = ajztz;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "AjxxMysqlEntity [id=" + id + ", ajmc=" + ajmc + ", ajbh=" + ajbh + ", ajlb=" + ajlb + ", ajzt=" + ajzt
				+ ", zbmj=" + zbmj + ", slsj=" + slsj + ", org_id=" + orgId + ", bmmc=" + bmmc + ", badw=" + badw
				+ ", jqbh=" + jqbh + ", rkzt=" + rkzt + ", lasj=" + lasj+ ", cjsj=" + cjsj
				+ "]";
	}

}