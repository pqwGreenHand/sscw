package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;
import java.util.Date;

public class ArraignForm implements Serializable {

    private static final long serialVersionUID = -7747652732206722466L;

    private Long id;
    private Long serialId;
    private Long roomId;
    private Long waitingRoomId;
    private Integer status;
    private Long policeId;
    private Integer recordId;

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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getWaitingRoomId() {
        return waitingRoomId;
    }

    public void setWaitingRoomId(Long waitingRoomId) {
        this.waitingRoomId = waitingRoomId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPoliceId() {
        return policeId;
    }

    public void setPoliceId(Long policeId) {
        this.policeId = policeId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
}
