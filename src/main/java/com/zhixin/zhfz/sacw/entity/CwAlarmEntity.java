package com.zhixin.zhfz.sacw.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 财务违规督查
 */
public class CwAlarmEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer involved_id; //物品ID
    private Integer label_id; //RFID标签ID
    private Integer device_id; //RFID激活器设备ID
    private Integer alarm_type; //告警类型
    private String alarm_name;   //告警名称
    private Date alarm_time;  //告警时间
    private Integer status;//状态 0未处理 1已处理
    private Date handler_time; //处理时间
    private String handler_content;//处理回复
    private Integer location_id;//区域ID
    private Integer warehouse_id;//仓库IDlabel_no


    private Date startTime;//开始日期
    private Date endTime;//结束日期
    private String label_no;   //标签编号
    private String caseNo;   //案件编号
    private String caseName;  //案件名称
    private String involvedName;//物品名称
    private String involvedTypeName;//物品类型名称
    private String involvedOwner;//物品持有人
    private String warehouseName;//仓库名称
    private String locationName;//区域名称

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLabel_no() {
        return label_no;
    }

    public void setLabel_no(String label_no) {
        this.label_no = label_no;
    }

    public String getInvolvedName() {
        return involvedName;
    }

    public void setInvolvedName(String involvedName) {
        this.involvedName = involvedName;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getInvolvedTypeName() {
        return involvedTypeName;
    }

    public void setInvolvedTypeName(String involvedTypeName) {
        this.involvedTypeName = involvedTypeName;
    }

    public String getInvolvedOwner() {
        return involvedOwner;
    }

    public void setInvolvedOwner(String involvedOwner) {
        this.involvedOwner = involvedOwner;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInvolved_id() {
        return involved_id;
    }

    public void setInvolved_id(Integer involved_id) {
        this.involved_id = involved_id;
    }

    public Integer getLabel_id() {
        return label_id;
    }

    public void setLabel_id(Integer label_id) {
        this.label_id = label_id;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public Integer getAlarm_type() {
        return alarm_type;
    }

    public void setAlarm_type(Integer alarm_type) {
        this.alarm_type = alarm_type;
    }

    public String getAlarm_name() {
        return alarm_name;
    }

    public void setAlarm_name(String alarm_name) {
        this.alarm_name = alarm_name;
    }

    public Date getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(Date alarm_time) {
        this.alarm_time = alarm_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getHandler_time() {
        return handler_time;
    }

    public void setHandler_time(Date handler_time) {
        this.handler_time = handler_time;
    }

    public String getHandler_content() {
        return handler_content;
    }

    public void setHandler_content(String handler_content) {
        this.handler_content = handler_content;
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    public Integer getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(Integer warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    @Override
    public String toString() {
        return "CwAlarmEntity{" +
                "id=" + id +
                ", involved_id=" + involved_id +
                ", label_id=" + label_id +
                ", device_id=" + device_id +
                ", alarm_type=" + alarm_type +
                ", alarm_name='" + alarm_name + '\'' +
                ", alarm_time=" + alarm_time +
                ", status=" + status +
                ", handler_time=" + handler_time +
                ", handler_content='" + handler_content + '\'' +
                ", location_id=" + location_id +
                ", warehouse_id=" + warehouse_id +
                ", caseNo='" + caseNo + '\'' +
                ", caseName='" + caseName + '\'' +
                ", involvedName='" + involvedName + '\'' +
                ", involvedTypeName='" + involvedTypeName + '\'' +
                ", involvedOwner='" + involvedOwner + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", locationName='" + locationName + '\'' +
                ", label_no='" + label_no + '\'' +
                '}';
    }

}
