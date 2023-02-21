package com.zhixin.zhfz.bacs.entity;

public class ComboboxEntity {

	private String id = "";

	private String value = "";
	
	private String groupName;
	
	private String status;

	private String involvedName;

	private Integer serialId;

	private Integer areaId;

	private String serialNo;

	private String personName;

	private String certificateNo;
	
	private String caseNo;

	public Integer getSerialId() {
		return serialId;
	}

	public void setSerialId(Integer serialId) {
		this.serialId = serialId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public ComboboxEntity() {
	}

	public ComboboxEntity(String id, String value) {
		this.id = id;
		this.value = value;
	}

	public ComboboxEntity(Long id, String value) {
		this.id = id.toString();
		this.value = value;
	}

	public String getInvolvedName() {
		return involvedName;
	}

	public void setInvolvedName(String involvedName) {
		this.involvedName = involvedName;
	}

	@Override
	public String toString() {
		return "ComboboxEntity{" +
				"id='" + id + '\'' +
				", value='" + value + '\'' +
				", groupName='" + groupName + '\'' +
				", status='" + status + '\'' +
				", involvedName='" + involvedName + '\'' +
				", caseNo='" + caseNo + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}



}
