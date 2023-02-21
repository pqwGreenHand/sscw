package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类CrgLogEntity table: crgLog
 * 
 * @author cuichengwei
 */
public class CrgLogEntity implements Serializable {

	private static final long serialVersionUID = 6870332522857929893L;

	private Long id;

	private Long ajxxId;

	private Long jzxxId;

	private Long cglx;

	private String reason;

	private String remark;

	private Integer yqday;

	private Integer cgsj;

	private Date cjsj;

	private String uuid;

	private Long jzzt;

	private String cgType;

	private String yhId;

	private String gmxxId;

	private Date time;

	private Long czlx;

	private Integer opUserId;

	private Integer opPid;

	private String czrName;

	public String getCzrName() {
		return czrName;
	}

	public void setCzrName(String czrName) {
		this.czrName = czrName;
	}

	public Integer getOpPid() {
		return opPid;
	}

	public void setOpPid(Integer opPid) {
		this.opPid = opPid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAjxxId() {
		return ajxxId;
	}

	public void setAjxxId(Long ajxxId) {
		this.ajxxId = ajxxId;
	}

	public Long getJzxxId() {
		return jzxxId;
	}

	public void setJzxxId(Long jzxxId) {
		this.jzxxId = jzxxId;
	}

	public Long getCglx() {
		return cglx;
	}

	public void setCglx(Long cglx) {
		this.cglx = cglx;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getYqday() {
		return yqday;
	}

	public void setYqday(Integer yqday) {
		this.yqday = yqday;
	}

	public Integer getCgsj() {
		return cgsj;
	}

	public void setCgsj(Integer cgsj) {
		this.cgsj = cgsj;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getJzzt() {
		return jzzt;
	}

	public void setJzzt(Long jzzt) {
		this.jzzt = jzzt;
	}

	public String getCgType() {
		return cgType;
	}

	public void setCgType(String cgType) {
		this.cgType = cgType;
	}

	public String getYhId() {
		return yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}

	public String getGmxxId() {
		return gmxxId;
	}

	public void setGmxxId(String gmxxId) {
		this.gmxxId = gmxxId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getCzlx() {
		return czlx;
	}

	public void setCzlx(Long czlx) {
		this.czlx = czlx;
	}

	public Integer getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(Integer opUserId) {
		this.opUserId = opUserId;
	}

	@Override
	public String toString() {
		return "CrgLogEntity{" +
				"id=" + id +
				", ajxxId=" + ajxxId +
				", jzxxId=" + jzxxId +
				", cglx=" + cglx +
				", reason='" + reason + '\'' +
				", remark='" + remark + '\'' +
				", yqday=" + yqday +
				", cgsj=" + cgsj +
				", cjsj=" + cjsj +
				", uuid='" + uuid + '\'' +
				", jzzt=" + jzzt +
				", cgType='" + cgType + '\'' +
				", yhId='" + yhId + '\'' +
				", gmxxId='" + gmxxId + '\'' +
				", time=" + time +
				", czlx=" + czlx +
				", opUserId='" + opUserId + '\'' +
				'}';
	}
}