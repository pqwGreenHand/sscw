package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class OrderStatusEntity implements Serializable{
    /**
	 * 预约状态
	 */
	private static final long serialVersionUID = 8578674861753714751L;

	private Integer id;

    private String statusName;

    private Date statusTime;

    private Integer orderRequestId;
    
    //链表
	private Integer oreId;

	private String orderNo;

	private Integer orderUserId1;

	private Integer orderUserId2;

	private Integer maleCount;

	private Integer femaleCount;

	private Integer juvenilesCount;

	private Integer specialCount;

	private Integer otherCount;

	private String caseName;

	private Timestamp planTime;

	private Timestamp arriveTime;

	private String type;

	private Integer status;

	private String description;

	private Integer noterId;

	private Integer interrogateAreaId;

	private Date createdTime;

	private Date updatedTime;

	// 链表

	private String interrogateAreaName;

	private String person1Name;

	private String person2Name;

	private String notePersonName;

	// 链表
	private Integer contentId;

	private String name;

	private Integer certificateTypeId;

	private String certificateNo;

	private Integer sex;

	private Integer opUserId;

	private String opPid;

	public Integer getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(Integer opUserId) {
		this.opUserId = opUserId;
	}

	public String getOpPid() {
		return opPid;
	}

	public void setOpPid(String opPid) {
		this.opPid = opPid;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public Date getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }

    public Integer getOrderRequestId() {
        return orderRequestId;
    }

    public void setOrderRequestId(Integer orderRequestId) {
        this.orderRequestId = orderRequestId;
    }



	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderUserId1() {
		return orderUserId1;
	}

	public void setOrderUserId1(Integer orderUserId1) {
		this.orderUserId1 = orderUserId1;
	}

	public Integer getOrderUserId2() {
		return orderUserId2;
	}

	public void setOrderUserId2(Integer orderUserId2) {
		this.orderUserId2 = orderUserId2;
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

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public Timestamp getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Timestamp planTime) {
		this.planTime = planTime;
	}

	public Timestamp getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Timestamp arriveTime) {
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

	public String getInterrogateAreaName() {
		return interrogateAreaName;
	}

	public void setInterrogateAreaName(String interrogateAreaName) {
		this.interrogateAreaName = interrogateAreaName;
	}

	public String getPerson1Name() {
		return person1Name;
	}

	public void setPerson1Name(String person1Name) {
		this.person1Name = person1Name;
	}

	public String getPerson2Name() {
		return person2Name;
	}

	public void setPerson2Name(String person2Name) {
		this.person2Name = person2Name;
	}

	public String getNotePersonName() {
		return notePersonName;
	}

	public void setNotePersonName(String notePersonName) {
		this.notePersonName = notePersonName;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
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

	public Integer getOreId() {
		return oreId;
	}

	public void setOreId(Integer oreId) {
		this.oreId = oreId;
	}
}