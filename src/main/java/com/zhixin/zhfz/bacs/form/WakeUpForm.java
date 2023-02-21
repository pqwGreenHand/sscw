package com.zhixin.zhfz.bacs.form;

/**
 * @program: zhfz
 * @description:
 * @author: jzw
 * @create: 2019-02-27 16:52
 **/

public class WakeUpForm {

    private Long id;

    private Long serialId;

    private Long registerUserId;

    private Long inUserId;

    private Long outUserId;

    private Long caseId;

    private Long areaId;

    private Long personId;

    private String inDescription;

    private String outDescription;



    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("WakeUpForm [");
        buffer.append("id = ").append(id)
                .append(", serialId = ").append(serialId)
                .append(", registerUserId = ").append(registerUserId)
                .append(", inUserId = ").append(inUserId)
                .append(", outUserId = ").append(outUserId)
                .append(", personId = ").append(personId)
                .append(", caseId = ").append(caseId)
                .append(", areaId = ").append(areaId)
                .append(", inDdescription = ").append(inDescription)
                .append(", outDdescription = ").append(outDescription)
                .append("]");

        return buffer.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public Long getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(Long registerUserId) {
        this.registerUserId = registerUserId;
    }

    public Long getInUserId() {
        return inUserId;
    }

    public void setInUserId(Long inUserId) {
        this.inUserId = inUserId;
    }

    public Long getOutUserId() {
        return outUserId;
    }

    public void setOutUserId(Long outUserId) {
        this.outUserId = outUserId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getInDescription() {
        return inDescription;
    }

    public void setInDescription(String inDescription) {
        this.inDescription = inDescription;
    }

    public String getOutDescription() {
        return outDescription;
    }

    public void setOutDescription(String outDescription) {
        this.outDescription = outDescription;
    }
}
