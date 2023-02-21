package com.zhixin.zhfz.jzgl.entity;

import java.util.List;

public class RuleTree {

	private Long id; // 要显示的子节点的ID
	private String text; // 要显示的子节点的 Text
	private String iconCls; // 节点的图标
	private String state; // 节点状态
	private String attributes;
	private boolean checked;
	private Long parentId; // 父节点的ID
	private List<RuleTree> children; // 孩子节点的List

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<RuleTree> getChildren() {
		return children;
	}

	public void setChildren(List<RuleTree> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "RuleTree [id=" + id + ", text=" + text + ", iconCls=" + iconCls + ", state=" + state + ", attributes="
				+ attributes + ", checked=" + checked + ", parentId=" + parentId + ", children=" + children + "]";
	}

}
