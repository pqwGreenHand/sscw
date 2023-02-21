package com.zhixin.zhfz.common.form;

import com.zhixin.zhfz.common.entity.UserEntity;

import java.util.Date;
import java.util.List;

public class CaseForm implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String ajmc;

    private String ajbh;

    private String afsj;

    private String afdd;

    private Integer ajlx;

    private Integer ab;

    private Integer zbmjId;

    private String xbmjId;

    private Integer zbdwId;

    private String jqh;

    private String zyaq;

    private Integer ajly;

    private Integer cjr;

    private Date updatedTime;

    private Date createdTime;

    private String zbdwName;

    private String involvedReasonText;

    private String zbmjName;

    private String xbmjName;

    private String afddCode;

    private Integer warehouseId;

    private String xbmjIds;

    private String zbmjCertificateNo;

    private String involvedAddress;
    //协办民警姓名
    private String xbmjXm;
    //主办民警姓名
    private String zbmjxm;
    //主办民警警号
    private String zbmjjh;
    //主办单位名称
    private String zbdwmc;
    //协办民警警号
    private String xbmjjh;
    //案别名称
    private String abmc;
    // 案件状态
    private String ajzt;

    public String getAjzt() {
        return ajzt;
    }

    public void setAjzt(String ajzt) {
        this.ajzt = ajzt;
    }

    public String getZbmjCertificateNo() {
        return zbmjCertificateNo;
    }

    public void setZbmjCertificateNo(String zbmjCertificateNo) {
        this.zbmjCertificateNo = zbmjCertificateNo;
    }

    public String getInvolvedAddress() {
        return involvedAddress;
    }

    public void setInvolvedAddress(String involvedAddress) {
        this.involvedAddress = involvedAddress;
    }

    public String getZbmjName() {
        return zbmjName;
    }

    public void setZbmjName(String zbmjName) {
        this.zbmjName = zbmjName;
    }

    public String getXbmjName() {
        return xbmjName;
    }

    public void setXbmjName(String xbmjName) {
        this.xbmjName = xbmjName;
    }

    public String getInvolvedReasonText() {
        return involvedReasonText;
    }

    public void setInvolvedReasonText(String involvedReasonText) {
        this.involvedReasonText = involvedReasonText;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    public String getAfsj() {
        return afsj;
    }

    public void setAfsj(String afsj) {
        this.afsj = afsj;
    }

    public String getAfdd() {
        return afdd;
    }

    public void setAfdd(String afdd) {
        this.afdd = afdd;
    }

    public Integer getAjlx() {
        return ajlx;
    }

    public void setAjlx(Integer ajlx) {
        this.ajlx = ajlx;
    }

    public Integer getAb() {
        return ab;
    }

    public void setAb(Integer ab) {
        this.ab = ab;
    }

    public Integer getZbmjId() {
        return zbmjId;
    }

    public void setZbmjId(Integer zbmjId) {
        this.zbmjId = zbmjId;
    }

    public String getXbmjId() {
        return xbmjId;
    }

    public void setXbmjId(String xbmjId) {
        this.xbmjId = xbmjId;
    }

    public Integer getZbdwId() {
        return zbdwId;
    }

    public void setZbdwId(Integer zbdwId) {
        this.zbdwId = zbdwId;
    }

    public String getJqh() {
        return jqh;
    }

    public void setJqh(String jqh) {
        this.jqh = jqh;
    }

    public String getZyaq() {
        return zyaq;
    }

    public void setZyaq(String zyaq) {
        this.zyaq = zyaq;
    }

    public Integer getAjly() {
        return ajly;
    }

    public void setAjly(Integer ajly) {
        this.ajly = ajly;
    }

    public Integer getCjr() {
        return cjr;
    }

    public void setCjr(Integer cjr) {
        this.cjr = cjr;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getZbdwName() {
        return zbdwName;
    }

    public void setZbdwName(String zbdwName) {
        this.zbdwName = zbdwName;
    }

    public String getAfddCode() {
        return afddCode;
    }

    public void setAfddCode(String afddCode) {
        this.afddCode = afddCode;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getXbmjIds() {
        return xbmjIds;
    }

    public void setXbmjIds(String xbmjIds) {
        this.xbmjIds = xbmjIds;
    }

    public String getXbmjXm() {
        return xbmjXm;
    }

    public void setXbmjXm(String xbmjXm) {
        this.xbmjXm = xbmjXm;
    }

    public String getZbmjxm() {
        return zbmjxm;
    }

    public void setZbmjxm(String zbmjxm) {
        this.zbmjxm = zbmjxm;
    }

    public String getZbmjjh() {
        return zbmjjh;
    }

    public void setZbmjjh(String zbmjjh) {
        this.zbmjjh = zbmjjh;
    }

    public String getZbdwmc() {
        return zbdwmc;
    }

    public void setZbdwmc(String zbdwmc) {
        this.zbdwmc = zbdwmc;
    }

    public String getXbmjjh() {
        return xbmjjh;
    }

    public void setXbmjjh(String xbmjjh) {
        this.xbmjjh = xbmjjh;
    }

    public String getAbmc() {
        return abmc;
    }

    public void setAbmc(String abmc) {
        this.abmc = abmc;
    }

    @Override
    public String toString() {
        return "CaseForm{" +
                "id=" + id +
                ", ajmc='" + ajmc + '\'' +
                ", ajbh='" + ajbh + '\'' +
                ", afsj='" + afsj + '\'' +
                ", afdd='" + afdd + '\'' +
                ", ajlx=" + ajlx +
                ", ab=" + ab +
                ", zbmjId=" + zbmjId +
                ", xbmjId='" + xbmjId + '\'' +
                ", zbdwId=" + zbdwId +
                ", jqh='" + jqh + '\'' +
                ", zyaq='" + zyaq + '\'' +
                ", ajly=" + ajly +
                ", cjr=" + cjr +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + createdTime +
                ", zbdwName='" + zbdwName + '\'' +
                ", involvedReasonText='" + involvedReasonText + '\'' +
                ", zbmjName='" + zbmjName + '\'' +
                ", xbmjName='" + xbmjName + '\'' +
                ", afddCode='" + afddCode + '\'' +
                ", warehouseId=" + warehouseId +
                ", xbmjIds='" + xbmjIds + '\'' +
                ", zbmjCertificateNo='" + zbmjCertificateNo + '\'' +
                ", involvedAddress='" + involvedAddress + '\'' +
                ", xbmjXm='" + xbmjXm + '\'' +
                ", zbmjxm='" + zbmjxm + '\'' +
                ", zbmjjh='" + zbmjjh + '\'' +
                ", zbdwmc='" + zbdwmc + '\'' +
                ", xbmjjh='" + xbmjjh + '\'' +
                ", abmc='" + abmc + '\'' +
                '}';
    }
}
