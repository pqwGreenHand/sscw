package com.zhixin.zhfz.jxkp.form;

import java.io.Serializable;

/**
 * Created by wangly on 2018/5/6.
 */
public class BaseInformationForm implements Serializable{
    private String subCaseNo;
    private String subCaseName;
    private String subBar;
    private String subXyr;
    private String subLederIdA;
    private String subZcA;
    private String subHandleTime;
    private String subCpsx;
    private String subWorkTime;
    private String subCreaterId;
    private String subAjxz;
    private String subOrgId;
    private String subCaseDesc;
    private String subImplementation;
    private String subAccordwith;
    private String  subRemarks;
    private String lawerId;/*法制人员的id*/
    private String subScorevalue;
    private String pointsvalue;
    private String lawAccording;
    private String existEvidence;
    private String lawerOpinion;
    private String opUserId;
    private String opPid;
    public String getLawerOpinion() {
        return lawerOpinion;
    }
    public void setLawerOpinion(String lawerOpinion) {
        this.lawerOpinion = lawerOpinion;
    }
    public String getLawAccording() {
        return lawAccording;
    }

    public void setLawAccording(String lawAccording) {
        this.lawAccording = lawAccording;
    }

    public String getExistEvidence() {
        return existEvidence;
    }

    public void setExistEvidence(String existEvidence) {
        this.existEvidence = existEvidence;
    }

    public String getLawerId() {
        return lawerId;
    }

    public void setLawerId(String lawerId) {
        this.lawerId = lawerId;
    }

    public String getSubCaseNo() {
        return subCaseNo;
    }

    public void setSubCaseNo(String subCaseNo) {
        this.subCaseNo = subCaseNo;
    }

    public String getSubCaseName() {
        return subCaseName;
    }

    public void setSubCaseName(String subCaseName) {
        this.subCaseName = subCaseName;
    }

    public String getSubBar() {
        return subBar;
    }

    public void setSubBar(String subBar) {
        this.subBar = subBar;
    }

    public String getSubXyr() {
        return subXyr;
    }

    public void setSubXyr(String subXyr) {
        this.subXyr = subXyr;
    }

    public String getSubLederIdA() {
        return subLederIdA;
    }

    public void setSubLederIdA(String subLederIdA) {
        this.subLederIdA = subLederIdA;
    }

    public String getSubZcA() {
        return subZcA;
    }

    public void setSubZcA(String subZcA) {
        this.subZcA = subZcA;
    }

    public String getSubHandleTime() {
        return subHandleTime;
    }

    public void setSubHandleTime(String subHandleTime) {
        this.subHandleTime = subHandleTime;
    }

    public String getSubCpsx() {
        return subCpsx;
    }

    public void setSubCpsx(String subCpsx) {
        this.subCpsx = subCpsx;
    }

    public String getSubWorkTime() {
        return subWorkTime;
    }

    public void setSubWorkTime(String subWorkTime) {
        this.subWorkTime = subWorkTime;
    }

    public String getSubCreaterId() {
        return subCreaterId;
    }

    public void setSubCreaterId(String subCreaterId) {
        this.subCreaterId = subCreaterId;
    }

    public String getSubAjxz() {
        return subAjxz;
    }

    public void setSubAjxz(String subAjxz) {
        this.subAjxz = subAjxz;
    }

    public String getSubOrgId() {
        return subOrgId;
    }

    public void setSubOrgId(String subOrgId) {
        this.subOrgId = subOrgId;
    }

    public String getSubCaseDesc() {
        return subCaseDesc;
    }

    public void setSubCaseDesc(String subCaseDesc) {
        this.subCaseDesc = subCaseDesc;
    }

    public String getSubImplementation() {
        return subImplementation;
    }

    public void setSubImplementation(String subImplementation) {
        this.subImplementation = subImplementation;
    }

    public String getSubAccordwith() {
        return subAccordwith;
    }

    public void setSubAccordwith(String subAccordwith) {
        this.subAccordwith = subAccordwith;
    }

    public String getSubRemarks() {
        return subRemarks;
    }

    public void setSubRemarks(String subRemarks) {
        this.subRemarks = subRemarks;
    }

    public String getSubScorevalue() {
        return subScorevalue;
    }

    public void setSubScorevalue(String subScorevalue) {
        this.subScorevalue = subScorevalue;
    }

    public String getPointsvalue() {
        return pointsvalue;
    }

    public void setPointsvalue(String pointsvalue) {
        this.pointsvalue = pointsvalue;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    public String getOpPid() {
        return opPid;
    }

    public void setOpPid(String opPid) {
        this.opPid = opPid;
    }

    @Override
    public String toString() {
        return "BaseInformationForm{" +
                "subCaseNo='" + subCaseNo + '\'' +
                ", subCaseName='" + subCaseName + '\'' +
                ", subBar='" + subBar + '\'' +
                ", subXyr='" + subXyr + '\'' +
                ", subLederIdA='" + subLederIdA + '\'' +
                ", subZcA='" + subZcA + '\'' +
                ", subHandleTime='" + subHandleTime + '\'' +
                ", subCpsx='" + subCpsx + '\'' +
                ", subWorkTime='" + subWorkTime + '\'' +
                ", subCreaterId='" + subCreaterId + '\'' +
                ", subAjxz='" + subAjxz + '\'' +
                ", subOrgId='" + subOrgId + '\'' +
                ", subCaseDesc='" + subCaseDesc + '\'' +
                ", subImplementation='" + subImplementation + '\'' +
                ", subAccordwith='" + subAccordwith + '\'' +
                ", subRemarks='" + subRemarks + '\'' +
                ", lawerId='" + lawerId + '\'' +
                ", subScorevalue='" + subScorevalue + '\'' +
                ", pointsvalue='" + pointsvalue + '\'' +
                ", lawAccording='" + lawAccording + '\'' +
                ", existEvidence='" + existEvidence + '\'' +
                ", lawerOpinion='" + lawerOpinion + '\'' +
                ", opUserId='" + opUserId + '\'' +
                ", opPid='" + opPid + '\'' +
                '}';
    }
}
