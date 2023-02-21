package com.zhixin.zhfz.bacsapp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约登记
 */
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = -5321126181757063342L;

    // 预约
    private Integer orderRequestId;

    private String orderNo;

    private Integer orderUserId;

    private Integer maleCount; // 男性数量

    private Integer femaleCount;// 女性数量

    private Integer juvenilesCount;// 未成年数量

    private Integer specialCount;// 传染病数量

    private Integer otherCount;// 其他人数量

    private Integer type; //类型 0电话预约 1现场预约 2网上预约

    private Integer orderStatus;

    private Date planTime;

    private Date arriveTime;

    private Integer caseId;

    private Integer areaId;

    private Integer noterId;

    private String ajmc;

    private String ajbh;

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
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

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(Integer orderUserId) {
        this.orderUserId = orderUserId;
    }

    public Integer getMaleCount() {
        return maleCount;
    }

    public void setMaleCount(Integer maleCount) {
        this.maleCount = maleCount;
    }

    public Integer getFemaleCount() {
        return femaleCount;
    }

    public void setFemaleCount(Integer femaleCount) {
        this.femaleCount = femaleCount;
    }

    public Integer getJuvenilesCount() {
        return juvenilesCount;
    }

    public void setJuvenilesCount(Integer juvenilesCount) {
        this.juvenilesCount = juvenilesCount;
    }

    public Integer getSpecialCount() {
        return specialCount;
    }

    public void setSpecialCount(Integer specialCount) {
        this.specialCount = specialCount;
    }

    public Integer getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(Integer otherCount) {
        this.otherCount = otherCount;
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

    public Integer getNoterId() {
        return noterId;
    }

    public void setNoterId(Integer noterId) {
        this.noterId = noterId;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "orderRequestId=" + orderRequestId +
                ", orderNo='" + orderNo + '\'' +
                ", orderUserId=" + orderUserId +
                ", maleCount=" + maleCount +
                ", femaleCount=" + femaleCount +
                ", juvenilesCount=" + juvenilesCount +
                ", specialCount=" + specialCount +
                ", otherCount=" + otherCount +
                ", type=" + type +
                ", orderStatus=" + orderStatus +
                ", planTime=" + planTime +
                ", arriveTime=" + arriveTime +
                ", caseId=" + caseId +
                ", areaId=" + areaId +
                ", noterId=" + noterId +
                ", ajmc='" + ajmc + '\'' +
                ", ajbh='" + ajbh + '\'' +
                '}';
    }
}
