package com.zhixin.zhfz.bacs.entity;

public class CrimeDefineEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String codeClass;
	private String codeClassDesc;
	private String code;
	private String codeDesc;
	private String spellCode;
	private int sortNo;

	private String op_Pid;
	private Integer op_User_Id;

	public String getOp_Pid() {
		return op_Pid;
	}

	public void setOp_Pid(String op_Pid) {
		this.op_Pid = op_Pid;
	}

	public Integer getOp_User_Id() {
		return op_User_Id;
	}

	public void setOp_User_Id(Integer op_User_Id) {
		this.op_User_Id = op_User_Id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getCodeClass() {
		return codeClass;
	}

	public void setCodeClass(String codeClass) {
		this.codeClass = codeClass;
	}

	public String getCodeClassDesc() {
		return codeClassDesc;
	}

	public void setCodeClassDesc(String codeClassDesc) {
		this.codeClassDesc = codeClassDesc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeDesc() {
		return codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public String getSpellCode() {
		return spellCode;
	}

	public void setSpellCode(String spellCode) {
		this.spellCode = spellCode;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\", \"codeClass\":\"" + codeClass + "\", \"codeClassDesc\":\"" + codeClassDesc + "\", \"code\":\"" + code
				+ "\", \"codeDesc\":\"" + codeDesc + "\", \"spellCode\":\"" + spellCode + "\", \"sortNo\":\"" + sortNo+"\"}";
	}

	
}
