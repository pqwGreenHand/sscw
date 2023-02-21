package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArchivesTreeEntity implements Serializable {

	private static final long serialVersionUID = 6975213564780163761L;

	// tree
	private String id;

	private String name;

	private List<ArchivesTreeEntity> children = new ArrayList<ArchivesTreeEntity>();

	private String areaId;

	private String caseId;

	private String certificateNo;

	private String ajmc;

	private String ajbh;

	private String personId;

	private String personName;

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

	public List<ArchivesTreeEntity> getChildren() {
		return children;
	}

	public void setChildren(List<ArchivesTreeEntity> children) {
		this.children = children;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public String getAjbh() {
		return ajbh;
	}

	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Override
	public String toString() {
		return "ArchivesTreeEntity [id=" + id + ", name=" + name + ", children=" + children + ", areaId=" + areaId
				+ ", caseId=" + caseId + ", certificateNo=" + certificateNo + ", ajmc=" + ajmc + ", ajbh=" + ajbh
				+ ", personId=" + personId + ", personName=" + personName + "]";
	}

	public void addChildren(ArchivesTreeEntity entity) {
		if (children == null) {
			children = new ArrayList<ArchivesTreeEntity>();
		}
		children.add(entity);
	}
}
