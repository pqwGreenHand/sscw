package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class JzgLieEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8268072125745228130L;

	/** id */
	private Long id;

	/** jzg_id 所属卷宗柜id */
	private Long jzgId;

	/** lx 列类型（3行,5行,7行） */
	private Integer lx;

	/** wz 列所在位置，左边为第一列 */
	private Integer wz;

	/** org_id 部门 */
	private Integer orgId;

	/** cjsj 创建时间 */
	private Timestamp cjsj;

	/** qy 是否启用 */
	private Long qy;

	/** mc 柜子名称 */
	private String mc;

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

	public Integer getLx() {
		return lx;
	}

	public void setLx(Integer lx) {
		this.lx = lx;
	}

	public Integer getWz() {
		return wz;
	}

	public void setWz(Integer wz) {
		this.wz = wz;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
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
		return "JzgLieEntity [id=" + id + ", jzgId=" + jzgId + ", lx=" + lx + ", wz=" + wz + ", orgId=" + orgId
				+ ", cjsj=" + cjsj + ", qy=" + qy + ", mc=" + mc + "]";
	}

}
