package com.zhixin.zhfz.sacw.form;

import java.io.Serializable;

public class InputForm implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -5592636311235050424L;

    private long recordId;
    private int lawCaseId;

    private int involvedId;

    private int involvedTypeId;

    private int inputTypeId;

    private int policeId;

    private int shelfId;

    private int warehouseId;

    private int locationId;

    private int registerUserId;

    private int areaId;//保管场所ID

    private int labelId;//电子标签

    private String labelNo;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

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

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public String getLabelNo() {
        return labelNo;
    }

    public void setLabelNo(String labelNo) {
        this.labelNo = labelNo;
    }


    @Override
    public String toString() {
        return "InputForm{" +
                "recordId=" + recordId +
                ", lawCaseId=" + lawCaseId +
                ", involvedId=" + involvedId +
                ", involvedTypeId=" + involvedTypeId +
                ", inputTypeId=" + inputTypeId +
                ", policeId=" + policeId +
                ", shelfId=" + shelfId +
                ", warehouseId=" + warehouseId +
                ", locationId=" + locationId +
                ", registerUserId=" + registerUserId +
                ", areaId=" + areaId +
                ", labelId=" + labelId +
                ", labelNo='" + labelNo + '\'' +
                '}';
    }
}
