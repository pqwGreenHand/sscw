package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

/*
 * 手环
 * */
public class CuffEntity implements Serializable {

	private static final long serialVersionUID = -5103704590064454356L;

	public static final String INSERT_TYPE = "添加手环";

	public static final String EDIT_TYPE = "修改手环";

	public static final String DELETE_TYPE = "删除手环";

	private Integer id;

	private String cuffNo;

	private String icNo;

	private Integer status;

	private Integer type;

	private Integer interrogateAreaId;

	private String interrogateAreaName;
	
	private Long personId;
	
	private Long userId;
	
	private String binderName;
	
	private String binderCertificateNo;
	
	//最后绑定ID 根据lastBindType 对应不同表的ID (更新用，不然每次都查最后一条，速度会越来越慢，属于额外字段)
	private Long lastBindId;
	//0:上下班,1:他所民警,2:嫌疑人,3:其他人员
	private Integer lastBindType;

	private String heartNo;
	private String op_Pid;
	private Integer op_User_Id;

	public String getOp_Pid() {
		return op_Pid;
	}

	public void setOp_Pid(String op_Pid) {
		this.op_Pid = op_Pid;
	}

	public Integer getOp_User_Id() {
		return op_User_Id;
	}

	public void setOp_User_Id(Integer op_User_Id) {
		this.op_User_Id = op_User_Id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCuffNo() {
		return cuffNo;
	}

	public void setCuffNo(String cuffNo) {
		this.cuffNo = cuffNo == null ? null : cuffNo.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public String getInterrogateAreaName() {
		return interrogateAreaName;
	}

	public void setInterrogateAreaName(String interrogateAreaName) {
		this.interrogateAreaName = interrogateAreaName;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getInterrogateAreaId() {
		return interrogateAreaId;
	}

	public void setInterrogateAreaId(Integer interrogateAreaId) {
		this.interrogateAreaId = interrogateAreaId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIcNo() {
		return icNo;
	}

	public void setIcNo(String icNo) {
		this.icNo = icNo;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBinderName() {
		return binderName;
	}

	public void setBinderName(String binderName) {
		this.binderName = binderName;
	}

	
	public Long getLastBindId() {
		return lastBindId;
	}

	public void setLastBindId(Long lastBindId) {
		this.lastBindId = lastBindId;
	}

	public Integer getLastBindType() {
		return lastBindType;
	}

	public void setLastBindType(Integer lastBindType) {
		this.lastBindType = lastBindType;
	}

	
	public String getBinderCertificateNo() {
		return binderCertificateNo;
	}

	public void setBinderCertificateNo(String binderCertificateNo) {
		this.binderCertificateNo = binderCertificateNo;
	}

	public String getHeartNo() {
		return heartNo;
	}

	public void setHeartNo(String heartNo) {
		this.heartNo = heartNo;
	}

	@Override
	public String toString() {
		return "CuffEntity{" +
				"id=" + id +
				", cuffNo='" + cuffNo + '\'' +
				", icNo='" + icNo + '\'' +
				", status=" + status +
				", type=" + type +
				", interrogateAreaId=" + interrogateAreaId +
				", interrogateAreaName='" + interrogateAreaName + '\'' +
				", personId=" + personId +
				", userId=" + userId +
				", binderName='" + binderName + '\'' +
				", binderCertificateNo='" + binderCertificateNo + '\'' +
				", lastBindId=" + lastBindId +
				", lastBindType=" + lastBindType +
				", heartNo='" + heartNo + '\'' +
				'}';
	}
}