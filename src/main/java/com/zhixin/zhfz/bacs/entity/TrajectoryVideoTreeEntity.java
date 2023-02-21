package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrajectoryVideoTreeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String text;

	private String realFilePath;

	private List<TrajectoryVideoTreeEntity> children = new ArrayList<TrajectoryVideoTreeEntity>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRealFilePath() {
		return realFilePath;
	}

	public void setRealFilePath(String realFilePath) {
		this.realFilePath = realFilePath;
	}

	public List<TrajectoryVideoTreeEntity> getChildren() {
		return children;
	}

	public void setChildren(List<TrajectoryVideoTreeEntity> children) {
		this.children = children;
	}

	public void addChildren(TrajectoryVideoTreeEntity entity) {
		if (children == null) {
			children = new ArrayList<TrajectoryVideoTreeEntity>();
		}
		children.add(entity);
	}

}
