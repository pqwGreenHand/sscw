package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;
import java.util.Date;

public class WaitingRecordForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
    private Long serialId;
    private Long personId;
	private int sendUserId1;
	private Integer areaId;
	private Integer caseId;
	private int roommId;
	public Long getSerialId() {
		return serialId;
	}

	public void setSerialId(Long serialId) {
		this.serialId = serialId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public int getSendUserId1() {
		return sendUserId1;
	}

	public void setSendUserId1(int sendUserId1) {
		this.sendUserId1 = sendUserId1;
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

	public int getRoommId() {
		return roommId;
	}

	public void setRoommId(int roommId) {
		this.roommId = roommId;
	}

	@Override
	public String toString() {
		return "WaitingRecordForm{" +
				"serialId=" + serialId +
				", personId=" + personId +
				", sendUserId1=" + sendUserId1 +
				", areaId=" + areaId +
				", caseId=" + caseId +
				", roommId=" + roommId +
				'}';
	}
}
