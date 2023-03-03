package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class BaCaseEntity implements Serializable {


    private static final long serialVersionUID = -2100268027153765572L;

    private Integer id;
    private String ajmc;
    private String ajbh;//案件编号
    private Date afsj;//案发时间
    private String afdd;//案发地点
    private Integer ajlx;//0刑事 1行政 2,警情
    private Integer ab;//案由
    private Integer zbmjid;//主办民警
    private Integer xbmjId;//协办民警
    private Integer zbdwid;//主办单位ID
    private String jqh;//警情号
    private String zyaq;
    private Integer ajly;//案件来源0手动创建，1，警综导入
    private Integer cjr;//创建人
    private Date updatedTime;//更新时间
    private Date createdTime;
    private String uuid;
    private String afddCode;//保存案发地点的code(省*市*县*镇)
    private String areaName;
    private String abmc;
    private String involvedReason;
    private String certificateNo;
    private String personName;
    private Integer personId;
    private Integer belongId;

    public Integer getBelongId() {
        return belongId;
    }

    public void setBelongId(Integer belongId) {
        this.belongId = belongId;
    }

    public String getAbmc() {
        return abmc;
    }

    public void setAbmc(String abmc) {
        this.abmc = abmc;
    }

    public String getInvolvedReason() {
        return involvedReason;
    }

    public void setInvolvedReason(String involvedReason) {
        this.involvedReason = involvedReason;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public Date getAfsj() {
        return afsj;
    }

    public void setAfsj(Date afsj) {
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

    public Integer getZbmjid() {
        return zbmjid;
    }

    public void setZbmjid(Integer zbmjid) {
        this.zbmjid = zbmjid;
    }

    public Integer getXbmjId() {
        return xbmjId;
    }

    public void setXbmjId(Integer xbmjId) {
        this.xbmjId = xbmjId;
    }

    public Integer getZbdwid() {
        return zbdwid;
    }

    public void setZbdwid(Integer zbdwid) {
        this.zbdwid = zbdwid;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAfddCode() {
        return afddCode;
    }

    public void setAfddCode(String afddCode) {
        this.afddCode = afddCode;
    }

    @Override
    public String toString() {
        return "BaCaseEntity{" +
                "id=" + id +
                ", ajmc='" + ajmc + '\'' +
                ", ajbh='" + ajbh + '\'' +
                ", afsj=" + afsj +
                ", afdd='" + afdd + '\'' +
                ", ajlx=" + ajlx +
                ", ab=" + ab +
                ", zbmjid=" + zbmjid +
                ", xbmjId=" + xbmjId +
                ", zbdwid=" + zbdwid +
                ", jqh='" + jqh + '\'' +
                ", zyaq='" + zyaq + '\'' +
                ", ajly=" + ajly +
                ", cjr=" + cjr +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + createdTime +
                ", uuid='" + uuid + '\'' +
                ", afddCode='" + afddCode + '\'' +
                '}';
    }
}
