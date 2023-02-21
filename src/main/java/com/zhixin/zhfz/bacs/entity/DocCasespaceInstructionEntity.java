package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class DocCasespaceInstructionEntity implements Serializable{
    /**
	 * 作案地点说明
	 */
	private static final long serialVersionUID = 6152104193431769792L;

	private Integer id;

    private Long serialId;

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

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    @Override
    public String toString() {
        return "DocCasespaceInstructionEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
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