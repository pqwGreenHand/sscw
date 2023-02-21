package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;
import java.util.Date;


public class ExhibitForm implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1909739640755943314L;

	private Integer id;
	
	private Integer did;
	
	private Integer cid;
	
	private String id1;
	private Long serialId;
	
	private Long sserialId;

	private Integer registerUserId;

	private Date registerTime;

	private Integer caseId;
	
	private Integer sCaseId;

	private Integer areaId;
	
	private Integer sareaId;

	private String lockerId;

	private Integer isGet;

	private String getWay;

	private String getPerson;

	private Date getTime;

	private Date createdTime;

	private Date updatedTime;

	private Integer exhibitId;

	private String name;

	private Integer detailCount;

	private String description;

	private String burcode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Long getSerialId() {
		return serialId;
	}

	public void setSerialId(Long serialId) {
		this.serialId = serialId;
	}

	public Long getSserialId() {
		return sserialId;
	}

	public void setSserialId(Long sserialId) {
		this.sserialId = sserialId;
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

	public Integer getsCaseId() {
		return sCaseId;
	}

	public void setsCaseId(Integer sCaseId) {
		this.sCaseId = sCaseId;
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

	public Integer getExhibitId() {
		return exhibitId;
	}

	public void setExhibitId(Integer exhibitId) {
		this.exhibitId = exhibitId;
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

	@Override
	public String toString() {
		return "ExhibitForm{" +
				"id=" + id +
				", did=" + did +
				", cid=" + cid +
				", id1='" + id1 + '\'' +
				", serialId=" + serialId +
				", sserialId=" + sserialId +
				", registerUserId=" + registerUserId +
				", registerTime=" + registerTime +
				", caseId=" + caseId +
				", sCaseId=" + sCaseId +
				", areaId=" + areaId +
				", sareaId=" + sareaId +
				", lockerId='" + lockerId + '\'' +
				", isGet=" + isGet +
				", getWay='" + getWay + '\'' +
				", getPerson='" + getPerson + '\'' +
				", getTime=" + getTime +
				", createdTime=" + createdTime +
				", updatedTime=" + updatedTime +
				", exhibitId=" + exhibitId +
				", name='" + name + '\'' +
				", detailCount=" + detailCount +
				", description='" + description + '\'' +
				", burcode='" + burcode + '\'' +
				'}';
	}
}