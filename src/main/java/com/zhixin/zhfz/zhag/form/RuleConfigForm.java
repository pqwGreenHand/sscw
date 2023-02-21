package com.zhixin.zhfz.zhag.form;

import java.io.Serializable;

public class RuleConfigForm implements Serializable{

	private Long id ;

	private int ruleId;
	private String sendRoleids;
	private String  sendRoleNames;
	private  int  sendDay;
	private  int  ruleConfigType;
	private  int  sendBeginTime;
	private  int  sendEndTime;
	private  String   sendContentTemplate;
	private  String  sendMode;
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
	@Override
	public String toString() {
		return "RuleConfigForm [id=" + id + ", ruleId=" + ruleId + ", sendRoleids=" + sendRoleids + ", sendRoleNames="
				+ sendRoleNames + ", sendDay=" + sendDay + ", ruleConfigType=" + ruleConfigType + ", sendBeginTime="
				+ sendBeginTime + ", sendEndTime=" + sendEndTime + ", sendContentTemplate=" + sendContentTemplate
				+ ", sendMode=" + sendMode + "]";
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
	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}
	
	public String getSendMode() {
		return sendMode;
	}
	public int getSendDay() {
		return sendDay;
	}
	public void setSendDay(int sendDay) {
		this.sendDay = sendDay;
	}
	public int getRuleConfigType() {
		return ruleConfigType;
	}
	public void setRuleConfigType(int ruleConfigType) {
		this.ruleConfigType = ruleConfigType;
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


}
