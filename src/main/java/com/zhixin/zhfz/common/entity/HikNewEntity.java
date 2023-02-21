package com.zhixin.zhfz.common.entity;

import java.io.Serializable;

public class HikNewEntity implements Serializable{

    /** Serial UID */
    private static final long serialVersionUID = 1L;

    private String recordPlanUuid;//

    private String planName;

    private int planType;

    private String cameraUuid;

    public String getRecordPlanUuid() {
        return recordPlanUuid;
    }

    public void setRecordPlanUuid(String recordPlanUuid) {
        this.recordPlanUuid = recordPlanUuid;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getPlanType() {
        return planType;
    }

    public void setPlanType(int planType) {
        this.planType = planType;
    }

    public String getCameraUuid() {
        return cameraUuid;
    }

    public void setCameraUuid(String cameraUuid) {
        this.cameraUuid = cameraUuid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"recordPlanUuid\":\"")
                .append(recordPlanUuid).append('\"');
        sb.append(",\"planName\":\"")
                .append(planName).append('\"');
        sb.append(",\"planType\":\"")
                .append(planType).append('\"');
        sb.append(",\"cameraUuid\":\"")
                .append(cameraUuid).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
