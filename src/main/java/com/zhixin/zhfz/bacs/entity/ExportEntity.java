package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class ExportEntity implements Serializable {

	/**
	 * 数据导出
	 */
	private static final long serialVersionUID = 9007464148560081295L;
	
	//id
	private Long id;
	
	private String serialId;
	//人员类型
	private String personType;
	//姓名
	private String personName;
	//性别
	private String personSex;
	//证件类型
	private String certificateTypeId;
		
	//证件号码
	private String personCertificateNo;
	//案件类型
	private String caseType;
	//案件性质
	private String caseProperties;
	//入区时间
	private Date inTime;
	//出区时间
	private Date outTime;
	//时间范围
	private Date betweenTime;
	//时间范围max
	private Date betweenTimeMax;
	//出区去向
	private String outPlace;
	//办案民警
	private String policeman;
	
	private String policeNo;
	
	private Integer policeId;
	//办案单位
	private String workSpace;
	
	//年龄 1 成年，0 未成年
	private String personAge;
	//是否为身份证 1 身份证 ，0 其他
	private String pType;
	 //裁决时间
	private Date confirmTime;
	//链表数据
	//案件id
	private Integer interrogateCaseId;
	//人员id
	private Long personId;
	//用户id
	private Integer userId;
	//部门id
	private Integer organizationId;
	
	private String outType;
	
	private String OutReason;
	
	private String edataType;
	
	private String edirection;
	
	private String confirmResult;
	
	private String crimeTypeId;
	
	private String crimeSection;
	
	private String caseNature;
	//羁押超时
	private String jyCasetType;
	
	private Date calltime;
	
	private String detainInterval;
	
	//人身检查详情
	private Integer securityId;
	
	private String medicalHistory;

    private Integer type;

    private Integer physical;

    private String physicalDescription;

    private Integer injury;

    private String injuryDescription;
    
    private String dangerous;

    private String otherDescription;
    
    private String entranceProcedure;//法律手续
    
    private Date writtenTime;//手续开具时间
    
    private Integer sfxxcj;//是否信息采集

    private Integer sfsyjd;//是否信息采集
    
    private String recordTime;//入区到第一次审讯时间
    
    private String lian;//年
    
    private String yue;//月
    
    private String ri;//日
    
	public Integer getPoliceId() {
		return policeId;
	}
	public void setPoliceId(Integer policeId) {
		this.policeId = policeId;
	}
	public String getPoliceNo() {
		return policeNo;
	}
	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}
	public Integer getSecurityId() {
		return securityId;
	}
	public void setSecurityId(Integer securityId) {
		this.securityId = securityId;
	}
	public String getMedicalHistory() {
		return medicalHistory;
	}
	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPhysical() {
		return physical;
	}
	public void setPhysical(Integer physical) {
		this.physical = physical;
	}
	public String getPhysicalDescription() {
		return physicalDescription;
	}
	public void setPhysicalDescription(String physicalDescription) {
		this.physicalDescription = physicalDescription;
	}
	public Integer getInjury() {
		return injury;
	}
	public void setInjury(Integer injury) {
		this.injury = injury;
	}
	public String getInjuryDescription() {
		return injuryDescription;
	}
	public void setInjuryDescription(String injuryDescription) {
		this.injuryDescription = injuryDescription;
	}
	public String getDangerous() {
		return dangerous;
	}
	public void setDangerous(String dangerous) {
		this.dangerous = dangerous;
	}
	public String getOtherDescription() {
		return otherDescription;
	}
	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonSex() {
		return personSex;
	}
	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}
	public String getPersonCertificateNo() {
		return personCertificateNo;
	}
	public void setPersonCertificateNo(String personCertificateNo) {
		this.personCertificateNo = personCertificateNo;
	}
	
	
	public String getCertificateTypeId() {
		return certificateTypeId;
	}
	public void setCertificateTypeId(String certificateTypeId) {
		this.certificateTypeId = certificateTypeId;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getCaseProperties() {
		return caseProperties;
	}
	public void setCaseProperties(String caseProperties) {
		this.caseProperties = caseProperties;
	}

	public String getOutPlace() {
		return outPlace;
	}
	public void setOutPlace(String outPlace) {
		this.outPlace = outPlace;
	}
	public String getPoliceman() {
		return policeman;
	}
	public void setPoliceman(String policeman) {
		this.policeman = policeman;
	}
	public String getWorkSpace() {
		return workSpace;
	}
	public void setWorkSpace(String workSpace) {
		this.workSpace = workSpace;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
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
	public Date getBetweenTime() {
		return betweenTime;
	}
	public void setBetweenTime(Date betweenTime) {
		this.betweenTime = betweenTime;
	}
	public Date getBetweenTimeMax() {
		return betweenTimeMax;
	}
	public void setBetweenTimeMax(Date betweenTimeMax) {
		this.betweenTimeMax = betweenTimeMax;
	}
	public String getPersonAge() {
		return personAge;
	}
	public void setPersonAge(String personAge) {
		this.personAge = personAge;
	}
	public String getpType() {
		return pType;
	}
	public void setpType(String pType) {
		this.pType = pType;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public Integer getInterrogateCaseId() {
		return interrogateCaseId;
	}
	public void setInterrogateCaseId(Integer interrogateCaseId) {
		this.interrogateCaseId = interrogateCaseId;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getSfsyjd() {
		return sfsyjd;
	}

	public void setSfsyjd(Integer sfsyjd) {
		this.sfsyjd = sfsyjd;
	}

	@Override
	public String toString() {
		return "ExportEntity [id=" + id + ", serialId=" + serialId + ", personType=" + personType + ", personName="
				+ personName + ", personSex=" + personSex + ", certificateTypeId=" + certificateTypeId
				+ ", personCertificateNo=" + personCertificateNo + ", caseType=" + caseType + ", caseProperties="
				+ caseProperties + ", inTime=" + inTime + ", outTime=" + outTime + ", betweenTime=" + betweenTime
				+ ", betweenTimeMax=" + betweenTimeMax + ", outPlace=" + outPlace + ", policeman=" + policeman
				+ ", workSpace=" + workSpace + ", personAge=" + personAge + ", pType=" + pType + ", confirmTime="
				+ confirmTime + ", interrogateCaseId=" + interrogateCaseId + ", personId=" + personId + ", userId="
				+ userId + ", organizationId=" + organizationId + ", outType=" + outType + ", OutReason=" + OutReason
				+ ", edataType=" + edataType + ", edirection=" + edirection + ", confirmResult=" + confirmResult
				+ ", crimeTypeId=" + crimeTypeId + ", crimeSection=" + crimeSection + ", caseNature=" + caseNature
				+ ", jyCasetType=" + jyCasetType + ", calltime=" + calltime + ",sfsyjd=\" + sfsyjd + \",  detainInterval=" + detainInterval
				+ "]";
	}
	public String getOutType() {
		return outType;
	}
	public void setOutType(String outType) {
		this.outType = outType;
	}
	public String getOutReason() {
		return OutReason;
	}
	public void setOutReason(String outReason) {
		OutReason = outReason;
	}
	public String getEdataType() {
		return edataType;
	}
	public void setEdataType(String edataType) {
		this.edataType = edataType;
	}
	public String getEdirection() {
		return edirection;
	}
	public void setEdirection(String edirection) {
		this.edirection = edirection;
	}
	public String getConfirmResult() {
		return confirmResult;
	}
	public void setConfirmResult(String confirmResult) {
		this.confirmResult = confirmResult;
	}
	public String getCrimeTypeId() {
		return crimeTypeId;
	}
	public void setCrimeTypeId(String crimeTypeId) {
		this.crimeTypeId = crimeTypeId;
	}
	public String getCrimeSection() {
		return crimeSection;
	}
	public void setCrimeSection(String crimeSection) {
		this.crimeSection = crimeSection;
	}
	public String getCaseNature() {
		return caseNature;
	}
	public void setCaseNature(String caseNature) {
		this.caseNature = caseNature;
	}
	public String getJyCasetType() {
		return jyCasetType;
	}
	public void setJyCasetType(String jyCasetType) {
		this.jyCasetType = jyCasetType;
	}
	public Date getCalltime() {
		return calltime;
	}
	public void setCalltime(Date calltime) {
		this.calltime = calltime;
	}
	public String getDetainInterval() {
		return detainInterval;
	}
	public void setDetainInterval(String detainInterval) {
		this.detainInterval = detainInterval;
	}
	public String getEntranceProcedure() {
		return entranceProcedure;
	}
	public void setEntranceProcedure(String entranceProcedure) {
		this.entranceProcedure = entranceProcedure;
	}
	public Date getWrittenTime() {
		return writtenTime;
	}
	public void setWrittenTime(Date writtenTime) {
		this.writtenTime = writtenTime;
	}
	public Integer getSfxxcj() {
		return sfxxcj;
	}
	public void setSfxxcj(Integer sfxxcj) {
		this.sfxxcj = sfxxcj;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	public String getLian() {
		return lian;
	}
	public void setLian(String lian) {
		this.lian = lian;
	}
	public String getYue() {
		return yue;
	}
	public void setYue(String yue) {
		this.yue = yue;
	}
	public String getRi() {
		return ri;
	}
	public void setRi(String ri) {
		this.ri = ri;
	} 
	
}
