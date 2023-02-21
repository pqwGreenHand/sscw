package com.zhixin.zhfz.jxkp.entity;

import java.util.List;

public class EvaluationEntity_tqy {
	private int id;
	private String caseNo;
	private String caseName;
	private String informant;
	private String suspect;
	private int caseType;
	private String case_desc;
	private int batch_item_id;
	private int oraganizationId;
	private String orgname;
	private int leader_id;
	private int police_id;
	private String implementation;
	private String accordwith;
	private String remarks;
	private int scorevalue;
	private int pointsvalue;
	
	
	private PoliceIdLoginnameRealname zb;
	private PoliceIdLoginnameRealname ld;
	private List<PoliceIdLoginnameRealname> xb;
	
	
	public String getOrgname() {
		return orgname;
	}



	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}



	public int getScorevalue() {
		return scorevalue;
	}



	public void setScorevalue(int scorevalue) {
		this.scorevalue = scorevalue;
	}



	public int getPointsvalue() {
		return pointsvalue;
	}



	public void setPointsvalue(int pointsvalue) {
		this.pointsvalue = pointsvalue;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
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



	public String getInformant() {
		return informant;
	}



	public void setInformant(String informant) {
		this.informant = informant;
	}



	public String getSuspect() {
		return suspect;
	}



	public void setSuspect(String suspect) {
		this.suspect = suspect;
	}



	public int getCaseType() {
		return caseType;
	}



	public void setCaseType(int caseType) {
		this.caseType = caseType;
	}



	public String getCase_desc() {
		return case_desc;
	}



	public void setCase_desc(String case_desc) {
		this.case_desc = case_desc;
	}



	public int getBatch_item_id() {
		return batch_item_id;
	}



	public void setBatch_item_id(int batch_item_id) {
		this.batch_item_id = batch_item_id;
	}



	public int getOraganizationId() {
		return oraganizationId;
	}



	public void setOraganizationId(int oraganizationId) {
		this.oraganizationId = oraganizationId;
	}



	public int getLeader_id() {
		return leader_id;
	}



	public void setLeader_id(int leader_id) {
		this.leader_id = leader_id;
	}



	public int getPolice_id() {
		return police_id;
	}



	public void setPolice_id(int police_id) {
		this.police_id = police_id;
	}



	public String getImplementation() {
		return implementation;
	}



	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}



	public String getAccordwith() {
		return accordwith;
	}



	public void setAccordwith(String accordwith) {
		this.accordwith = accordwith;
	}



	public String getRemarks() {
		return remarks;
	}



	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	public PoliceIdLoginnameRealname getZb() {
		return zb;
	}



	public void setZb(PoliceIdLoginnameRealname zb) {
		this.zb = zb;
	}



	public PoliceIdLoginnameRealname getLd() {
		return ld;
	}



	public void setLd(PoliceIdLoginnameRealname ld) {
		this.ld = ld;
	}



	public List<PoliceIdLoginnameRealname> getXb() {
		return xb;
	}



	public void setXb(List<PoliceIdLoginnameRealname> xb) {
		this.xb = xb;
	}



	public EvaluationEntity_tqy() {}
}
