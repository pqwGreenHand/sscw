package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;

public class RuleConfigEntity implements Serializable {

	private static final long serialVersionUID = 5643879189025510098L;

	private Long id;

	private Long ruleId;

	private String sendRoleIds;

	private String sendRoleNames;

	private Integer sendHour;
	
	private String opPid;
	private Integer opUserId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getSendRoleIds() {
		return sendRoleIds;
	}

	public void setSendRoleIds(String sendRoleIds) {
		this.sendRoleIds = sendRoleIds;
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
		return "RuleConfigEntity [id=" + id + ", ruleId=" + ruleId + ", sendRoleIds=" + sendRoleIds + ", sendRoleNames="
				+ sendRoleNames + ", sendHour=" + sendHour + "]";
	}

}
