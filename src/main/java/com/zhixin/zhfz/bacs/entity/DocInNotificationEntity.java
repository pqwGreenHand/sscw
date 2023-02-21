package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class DocInNotificationEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Long serialId;//专属编号ID

    private String policeStation;//派出所

    private Date inTime;//入所时间

    private String sendUnit;//送案单位

    private String handlingUnit;//办案单位

    private String connectionSituation;//同案情况

    private String approver;//批准人

    private String agent;//经办人
    
    private String serialNo;//专属编号
    
    private String personName;
    private String address;
    private String sex;
    private String birth;
    private String type;
    private String age;

    public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation == null ? null : policeStation.trim();
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getSendUnit() {
        return sendUnit;
    }

    public void setSendUnit(String sendUnit) {
        this.sendUnit = sendUnit == null ? null : sendUnit.trim();
    }

    public String getHandlingUnit() {
        return handlingUnit;
    }

    public void setHandlingUnit(String handlingUnit) {
        this.handlingUnit = handlingUnit == null ? null : handlingUnit.trim();
    }

    public String getConnectionSituation() {
        return connectionSituation;
    }

    public void setConnectionSituation(String connectionSituation) {
        this.connectionSituation = connectionSituation == null ? null : connectionSituation.trim();
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver == null ? null : approver.trim();
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

    @Override
    public String toString() {
        return "DocInNotificationEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", policeStation='" + policeStation + '\'' +
                ", inTime=" + inTime +
                ", sendUnit='" + sendUnit + '\'' +
                ", handlingUnit='" + handlingUnit + '\'' +
                ", connectionSituation='" + connectionSituation + '\'' +
                ", approver='" + approver + '\'' +
                ", agent='" + agent + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", personName='" + personName + '\'' +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", birth='" + birth + '\'' +
                ", type='" + type + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}