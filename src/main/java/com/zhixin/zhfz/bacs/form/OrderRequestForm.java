package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约信息
 * 
 * @author
 *
 */
public class OrderRequestForm implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1461140198356139999L;

	private Integer id;

	private String orderNo;

	private Integer orderUserId;

	private String orderUserName;

	private Integer maleCount;

	private Integer femaleCount;

	private Integer juvenilesCount;

	private Integer juvenilesCountGirl;

	public Integer getJuvenilesCountGirl() {
		return juvenilesCountGirl;
	}

	public void setJuvenilesCountGirl(Integer juvenilesCountGirl) {
		this.juvenilesCountGirl = juvenilesCountGirl;
	}

	private Integer specialCount;

	private Integer otherCount;

	private String planTime;
	private String jbaq;

	private Integer ajlx;

	private Integer ab;

	public Integer getAjlx() {
		return ajlx;
	}

	public void setAjlx(Integer ajlx) {
		this.ajlx = ajlx;
	}

	public Integer getAb() {
		return ab;
	}

	public void setAb(Integer ab) {
		this.ab = ab;
	}

	public String getJbaq() {
		return jbaq;
	}

	public void setJbaq(String jbaq) {
		this.jbaq = jbaq;
	}

	private Date arriveTime;

	private String type;

	private Integer status;

	private String description;

	private Integer noterId;

	private Integer areaId;

	private Date createdTime;

	private Date updatedTime;

	private Integer sumPerson;

	private String interpreter;

	private Integer caseId;

	// 链表

	private String areaName;

	private String personName;

	private String notePersonName;

	private Integer statusid;

	private String organizationName;

	private String userMobile;

	private String caseName;


	private String name;

	private Integer certificateTypeId;

	private String certificateNo;

	private Integer sex;

	private String jzCaseNumber;

	private String personNo;


	private String ajbh;

	private String rybh;

	private String ajmc;

	public String getAjbh() {
		return ajbh;
	}

	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}

	public String getRybh() {
		return rybh;
	}

	public void setRybh(String rybh) {
		this.rybh = rybh;
	}

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public String getPersonNo() {
		return personNo;
	}

	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}

	public String getJzCaseNumber() {
		return jzCaseNumber;
	}

	public void setJzCaseNumber(String jzCaseNumber) {
		this.jzCaseNumber = jzCaseNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCertificateTypeId() {
		return certificateTypeId;
	}

	public void setCertificateTypeId(Integer certificateTypeId) {
		this.certificateTypeId = certificateTypeId;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderUserId() {
		return orderUserId;
	}

	public void setOrderUserId(Integer orderUserId) {
		this.orderUserId = orderUserId;
	}

	public Integer getMaleCount() {
		return maleCount;
	}

	public void setMaleCount(Integer maleCount) {
		this.maleCount = maleCount;
	}

	public Integer getFemaleCount() {
		return femaleCount;
	}

	public void setFemaleCount(Integer femaleCount) {
		this.femaleCount = femaleCount;
	}

	public Integer getJuvenilesCount() {
		return juvenilesCount;
	}

	public void setJuvenilesCount(Integer juvenilesCount) {
		this.juvenilesCount = juvenilesCount;
	}

	public Integer getSpecialCount() {
		return specialCount;
	}

	public void setSpecialCount(Integer specialCount) {
		this.specialCount = specialCount;
	}

	public Integer getOtherCount() {
		return otherCount;
	}

	public void setOtherCount(Integer otherCount) {
		this.otherCount = otherCount;
	}

	public String getPlanTime() {
		return planTime;
	}

	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNoterId() {
		return noterId;
	}

	public void setNoterId(Integer noterId) {
		this.noterId = noterId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
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

	public Integer getSumPerson() {
		return sumPerson;
	}

	public void setSumPerson(Integer sumPerson) {
		this.sumPerson = sumPerson;
	}

	public String getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(String interpreter) {
		this.interpreter = interpreter;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getNotePersonName() {
		return notePersonName;
	}

	public void setNotePersonName(String notePersonName) {
		this.notePersonName = notePersonName;
	}

	public Integer getStatusid() {
		return statusid;
	}

	public void setStatusid(Integer statusid) {
		this.statusid = statusid;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getOrderUserName() {
		return orderUserName;
	}

	public void setOrderUserName(String orderUserName) {
		this.orderUserName = orderUserName;
	}

	@Override
	public String toString() {
		return "OrderRequestForm{" +
				"id=" + id +
				", orderNo='" + orderNo + '\'' +
				", orderUserId=" + orderUserId +
				", orderUserName='" + orderUserName + '\'' +
				", maleCount=" + maleCount +
				", femaleCount=" + femaleCount +
				", juvenilesCount=" + juvenilesCount +
				", juvenilesCountGirl=" + juvenilesCountGirl +
				", specialCount=" + specialCount +
				", otherCount=" + otherCount +
				", planTime='" + planTime + '\'' +
				", jbaq='" + jbaq + '\'' +
				", ajlx=" + ajlx +
				", ab=" + ab +
				", arriveTime=" + arriveTime +
				", type='" + type + '\'' +
				", status=" + status +
				", description='" + description + '\'' +
				", noterId=" + noterId +
				", areaId=" + areaId +
				", createdTime=" + createdTime +
				", updatedTime=" + updatedTime +
				", sumPerson=" + sumPerson +
				", interpreter='" + interpreter + '\'' +
				", caseId=" + caseId +
				", areaName='" + areaName + '\'' +
				", personName='" + personName + '\'' +
				", notePersonName='" + notePersonName + '\'' +
				", statusid=" + statusid +
				", organizationName='" + organizationName + '\'' +
				", userMobile='" + userMobile + '\'' +
				", caseName='" + caseName + '\'' +
				", name='" + name + '\'' +
				", certificateTypeId=" + certificateTypeId +
				", certificateNo='" + certificateNo + '\'' +
				", sex=" + sex +
				", jzCaseNumber='" + jzCaseNumber + '\'' +
				", personNo='" + personNo + '\'' +
				", ajbh='" + ajbh + '\'' +
				", rybh='" + rybh + '\'' +
				", ajmc='" + ajmc + '\'' +
				'}';
	}
}