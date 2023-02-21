package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class DocDietrestRegistrationEntity implements Serializable{
    /**
	 * 饮食休息表
	 */
	private static final long serialVersionUID = 2011715025212697762L;

	private Integer id;

    private Long serialId;

    private Long personId;

    private String registry;

    private String date;

    private String beginTime;

    private String endTime;

    private String criminalSuspect;

    private String police;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DocDietrestRegistrationEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", personId=" + personId +
                ", registry='" + registry + '\'' +
                ", date='" + date + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", criminalSuspect='" + criminalSuspect + '\'' +
                ", police='" + police + '\'' +
                '}';
    }

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

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry == null ? null : registry.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getCriminalSuspect() {
        return criminalSuspect;
    }

    public void setCriminalSuspect(String criminalSuspect) {
        this.criminalSuspect = criminalSuspect == null ? null : criminalSuspect.trim();
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police == null ? null : police.trim();
    }
}