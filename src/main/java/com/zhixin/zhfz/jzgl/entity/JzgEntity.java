package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 实体类table: jzg 卷宗柜信息
 * 
 * @author xdp
 */
public class JzgEntity implements Serializable {
	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}


	/** Serial UID */
	private static final long serialVersionUID = 1L;

	/** id */
	private Long id;

	/** map 柜子位置 */
	private String map;
	/** mc 柜子名称 */
	private String mc;

	/** ms 柜子描述 */
	private String ms;

	/** ip 柜子ip */
	private String ip;

	/** dk 柜子端口 */
	private Integer dk;

	/** lx 柜子类型 */
	private Integer lx;
	
	/** lx 主柜类型 */
	private Integer zglx;
	/** mllx 命令类型 */
	private Integer mllx;
	
	/** sl 列数 */
	private Integer sl;
    /** wz 所属柜子位置wz */
    private Integer wz;

	/** dlyh 登录用户 */
	private String dlyh;

	/** dlmm 登录密码 */
	private String dlmm;

	/** bmId 部门id */
	private Integer orgId;

	/** cjsj 创建时间 */
	private Timestamp cjsj;

	/** qy 是否启用 */
	private Long qy;

	/** lxz 类型字典值 */
	private String lxz;

	/** qyz 启用字典值 */
	private String qyz;

	/** bmmc 分配部门名称 */
	private String bmmc;
	/** bmmc 分配部门名称 */
	private int count;
	private String lielx;
    private String mapName;
	private String opPid;
	private Integer opUserId;
	
	public Integer getZglx() {
		return zglx;
	}

	public void setZglx(Integer zglx) {
		this.zglx = zglx;
	}

	public Integer getMllx() {
		return mllx;
	}

	public void setMllx(Integer mllx) {
		this.mllx = mllx;
	}

	public String getLielx() {
		return lielx;
	}

	public Integer getWz() {
		return wz;
	}

	public void setWz(Integer wz) {
		this.wz = wz;
	}

	public void setLielx(String lielx) {
		this.lielx = lielx;
	}

	public Integer getSl() {
		return sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}


	/** bmmc 分配部门名称 */
	private String organization;

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public Long getQy() {
		return qy;
	}

	public void setQy(Long qy) {
		this.qy = qy;
	}

	public String getLxz() {
		return lxz;
	}

	public void setLxz(String lxz) {
		this.lxz = lxz;
	}

	public String getQyz() {
		return qyz;
	}

	public void setQyz(String qyz) {
		this.qyz = qyz;
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
	 * @return the mc
	 */
	public String getMc() {
		return this.mc;
	}

	/**
	 * @param mc
	 *            the mc to set
	 */
	public void setMc(String mc) {
		this.mc = mc;
	}

	/**
	 * @return the ms
	 */
	public String getMs() {
		return this.ms;
	}

	/**
	 * @param ms
	 *            the ms to set
	 */
	public void setMs(String ms) {
		this.ms = ms;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return this.ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the dk
	 */
	public Integer getDk() {
		return this.dk;
	}

	/**
	 * @param dk
	 *            the dk to set
	 */
	public void setDk(Integer dk) {
		this.dk = dk;
	}

	/**
	 * @return the lx
	 */
	public Integer getLx() {
		return this.lx;
	}

	/**
	 * @param lx
	 *            the lx to set
	 */
	public void setLx(Integer lx) {
		this.lx = lx;
	}

	/**
	 * @return the dlyh
	 */
	public String getDlyh() {
		return this.dlyh;
	}

	/**
	 * @param dlyh
	 *            the dlyh to set
	 */
	public void setDlyh(String dlyh) {
		this.dlyh = dlyh;
	}

	/**
	 * @return the dlmm
	 */
	public String getDlmm() {
		return this.dlmm;
	}

	/**
	 * @param dlmm
	 *            the dlmm to set
	 */
	public void setDlmm(String dlmm) {
		this.dlmm = dlmm;
	}

	/**
	 * @return the bmId
	 */
	public Integer getOrgId() {
		return this.orgId;
	}

	/**
	 * @param bmId
	 *            the bmId to set
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
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
	
	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
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

	@Override
	public String toString() {
		return "JzgMysqlEntity [id=" + id + ", map=" + map + ", mc=" + mc + ",mapName=" + mapName + ", ms=" + ms + ", ip=" + ip + ", dk=" + dk
				+ ", lx=" + lx + ", zglx=" + zglx + ", mllx=" + mllx + ", sl=" + sl + ", wz=" + wz + ", dlyh=" + dlyh
				+ ", dlmm=" + dlmm + ", orgId=" + orgId + ", cjsj=" + cjsj + ", qy=" + qy + ", lxz=" + lxz + ", qyz="
				+ qyz + ", bmmc=" + bmmc + ", count=" + count + ", lielx=" + lielx + ", organization=" + organization+ ", opPid=" + opPid+ ", opUserId=" + opUserId
				+ "]";
	}

}