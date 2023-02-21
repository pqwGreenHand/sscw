package com.zhixin.zhfz.bacsapp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约登记
 */
public class OrderPersonEntity implements Serializable {

    private static final long serialVersionUID = -1464138917205883110L;

    // person
    private String personName;

    private Integer sex;

    private Date birth;

    private Integer certificateTypeId;

    private String certificateNo;

    private Integer country;

    private Integer nation;

    private Integer officer;

    private String censusRegister;

    private String address;

    private Integer caseId;

    private Integer areaId;

    private String uuid;

    // order_person

    private Integer orderRequestId;

    private Integer personId;

    private Integer orderPersonStatus;

    private Integer personType;

    private Integer isJuvenile;// 是否成年人

    private Integer isDisease;// 是否有其他疾病

    private Integer isGuardian;// 是否有监护人

    private Integer isDiseaseProve; // 是否有疾病证明

    private Integer isGravida;// 是否孕妇

    private Integer gravidaMonth;// 怀孕月数

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
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

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

    public Integer getOfficer() {
        return officer;
    }

    public void setOfficer(Integer officer) {
        this.officer = officer;
    }

    public String getCensusRegister() {
        return censusRegister;
    }

    public void setCensusRegister(String censusRegister) {
        this.censusRegister = censusRegister;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Integer getOrderPersonStatus() {
        return orderPersonStatus;
    }

    public void setOrderPersonStatus(Integer orderPersonStatus) {
        this.orderPersonStatus = orderPersonStatus;
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    public Integer getIsJuvenile() {
        return isJuvenile;
    }

    public void setIsJuvenile(Integer isJuvenile) {
        this.isJuvenile = isJuvenile;
    }

    public Integer getIsDisease() {
        return isDisease;
    }

    public void setIsDisease(Integer isDisease) {
        this.isDisease = isDisease;
    }

    public Integer getIsGuardian() {
        return isGuardian;
    }

    public void setIsGuardian(Integer isGuardian) {
        this.isGuardian = isGuardian;
    }

    public Integer getIsDiseaseProve() {
        return isDiseaseProve;
    }

    public void setIsDiseaseProve(Integer isDiseaseProve) {
        this.isDiseaseProve = isDiseaseProve;
    }

    public Integer getIsGravida() {
        return isGravida;
    }

    public void setIsGravida(Integer isGravida) {
        this.isGravida = isGravida;
    }

    public Integer getGravidaMonth() {
        return gravidaMonth;
    }

    public void setGravidaMonth(Integer gravidaMonth) {
        this.gravidaMonth = gravidaMonth;
    }

    @Override
    public String toString() {
        return "OrderPersonEntity{" +
                "personName='" + personName + '\'' +
                ", sex=" + sex +
                ", birth=" + birth +
                ", certificateTypeId=" + certificateTypeId +
                ", certificateNo='" + certificateNo + '\'' +
                ", country=" + country +
                ", nation=" + nation +
                ", officer=" + officer +
                ", censusRegister='" + censusRegister + '\'' +
                ", address='" + address + '\'' +
                ", caseId=" + caseId +
                ", areaId=" + areaId +
                ", uuid='" + uuid + '\'' +
                ", orderRequestId=" + orderRequestId +
                ", personId=" + personId +
                ", orderPersonStatus=" + orderPersonStatus +
                ", personType=" + personType +
                ", isJuvenile=" + isJuvenile +
                ", isDisease=" + isDisease +
                ", isGuardian=" + isGuardian +
                ", isDiseaseProve=" + isDiseaseProve +
                ", isGravida=" + isGravida +
                ", gravidaMonth=" + gravidaMonth +
                '}';
    }
}
