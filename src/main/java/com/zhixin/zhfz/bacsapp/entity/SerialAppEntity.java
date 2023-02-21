package com.zhixin.zhfz.bacsapp.entity;

import java.util.Date;

public class SerialAppEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String serialNo;

	private String uuid;

	private Integer status;

	private Date statusChangeTime;

	private Long personId;
	//嫌疑人姓名
	private String perName;
	//送押民警ID
	private Long sendUserId;

	private String sendUserNo;

	private String sendUserRealName;

	private String name;

	private String personInfo;

	private Integer type;

	private Integer orderId;

	private String orderNo;

	private Integer areaId;

	private String interrogateAreaName;

	private Integer caseId;

	private String interrogateCaseName;

	private Integer cuffId;

	private Integer policeCuffId1;

	private Integer policeCuffId2;

	private String cuffNo;

	private String policeCuffNo1;

	private String policeCuffNo2;

	private String inPhotoId;

	private Integer inUserId1;

	private String inUserRealName1;

	private String organization;

	private Integer inUserId2;

	private String inUserRealName2;

	private Integer inRegisterUserId;

	private String inRegisterUserRealName;

	private Date inTime;

	private Date confirmTime;

	private String confirmResult;

	private String outReason;

	private String outPhotoId;

	private Date outTime;

	private Integer outType;

	private Integer outRegisterUserId;

	private String outRegisterUserRealName;

	private Integer outUserId1;

	private String outUserRealName1;

	private Integer outUserId2;

	private String outUserRealName2;

	private Integer certificateTypeId;

	private String certificateTypeName;

	private Integer flog;

	private String inUserNo1;

	private String inUserNo2;

	private String caseNature;

	private String entranceProcedure;

	private Date writtenTime;

	private Integer count;


	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public String getSendUserNo() {
		return sendUserNo;
	}

	public void setSendUserNo(String sendUserNo) {
		this.sendUserNo = sendUserNo;
	}

	public String getSendUserRealName() {
		return sendUserRealName;
	}

	public void setSendUserRealName(String sendUserRealName) {
		this.sendUserRealName = sendUserRealName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 法律文书需要的信息
	 */
	private String certificateNo;

	private String personType;

	private String policeRealName;

	private Integer sex;

	private String personName;

	private String involvedReason;

	private Integer caseType;

	private Date birth;

	private String address;

	private String nation;

	private String height;

	private String weight;

	private String bodysize;

	private String faceShape;

	private String footsize;

	private String ear;

	private String eye;

	private String eyeBrow;

	private String formTime;

	private String hairType;

	private String nose;

	private String otherFeature;

	private String physiologicalMarkers;

	private String preparer;

	private String remark;

	private String skinColor;

	private String tooth;

	private String caseName;

	private String informationContent;
	private String fomate;
	private String extractTime;
	private String extractSites;
	private String extractMethod;
	private String vedioResouce;
	private String isOriginal;
	private String reason;
	private String witnesses;
	private String saveSpace;
	private String maker;
	private String policemen;
	private String workspce;

	private String docInjuryisOriginal;
	private String docInjurymakeSites;
	private String docInjurymakeTime;
	private String docInjurymaker;
	private String docInjuryrecordPerson;
	private String docInjurysaveSpace;
	private String docInjurytheLord;
	private String docInjurywitnesses;
	private String docInjuryworkspce;
	private String docInjurycontent;

	private String docSpaceisOriginal;
	private String docSpacemakeSites;
	private String docSpacemakeTime;
	private String docSpacemaker;
	private String docSpacerecordPerson;
	private String docSpacesaveSpace;
	private String docSpacetheLord;
	private String docSpacewitnesses;
	private String docSpaceworkspce;
	private String docSpacecontent;

	private String nickName;
	private String tel;
	private String qq;
	private String email;
	private String mobile;
	private String censusRegister;
	private String job;

	private String phmakeAddress;
	private String phstorageAddress;
	private String phevidenceExplain;
	private String phmakeTime;
	private String phchecks;
	private String phparty;
	private String phproducer;
	private String phshootingPerson;
	private String phunit;
	private String phwitnessPerson;

	private String efdataContent;
	private String efevidtime;
	private String efextractionAddress;
	private String efextractionMethod;
	private String efformat;
	private String eforiginal;
	private String eforiginalplace;
	private String efpoliceName;
	private String efproducer;
	private String efreason;
	private String efunit;
	private String efvideosource;
	private String efwitnessPerson;

	// 传唤手续 、 时间
	private String callProcedure;

	private Date calltime;

	private String ajmc;

	private String ajbh;

	private String areaName;

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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCallProcedure() {
		return callProcedure;
	}

	public void setCallProcedure(String callProcedure) {
		this.callProcedure = callProcedure;
	}

	public Date getCalltime() {
		return calltime;
	}

	public void setCalltime(Date calltime) {
		this.calltime = calltime;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getInvolvedReason() {
		return involvedReason;
	}

	public void setInvolvedReason(String involvedReason) {
		this.involvedReason = involvedReason;
	}

	public Integer getCaseType() {
		return caseType;
	}

	public void setCaseType(Integer caseType) {
		this.caseType = caseType;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPoliceRealName() {
		return policeRealName;
	}

	public void setPoliceRealName(String policeRealName) {
		this.policeRealName = policeRealName;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
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

	public String getCertificateTypeName() {
		return certificateTypeName;
	}

	public void setCertificateTypeName(String certificateTypeName) {
		this.certificateTypeName = certificateTypeName;
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

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getInterrogateAreaName() {
		return interrogateAreaName;
	}

	public void setInterrogateAreaName(String interrogateAreaName) {
		this.interrogateAreaName = interrogateAreaName;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public String getInterrogateCaseName() {
		return interrogateCaseName;
	}

	public void setInterrogateCaseName(String interrogateCaseName) {
		this.interrogateCaseName = interrogateCaseName;
	}

	public Integer getCuffId() {
		return cuffId;
	}

	public void setCuffId(Integer cuffId) {
		this.cuffId = cuffId;
	}

	public String getCuffNo() {
		return cuffNo;
	}

	public void setCuffNo(String cuffNo) {
		this.cuffNo = cuffNo;
	}

	public Integer getInUserId1() {
		return inUserId1;
	}

	public void setInUserId1(Integer inUserId1) {
		this.inUserId1 = inUserId1;
	}

	public String getInUserRealName1() {
		return inUserRealName1;
	}

	public void setInUserRealName1(String inUserRealName1) {
		this.inUserRealName1 = inUserRealName1;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Integer getInUserId2() {
		return inUserId2;
	}

	public void setInUserId2(Integer inUserId2) {
		this.inUserId2 = inUserId2;
	}

	public String getInUserRealName2() {
		return inUserRealName2;
	}

	public void setInUserRealName2(String inUserRealName2) {
		this.inUserRealName2 = inUserRealName2;
	}

	public Integer getInRegisterUserId() {
		return inRegisterUserId;
	}

	public void setInRegisterUserId(Integer inRegisterUserId) {
		this.inRegisterUserId = inRegisterUserId;
	}

	public String getInRegisterUserRealName() {
		return inRegisterUserRealName;
	}

	public void setInRegisterUserRealName(String inRegisterUserRealName) {
		this.inRegisterUserRealName = inRegisterUserRealName;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public String getOutReason() {
		return outReason;
	}

	public void setOutReason(String outReason) {
		this.outReason = outReason;
	}

	public String getInPhotoId() {
		return inPhotoId;
	}

	public void setInPhotoId(String inPhotoId) {
		this.inPhotoId = inPhotoId;
	}

	public String getOutPhotoId() {
		return outPhotoId;
	}

	public void setOutPhotoId(String outPhotoId) {
		this.outPhotoId = outPhotoId;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public Integer getOutType() {
		return outType;
	}

	public void setOutType(Integer outType) {
		this.outType = outType;
	}

	public Integer getOutRegisterUserId() {
		return outRegisterUserId;
	}

	public void setOutRegisterUserId(Integer outRegisterUserId) {
		this.outRegisterUserId = outRegisterUserId;
	}

	public String getOutRegisterUserRealName() {
		return outRegisterUserRealName;
	}

	public void setOutRegisterUserRealName(String outRegisterUserRealName) {
		this.outRegisterUserRealName = outRegisterUserRealName;
	}

	public Integer getOutUserId1() {
		return outUserId1;
	}

	public void setOutUserId1(Integer outUserId1) {
		this.outUserId1 = outUserId1;
	}

	public String getOutUserRealName1() {
		return outUserRealName1;
	}

	public void setOutUserRealName1(String outUserRealName1) {
		this.outUserRealName1 = outUserRealName1;
	}

	public Integer getOutUserId2() {
		return outUserId2;
	}

	public void setOutUserId2(Integer outUserId2) {
		this.outUserId2 = outUserId2;
	}

	public String getOutUserRealName2() {
		return outUserRealName2;
	}

	public void setOutUserRealName2(String outUserRealName2) {
		this.outUserRealName2 = outUserRealName2;
	}

	public Integer getFlog() {
		return flog;
	}

	public void setFlog(Integer flog) {
		this.flog = flog;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getInUserNo1() {
		return inUserNo1;
	}

	public void setInUserNo1(String inUserNo1) {
		this.inUserNo1 = inUserNo1;
	}

	public String getInUserNo2() {
		return inUserNo2;
	}

	public void setInUserNo2(String inUserNo2) {
		this.inUserNo2 = inUserNo2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getBodysize() {
		return bodysize;
	}

	public void setBodysize(String bodysize) {
		this.bodysize = bodysize;
	}

	public String getFaceShape() {
		return faceShape;
	}

	public void setFaceShape(String faceShape) {
		this.faceShape = faceShape;
	}

	public String getFootsize() {
		return footsize;
	}

	public void setFootsize(String footsize) {
		this.footsize = footsize;
	}

	public String getEar() {
		return ear;
	}

	public void setEar(String ear) {
		this.ear = ear;
	}

	public String getEye() {
		return eye;
	}

	public void setEye(String eye) {
		this.eye = eye;
	}

	public String getEyeBrow() {
		return eyeBrow;
	}

	public void setEyeBrow(String eyeBrow) {
		this.eyeBrow = eyeBrow;
	}

	public String getFormTime() {
		return formTime;
	}

	public void setFormTime(String formTime) {
		this.formTime = formTime;
	}

	public String getHairType() {
		return hairType;
	}

	public void setHairType(String hairType) {
		this.hairType = hairType;
	}

	public String getNose() {
		return nose;
	}

	public void setNose(String nose) {
		this.nose = nose;
	}

	public String getOtherFeature() {
		return otherFeature;
	}

	public void setOtherFeature(String otherFeature) {
		this.otherFeature = otherFeature;
	}

	public String getPhysiologicalMarkers() {
		return physiologicalMarkers;
	}

	public void setPhysiologicalMarkers(String physiologicalMarkers) {
		this.physiologicalMarkers = physiologicalMarkers;
	}

	public String getPreparer() {
		return preparer;
	}

	public void setPreparer(String preparer) {
		this.preparer = preparer;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSkinColor() {
		return skinColor;
	}

	public void setSkinColor(String skinColor) {
		this.skinColor = skinColor;
	}

	public String getTooth() {
		return tooth;
	}

	public void setTooth(String tooth) {
		this.tooth = tooth;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getInformationContent() {
		return informationContent;
	}

	public void setInformationContent(String informationContent) {
		this.informationContent = informationContent;
	}

	public String getFomate() {
		return fomate;
	}

	public void setFomate(String fomate) {
		this.fomate = fomate;
	}

	public String getExtractTime() {
		return extractTime;
	}

	public void setExtractTime(String extractTime) {
		this.extractTime = extractTime;
	}

	public String getExtractSites() {
		return extractSites;
	}

	public void setExtractSites(String extractSites) {
		this.extractSites = extractSites;
	}

	public String getExtractMethod() {
		return extractMethod;
	}

	public void setExtractMethod(String extractMethod) {
		this.extractMethod = extractMethod;
	}

	public String getVedioResouce() {
		return vedioResouce;
	}

	public void setVedioResouce(String vedioResouce) {
		this.vedioResouce = vedioResouce;
	}

	public String getIsOriginal() {
		return isOriginal;
	}

	public void setIsOriginal(String isOriginal) {
		this.isOriginal = isOriginal;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getWitnesses() {
		return witnesses;
	}

	public void setWitnesses(String witnesses) {
		this.witnesses = witnesses;
	}

	public String getSaveSpace() {
		return saveSpace;
	}

	public void setSaveSpace(String saveSpace) {
		this.saveSpace = saveSpace;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getPolicemen() {
		return policemen;
	}

	public void setPolicemen(String policemen) {
		this.policemen = policemen;
	}

	public String getWorkspce() {
		return workspce;
	}

	public void setWorkspce(String workspce) {
		this.workspce = workspce;
	}

	public String getDocInjuryisOriginal() {
		return docInjuryisOriginal;
	}

	public void setDocInjuryisOriginal(String docInjuryisOriginal) {
		this.docInjuryisOriginal = docInjuryisOriginal;
	}

	public String getDocInjurymakeSites() {
		return docInjurymakeSites;
	}

	public void setDocInjurymakeSites(String docInjurymakeSites) {
		this.docInjurymakeSites = docInjurymakeSites;
	}

	public String getDocInjurymakeTime() {
		return docInjurymakeTime;
	}

	public void setDocInjurymakeTime(String docInjurymakeTime) {
		this.docInjurymakeTime = docInjurymakeTime;
	}

	public String getDocInjurymaker() {
		return docInjurymaker;
	}

	public void setDocInjurymaker(String docInjurymaker) {
		this.docInjurymaker = docInjurymaker;
	}

	public String getDocInjuryrecordPerson() {
		return docInjuryrecordPerson;
	}

	public void setDocInjuryrecordPerson(String docInjuryrecordPerson) {
		this.docInjuryrecordPerson = docInjuryrecordPerson;
	}

	public String getDocInjurysaveSpace() {
		return docInjurysaveSpace;
	}

	public void setDocInjurysaveSpace(String docInjurysaveSpace) {
		this.docInjurysaveSpace = docInjurysaveSpace;
	}

	public String getDocInjurytheLord() {
		return docInjurytheLord;
	}

	public void setDocInjurytheLord(String docInjurytheLord) {
		this.docInjurytheLord = docInjurytheLord;
	}

	public String getDocInjurywitnesses() {
		return docInjurywitnesses;
	}

	public void setDocInjurywitnesses(String docInjurywitnesses) {
		this.docInjurywitnesses = docInjurywitnesses;
	}

	public String getDocInjuryworkspce() {
		return docInjuryworkspce;
	}

	public void setDocInjuryworkspce(String docInjuryworkspce) {
		this.docInjuryworkspce = docInjuryworkspce;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getDocInjurycontent() {
		return docInjurycontent;
	}

	public void setDocInjurycontent(String docInjurycontent) {
		this.docInjurycontent = docInjurycontent;
	}

	public String getDocSpaceisOriginal() {
		return docSpaceisOriginal;
	}

	public void setDocSpaceisOriginal(String docSpaceisOriginal) {
		this.docSpaceisOriginal = docSpaceisOriginal;
	}

	public String getDocSpacemakeSites() {
		return docSpacemakeSites;
	}

	public void setDocSpacemakeSites(String docSpacemakeSites) {
		this.docSpacemakeSites = docSpacemakeSites;
	}

	public String getDocSpacemakeTime() {
		return docSpacemakeTime;
	}

	public void setDocSpacemakeTime(String docSpacemakeTime) {
		this.docSpacemakeTime = docSpacemakeTime;
	}

	public String getDocSpacemaker() {
		return docSpacemaker;
	}

	public void setDocSpacemaker(String docSpacemaker) {
		this.docSpacemaker = docSpacemaker;
	}

	public String getDocSpacerecordPerson() {
		return docSpacerecordPerson;
	}

	public void setDocSpacerecordPerson(String docSpacerecordPerson) {
		this.docSpacerecordPerson = docSpacerecordPerson;
	}

	public String getDocSpacesaveSpace() {
		return docSpacesaveSpace;
	}

	public void setDocSpacesaveSpace(String docSpacesaveSpace) {
		this.docSpacesaveSpace = docSpacesaveSpace;
	}

	public String getDocSpacetheLord() {
		return docSpacetheLord;
	}

	public void setDocSpacetheLord(String docSpacetheLord) {
		this.docSpacetheLord = docSpacetheLord;
	}

	public String getDocSpacewitnesses() {
		return docSpacewitnesses;
	}

	public void setDocSpacewitnesses(String docSpacewitnesses) {
		this.docSpacewitnesses = docSpacewitnesses;
	}

	public String getDocSpaceworkspce() {
		return docSpaceworkspce;
	}

	public void setDocSpaceworkspce(String docSpaceworkspce) {
		this.docSpaceworkspce = docSpaceworkspce;
	}

	public String getDocSpacecontent() {
		return docSpacecontent;
	}

	public void setDocSpacecontent(String docSpacecontent) {
		this.docSpacecontent = docSpacecontent;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCensusRegister() {
		return censusRegister;
	}

	public void setCensusRegister(String censusRegister) {
		this.censusRegister = censusRegister;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getPhmakeAddress() {
		return phmakeAddress;
	}

	public void setPhmakeAddress(String phmakeAddress) {
		this.phmakeAddress = phmakeAddress;
	}

	public String getPhstorageAddress() {
		return phstorageAddress;
	}

	public void setPhstorageAddress(String phstorageAddress) {
		this.phstorageAddress = phstorageAddress;
	}

	public String getPhevidenceExplain() {
		return phevidenceExplain;
	}

	public void setPhevidenceExplain(String phevidenceExplain) {
		this.phevidenceExplain = phevidenceExplain;
	}

	public String getPhmakeTime() {
		return phmakeTime;
	}

	public void setPhmakeTime(String phmakeTime) {
		this.phmakeTime = phmakeTime;
	}

	public String getPhchecks() {
		return phchecks;
	}

	public void setPhchecks(String phchecks) {
		this.phchecks = phchecks;
	}

	public String getPhparty() {
		return phparty;
	}

	public void setPhparty(String phparty) {
		this.phparty = phparty;
	}

	public String getPhproducer() {
		return phproducer;
	}

	public void setPhproducer(String phproducer) {
		this.phproducer = phproducer;
	}

	public String getPhshootingPerson() {
		return phshootingPerson;
	}

	public void setPhshootingPerson(String phshootingPerson) {
		this.phshootingPerson = phshootingPerson;
	}

	public String getPhunit() {
		return phunit;
	}

	public void setPhunit(String phunit) {
		this.phunit = phunit;
	}

	public String getPhwitnessPerson() {
		return phwitnessPerson;
	}

	public void setPhwitnessPerson(String phwitnessPerson) {
		this.phwitnessPerson = phwitnessPerson;
	}

	public String getEfdataContent() {
		return efdataContent;
	}

	public void setEfdataContent(String efdataContent) {
		this.efdataContent = efdataContent;
	}

	public String getEfevidtime() {
		return efevidtime;
	}

	public void setEfevidtime(String efevidtime) {
		this.efevidtime = efevidtime;
	}

	public String getEfextractionAddress() {
		return efextractionAddress;
	}

	public void setEfextractionAddress(String efextractionAddress) {
		this.efextractionAddress = efextractionAddress;
	}

	public String getEfextractionMethod() {
		return efextractionMethod;
	}

	public void setEfextractionMethod(String efextractionMethod) {
		this.efextractionMethod = efextractionMethod;
	}

	public String getEfformat() {
		return efformat;
	}

	public void setEfformat(String efformat) {
		this.efformat = efformat;
	}

	public String getEforiginal() {
		return eforiginal;
	}

	public void setEforiginal(String eforiginal) {
		this.eforiginal = eforiginal;
	}

	public String getEforiginalplace() {
		return eforiginalplace;
	}

	public void setEforiginalplace(String eforiginalplace) {
		this.eforiginalplace = eforiginalplace;
	}

	public String getEfpoliceName() {
		return efpoliceName;
	}

	public void setEfpoliceName(String efpoliceName) {
		this.efpoliceName = efpoliceName;
	}

	public String getEfproducer() {
		return efproducer;
	}

	public void setEfproducer(String efproducer) {
		this.efproducer = efproducer;
	}

	public String getEfreason() {
		return efreason;
	}

	public void setEfreason(String efreason) {
		this.efreason = efreason;
	}

	public String getEfunit() {
		return efunit;
	}

	public void setEfunit(String efunit) {
		this.efunit = efunit;
	}

	public String getEfvideosource() {
		return efvideosource;
	}

	public void setEfvideosource(String efvideosource) {
		this.efvideosource = efvideosource;
	}

	public String getEfwitnessPerson() {
		return efwitnessPerson;
	}

	public void setEfwitnessPerson(String efwitnessPerson) {
		this.efwitnessPerson = efwitnessPerson;
	}

	public String getCaseNature() {
		return caseNature;
	}

	public void setCaseNature(String caseNature) {
		this.caseNature = caseNature;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(String personInfo) {
		this.personInfo = personInfo;
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

	public Date getWrittenTime() {
		return writtenTime;
	}

	public void setWrittenTime(Date writtenTime) {
		this.writtenTime = writtenTime;
	}

	public String getConfirmResult() {
		return confirmResult;
	}

	public void setConfirmResult(String confirmResult) {
		this.confirmResult = confirmResult;
	}

	public Integer getPoliceCuffId1() {
		return policeCuffId1;
	}

	public void setPoliceCuffId1(Integer policeCuffId1) {
		this.policeCuffId1 = policeCuffId1;
	}

	public Integer getPoliceCuffId2() {
		return policeCuffId2;
	}

	public void setPoliceCuffId2(Integer policeCuffId2) {
		this.policeCuffId2 = policeCuffId2;
	}

	public String getPoliceCuffNo1() {
		return policeCuffNo1;
	}

	public void setPoliceCuffNo1(String policeCuffNo1) {
		this.policeCuffNo1 = policeCuffNo1;
	}

	public String getPoliceCuffNo2() {
		return policeCuffNo2;
	}

	public void setPoliceCuffNo2(String policeCuffNo2) {
		this.policeCuffNo2 = policeCuffNo2;
	}

}