package com.zhixin.zhfz.bacs.form;

import java.util.Date;

public class SerialForm implements java.io.Serializable {

	private static final long serialVersionUID = 871600227123355981L;
	/**
	 * 嫌疑人信息
	 */
	private Long id;

	private String serialNo;

	private String uuid;

	private Integer status;

	private Date statusChangeTime;

	private Integer personId;
	private Integer sfzdgl;

	private Long sendUserId;

	private Integer type;

	private String personInfo;

	private Integer orderRequestId;

	private Integer areaId;

	private Integer caseId;

	private Integer cuffId;

	private String inPhotoName;

	private String outPhotoName;

	private Integer inRegisterUserId;

	private Integer outRegisterUserId;

	private Date inTime;

	private Date outTime;

	private String entranceProcedure;

	private String writtenTime;

	/**
	 * person信息
	 */
	private String name;

	private String oldName;

	private String nickName;

	private Integer sex;

	private Date birth;

	private String certificateNo;

	private Integer certificateTypeId;

	private String education;

	private String politic;

	private String officer;

	private Integer country;

	private Integer nation;

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

	private Integer age;

	private String personNo;

	private Date updatedTime;

	private Date createdTime;

	//嫌疑人入区添加
	private Integer outType;
	private String outReason;
	private String confirmResult;

	private Integer zbmjId;

	private String ajmc;

	private String ajbh;

	private Integer ajlx;

	private Integer ab;

	private String sendUserNO;

	private String pid;

	private Integer sfxxcj;
	
	private Integer sfsyjd;//是否送押解队

	private String  caseNo;
	private String  rybh;
	private String  yy;
	private String flag;

	private String rssj;

	public Integer getSfzdgl() {
		return sfzdgl;
	}

	public void setSfzdgl(Integer sfzdgl) {
		this.sfzdgl = sfzdgl;
	}

	public String getRssj() {
		return rssj;
	}

