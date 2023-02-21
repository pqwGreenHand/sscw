package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

/**
 * 告警类型数量
 */
public class AlarmTypeCoutEntity implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer alarmTypeId;
    private Integer alarmCount;
    private String alarmName;

    public Integer getAlarmTypeId() {
        return alarmTypeId;
    }

    public void setAlarmTypeId(Integer alarmTypeId) {
        this.alarmTypeId = alarmTypeId;
    }

    public Integer getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(Integer alarmCount) {
        this.alarmCount = alarmCount;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    @Override
    public String toString() {
        return "AlarmTypeCoutEntity{" +
                "alarmTypeId=" + alarmTypeId +
                ", alarmCount=" + alarmCount +
                ", alarmName='" + alarmName + '\'' +
                '}';
    }
}
