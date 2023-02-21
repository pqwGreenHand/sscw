package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 涉案摄像头
 */
public class SaCameraEntity implements Serializable {


    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String channel;
    private String ip;
    private String account;
    private String password;
    private Integer port;
    private String screen;
    private Integer type;
    private Integer download;//是否需要下载
    private String description;
    private String nvrName;
    private Integer nvrId;
    private String vender;
    private String areaName;
    private Integer areaId;
    private String roomName;
    private Integer roomId;
    private Date createdTime;
    private Date updatedTime;
    private String cameraNo;


    //涉案财物摄像头字段
    private Integer warehouseId;
    private Integer locationId;
    private String warehouseName;
    private String locationName;

    private String op_pid;
    private Integer op_user_id;

    public String getOp_pid() {
        return op_pid;
    }

    public void setOp_pid(String op_pid) {
        this.op_pid = op_pid;
    }

    public Integer getOp_user_id() {
        return op_user_id;
    }

    public void setOp_user_id(Integer op_user_id) {
        this.op_user_id = op_user_id;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNvrName() {
        return nvrName;
    }

    public void setNvrName(String nvrName) {
        this.nvrName = nvrName;
    }

    public Integer getNvrId() {
        return nvrId;
    }

    public void setNvrId(Integer nvrId) {
        this.nvrId = nvrId;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCameraNo() {
        return cameraNo;
    }

    public void setCameraNo(String cameraNo) {
        this.cameraNo = cameraNo;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public String toString() {
        return "SaCameraEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", channel='" + channel + '\'' +
                ", ip='" + ip + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                ", screen='" + screen + '\'' +
                ", type=" + type +
                ", download=" + download +
                ", description='" + description + '\'' +
                ", nvrName='" + nvrName + '\'' +
                ", nvrId=" + nvrId +
                ", vender='" + vender + '\'' +
                ", areaName='" + areaName + '\'' +
                ", areaId=" + areaId +
                ", roomName='" + roomName + '\'' +
                ", roomId=" + roomId +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", cameraNo='" + cameraNo + '\'' +
                ", warehouseId=" + warehouseId +
                ", locationId=" + locationId +
                ", warehouseName='" + warehouseName + '\'' +
                ", locationName='" + locationName + '\'' +
                '}';
    }
}
