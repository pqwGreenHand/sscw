package com.zhixin.zhfz.common.entity;

import java.util.Date;

public class OperLogEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final String INSERT_TYPE = "add";
	public static final String EDIT_TYPE = "edit";
	public static final String DELETE_TYPE = "delete";
	public static final String RMI_TYPE = "rmi";
	public static final String UPLOAD_TYPE = "upload";
	public static final String DOWNLOAD_TYPE = "download";
	public static final String OTHER_TYPE = "other";

	public static final String SYSTEM_XTPZ = "系统配置";
	public static final String SYSTEM_BACS = "办案场所";
	public static final String SYSTEM_JZGL = "卷宗管理";
	public static final String SYSTEM_SACW = "涉案财物";
	public static final String SYSTEM_JXKP = "绩效考评";
	public static final String SYSTEM_BACS_APP = "办案场所app";

	private Long id;
	private String type;
	private String content;
	private String user;
	private String systemName;
	private Date operTime;
	private Boolean isSuccess;
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
	@Override
	public String toString() {
		return "OperLogEntity [id=" + id + ", type=" + type + ", content=" + content + ", user=" + user
				+ ", systemName=" + systemName + ", operTime=" + operTime + ", isSuccess=" + isSuccess + "]";
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

}
