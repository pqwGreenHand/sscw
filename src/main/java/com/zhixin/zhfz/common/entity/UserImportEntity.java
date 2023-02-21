package com.zhixin.zhfz.common.entity;

import org.apache.commons.lang.StringUtils;

public class UserImportEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4614966861269493860L;

	private Integer rowNum;

	private String name;// 姓名

	private String no;// 警号

	private String title;// 职务

	private String sex;// 性别

	private String mobile;// 手机

	private String org;// 所属部门

	private String porg;// 所属部门上级部门

	private String roleName;// 角色
	
	/* 用户插入部分 */
	private UserEntity user=null;
	
	/* 标示 检查出的 -------------------*/
	
	private boolean repetitionNo=false;// 重复警号
	
	private boolean errorRoleName=false;// 错误的权限
	
	
	public UserEntity getUserEntityByThis(){
		
		UserEntity u=new UserEntity();
		u.setRealName(this.name);
		u.setLoginName(this.no);
		u.setPassword("123456");
		u.setJobTitle(this.title);
		if("女".equals(this.sex)){
			u.setSex(1);
		}else{
			u.setSex(0);
		}
		u.setMobile(this.mobile);
		u.setEmail("");
		u.setCertificateTypeId(191);//191警官证
		u.setCertificateNo(this.no);
		u.setIsActive(1);
		return u;
	}
	

	public boolean isGood() {
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(no) || StringUtils.isEmpty(title)
				|| StringUtils.isEmpty(org) || StringUtils.isEmpty(roleName)) {
			return false;
		}
		return true;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getPorg() {
		return porg;
	}

	public void setPorg(String porg) {
		this.porg = porg;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
	

	public boolean isRepetitionNo() {
		return repetitionNo;
	}

	public void setRepetitionNo(boolean repetitionNo) {
		this.repetitionNo = repetitionNo;
	}

	public boolean isErrorRoleName() {
		return errorRoleName;
	}

	public void setErrorRoleName(boolean errorRoleName) {
		this.errorRoleName = errorRoleName;
	}
	
	

	public UserEntity getUser() {
		return user;
	}


	public void setUser(UserEntity user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "UserImportEntity [rowNum=" + rowNum + ", name=" + name + ", no=" + no + ", title=" + title + ", sex="
				+ sex + ", mobile=" + mobile + ", org=" + org + ", porg=" + porg + ", roleName=" + roleName + ", user="
				+ user + ", repetitionNo=" + repetitionNo + ", errorRoleName=" + errorRoleName + "]";
	}

}