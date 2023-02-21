package com.zhixin.zhfz.common.entity;

import java.util.Date;

/**
 * 下拉框
 */
public class CombogridEntity implements java.io.Serializable {

    private static final long serialVersionUID = -4783107711681656029L;

    private int id;
    private Long serialId;
    private String serialNo;
    private String status;
    private String orderRequestId;
    private String inPhotoName;
    private String ajlx;
    private String ab;
    private String orderAjlx;
    private String orderAb;
    private String description;
    private String name;
    private String certificateNo;
    private String sex;
    private String birth;
    private String nation;
    private String education;
    private String personId;
    private String areaId;
    private String caseId;
    private String inUserNo1;
    private String policeCuffNo1;
    private String inUserId1;
    private String inUserNo2;
    private String policeCuffNo2;
    private String inUserId2;
    private String policeName1;
    private String afdd;
    private Date afsj;
    private Date inTime;
    private String uuid;
    private Long roomId;
    private String roomName;
    private String shzt;
    private String type;

     //嫌疑人入区添加
    private String personType;
    private int certificateTypeId;
    private String orderNo;
    private Date timenow;
    private Integer userId;
    private int policeId;
    private String ajmc;
    private Integer sendUserId;
    private String sendUserName;
    private String belongingsId;
    private String personCuff;
    private Integer recordId;
    private String orderUserNo;
    private String orderUserName;
    private Integer orderUserId;
    private String sendUserNo;
//    is_juvenile,orp.is_gravida,orp.is_disease,
    private Integer isJuvenile;
    private Integer isGravida;
    private Integer isDisease;
    private Integer sfxxcj;
    
    private String ajbh;

    private String rybh;

    private String caseNo;

    public String getBelongingsId() {
        return belongingsId;
    }

