package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

/*
 * 三室底联录像说明
 * */
public class DocVideoInstructionEntity implements Serializable {

	private static final long serialVersionUID = -7991838396791787739L;

	private Integer id;

    private Long serialId;

    private Long personId;

    private String informationContent;

    private String extractTime;

    private String extractSites;

    private String extractMethod;

    private String vedioResouce;

    private String isOriginal;

    private String reason;

    private String saveSpace;

    private String witnesses;

    private String maker;

    private String policemen;

    private String workspce;
    
    private String fomate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSerialId() {
        return serialId;
    }

    @Override
    public String toString() {
        return "DocVideoInstructionEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", personId=" + personId +
                ", informationContent='" + informationContent + '\'' +
                ", extractTime='" + extractTime + '\'' +
                ", extractSites='" + extractSites + '\'' +
                ", extractMethod='" + extractMethod + '\'' +
                ", vedioResouce='" + vedioResouce + '\'' +
                ", isOriginal='" + isOriginal + '\'' +
                ", reason='" + reason + '\'' +
                ", saveSpace='" + saveSpace + '\'' +
                ", witnesses='" + witnesses + '\'' +
                ", maker='" + maker + '\'' +
                ", policemen='" + policemen + '\'' +
                ", workspce='" + workspce + '\'' +
                ", fomate='" + fomate + '\'' +
                '}';
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

    public String getInformationContent() {
        return informationContent;
    }

    public void setInformationContent(String informationContent) {
        this.informationContent = informationContent == null ? null : informationContent.trim();
    }

    public String getExtractTime() {
        return extractTime;
    }

    public void setExtractTime(String extractTime) {
        this.extractTime = extractTime == null ? null : extractTime.trim();
    }

    public String getExtractSites() {
        return extractSites;
    }

    public void setExtractSites(String extractSites) {
        this.extractSites = extractSites == null ? null : extractSites.trim();
    }

    public String getExtractMethod() {
        return extractMethod;
    }

    public void setExtractMethod(String extractMethod) {
        this.extractMethod = extractMethod == null ? null : extractMethod.trim();
    }

    public String getVedioResouce() {
        return vedioResouce;
    }

    public void setVedioResouce(String vedioResouce) {
        this.vedioResouce = vedioResouce == null ? null : vedioResouce.trim();
    }

    public String getIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(String isOriginal) {
        this.isOriginal = isOriginal == null ? null : isOriginal.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
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

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker == null ? null : maker.trim();
    }

    public String getPolicemen() {
        return policemen;
    }

    public void setPolicemen(String policemen) {
        this.policemen = policemen == null ? null : policemen.trim();
    }

    public String getWorkspce() {
        return workspce;
    }

    public void setWorkspce(String workspce) {
        this.workspce = workspce == null ? null : workspce.trim();
    }

	public String getFomate() {
		return fomate;
	}

	public void setFomate(String fomate) {
		this.fomate = fomate;
	}
}