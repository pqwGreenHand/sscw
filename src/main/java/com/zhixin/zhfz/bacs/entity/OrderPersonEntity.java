package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderPersonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer orderRequestId;

    private Integer personId;

    private Integer status;

    private String personType;

    private Integer isJuvenile;

    private Integer isGravida;

    private Integer isGravidaProve;

    private String gravidaMonth;

    private Integer isDisease;

    private Integer isDiseaseProve;
    

    private String other;
    private String orderUserId;
    private String type;
    private Integer shzt;

    private Integer isGuardian;
    private Integer areaId;

    //链表
    private String name;
    private Integer certificateTypeId;
    private String certificateNo;
    private Integer sex;
    private Date createdTime;
    private Date updatedTime;
    private int country;
    private int nation;
    private String jzyms;
    private Integer jkb;
    private String sfcrgfxdq;

    private String zjdz;
    private String zhdz;
    private Integer sfsjyqldaj;
    private String xyrtw;
    private String zjfjrq;
    private String mgsf;
    private String j3gysfyjjcgqk;
    private Integer sfymjjcs;

    private String opPid;
    private Integer opUserId;

    private String ajbh;

    private String rybh;

    private String ajmc;
    private String shjg;

    public String getZjdz() {
        return zjdz;
    }

    public void setZjdz(String zjdz) {
        this.zjdz = zjdz;
    }

    public String getZhdz() {
        return zhdz;
    }

    public void setZhdz(String zhdz) {
        this.zhdz = zhdz;
    }

    public Integer getSfsjyqldaj() {
        return sfsjyqldaj;
    }

    public void setSfsjyqldaj(Integer sfsjyqldaj) {
        this.sfsjyqldaj = sfsjyqldaj;
    }

    public String getXyrtw() {
        return xyrtw;
    }

    public void setXyrtw(String xyrtw) {
        this.xyrtw = xyrtw;
    }

    public String getZjfjrq() {
        return zjfjrq;
    }

    public void setZjfjrq(String zjfjrq) {
        this.zjfjrq = zjfjrq;
    }

    public String getMgsf() {
        return mgsf;
    }

    public void setMgsf(String mgsf) {
        this.mgsf = mgsf;
    }


    public Integer getSfymjjcs() {
        return sfymjjcs;
    }

    public void setSfymjjcs(Integer sfymjjcs) {
        this.sfymjjcs = sfymjjcs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getShjg() {
        return shjg;
    }

    public void setShjg(String shjg) {
        this.shjg = shjg;
    }

    public Integer getShzt() {
        return shzt;
    }

    public void setShzt(Integer shzt) {
        this.shzt = shzt;
    }

    public String getJzyms() {
        return jzyms;
    }

    @Override
    public String toString() {
        return "OrderPersonEntity{" +
                "id=" + id +
                ", orderRequestId=" + orderRequestId +
                ", personId=" + personId +
                ", status=" + status +
                ", personType='" + personType + '\'' +
                ", isJuvenile=" + isJuvenile +
                ", isGravida=" + isGravida +
                ", isGravidaProve=" + isGravidaProve +
                ", gravidaMonth='" + gravidaMonth + '\'' +
                ", isDisease=" + isDisease +
                ", isDiseaseProve=" + isDiseaseProve +
                ", other='" + other + '\'' +
                ", orderUserId='" + orderUserId + '\'' +
                ", type='" + type + '\'' +
                ", shzt=" + shzt +
                ", isGuardian=" + isGuardian +
                ", areaId=" + areaId +
                ", name='" + name + '\'' +
                ", certificateTypeId=" + certificateTypeId +
                ", certificateNo='" + certificateNo + '\'' +
                ", sex=" + sex +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", country=" + country +
                ", nation=" + nation +
                ", jzyms='" + jzyms + '\'' +
                ", jkb=" + jkb +
                ", sfcrgfxdq=" + sfcrgfxdq +
                ", zjdz='" + zjdz + '\'' +
                ", zhdz='" + zhdz + '\'' +
                ", sfsjyqldaj=" + sfsjyqldaj +
                ", xyrtw='" + xyrtw + '\'' +
                ", zjfjrq='" + zjfjrq + '\'' +
                ", mgsf='" + mgsf + '\'' +
                ", j3gysfyjjcgqk=" + j3gysfyjjcgqk +
                ", sfymjjcs=" + sfymjjcs +
                ", opPid='" + opPid + '\'' +
                ", opUserId=" + opUserId +
                ", ajbh='" + ajbh + '\'' +
                ", rybh='" + rybh + '\'' +
                ", ajmc='" + ajmc + '\'' +
                ", shjg='" + shjg + '\'' +
                '}';
    }

    public void setJzyms(String jzyms) {
        this.jzyms = jzyms;
    }

    public Integer getJkb() {
        return jkb;
    }

    public void setJkb(Integer jkb) {
        this.jkb = jkb;
    }

    public String getSfcrgfxdq() {
        return sfcrgfxdq;
    }

    public void setSfcrgfxdq(String sfcrgfxdq) {
        this.sfcrgfxdq = sfcrgfxdq;
    }

    public String getJ3gysfyjjcgqk() {
        return j3gysfyjjcgqk;
    }

    public void setJ3gysfyjjcgqk(String j3gysfyjjcgqk) {
        this.j3gysfyjjcgqk = j3gysfyjjcgqk;
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getOpPid() {
        return opPid;
    }

    public void setOpPid(String opPid) {
        this.opPid = opPid;
    }

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCertificateTypeId() {
        return certificateTypeId;
    }

    public void setCertificateTypeId(Integer certificateTypeId) {
        this.certificateTypeId = certificateTypeId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getNation() {
        return nation;
    }

    public void setNation(int nation) {
        this.nation = nation;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderRequestId() {
        return orderRequestId;
    }

    public void setOrderRequestId(Integer orderRequestId) {
        this.orderRequestId = orderRequestId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public Integer getIsJuvenile() {
        return isJuvenile;
    }

    public void setIsJuvenile(Integer isJuvenile) {
        this.isJuvenile = isJuvenile;
    }

    public Integer getIsGravida() {
        return isGravida;
    }

    public void setIsGravida(Integer isGravida) {
        this.isGravida = isGravida;
    }

    public Integer getIsGravidaProve() {
        return isGravidaProve;
    }

    public void setIsGravidaProve(Integer isGravidaProve) {
        this.isGravidaProve = isGravidaProve;
    }

    public String getGravidaMonth() {
        return gravidaMonth;
    }

    public void setGravidaMonth(String gravidaMonth) {
        this.gravidaMonth = gravidaMonth;
    }

    public Integer getIsDisease() {
        return isDisease;
    }

    public void setIsDisease(Integer isDisease) {
        this.isDisease = isDisease;
    }

    public Integer getIsDiseaseProve() {
        return isDiseaseProve;
    }

    public void setIsDiseaseProve(Integer isDiseaseProve) {
        this.isDiseaseProve = isDiseaseProve;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Integer getIsGuardian() {
        return isGuardian;
    }

    public void setIsGuardian(Integer isGuardian) {
        this.isGuardian = isGuardian;
    }

}