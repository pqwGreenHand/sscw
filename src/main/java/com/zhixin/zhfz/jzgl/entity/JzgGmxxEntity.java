package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;

import java.sql.Timestamp;

/**
 * 实体类 table: jzg
 * 
 * @author xdp
 */
public class JzgGmxxEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8583071199617841833L;

	/** id */
	private Long id;

	/** jzg_id 所属卷宗柜id */
	private Long jzgId;

	/** jzg_lie_id 所属卷宗柜列id */
	private Long jzgLieId;

	/** lx 类型lx（0普通1重要） */
	private String lx;
	
	/** pz 类型pz（0 16进制 1 长字符串） */
	private String pz;

	public String getPz() {
		return pz;
	}

	public void setPz(String pz) {
		this.pz = pz;
	}

	/** mlbh 命令编号 */
	private String mlbh;
	
	//命令类型
	private Integer mllx;

	public Integer getMllx() {
		return mllx;
	}

	public void setMllx(Integer mllx) {
		this.mllx = mllx;
	}

	/** xsbh 显示编号 */
	private String xsbh;

	/** cjsj 创建时间 */
	private Timestamp cjsj;

	/** qy 是否启用 */
	private Long qy;

	/** mc 柜子名称 */
	private String mc;

	/** wz 柜子位置 */
	private String wz;

	public String getWz() {
		return wz;
	}

	public void setWz(String wz) {
		this.wz = wz;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJzgId() {
		return jzgId;
	}

	public void setJzgId(Long jzgId) {
		this.jzgId = jzgId;
	}

	public Long getJzgLieId() {
		return jzgLieId;
	}

	public void setJzgLieId(Long jzgLieId) {
		this.jzgLieId = jzgLieId;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getMlbh() {
		return mlbh;
	}

	public void setMlbh(String mlbh) {
		this.mlbh = mlbh;
	}

	public String getXsbh() {
		return xsbh;
	}

	public void setXsbh(String xsbh) {
		this.xsbh = xsbh;
	}

	public Timestamp getCjsj() {
		return cjsj;
	}

	public void setCjsj(Timestamp cjsj) {
		this.cjsj = cjsj;
	}

	public Long getQy() {
		return qy;
	}

	public void setQy(Long qy) {
		this.qy = qy;
	}

	@Override
	public String toString() {
		return "JzgGmxxMysqlEntity [id=" + id + ", jzgId=" + jzgId + ", jzgLieId=" + jzgLieId + ", lx=" + lx + ", pz="
				+ pz + ", mlbh=" + mlbh + ", mllx=" + mllx + ", xsbh=" + xsbh + ", cjsj=" + cjsj + ", qy=" + qy
				+ ", mc=" + mc + ", wz=" + wz + "]";
	}

}