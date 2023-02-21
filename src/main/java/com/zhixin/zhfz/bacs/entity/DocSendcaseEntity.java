package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class DocSendcaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Long serialId;

    private String physicalCondition;

    private String lawProcedure;

    private String lawProcedureComment;

    private String basicFacts;

    private String evidenceCondition;

    private String dealSuggestion;

    private String leaderInstructions;

    private String undertaker1;

    private String undertaker2;

    private String telephone;

    private String legalMember;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public String getPhysicalCondition() {
        return physicalCondition;
    }

    public void setPhysicalCondition(String physicalCondition) {
        this.physicalCondition = physicalCondition == null ? null : physicalCondition.trim();
    }

    public String getLawProcedure() {
        return lawProcedure;
    }

    public void setLawProcedure(String lawProcedure) {
        this.lawProcedure = lawProcedure == null ? null : lawProcedure.trim();
    }

    public String getLawProcedureComment() {
        return lawProcedureComment;
    }

    public void setLawProcedureComment(String lawProcedureComment) {
        this.lawProcedureComment = lawProcedureComment == null ? null : lawProcedureComment.trim();
    }

    public String getBasicFacts() {
        return basicFacts;
    }

    public void setBasicFacts(String basicFacts) {
        this.basicFacts = basicFacts == null ? null : basicFacts.trim();
    }

    public String getEvidenceCondition() {
        return evidenceCondition;
    }

    public void setEvidenceCondition(String evidenceCondition) {
        this.evidenceCondition = evidenceCondition == null ? null : evidenceCondition.trim();
    }

    public String getDealSuggestion() {
        return dealSuggestion;
    }

    public void setDealSuggestion(String dealSuggestion) {
        this.dealSuggestion = dealSuggestion == null ? null : dealSuggestion.trim();
    }

    public String getLeaderInstructions() {
        return leaderInstructions;
    }

    public void setLeaderInstructions(String leaderInstructions) {
        this.leaderInstructions = leaderInstructions == null ? null : leaderInstructions.trim();
    }

    public String getUndertaker1() {
        return undertaker1;
    }

    public void setUndertaker1(String undertaker1) {
        this.undertaker1 = undertaker1 == null ? null : undertaker1.trim();
    }

    public String getUndertaker2() {
        return undertaker2;
    }

    public void setUndertaker2(String undertaker2) {
        this.undertaker2 = undertaker2 == null ? null : undertaker2.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getLegalMember() {
        return legalMember;
    }

    public void setLegalMember(String legalMember) {
        this.legalMember = legalMember == null ? null : legalMember.trim();
    }

    @Override
    public String toString() {
        return "DocSendcaseEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", physicalCondition='" + physicalCondition + '\'' +
                ", lawProcedure='" + lawProcedure + '\'' +
                ", lawProcedureComment='" + lawProcedureComment + '\'' +
                ", basicFacts='" + basicFacts + '\'' +
                ", evidenceCondition='" + evidenceCondition + '\'' +
                ", dealSuggestion='" + dealSuggestion + '\'' +
                ", leaderInstructions='" + leaderInstructions + '\'' +
                ", undertaker1='" + undertaker1 + '\'' +
                ", undertaker2='" + undertaker2 + '\'' +
                ", telephone='" + telephone + '\'' +
                ", legalMember='" + legalMember + '\'' +
                '}';
    }
}