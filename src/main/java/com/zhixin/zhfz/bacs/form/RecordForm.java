package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;

public class RecordForm implements Serializable {

    private static final long serialVersionUID = -7747652732206722466L;

    private Long id;
    private Long serialId;
    private int caseType;
    private String askNo;
    private String recodeNo;
    private String recordType;
    private String isFlage;
    private String flageType;
    private String recordTemp;
    private String isBurn;
    private String detectPerson;
    private String detectDept;
    private String caseNo;
    
    @Override
	public String toString() {
		return "RecordForm [id=" + id + ", serialId=" + serialId + ", caseType=" + caseType + ", askNo=" + askNo
				+ ", recodeNo=" + recodeNo + ", recordType=" + recordType + ", isFlage=" + isFlage + ", flageType="
				+ flageType + ", recordTemp=" + recordTemp + ", isBurn=" + isBurn + ", detectPerson=" + detectPerson
				+ ", detectDept=" + detectDept + ",caseNo="+caseNo+"]";
	}

	public long getSerialId() {
        return serialId;
    }

    public void setSerialId(long serialId) {
        this.serialId = serialId;
    }

    public int getCaseType() {
        return caseType;
    }

    public void setCaseType(int caseType) {
        this.caseType = caseType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAskNo() {
        return askNo;
    }

    public void setAskNo(String askNo) {
        this.askNo = askNo;
    }

    public String getRecodeNo() {
        return recodeNo;
    }

    public void setRecodeNo(String recodeNo) {
        this.recodeNo = recodeNo;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getFlageType() {
        return flageType;
    }

    public void setFlageType(String flageType) {
        this.flageType = flageType;
    }

    public String getRecordTemp() {
        return recordTemp;
    }

    public void setRecordTemp(String recordTemp) {
        this.recordTemp = recordTemp;
    }

    public String getDetectPerson() {
        return detectPerson;
    }

    public void setDetectPerson(String detectPerson) {
        this.detectPerson = detectPerson;
    }

    public String getDetectDept() {
        return detectDept;
    }

    public void setDetectDept(String detectDept) {
        this.detectDept = detectDept;
    }

    public String getIsFlage() {
        return isFlage;
    }

    public void setIsFlage(String isFlage) {
        this.isFlage = isFlage;
    }

    public String getIsBurn() {
        return isBurn;
    }

    public void setIsBurn(String isBurn) {
        this.isBurn = isBurn;
    }

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
    
}
