package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;

/*
 * 手环表单
 * */
public class CuffForm implements Serializable {
	
	private static final long serialVersionUID = 5577128090368009959L;

	private Integer id;

	private String icNo;

    private String cuffNo;

    private Integer status;

	private Integer type;

    private Integer interrogateAreaId;

    private String heartNo;

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
		this.cuffNo = cuffNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CuffForm [id=" + id + ", cuffNo=" + cuffNo + ", status=" + status + ", type=" + type
				+ ", interrogateAreaId=" + interrogateAreaId + "]";
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

	public String getHeartNo() {
		return heartNo;
	}

	public void setHeartNo(String heartNo) {
		this.heartNo = heartNo;
	}
}
