package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class PersonEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer certificateTypeId;

	private String certificateTypeName;

	private String certificateNo;

	private String name;

	private String oldName;

	private String nickname;

	private Integer sex;

	private String sexName;

	private Date birth;

	private Integer education;

	private String educationName;

	private Integer politic;

	private String politicName;

	private Integer officer;

	private String officerName;

	private Integer country;

	private String countryName;

	private Integer nation;

	private String nationName;

	private String censusRegister;

	private String address;

	private String job;

	private String jobTitle;

	private String mobile;

	private String telephone;

	private String email;

	private String qq;

	private String weixin;

	private String weibo;

	private String internetInfo;

	private Date createdTime;

	private Date updatedTime;

	private Integer personAge;

	private Integer age;

	private String personNo;

	private int areaId;

	private String areaName;

	private String uuid;

	private Integer caseId;

	private String opPid;

	private Integer opUserId;

	private Integer isArrive;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCertificateTypeId() {
		return certificateTypeId;
	}

	public void setCertificateTypeId(Integer certificateTypeId) {
		this.certificateTypeId = certificateTypeId;
	}

	public String getCertificateTypeName() {
		return certificateTypeName;
	}

	public void setCertificateTypeName(String certificateTypeName) {
		this.certificateTypeName = certificateTypeName;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public String getEducationName() {
		return educationName;
	}

	public void setEducationName(String educationName) {
		this.educationName = educationName;
	}

	public Integer getPolitic() {
		return politic;
	}

	public void setPolitic(Integer politic) {
		this.politic = politic;
	}

	public String getPoliticName() {
		return politicName;
	}

	public void setPoliticName(String politicName) {
		this.politicName = politicName;
	}

	public Integer getOfficer() {
		return officer;
	}

	public void setOfficer(Integer officer) {
		this.officer = officer;
	}

	public String getOfficerName() {
		return officerName;
	}

	public void setOfficerName(String officerName) {
		this.officerName = officerName;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Integer getNation() {
		return nation;
	}

	public void setNation(Integer nation) {
		this.nation = nation;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getCensusRegister() {
		return censusRegister;
	}

	public void setCensusRegister(String censusRegister) {
		this.censusRegister = censusRegister;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public String getInternetInfo() {
		return internetInfo;
	}

	public void setInternetInfo(String internetInfo) {
		this.internetInfo = internetInfo;
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

	public Integer getPersonAge() {
		return personAge;
	}

	public void setPersonAge(Integer personAge) {
		this.personAge = personAge;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPersonNo() {
		return personNo;
	}

	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public String getOpPid() {
		return opPid;
	}

	public void setOpPid(String opPid) {
		this.opPid = opPid;
	}

	public Integer getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(Integer opUserId) {
		this.opUserId = opUserId;
	}

	public Integer getIsArrive() {
		return isArrive;
	}

	public void setIsArrive(Integer isArrive) {
		this.isArrive = isArrive;
	}

	@Override
	public String toString() {
		return "PersonEntity{" +
				"id=" + id +
				", certificateTypeId=" + certificateTypeId +
				", certificateTypeName='" + certificateTypeName + '\'' +
				", certificateNo='" + certificateNo + '\'' +
				", name='" + name + '\'' +
				", oldName='" + oldName + '\'' +
				", nickname='" + nickname + '\'' +
				", sex=" + sex +
				", sexName='" + sexName + '\'' +
				", birth=" + birth +
				", education=" + education +
				", educationName='" + educationName + '\'' +
				", politic=" + politic +
				", politicName='" + politicName + '\'' +
				", officer=" + officer +
				", officerName='" + officerName + '\'' +
				", country=" + country +
				", countryName='" + countryName + '\'' +
				", nation=" + nation +
				", nationName='" + nationName + '\'' +
				", censusRegister='" + censusRegister + '\'' +
				", address='" + address + '\'' +
				", job='" + job + '\'' +
				", jobTitle='" + jobTitle + '\'' +
				", mobile='" + mobile + '\'' +
				", telephone='" + telephone + '\'' +
				", email='" + email + '\'' +
				", qq='" + qq + '\'' +
				", weixin='" + weixin + '\'' +
				", weibo='" + weibo + '\'' +
				", internetInfo='" + internetInfo + '\'' +
				", createdTime=" + createdTime +
				", updatedTime=" + updatedTime +
				", personAge=" + personAge +
				", age=" + age +
				", personNo='" + personNo + '\'' +
				", areaId=" + areaId +
				", areaName='" + areaName + '\'' +
				", uuid='" + uuid + '\'' +
				", caseId=" + caseId +
				", opPid='" + opPid + '\'' +
				", opUserId=" + opUserId +
				", isArrive=" + isArrive +
				", ajbh='" + ajbh + '\'' +
				", rybh='" + rybh + '\'' +
				", ajmc='" + ajmc + '\'' +
				'}';
	}

	public String toJsonString() {
		return "{\"id\":\"" + id + "\", \"certificateTypeId\":\"" + certificateTypeId + "\", \"certificateTypeName\":\""
				+ certificateTypeName + "\", \"certificateNo\":\"" + certificateNo + "\", \"name\":\"" + name
				+ "\", \"oldName\":\"" + oldName + "\", \"nickname\":\"" + nickname + "\", \"sex\":\"" + sex
				+ "\", \"sexName\":\"" + sexName + "\", \"birth\":\"" + birth + "\", \"education\":\"" + education
				+ "\", \"educationName\":\"" + educationName + "\", \"politic\":\"" + politic + "\", \"politicName\":\""
				+ politicName + "\", \"officer\":\"" + officer + "\", \"officerName\":\"" + officerName
				+ "\", \"country\":\"" + country + "\", \"countryName\":\"" + countryName + "\", \"nation\":\"" + nation
				+ "\", \"nationName\":\"" + nationName + "\", \"censusRegister\":\"" + censusRegister
				+ "\", \"address\":\"" + address + "\", \"job\":\"" + job + "\", \"jobTitle\":\"" + jobTitle
				+ "\", \"mobile\":\"" + mobile + "\", \"telephone\":\"" + telephone + "\", \"email\":\"" + email
				+ "\", \"qq\":\"" + qq + "\", \"weixin\":\"" + weixin + "\", \"weibo\":\"" + weibo
				+ "\", \"internetInfo\":\"" + internetInfo + "\", \"createdTime\":\"" + createdTime
				+ "\", \"updatedTime\":\"" + updatedTime + "\", \"personAge\":\"" + personAge + "\", \"age\":\"" + age
				+ "\", \"personNo\":\"" + personNo + "\", \"areaId\":\"" + areaId + "\", \"areaName\":\"" + areaName
				+ "\", \"uuid\":\"" + uuid + "\", \"caseId\":\"" + caseId + "\", \"opPid\":\"" + opPid
				+ "\", \"opUserId\":\"" + opUserId + "\", \"isArrive\":\"" + isArrive + "\"}";
	}
}