package com.zhixin.zhfz.common.form;

import java.io.Serializable;
import java.util.Date;

public class OrganizationForm implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private String type;
    
    private String address;
    
    private String telephone;
    
    private String postcode;
    private Date createdTime;
    
    private Date updatedTime;
    
    private Integer parentId; //上级部门
	private String orgCode;
	private String orgAlias;
	private String orgRep;
	private String orgStatus;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgAlias() {
		return orgAlias;
	}
	public void setOrgAlias(String orgAlias) {
		this.orgAlias = orgAlias;
	}
	public String getOrgRep() {
		return orgRep;
	}
	public void setOrgRep(String orgRep) {
		this.orgRep = orgRep;
	}
	public String getOrgStatus() {
		return orgStatus;
	}
	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
	}
	@Override
	public String toString() {
		return "OrganizationForm [id=" + id + ", name=" + name + ", type=" + type + ", address=" + address
				+ ", telephone=" + telephone + ", postcode=" + postcode + ", createdTime=" + createdTime
				+ ", updatedTime=" + updatedTime + ", parentId=" + parentId + ", orgCode=" + orgCode + ", orgAlias="
				+ orgAlias + ", orgRep=" + orgRep + ", orgStatus=" + orgStatus + "]";
	}


    
}
