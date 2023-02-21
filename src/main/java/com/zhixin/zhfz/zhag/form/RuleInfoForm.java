package com.zhixin.zhfz.zhag.form;

import java.io.Serializable;

public class RuleInfoForm implements Serializable{

	private Long id ;

	private String ruleName;
	
	private int parentRuleId;
	
	private int isDirectories;
	
	private String  className;

	private String txnr;

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

	public String getTxnr() {
		return txnr;
	}

	public void setTxnr(String txnr) {
		this.txnr = txnr;
	}

	@Override
	public String toString() {
		return "RuleEntity [id=" + id + ", ruleName=" + ruleName + ", parentRuleId=" + parentRuleId + ", isDirectories="
				+ isDirectories + ", className=" + className +", txnr=" + txnr + "]";
	}



}
