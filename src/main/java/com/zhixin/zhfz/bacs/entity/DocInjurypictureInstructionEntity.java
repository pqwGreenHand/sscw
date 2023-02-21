package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class DocInjurypictureInstructionEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6024908408227019228L;

	private Integer id;

    private Long SerialId;

    private Long personId;

    private String makeTime;

    private String makeSites;

    private String content;

    private String isOriginal;

    private String saveSpace;

    private String witnesses;

    private String recordPerson;

    private String maker;

    private String theLord;

    private String workspce;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSerialId() {
        return SerialId;
    }

    public void setSerialId(Long serialId) {
        SerialId = serialId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(String makeTime) {
        this.makeTime = makeTime == null ? null : makeTime.trim();
    }

    public String getMakeSites() {
        return makeSites;
    }

    public void setMakeSites(String makeSites) {
        this.makeSites = makeSites == null ? null : makeSites.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(String isOriginal) {
        this.isOriginal = isOriginal == null ? null : isOriginal.trim();
    }

    public String getSaveSpace() {
        return saveSpace;
    }

    public void setSaveSpace(String saveSpace) {
        this.saveSpace = saveSpace == null ? null : saveSpace.trim();
    }

    public String getWitnesses() {
        return witnesses;
    }

    public void setWitnesses(String witnesses) {
        this.witnesses = witnesses == null ? null : witnesses.trim();
    }

    public String getRecordPerson() {
        return recordPerson;
    }

    public void setRecordPerson(String recordPerson) {
        this.recordPerson = recordPerson == null ? null : recordPerson.trim();
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker == null ? null : maker.trim();
    }

    public String getTheLord() {
        return theLord;
    }

    public void setTheLord(String theLord) {
        this.theLord = theLord == null ? null : theLord.trim();
    }

    public String getWorkspce() {
        return workspce;
    }

    public void setWorkspce(String workspce) {
        this.workspce = workspce == null ? null : workspce.trim();
    }

    @Override
    public String toString() {
        return "DocInjurypictureInstructionEntity{" +
                "id=" + id +
                ", SerialId=" + SerialId +
                ", personId=" + personId +
                ", makeTime='" + makeTime + '\'' +
                ", makeSites='" + makeSites + '\'' +
                ", content='" + content + '\'' +
                ", isOriginal='" + isOriginal + '\'' +
                ", saveSpace='" + saveSpace + '\'' +
                ", witnesses='" + witnesses + '\'' +
                ", recordPerson='" + recordPerson + '\'' +
                ", maker='" + maker + '\'' +
                ", theLord='" + theLord + '\'' +
                ", workspce='" + workspce + '\'' +
                '}';
    }
}