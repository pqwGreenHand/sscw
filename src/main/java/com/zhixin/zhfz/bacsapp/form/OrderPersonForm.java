package com.zhixin.zhfz.bacsapp.form;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约登记
 */
public class OrderPersonForm implements Serializable {

    // person
    private Integer personId;

    private String personName;

    private String oldName;

    private String nickname;

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

    private String education;

    private String politic;

    private String jobTitle;

    private String job;

    private String mobile;

    private String telephone;

    private String email;

    private String qq;

    private String weixin;

    private String weibo;

    private String internetInfo;

    private String personNo;

    private Integer age;

    // order_person

    private Integer orderRequestId;

    private Integer orderPersonId;

    private Integer orderPersonStatus;

    private String personType;

    private Integer isJuvenile;// 是否成年人

    private Integer isDisease;// 是否有其他疾病

    private Integer isGuardian;// 是否有监护人

    private Integer isDiseaseProve; // 是否有疾病证明

    private Integer isGravida;// 是否孕妇

    private Integer gravidaMonth;// 怀孕月数

    private Integer isGravidaProve;// 是否有怀孕证明

    private String other;

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

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

    public Integer getIsGravidaProve() {
        return isGravidaProve;
    }

    public void setIsGravidaProve(Integer isGravidaProve) {
        this.isGravidaProve = isGravidaProve;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPolitic() {
        return politic;
    }

    public void setPolitic(String politic) {
        this.politic = politic;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getInternetInfo() {
        return internetInfo;
    }

    public void setInternetInfo(String internetInfo) {
        this.internetInfo = internetInfo;
    }

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getOrderPersonId() {
        return orderPersonId;
    }

    public void setOrderPersonId(Integer orderPersonId) {
        this.orderPersonId = orderPersonId;
    }

    @Override
    public String toString() {
        return "OrderPersonForm{" +
                "personId=" + personId +
                ", personName='" + personName + '\'' +
                ", oldName='" + oldName + '\'' +
                ", nickname='" + nickname + '\'' +
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
                ", education='" + education + '\'' +
                ", politic='" + politic + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", job='" + job + '\'' +
                ", mobile='" + mobile + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", weixin='" + weixin + '\'' +
                ", weibo='" + weibo + '\'' +
                ", internetInfo='" + internetInfo + '\'' +
                ", personNo='" + personNo + '\'' +
                ", age=" + age +
                ", orderRequestId=" + orderRequestId +
                ", orderPersonId=" + orderPersonId +
                ", orderPersonStatus=" + orderPersonStatus +
                ", personType='" + personType + '\'' +
                ", isJuvenile=" + isJuvenile +
                ", isDisease=" + isDisease +
                ", isGuardian=" + isGuardian +
                ", isDiseaseProve=" + isDiseaseProve +
                ", isGravida=" + isGravida +
                ", gravidaMonth=" + gravidaMonth +
                ", isGravidaProve=" + isGravidaProve +
                ", other='" + other + '\'' +
                '}';
    }
}
