package com.zhixin.zhfz.bacs.form;

import java.util.Date;

public class PersonForm implements java.io.Serializable {

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

	private String birth;

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

	private String  personNo;

	private int areaId;

	private String areaName;

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

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"id\":")
				.append(id);
		sb.append(",\"certificateTypeId\":")
				.append(certificateTypeId);
		sb.append(",\"certificateTypeName\":\"")
				.append(certificateTypeName).append('\"');
		sb.append(",\"certificateNo\":\"")
				.append(certificateNo).append('\"');
		sb.append(",\"name\":\"")
				.append(name).append('\"');
		sb.append(",\"oldName\":\"")
				.append(oldName).append('\"');
		sb.append(",\"nickname\":\"")
				.append(nickname).append('\"');
		sb.append(",\"sex\":")
				.append(sex);
		sb.append(",\"sexName\":\"")
				.append(sexName).append('\"');
		sb.append(",\"birth\":\"")
				.append(birth).append('\"');
		sb.append(",\"education\":")
				.append(education);
		sb.append(",\"educationName\":\"")
				.append(educationName).append('\"');
		sb.append(",\"politic\":")
				.append(politic);
		sb.append(",\"politicName\":\"")
				.append(politicName).append('\"');
		sb.append(",\"officer\":")
				.append(officer);
		sb.append(",\"officerName\":\"")
				.append(officerName).append('\"');
		sb.append(",\"country\":")
				.append(country);
		sb.append(",\"countryName\":\"")
				.append(countryName).append('\"');
		sb.append(",\"nation\":")
				.append(nation);
		sb.append(",\"nationName\":\"")
				.append(nationName).append('\"');
		sb.append(",\"censusRegister\":\"")
				.append(censusRegister).append('\"');
		sb.append(",\"address\":\"")
				.append(address).append('\"');
		sb.append(",\"job\":\"")
				.append(job).append('\"');
		sb.append(",\"jobTitle\":\"")
				.append(jobTitle).append('\"');
		sb.append(",\"mobile\":\"")
				.append(mobile).append('\"');
		sb.append(",\"telephone\":\"")
				.append(telephone).append('\"');
		sb.append(",\"email\":\"")
				.append(email).append('\"');
		sb.append(",\"qq\":\"")
				.append(qq).append('\"');
		sb.append(",\"weixin\":\"")
				.append(weixin).append('\"');
		sb.append(",\"weibo\":\"")
				.append(weibo).append('\"');
		sb.append(",\"internetInfo\":\"")
				.append(internetInfo).append('\"');
		sb.append(",\"createdTime\":\"")
				.append(createdTime).append('\"');
		sb.append(",\"updatedTime\":\"")
				.append(updatedTime).append('\"');
		sb.append(",\"personAge\":")
				.append(personAge);
		sb.append(",\"age\":")
				.append(age);
		sb.append(",\"personNo\":\"")
				.append(personNo).append('\"');
		sb.append(",\"areaId\":")
				.append(areaId);
		sb.append(",\"areaName\":\"")
				.append(areaName).append('\"');
		sb.append('}');
		return sb.toString();
	}
}