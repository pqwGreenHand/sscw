package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 实体类JzxxEntity table: jzxx
 * 
 * @author cuichengwei
 */
public class JzxxEntity implements Serializable {

	private static final long serialVersionUID = 8848426333189180329L;

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

	/** ajmc 案件名称 */
	private String ajmc;

	/** xm 姓名 */
	private String xm;

	/** xsbh 显示编号 */
	private String xsbh;

	/** jzgMc 卷宗柜名称 */
	private String jzgMc;

	/** ajbh 案件编号 */
	private String ajbh;

	/** zbmj 主办民警 */
	private String zbmj;

	/** ajlb 案件类别 */
	private String ajlb;
	/** zrr 责任人 */
	private String zrr;
	/** pageNum 页数 */
	private Integer pageNum;
	/** 协警警号 */
	private String policeNo;
	
	private String opPid;
	private Integer opUserId;

	public String getZrr() {
		return zrr;
	}

	public void setZrr(String zrr) {
		this.zrr = zrr;
	}

	/** slsj 受理时间 */
	private Timestamp slsj;

	private String z;

	private String ztms;

	private String ajlbms;

	/** slsj 更新时间 */
	private Timestamp gxsj;

	/** ajmcJzbh 案件名称和卷宗编号的组合便于显示 */
	private String ajmcJzbh;

	/** ajmcJzbh 案件id和卷宗id的组合打印二维码使用 */
	private String ajIdJzId;

	/** bmmc 保管人部门名称 */
	private String bmmc;

	/** ajmcJzbhFlag 案件名称和卷宗编号的组合长度标识便于前台js打印二维码 */
	private String ajmcJzbhFlag;

	/** yjnr 卷宗预警内容 */
	private String yjnr;

	/** yjlx 卷宗预警类型 */
	private Integer yjlx;

	/** mc 卷宗柜名称 */
	private String mc;
	private String organization;
	private String count;

	private Integer jzlx;

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "JzxxMysqlEntity [id=" + id + ", ajxxId=" + ajxxId + ", userId=" + userId + ", gmxxId=" + gmxxId + ", jzzt="
				+ jzzt + ", jzbh=" + jzbh + ", cjsj=" + cjsj + ", ajmc=" + ajmc + ", xm=" + xm + ", xsbh=" + xsbh
				+ ", jzgMc=" + jzgMc + ", ajbh=" + ajbh + ", zbmj=" + zbmj + ", ajlb=" + ajlb + ", zrr=" + zrr
				+ ", slsj=" + slsj + ", z=" + z + ", ztms=" + ztms + ", ajlbms=" + ajlbms + ", gxsj=" + gxsj
				+ ", ajmcJzbh=" + ajmcJzbh + ", ajIdJzId=" + ajIdJzId + ", bmmc=" + bmmc + ", ajmcJzbhFlag="
				+ ajmcJzbhFlag + ", yjnr=" + yjnr + ", yjlx=" + yjlx + ", mc=" + mc + ", organization=" + organization
				+ ", count=" + count + ", jzlx=" + jzlx + ", jzms=" + jzms + ", jz_count=" + jz_count + ", zrrname="
				+ zrrname + ", incount=" + incount + ", outcount=" + outcount + ", uuid=" + uuid + "]";
	}

	public String getZrrname() {
		return zrrname;
	}

	public void setZrrname(String zrrname) {
		this.zrrname = zrrname;
	}

	private String jzms;

	private Integer jz_count;
	private String zrrname;

	public Integer getJz_count() {
		return jz_count;
	}

	public void setJz_count(Integer jz_count) {
		this.jz_count = jz_count;
	}

	public String getZ() {
		return z;
	}

	public void setZ(String z) {
		this.z = z;
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

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	/** count */
	private Integer incount;

	private Integer outcount;

	public Integer getIncount() {
		return incount;
	}

	public void setIncount(Integer incount) {
		this.incount = incount;
	}

	public Integer getOutcount() {
		return outcount;
	}

	public void setOutcount(Integer outcount) {
		this.outcount = outcount;
	}

	public String getYjnr() {
		return yjnr;
	}

	public void setYjnr(String yjnr) {
		this.yjnr = yjnr;
	}

	public Integer getYjlx() {
		return yjlx;
	}

	public void setYjlx(Integer yjlx) {
		this.yjlx = yjlx;
	}

	/** uuid */
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAjmcJzbhFlag() {
		return ajmcJzbhFlag;
	}

	public void setAjmcJzbhFlag(String ajmcJzbhFlag) {
		this.ajmcJzbhFlag = ajmcJzbhFlag;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getAjIdJzId() {
		return ajIdJzId;
	}

	public void setAjIdJzId(String ajIdJzId) {
		this.ajIdJzId = ajIdJzId;
	}

	public String getAjmcJzbh() {
		return ajmcJzbh;
	}

	public void setAjmcJzbh(String ajmcJzbh) {
		this.ajmcJzbh = ajmcJzbh;
	}

	public Timestamp getGxsj() {
		return gxsj;
	}

	public void setGxsj(Timestamp gxsj) {
		this.gxsj = gxsj;
	}

	public String getAjbh() {
		return ajbh;
	}

	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}

	public String getZbmj() {
		return zbmj;
	}

	public void setZbmj(String zbmj) {
		this.zbmj = zbmj;
	}

	public String getAjlb() {
		return ajlb;
	}

	public void setAjlb(String ajlb) {
		this.ajlb = ajlb;
	}

	public Timestamp getSlsj() {
		return slsj;
	}

	public void setSlsj(Timestamp slsj) {
		this.slsj = slsj;
	}

	public String getJzgMc() {
		return jzgMc;
	}

	public void setJzgMc(String jzgMc) {
		this.jzgMc = jzgMc;
	}

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getXsbh() {
		return xsbh;
	}

	public void setXsbh(String xsbh) {
		this.xsbh = xsbh;
	}

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

	/**
	 * @return the jzzt
	 */
	public Integer getJzzt() {
		return this.jzzt;
	}

	/**
	 * @param jzzt
	 *            the jzzt to set
	 */
	public void setJzzt(Integer jzzt) {
		this.jzzt = jzzt;
	}

	public String getJzbh() {
		return jzbh;
	}

	public void setJzbh(String jzbh) {
		this.jzbh = jzbh;
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

	public String getZtms() {
		return ztms;
	}

	public void setZtms(String ztms) {
		this.ztms = ztms;
	}

	public String getAjlbms() {
		return ajlbms;
	}

	public void setAjlbms(String ajlbms) {
		this.ajlbms = ajlbms;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}

	public String getOpPid() {
		return opPid;
	}

	public void setOpPid(String opPid) {
		this.opPid = opPid;
	}

	public Integer getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(Integer opUserId) {
		this.opUserId = opUserId;
	}

}