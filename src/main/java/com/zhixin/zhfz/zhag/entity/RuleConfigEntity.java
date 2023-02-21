package com.zhixin.zhfz.zhag.entity;

public class RuleConfigEntity {

	private Long id ;

	private int ruleId;
	private String  sendRoleids;
	private String  sendRoleNames;
	private int  sendDay;
	private int  sendBeginTime;
	private int  sendEndTime;
	private String   sendContentTemplate;
	private int ruleConfigType;
	private String sendMode;

	private String className;//关联rule_info的字段

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getSendRoleids() {
		return sendRoleids;
	}
	public void setSendRoleids(String sendRoleids) {
		this.sendRoleids = sendRoleids;
	}
	public String getSendRoleNames() {
		return sendRoleNames;
	}
	public void setSendRoleNames(String sendRoleNames) {
		this.sendRoleNames = sendRoleNames;
	}
	public int getSendDay() {
		return sendDay;
	}
	public void setSendDay(int sendDay) {
		this.sendDay = sendDay;
	}
	public int getSendBeginTime() {
		return sendBeginTime;
	}
	public void setSendBeginTime(int sendBeginTime) {
		this.sendBeginTime = sendBeginTime;
	}
	public int getSendEndTime() {
		return sendEndTime;
	}
	public void setSendEndTime(int sendEndTime) {
		this.sendEndTime = sendEndTime;
	}
	public String getSendContentTemplate() {
		return sendContentTemplate;
	}
	public void setSendContentTemplate(String sendContentTemplate) {
		this.sendContentTemplate = sendContentTemplate;
	}
	@Override
	public String toString() {
		return "RuleConfigEntity [id=" + id + ", ruleId=" + ruleId + ", sendRoleids=" + sendRoleids + ", sendRoleNames="
				+ sendRoleNames + ", sendDay=" + sendDay + ", ruleConfigType=" + ruleConfigType + ", sendBeginTime="
				+ sendBeginTime + ", sendEndTime=" + sendEndTime + ", sendContentTemplate=" + sendContentTemplate
				+ ", sendMode=" + sendMode + "]";
	}


	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getRuleConfigType() {
		return ruleConfigType;
	}

	public void setRuleConfigType(int ruleConfigType) {
		this.ruleConfigType = ruleConfigType;
	}

	public String getSendMode() {
		return sendMode;
	}

	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}
}
