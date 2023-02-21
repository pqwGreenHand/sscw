package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;
import java.util.Date;


public class PoliceBelongForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6068170122207873128L;

	private Long id;
	
	private Integer did;
	
	private Integer cid;
	private String id1;

    private Long policeId;
    
    private Long serialId;

    private Integer registerUserId;

    private Date registerTime;

    private Integer caseId;
    
    private Integer scaseId;

    private Integer areaId;
    
    private Integer sareaId;

    private String lockerId;

    private Integer isGet;

    private String getWay;

    private String getPerson;

    private Date getTime;

    private Date createdTime;

    private Date updatedTime;
    
    private Long belongingsId;

    private String name;

    private Integer detailCount;

    private String description;
    
    private String burcode;
    
    private String cuffNumber;
    
    private String unit;
	
	private String saveMethod;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getId1() {
		return id1;
	}

	public void setId1(String id1) {
		this.id1 = id1;
	}

	public Long getPoliceId() {
		return policeId;
	}

	public void setPoliceId(Long policeId) {
		this.policeId = policeId;
	}

	public Long getSerialId() {
		return serialId;
	}

	public void setSerialId(Long serialId) {
		this.serialId = serialId;
	}

	public Integer getRegisterUserId() {
		return registerUserId;
	}

	public void setRegisterUserId(Integer registerUserId) {
		this.registerUserId = registerUserId;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public Integer getScaseId() {
		return scaseId;
	}

	public void setScaseId(Integer scaseId) {
		this.scaseId = scaseId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getSareaId() {
		return sareaId;
	}

	public void setSareaId(Integer sareaId) {
		this.sareaId = sareaId;
	}

	public String getLockerId() {
		return lockerId;
	}

	public void setLockerId(String lockerId) {
		this.lockerId = lockerId;
	}

	public Integer getIsGet() {
		return isGet;
	}

	public void setIsGet(Integer isGet) {
		this.isGet = isGet;
	}

	public String getGetWay() {
		return getWay;
	}

	public void setGetWay(String getWay) {
		this.getWay = getWay;
	}

	public String getGetPerson() {
		return getPerson;
	}

	public void setGetPerson(String getPerson) {
		this.getPerson = getPerson;
	}

	public Date getGetTime() {
		return getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
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

	public Long getBelongingsId() {
		return belongingsId;
	}

	public void setBelongingsId(Long belongingsId) {
		this.belongingsId = belongingsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDetailCount() {
		return detailCount;
	}

	public void setDetailCount(Integer detailCount) {
		this.detailCount = detailCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBurcode() {
		return burcode;
	}

	public void setBurcode(String burcode) {
		this.burcode = burcode;
	}

	public String getCuffNumber() {
		return cuffNumber;
	}

	public void setCuffNumber(String cuffNumber) {
		this.cuffNumber = cuffNumber;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSaveMethod() {
		return saveMethod;
	}

	public void setSaveMethod(String saveMethod) {
		this.saveMethod = saveMethod;
	}

	@Override
	public String toString() {
		return "PoliceBelongForm{" +
				"id=" + id +
				", did=" + did +
				", cid=" + cid +
				", id1='" + id1 + '\'' +
				", policeId=" + policeId +
				", serialId=" + serialId +
				", registerUserId=" + registerUserId +
				", registerTime=" + registerTime +
				", caseId=" + caseId +
				", scaseId=" + scaseId +
				", areaId=" + areaId +
				", sareaId=" + sareaId +
				", lockerId='" + lockerId + '\'' +
				", isGet=" + isGet +
				", getWay='" + getWay + '\'' +
				", getPerson='" + getPerson + '\'' +
				", getTime=" + getTime +
				", createdTime=" + createdTime +
				", updatedTime=" + updatedTime +
				", belongingsId=" + belongingsId +
				", name='" + name + '\'' +
				", detailCount=" + detailCount +
				", description='" + description + '\'' +
				", burcode='" + burcode + '\'' +
				", cuffNumber='" + cuffNumber + '\'' +
				", unit='" + unit + '\'' +
				", saveMethod='" + saveMethod + '\'' +
				'}';
	}
}