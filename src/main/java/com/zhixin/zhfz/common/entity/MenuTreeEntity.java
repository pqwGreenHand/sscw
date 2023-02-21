package com.zhixin.zhfz.common.entity;

import java.io.Serializable;
import java.util.List;

public class MenuTreeEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8856024350859746791L;
	
	private Long id;

	private String parentTitle;

	private Integer sortNum;

	private List<LowerMenuEntiry>lowerMenu;

	public String getParentTitle() {
		return parentTitle;
	}

	public void setParentTitle(String parentTitle) {
		this.parentTitle = parentTitle;
	}

	public List<LowerMenuEntiry> getLowerMenu() {
		return lowerMenu;
	}

	public void setLowerMenu(List<LowerMenuEntiry> lowerMenu) {
		this.lowerMenu = lowerMenu;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
}
