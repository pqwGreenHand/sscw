package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;

public class CaseDataClueForm implements Serializable{
	
	private static final long serialVersionUID = -4576466305443496134L;
	
	private int id;
	private String fileName;
	private String fileUrl;
	private int uploadUserId;
	private int lawCaseId;
	private String fileDesc;
	private Long interrogateSerialId;
	public Long getInterrogateSerialId() {
		return interrogateSerialId;
	}
	public void setInterrogateSerialId(Long interrogateSerialId) {
		this.interrogateSerialId = interrogateSerialId;
	}
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
		return "CaseDataClueForm [id=" + id + ", fileName=" + fileName + ", fileUrl=" + fileUrl + ", uploadUserId="
				+ uploadUserId + ", lawCaseId=" + lawCaseId + ", fileDesc=" + fileDesc + ", interrogateSerialId="
				+ interrogateSerialId + "]";
	}
	
}
