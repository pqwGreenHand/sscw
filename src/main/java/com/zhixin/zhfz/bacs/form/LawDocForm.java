package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;

public class LawDocForm implements Serializable {

    private static final long serialVersionUID = 273697158321246266L;

    private int number;//编号
    private String name;//名称
    private int type;//1:word 2:execl
    private long userId;
    private String serialNo;
    private Long serialId;
    private Long caseId;
    private String startTime;
    private String endTime;
    private int areaId;
    private String userName;
    private String passWord;

    private long belongingsId;

    private Long policeId;
    private String serialUUID;
    private Integer count;

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getSerialUUID() {
        return serialUUID;
    }

    public void setSerialUUID(String serialUUID) {
        this.serialUUID = serialUUID;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getPoliceId() {
        return policeId;
    }

    public void setPoliceId(Long policeId) {
        this.policeId = policeId;
    }

    public long getBelongingsId() {
        return belongingsId;
    }

    public void setBelongingsId(long belongingsId) {
        this.belongingsId = belongingsId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Long getSerialId() {
		return serialId;
	}

	public void setSerialId(Long serialId) {
		this.serialId = serialId;
	}

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
}
