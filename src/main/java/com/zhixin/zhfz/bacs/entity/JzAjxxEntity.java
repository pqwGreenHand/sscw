package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * JzAjxxEntity table: ajxx_temp
 * 
 * @author wangguhua
 */
public class JzAjxxEntity implements Serializable {
	/** Serial UID */
	private static final long serialVersionUID = 1L;

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

	/** ajlb 案件类别 */
	private String ajlx;

	private String faddxz;//案发地址
	private Date fxsj;//案发时间

	/** zbmj 主办民警 */
	private String zbmj;
	/** zbmjName 主办民警 名称*/
	private String zbmjName;
	/** zbmjId 主办民警 名称*/
	private String zbmjId;
	/** slsj 受理时间 */
	private Timestamp slsj;

	/** slsj 立案时间 */
	private Date laSj;

	/** bmId 部门ID */
	private Long bmId;

	public String getAjlx() {
		return ajlx;
	}

	public void setAjlx(String ajlx) {
		this.ajlx = ajlx;
	}

	public String getFaddxz() {
		return faddxz;
	}

	public void setFaddxz(String faddxz) {
		this.faddxz = faddxz;
	}

	public Date getFxsj() {
		return fxsj;
	}

	public void setFxsj(Date fxsj) {
		this.fxsj = fxsj;
	}

	/** bmmc 部门名称 */
	private String bmmc;
	/** badw 办案单位 */
	private String badw;
	/** jqzt 警情编号 */
	private String jqbh;
	/** rkzt 入库状态 */
	private String rkzt;
	/** resource  来源*/
	private String resource;

	private Date afsj; // 案发时间

	private String jyaq; // 简要案情

	private String afdd; // 案发详细地址

	public String getAfdd() {
		return afdd;
	}

	public void setAfdd(String afdd) {
		this.afdd = afdd;
	}

	public Date getAfsj() {
		return afsj;
	}

	public void setAfsj(Date afsj) {
		this.afsj = afsj;
	}

	public String getJyaq() {
		return jyaq;
	}

	public void setJyaq(String jyaq) {
		this.jyaq = jyaq;
	}

	public String getBadw() {
		return badw;
	}

	public void setBadw(String badw) {
		this.badw = badw;
	}

	/** ajlbz 案件类别字典值 */
	private String ajlbz;

	/** ajztz 案件状态字典值 */
	private String ajztz;

	/** count 统计图的数量 */
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	/** cjsj 创建时间 */
	private Timestamp cjsj;

	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the ajmc
	 */
	public String getAjmc() {
		return this.ajmc;
	}

	/**
	 * @param ajmc
	 *            the ajmc to set
	 */
	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	/**
	 * @return the ajbh
	 */
	public String getAjbh() {
		return this.ajbh;
	}

	/**
	 * @param ajbh
	 *            the ajbh to set
	 */
	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}

	/**
	 * @return the ajlb
	 */
	public String getAjlb() {
		return this.ajlb;
	}

	/**
	 * @param ajlb
	 *            the ajlb to set
	 */
	public void setAjlb(String ajlb) {
		this.ajlb = ajlb;
	}

	/**
	 * @return the ajzt
	 */
	public String getAjzt() {
		return this.ajzt;
	}

	/**
	 * @param ajzt
	 *            the ajzt to set
	 */
	public void setAjzt(String ajzt) {
		this.ajzt = ajzt;
	}

	/**
	 * @return the zbmj
	 */
	public String getZbmj() {
		return this.zbmj;
	}

	/**
	 * @param zbmj
	 *            the zbmj to set
	 */
	public void setZbmj(String zbmj) {
		this.zbmj = zbmj;
	}

	/**
	 * @return the slsj
	 */
	public Timestamp getSlsj() {
		return this.slsj;
	}

	/**
	 * @param slsj
	 *            the slsj to set
	 */
	public void setSlsj(Timestamp slsj) {
		this.slsj = slsj;
	}

	/**
	 * @return the bmId
	 */
	public Long getBmId() {
		return this.bmId;
	}

	/**
	 * @param bmId
	 *            the bmId to set
	 */
	public void setBmId(Long bmId) {
		this.bmId = bmId;
	}

	/**
	 * @return the cjsj
	 */
	public Timestamp getCjsj() {
		return this.cjsj;
	}

	/**
	 * @param cjsj
	 *            the cjsj to set
	 */
	public void setCjsj(Timestamp cjsj) {
		this.cjsj = cjsj;
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

	public Date getLaSj() {
		return laSj;
	}

	public void setLaSj(Date laSj) {
		this.laSj = laSj;
	}

	public String getZbmjName() {
		return zbmjName;
	}

	public void setZbmjName(String zbmjName) {
		this.zbmjName = zbmjName;
	}

	public String getZbmjId() {
		return zbmjId;
	}

	public void setZbmjId(String zbmjId) {
		this.zbmjId = zbmjId;
	}

	@Override
	public String toString() {
		return "JzAjxxEntity{" +
				"id=" + id +
				", ajmc='" + ajmc + '\'' +
				", ajbh='" + ajbh + '\'' +
				", ajlb='" + ajlb + '\'' +
				", ajzt='" + ajzt + '\'' +
				", zbmj='" + zbmj + '\'' +
				", zbmjName='" + zbmjName + '\'' +
				", zbmjId='" + zbmjId + '\'' +
				", slsj=" + slsj +
				", laSj=" + laSj +
				", bmId=" + bmId +
				", bmmc='" + bmmc + '\'' +
				", badw='" + badw + '\'' +
				", jqbh='" + jqbh + '\'' +
				", rkzt='" + rkzt + '\'' +
				", resource='" + resource + '\'' +
				", afsj=" + afsj +
				", jyaq='" + jyaq + '\'' +
				", afdd='" + afdd + '\'' +
				", ajlbz='" + ajlbz + '\'' +
				", ajztz='" + ajztz + '\'' +
				", count=" + count +
				", cjsj=" + cjsj +
				'}';
	}
}