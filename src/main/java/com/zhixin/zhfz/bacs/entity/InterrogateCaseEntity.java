package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class InterrogateCaseEntity implements Serializable{
   
	private static final long serialVersionUID = -2100268027153765572L;

	private Integer id;

    private Date involvedDate;

    private String involvedAddress;

    private String involvedPeople;

    private String involvedReason;

    private String caseName;

    private String caseNo;

    private Integer interrogateAreaId;

    private Date createdTime;

    private Date updatedTime;
    
    private String interrogateAreaName;
    
    private Integer type;
    
    private Integer crimeTypeId;
    
    private String crimeType;
    
    private String caseTypeName;

    public String getInterrogateAreaName() {
		return interrogateAreaName;
	}

	public void setInterrogateAreaName(String interrogateAreaName) {
		this.interrogateAreaName = interrogateAreaName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
	public String toString() {
		return "InterrogateCaseEntity [id=" + id + ", involvedDate=" + involvedDate + ", involvedAddress="
				+ involvedAddress + ", involvedPeople=" + involvedPeople + ", involvedReason=" + involvedReason
				+ ", caseName=" + caseName + ", caseNo=" + caseNo + ", interrogateAreaId=" + interrogateAreaId
				+ ", createdTime=" + createdTime + ", updatedTime=" + updatedTime + ", interrogateAreaName="
				+ interrogateAreaName + "]";
	}

	public Date getInvolvedDate() {
        return involvedDate;
    }

    public void setInvolvedDate(Date involvedDate) {
        this.involvedDate = involvedDate;
    }

    public String getInvolvedAddress() {
        return involvedAddress;
    }

    public void setInvolvedAddress(String involvedAddress) {
        this.involvedAddress = involvedAddress == null ? null : involvedAddress.trim();
    }

    public String getInvolvedPeople() {
        return involvedPeople;
    }

    public void setInvolvedPeople(String involvedPeople) {
        this.involvedPeople = involvedPeople == null ? null : involvedPeople.trim();
    }

    public String getInvolvedReason() {
        return involvedReason;
    }

    public void setInvolvedReason(String involvedReason) {
        this.involvedReason = involvedReason == null ? null : involvedReason.trim();
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName == null ? null : caseName.trim();
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo == null ? null : caseNo.trim();
    }

    public Integer getInterrogateAreaId() {
        return interrogateAreaId;
    }

    public void setInterrogateAreaId(Integer interrogateAreaId) {
        this.interrogateAreaId = interrogateAreaId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCrimeTypeId() {
		return crimeTypeId;
	}

	public void setCrimeTypeId(Integer crimeTypeId) {
		this.crimeTypeId = crimeTypeId;
	}

	public String getCrimeType() {
		return crimeType;
	}

	public void setCrimeType(String crimeType) {
		this.crimeType = crimeType;
	}

	public String getCaseTypeName() {
		return caseTypeName;
	}

	public void setCaseTypeName(String caseTypeName) {
		this.caseTypeName = caseTypeName;
	}

	
    
	
    
}