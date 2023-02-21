package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class CaseDataClueEntity implements Serializable{
	
	private static final long serialVersionUID = -4576466305443496134L;
	
	private int id;
	private String fileName;
	private String fileUrl;
	private int uploadUserId;
	private int lawCaseId;
	private Date uploadTime;
	private String policeName;
	private String opPid;
	private String opUserId;
	private Date involvedDate;
	private Long interrogateSerialId;
	private Long personId;

	public String getOpPid() {
		return opPid;
	}

	public void setOpPid(String opPid) {
		this.opPid = opPid;
	}

	public String getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(String opUserId) {
		this.opUserId = opUserId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getInterrogateSerialId() {
		return interrogateSerialId;
	}
	public void setInterrogateSerialId(Long interrogateSerialId) {
		this.interrogateSerialId = interrogateSerialId;
	}
	public String getPoliceName() {
		return policeName;
	}
	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	private String fileDesc;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public int getUploadUserId() {
		return uploadUserId;
	}
	public void setUploadUserId(int uploadUserId) {
		this.uploadUserId = uploadUserId;
	}
	public int getLawCaseId() {
		return lawCaseId;
	}
	public void setLawCaseId(int lawCaseId) {
		this.lawCaseId = lawCaseId;
	}
	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}


	@Override
	public String toString() {
		return "CaseDataClueEntity{" +
				"id=" + id +
				", fileName='" + fileName + '\'' +
				", fileUrl='" + fileUrl + '\'' +
				", uploadUserId=" + uploadUserId +
				", lawCaseId=" + lawCaseId +
				", uploadTime=" + uploadTime +
				", policeName='" + policeName + '\'' +
				", involvedDate=" + involvedDate +
				", interrogateSerialId=" + interrogateSerialId +
				", personId=" + personId +
				", fileDesc='" + fileDesc + '\'' +
				'}';
	}

	public void setInvolvedDate(Date involvedDate) {
		this.involvedDate = involvedDate;
	}
	public Date getInvolvedDate() {
		return involvedDate;
	}
	
}
