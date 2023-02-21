package com.zhixin.zhfz.sacw.form;

import java.io.Serializable;

public class OutputForm implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -5592636311235050424L;

    private int lawCaseId;

    private int involvedId;

    private int involvedTypeId;

    private int inputTypeId;

    private int policeId;

    private int organizationId;

    private int shelfId;

    private int warehouseId;

    private int locationId;

    private int registerUserId;

    private int areaId;//保管场所ID

    private String handlerPerson;

    private String telephone;

    private int count;

    private String description;

    private int outputType;

    public int getLawCaseId() {
        return lawCaseId;
    }

    public void setLawCaseId(int lawCaseId) {
        this.lawCaseId = lawCaseId;
    }

    public int getInvolvedId() {
        return involvedId;
    }

    public void setInvolvedId(int involvedId) {
        this.involvedId = involvedId;
    }

    public int getInvolvedTypeId() {
        return involvedTypeId;
    }

    public void setInvolvedTypeId(int involvedTypeId) {
        this.involvedTypeId = involvedTypeId;
    }

    public int getInputTypeId() {
        return inputTypeId;
    }

    public void setInputTypeId(int inputTypeId) {
        this.inputTypeId = inputTypeId;
    }

    public int getPoliceId() {
        return policeId;
    }

    public void setPoliceId(int policeId) {
        this.policeId = policeId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(int registerUserId) {
        this.registerUserId = registerUserId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }


    public String getHandlerPerson() {
        return handlerPerson;
    }

    public void setHandlerPerson(String handlerPerson) {
        this.handlerPerson = handlerPerson;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOutputType() {
        return outputType;
    }

    public void setOutputType(int outputType) {
        this.outputType = outputType;
    }

    @Override
    public String toString() {
        return "OutputForm{" +
                "lawCaseId=" + lawCaseId +
                ", involvedId=" + involvedId +
                ", involvedTypeId=" + involvedTypeId +
                ", inputTypeId=" + inputTypeId +
                ", policeId=" + policeId +
                ", organizationId=" + organizationId +
                ", shelfId=" + shelfId +
                ", warehouseId=" + warehouseId +
                ", locationId=" + locationId +
                ", registerUserId=" + registerUserId +
                ", areaId=" + areaId +
                ", handlerPerson='" + handlerPerson + '\'' +
                ", telephone='" + telephone + '\'' +
                ", count=" + count +
                ", description='" + description + '\'' +
                ", outputType=" + outputType +
                '}';
    }
}
