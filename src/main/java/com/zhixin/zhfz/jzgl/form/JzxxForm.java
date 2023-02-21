/*
 * FormName: JzxxMysqlEntity.java
 * auto create by wangguhua
 * Description: JzxxMysqlForm实体类   
 */

package com.zhixin.zhfz.jzgl.form;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 实体类JzxxMysqlEntity table: jzxx
 * 
 * @author wangguhua
 */
public class JzxxForm implements Serializable {
	/** Serial UID */
	private static final long serialVersionUID = 1L;

	/** id */
	private Long id;

	/** ajxxId 案件信息ID */
	private Long ajxxId;

	/** userId 用户ID */
	private Long userId;

	/** gmxxId 柜门ID */
	private Long gmxxId;

	/** jzzt 卷宗状态 */
	private Integer jzzt;

	/** jzbh 卷宗编号 */
	private String jzbh;

	/** cjsj 创建时间 */
	private Timestamp cjsj;

	/** jzgMc 卷宗柜名称 */
	private String jzgMc;

	/** uuid */
	private String uuid;

	private Integer jzlx;

	private String jzms;
	/** 卷宗种类集合 */
	private String jzzlList;
	/** 卷宗类别 */
	private String jzlb;
	/** pageNum 页数 */
	private Integer pageNum;
	/** addpage 增加页数 */
	private Integer addpage;
	/** 案件编号 */
	private String ajbh;
	/** 协警警号 */
	private String policeNo;

	@Override
	public String toString() {
		return "JzxxForm [id=" + id + ", ajxxId=" + ajxxId + ", userId=" + userId + ", gmxxId=" + gmxxId + ", jzzt="
				+ jzzt + ", jzbh=" + jzbh + ", cjsj=" + cjsj + ", jzgMc=" + jzgMc + ", uuid=" + uuid + ", jzlx=" + jzlx
				+ ", jzms=" + jzms + ", jzzlList=" + jzzlList + ", jzlb=" + jzlb + ", pageNum=" + pageNum + ", addpage="
				+ addpage + ", ajbh=" + ajbh + ", policeNo=" + policeNo + ", zrr=" + zrr + ", ajmc=" + ajmc + "]";
	}

	/** zrr 责任人 */
	private String zrr;

	public String getJzlb() {
		return jzlb;
	}

	public void setJzlb(String jzlb) {
		this.jzlb = jzlb;
	}

	public String getJzzlList() {
		return jzzlList;
	}

	public void setJzzlList(String jzzlList) {
		this.jzzlList = jzzlList;
	}

	public Integer getJzlx() {
		return jzlx;
	}

	public void setJzlx(Integer jzlx) {
		this.jzlx = jzlx;
	}

	public String getJzms() {
		return jzms;
	}

	public void setJzms(String jzms) {
		this.jzms = jzms;
	}

	public String getUuid() {
		return uuid;
	}

	private String ajmc;

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * 
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
	 * @return the ajxxId
	 */
	public Long getAjxxId() {
		return this.ajxxId;
	}

	/**
	 * @param ajxxId
	 *            the ajxxId to set
	 */
	public void setAjxxId(Long ajxxId) {
		this.ajxxId = ajxxId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the gmxxId
	 */
	public Long getGmxxId() {
		return this.gmxxId;
	}

	/**
	 * @param gmxxId
	 *            the gmxxId to set
	 */
	public void setGmxxId(Long gmxxId) {
		this.gmxxId = gmxxId;
	}

	public Integer getJzzt() {
		return jzzt;
	}

	public void setJzzt(Integer jzzt) {
		this.jzzt = jzzt;
	}

	/**
	 * @return the cjsj
	 */
	public Timestamp getCjsj() {
		return this.cjsj;
	}

	public String getJzbh() {
		return jzbh;
	}

	public void setJzbh(String jzbh) {
		this.jzbh = jzbh;
	}

	/**
	 * @param cjsj
	 *            the cjsj to set
	 */
	public void setCjsj(Timestamp cjsj) {
		this.cjsj = cjsj;
	}

	public String getJzgMc() {
		return jzgMc;
	}

	public void setJzgMc(String jzgMc) {
		this.jzgMc = jzgMc;
	}

	public String getZrr() {
		return zrr;
	}

	public void setZrr(String zrr) {
		this.zrr = zrr;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getAddpage() {
		return addpage;
	}

	public void setAddpage(Integer addpage) {
		this.addpage = addpage;
	}

	public String getAjbh() {
		return ajbh;
	}

	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}

	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}

}