    public void setBelongingsId(String belongingsId) {
        this.belongingsId = belongingsId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShzt() {
        return shzt;
    }

    public void setShzt(String shzt) {
        this.shzt = shzt;
    }

    private String orderPersonAjmc;

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getOrderPersonAjmc() {
        return orderPersonAjmc;
    }

    public void setOrderPersonAjmc(String orderPersonAjmc) {
        this.orderPersonAjmc = orderPersonAjmc;
    }

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

    public String getOrderAjlx() {
        return orderAjlx;
    }

    public void setOrderAjlx(String orderAjlx) {
        this.orderAjlx = orderAjlx;
    }

    public String getOrderAb() {
        return orderAb;
    }

    public void setOrderAb(String orderAb) {
        this.orderAb = orderAb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsJuvenile() {
		return isJuvenile;
	}

	public void setIsJuvenile(Integer isJuvenile) {
		this.isJuvenile = isJuvenile;
	}

	public Integer getIsGravida() {
		return isGravida;
	}

	public void setIsGravida(Integer isGravida) {
		this.isGravida = isGravida;
	}

	public Integer getIsDisease() {
		return isDisease;
	}

	public void setIsDisease(Integer isDisease) {
		this.isDisease = isDisease;
	}

	public String getSendUserNo() {
        return sendUserNo;
    }

    public void setSendUserNo(String sendUserNo) {
        this.sendUserNo = sendUserNo;
    }

    public String getOrderUserNo() {
        return orderUserNo;
    }

    public void setOrderUserNo(String orderUserNo) {
        this.orderUserNo = orderUserNo;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public String getPersonCuff() {
        return personCuff;
    }

    public void setPersonCuff(String personCuff) {
        this.personCuff = personCuff;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public String getPoliceName1() {
        return policeName1;
    }

    public void setPoliceName1(String policeName1) {
        this.policeName1 = policeName1;
    }

    public String getPoliceName2() {
        return policeName2;
    }

    public void setPoliceName2(String policeName2) {
        this.policeName2 = policeName2;
    }

    private String policeName2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderRequestId() {
        return orderRequestId;
    }

    public void setOrderRequestId(String orderRequestId) {
        this.orderRequestId = orderRequestId;
    }

    public String getInPhotoName() {
        return inPhotoName;
    }

    public void setInPhotoName(String inPhotoName) {
        this.inPhotoName = inPhotoName;
    }

    public String getAjlx() {
        return ajlx;
    }

    public void setAjlx(String ajlx) {
        this.ajlx = ajlx;
    }

    public String getAb() {
        return ab;
    }

    public void setAb(String ab) {
        this.ab = ab;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getInUserNo1() {
        return inUserNo1;
    }

    public void setInUserNo1(String inUserNo1) {
        this.inUserNo1 = inUserNo1;
    }

    public String getPoliceCuffNo1() {
        return policeCuffNo1;
    }

    public void setPoliceCuffNo1(String policeCuffNo1) {
        this.policeCuffNo1 = policeCuffNo1;
    }

    public String getInUserId1() {
        return inUserId1;
    }

    public void setInUserId1(String inUserId1) {
        this.inUserId1 = inUserId1;
    }

    public String getInUserNo2() {
        return inUserNo2;
    }

    public void setInUserNo2(String inUserNo2) {
        this.inUserNo2 = inUserNo2;
    }

    public String getPoliceCuffNo2() {
        return policeCuffNo2;
    }

    public void setPoliceCuffNo2(String policeCuffNo2) {
        this.policeCuffNo2 = policeCuffNo2;
    }

    public String getInUserId2() {
        return inUserId2;
    }

    public void setInUserId2(String inUserId2) {
        this.inUserId2 = inUserId2;
    }

    public String getAfdd() {
        return afdd;
    }

    public void setAfdd(String afdd) {
        this.afdd = afdd;
    }

    public Date getAfsj() {
        return afsj;
    }

    public void setAfsj(Date afsj) {
        this.afsj = afsj;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public int getCertificateTypeId() {
        return certificateTypeId;
    }

    public void setCertificateTypeId(int certificateTypeId) {
        this.certificateTypeId = certificateTypeId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getTimenow() {
        return timenow;
    }

    public void setTimenow(Date timenow) {
        this.timenow = timenow;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getPoliceId() {
        return policeId;
    }

    public void setPoliceId(int policeId) {
        this.policeId = policeId;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }
    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(Integer orderUserId) {
        this.orderUserId = orderUserId;
    }

    @Override
    public String toString() {
        return "CombogridEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", serialNo='" + serialNo + '\'' +
                ", status='" + status + '\'' +
                ", orderRequestId='" + orderRequestId + '\'' +
                ", inPhotoName='" + inPhotoName + '\'' +
                ", ajlx='" + ajlx + '\'' +
                ", ab='" + ab + '\'' +
                ", orderAjlx='" + orderAjlx + '\'' +
                ", orderAb='" + orderAb + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", certificateNo='" + certificateNo + '\'' +
                ", sex='" + sex + '\'' +
                ", birth='" + birth + '\'' +
                ", nation='" + nation + '\'' +
                ", education='" + education + '\'' +
                ", personId='" + personId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", caseId='" + caseId + '\'' +
                ", inUserNo1='" + inUserNo1 + '\'' +
                ", policeCuffNo1='" + policeCuffNo1 + '\'' +
                ", inUserId1='" + inUserId1 + '\'' +
                ", inUserNo2='" + inUserNo2 + '\'' +
                ", policeCuffNo2='" + policeCuffNo2 + '\'' +
                ", inUserId2='" + inUserId2 + '\'' +
                ", policeName1='" + policeName1 + '\'' +
                ", afdd='" + afdd + '\'' +
                ", afsj=" + afsj +
                ", inTime=" + inTime +
                ", uuid='" + uuid + '\'' +
                ", roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", personType='" + personType + '\'' +
                ", certificateTypeId=" + certificateTypeId +
                ", orderNo='" + orderNo + '\'' +
                ", timenow=" + timenow +
                ", userId=" + userId +
                ", policeId=" + policeId +
                ", ajmc='" + ajmc + '\'' +
                ", sendUserId=" + sendUserId +
                ", sendUserName='" + sendUserName + '\'' +
                ", personCuff='" + personCuff + '\'' +
                ", recordId=" + recordId +
                ", orderUserNo='" + orderUserNo + '\'' +
                ", orderUserName='" + orderUserName + '\'' +
                ", orderUserId=" + orderUserId +
                ", sendUserNo='" + sendUserNo + '\'' +
                ", isJuvenile=" + isJuvenile +
                ", isGravida=" + isGravida +
                ", isDisease=" + isDisease +
                ", sfxxcj=" + sfxxcj +
                ", ajbh='" + ajbh + '\'' +
                ", rybh='" + rybh + '\'' +
                ", caseNo='" + caseNo + '\'' +
                ", orderPersonAjmc='" + orderPersonAjmc + '\'' +
                ", policeName2='" + policeName2 + '\'' +
                '}';
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

	public Integer getSfxxcj() {
		return sfxxcj;
	}

	public void setSfxxcj(Integer sfxxcj) {
		this.sfxxcj = sfxxcj;
	}
    
    
}
