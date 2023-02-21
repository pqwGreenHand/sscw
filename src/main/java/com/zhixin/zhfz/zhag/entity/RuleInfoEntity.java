package com.zhixin.zhfz.zhag.entity;

public class RuleInfoEntity {

	private Long id ;

	private String ruleName;
	
	private int parentRuleId;
	
	private int isDirectories;
	
	private String  className;
	private int sftx;//是否提醒
	private String txnr;//提醒内容

	public int getSftx() {
		return sftx;
	}

	public void setSftx(int sftx) {
		this.sftx = sftx;
	}

	public String getTxnr() {
		return txnr;
	}

	public void setTxnr(String txnr) {
		this.txnr = txnr;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public int getParentRuleId() {
		return parentRuleId;
	}

	public void setParentRuleId(int parentRuleId) {
		this.parentRuleId = parentRuleId;
	}

	public int getIsDirectories() {
		return isDirectories;
	}

	public void setIsDirectories(int isDirectories) {
		this.isDirectories = isDirectories;
	}

	@Override
	public String toString() {
		return "RuleInfoEntity [id=" + id + ", ruleName=" + ruleName + ", parentRuleId=" + parentRuleId
				+ ", isDirectories=" + isDirectories + ", className=" + className + ", sftx=" + sftx + ", txnr=" + txnr
				+ "]";
	}



}
