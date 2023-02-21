package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InterrogateRecordTreeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String realFilePath;

	private List<InterrogateRecordTreeEntity> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealFilePath() {
		return realFilePath;
	}

	public void setRealFilePath(String realFilePath) {
		this.realFilePath = realFilePath;
	}

	public List<InterrogateRecordTreeEntity> getChildren() {
		return children;
	}

	public void setChildren(List<InterrogateRecordTreeEntity> children) {
		this.children = children;
	}

	public void addChildren(InterrogateRecordTreeEntity entity) {
		if (children == null) {
			children = new ArrayList<InterrogateRecordTreeEntity>();
		}
		children.add(entity);
	}

}
