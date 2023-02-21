package com.zhixin.zhfz.common.entity;

/**
 * Created by Jet on 2017/1/8.
 */
public class HKDoorEntity {

     private String doorId;
     private  int status;
    private String alarmTime;

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    @Override
    public String toString() {
        return "HKDoorEntity [doorId=" + doorId + ", status=" + status + ", alarmTime=" + alarmTime+"]";
    }
}
