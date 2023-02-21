package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;

public class RuleInfoEntity implements Serializable {

	private static final long serialVersionUID = -5657997162363101310L;

	private Long id;

	private String ruleName;

	private Long parentRuleId;

	private Long isDirectories;

	private String className;

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

	public Long getParentRuleId() {
		return parentRuleId;
	}

	public void setParentRuleId(Long parentRuleId) {
		this.parentRuleId = parentRuleId;
	}

	public Long getIsDirectories() {
		return isDirectories;
	}

	public void setIsDirectories(Long isDirectories) {
		this.isDirectories = isDirectories;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return "RuleInfo [id=" + id + ", ruleName=" + ruleName + ", parentRuleId=" + parentRuleId + ", isDirectories="
				+ isDirectories + ", className=" + className + "]";
	}

}
