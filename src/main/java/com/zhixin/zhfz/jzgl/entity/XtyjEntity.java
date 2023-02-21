package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.util.Date;

public class XtyjEntity implements Serializable {

	private static final long serialVersionUID = 1345208473655321154L;

	private Long id;

	private String ajxxId;

	private String jzxxId;

	private String ruleId;

	private String ruleConfId;

	private Date cjDate;

	private Date startTime;

	private Date endTime;

	private String ajmc;

	private String ajbh;

	private String jzbh;

	private String xsbh;

	private String sendRoleNames;

	private Integer sendHour;

	private String userId;

	private String zrrId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAjxxId() {
		return ajxxId;
	}

	public void setAjxxId(String ajxxId) {
		this.ajxxId = ajxxId;
	}

	public String getJzxxId() {
		return jzxxId;
	}

	public void setJzxxId(String jzxxId) {
		this.jzxxId = jzxxId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleConfId() {
		return ruleConfId;
	}

	public void setRuleConfId(String ruleConfId) {
		this.ruleConfId = ruleConfId;
	}

	public Date getCjDate() {
		return cjDate;
	}

	public void setCjDate(Date cjDate) {
		this.cjDate = cjDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getJzbh() {
		return jzbh;
	}

	public void setJzbh(String jzbh) {
		this.jzbh = jzbh;
	}

	public String getXsbh() {
		return xsbh;
	}

	public void setXsbh(String xsbh) {
		this.xsbh = xsbh;
	}

	public String getSendRoleNames() {
		return sendRoleNames;
	}

	public void setSendRoleNames(String sendRoleNames) {
		this.sendRoleNames = sendRoleNames;
	}

	public Integer getSendHour() {
		return sendHour;
	}

	public void setSendHour(Integer sendHour) {
		this.sendHour = sendHour;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getZrrId() {
		return zrrId;
	}

	public void setZrrId(String zrrId) {
		this.zrrId = zrrId;
	}

	@Override
	public String toString() {
		return "XtyjEntity [id=" + id + ", ajxxId=" + ajxxId + ", jzxxId=" + jzxxId + ", ruleId=" + ruleId
				+ ", ruleConfId=" + ruleConfId + ", cjDate=" + cjDate + ", startTime=" + startTime + ", endTime="
				+ endTime + ", ajmc=" + ajmc + ", ajbh=" + ajbh + ", jzbh=" + jzbh + ", xsbh=" + xsbh
				+ ", sendRoleNames=" + sendRoleNames + ", sendHour=" + sendHour + ", userId=" + userId + ", zrrId=" + zrrId
				+ "]";
	}

}