	public void setRssj(String rssj) {
		this.rssj = rssj;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getRybh() {
		return rybh;
	}

	public void setRybh(String rybh) {
		this.rybh = rybh;
	}

	public String getYy() {
		return yy;
	}

	public void setYy(String yy) {
		this.yy = yy;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSendUserNO() {
		return sendUserNO;
	}

	public void setSendUserNO(String sendUserNO) {
		this.sendUserNO = sendUserNO;
	}

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

	private Integer isPhoto;

	public Integer getIsPhoto() {
		return isPhoto;
	}

	public void setIsPhoto(Integer isPhoto) {
		this.isPhoto = isPhoto;
	}

	public Integer getZbmjId() {
		return zbmjId;
	}

	public void setZbmjId(Integer zbmjId) {
		this.zbmjId = zbmjId;
	}

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public String getAjbh() {
		return ajbh;
	}

	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getStatusChangeTime() {
		return statusChangeTime;
	}

	public void setStatusChangeTime(Date statusChangeTime) {
		this.statusChangeTime = statusChangeTime;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(String personInfo) {
		this.personInfo = personInfo;
	}

	public Integer getOrderRequestId() {
		return orderRequestId;
	}

	public void setOrderRequestId(Integer orderRequestId) {
		this.orderRequestId = orderRequestId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public Integer getCuffId() {
		return cuffId;
	}

	public void setCuffId(Integer cuffId) {
		this.cuffId = cuffId;
	}

	public String getInPhotoName() {
		return inPhotoName;
	}

	public void setInPhotoName(String inPhotoName) {
		this.inPhotoName = inPhotoName;
	}

	public String getOutPhotoName() {
		return outPhotoName;
	}

	public void setOutPhotoName(String outPhotoName) {
		this.outPhotoName = outPhotoName;
	}

	public Integer getInRegisterUserId() {
		return inRegisterUserId;
	}

	public void setInRegisterUserId(Integer inRegisterUserId) {
		this.inRegisterUserId = inRegisterUserId;
	}

	public Integer getOutRegisterUserId() {
		return outRegisterUserId;
	}

	public void setOutRegisterUserId(Integer outRegisterUserId) {
		this.outRegisterUserId = outRegisterUserId;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public Integer getCertificateTypeId() {
		return certificateTypeId;
	}

	public void setCertificateTypeId(Integer certificateTypeId) {
		this.certificateTypeId = certificateTypeId;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getPolitic() {
		return politic;
	}

	public void setPolitic(String politic) {
		this.politic = politic;
	}

	public String getOfficer() {
		return officer;
	}

	public void setOfficer(String officer) {
		this.officer = officer;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public Integer getNation() {
		return nation;
	}

	public void setNation(Integer nation) {
		this.nation = nation;
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

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(Long sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getEntranceProcedure() {
		return entranceProcedure;
	}

	public void setEntranceProcedure(String entranceProcedure) {
		this.entranceProcedure = entranceProcedure;
	}

	public String getWrittenTime() {
		return writtenTime;
	}

	public void setWrittenTime(String writtenTime) {
		this.writtenTime = writtenTime;
	}

	public Integer getOutType() {
		return outType;
	}

	public void setOutType(Integer outType) {
		this.outType = outType;
	}

	public String getOutReason() {
		return outReason;
	}

	public void setOutReason(String outReason) {
		this.outReason = outReason;
	}

	public String getConfirmResult() {
		return confirmResult;
	}

	public void setConfirmResult(String confirmResult) {
		this.confirmResult = confirmResult;
	}

	public Integer getSfxxcj() {
		return sfxxcj;
	}

	public void setSfxxcj(Integer sfxxcj) {
		this.sfxxcj = sfxxcj;
	}

	public Integer getSfsyjd() {
		return sfsyjd;
	}

	public void setSfsyjd(Integer sfsyjd) {
		this.sfsyjd = sfsyjd;
	}

	@Override
	public String toString() {
		return "SerialForm{" +
				"id=" + id +
				", serialNo='" + serialNo + '\'' +
				", uuid='" + uuid + '\'' +
				", status=" + status +
				", statusChangeTime=" + statusChangeTime +
				", personId=" + personId +
				", sendUserId=" + sendUserId +
				", type=" + type +
				", personInfo='" + personInfo + '\'' +
				", orderRequestId=" + orderRequestId +
				", areaId=" + areaId +
				", caseId=" + caseId +
				", cuffId=" + cuffId +
				", inPhotoName='" + inPhotoName + '\'' +
				", outPhotoName='" + outPhotoName + '\'' +
				", inRegisterUserId=" + inRegisterUserId +
				", outRegisterUserId=" + outRegisterUserId +
				", inTime=" + inTime +
				", outTime=" + outTime +
				", entranceProcedure='" + entranceProcedure + '\'' +
				", writtenTime='" + writtenTime + '\'' +
				", name='" + name + '\'' +
				", oldName='" + oldName + '\'' +
				", nickName='" + nickName + '\'' +
				", sex=" + sex +
				", birth=" + birth +
				", certificateNo='" + certificateNo + '\'' +
				", certificateTypeId=" + certificateTypeId +
				", education='" + education + '\'' +
				", politic='" + politic + '\'' +
				", officer='" + officer + '\'' +
				", country=" + country +
				", nation=" + nation +
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
				", age=" + age +
				", personNo='" + personNo + '\'' +
				", updatedTime=" + updatedTime +
				", createdTime=" + createdTime +
				", outType=" + outType +
				", outReason='" + outReason + '\'' +
				", confirmResult='" + confirmResult + '\'' +
				", zbmjId=" + zbmjId +
				", ajmc='" + ajmc + '\'' +
				", ajbh='" + ajbh + '\'' +
				", ajlx=" + ajlx +
				", ab=" + ab +
				", sendUserNO='" + sendUserNO + '\'' +
				", pid='" + pid + '\'' +
				", sfxxcj=" + sfxxcj +
				", sfsyjd=" + sfsyjd +
				", caseNo='" + caseNo + '\'' +
				", rybh='" + rybh + '\'' +
				", yy='" + yy + '\'' +
				", flag='" + flag + '\'' +
				", isPhoto=" + isPhoto +
				'}';
	}
}