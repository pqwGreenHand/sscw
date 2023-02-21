package com.zhixin.zhfz.bacsapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 入区登记
 */
public class EntranceEntity implements Serializable {

    private static final long serialVersionUID = 7836848432720489513L;

    private Integer id;

    private Integer serialId;

    private String serialNo;

    private String uuid;

    private Integer status;

    private Integer type;

    private Integer areaId;

    private Integer caseId;

    private String inPhotoName;

    private String outPhotoName;

    private Date inTime;

    private Date outTime;

    private String personName;

    // 预约案件
    private Integer orderRequestId;

    private String orderNo;

    private Integer orderStatus;

    private Date planTime;

    private String ajmc;

    private String ajbh;

    private String areaName;

    private String policeName;

    private String mobile;

    private String zbmj;

    private String xbmj;

    private Integer zbmjId;

    private Integer xbmjId;

    // 人员
    private Integer personId;

    private Integer sex;

    private Integer certificateTypeId;

    private String certificateNo;

    private String exhibitLocker;

    private String exhibitGet;

    private String belongLocker;

    private String belongGet;

    private String caseNature;

    private String entranceProcedure;

    public String getEntranceProcedure() {
        return entranceProcedure;
    }

    public void setEntranceProcedure(String entranceProcedure) {
        this.entranceProcedure = entranceProcedure;
    }

    public String getCaseNature() {
        return caseNature;
    }

    public void setCaseNature(String caseNature) {
        this.caseNature = caseNature;
    }

    public String getExhibitLocker() {
        return exhibitLocker;
    }

    public void setExhibitLocker(String exhibitLocker) {
        this.exhibitLocker = exhibitLocker;
    }

    public String getExhibitGet() {
        return exhibitGet;
    }

    public void setExhibitGet(String exhibitGet) {
        this.exhibitGet = exhibitGet;
    }

    public String getBelongLocker() {
        return belongLocker;
    }

    public void setBelongLocker(String belongLocker) {
        this.belongLocker = belongLocker;
    }

    public String getBelongGet() {
        return belongGet;
    }

    public void setBelongGet(String belongGet) {
        this.belongGet = belongGet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getInPhotoName() {
        return inPhotoName;
    }

    public void setInPhotoName(String inPhotoName) {
        this.inPhotoName = inPhotoName;
    }

    public String getOutPhotoName() {
        return outPhotoName;
    }

    public void setOutPhotoName(String outPhotoName) {
        this.outPhotoName = outPhotoName;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getOrderRequestId() {
        return orderRequestId;
    }

    public void setOrderRequestId(Integer orderRequestId) {
        this.orderRequestId = orderRequestId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getZbmj() {
        return zbmj;
    }

    public void setZbmj(String zbmj) {
        this.zbmj = zbmj;
    }

    public String getXbmj() {
        return xbmj;
    }

    public void setXbmj(String xbmj) {
        this.xbmj = xbmj;
    }

    public Integer getZbmjId() {
        return zbmjId;
    }

    public void setZbmjId(Integer zbmjId) {
        this.zbmjId = zbmjId;
    }

    public Integer getXbmjId() {
        return xbmjId;
    }

    public void setXbmjId(Integer xbmjId) {
        this.xbmjId = xbmjId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getCertificateTypeId() {
        return certificateTypeId;
    }

    public void setCertificateTypeId(Integer certificateTypeId) {
        this.certificateTypeId = certificateTypeId;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    @Override
    public String toString() {
        return "EntranceEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", serialNo='" + serialNo + '\'' +
                ", uuid='" + uuid + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", areaId=" + areaId +
                ", caseId=" + caseId +
                ", inPhotoName='" + inPhotoName + '\'' +
                ", outPhotoName='" + outPhotoName + '\'' +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", personName='" + personName + '\'' +
                ", orderRequestId=" + orderRequestId +
                ", orderNo='" + orderNo + '\'' +
                ", orderStatus=" + orderStatus +
                ", planTime=" + planTime +
                ", ajmc='" + ajmc + '\'' +
                ", areaName='" + areaName + '\'' +
                ", policeName='" + policeName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", zbmj='" + zbmj + '\'' +
                ", xbmj='" + xbmj + '\'' +
                ", zbmjId=" + zbmjId +
                ", xbmjId=" + xbmjId +
                ", personId=" + personId +
                ", sex=" + sex +
                ", certificateTypeId=" + certificateTypeId +
                ", certificateNo='" + certificateNo + '\'' +
                ", exhibitLocker='" + exhibitLocker + '\'' +
                ", exhibitGet='" + exhibitGet + '\'' +
                ", belongLocker='" + belongLocker + '\'' +
                ", belongGet='" + belongGet + '\'' +
                '}';
    }
}
