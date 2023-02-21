package com.zhixin.zhfz.common.entity;

import java.io.Serializable;
import java.util.Date;

public class OrganizationEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;

    private String name;

    private String type;

	private String organization;
    
    private String address;
    
    private String telephone;
    
    private String postcode;
    private Date createdTime;
    
    private Date updatedTime;

	/** regionCode  */
	private Long regionCode;

	/** gis  */
	private String gis;

	/** isMap  */
	private Integer isMap;

	private String regionName;


    private Integer parentId; //上级部门
	private String pid;//上级部门ID

	private String orgCode;
	private String orgAlias;
	private String orgRep;
	private String orgStatus;
	private String proName;

	private Integer op_User_Id; //当前操作人id

	private String op_pid; //当前操作人员所在单位PID

	public String getOp_pid() {
		return op_pid;
	}

	public void setOp_pid(String op_pid) {
		this.op_pid = op_pid;
	}

	public Integer getOp_User_Id() {
		return op_User_Id;
	}

	public void setOp_User_Id(Integer op_User_Id) {
		this.op_User_Id = op_User_Id;
	}

	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
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

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Long getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(Long regionCode) {
		this.regionCode = regionCode;
	}

	public Integer getIsMap() {
		return isMap;
	}

	public void setIsMap(Integer isMap) {
		this.isMap = isMap;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
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

	public String getGis() {
		return gis;
	}

	public void setGis(String gis) {
		this.gis = gis;
	}

	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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
		return "OrganizationEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", type='" + type + '\'' +
				", organization='" + organization + '\'' +
				", address='" + address + '\'' +
				", telephone='" + telephone + '\'' +
				", postcode='" + postcode + '\'' +
				", createdTime=" + createdTime +
				", updatedTime=" + updatedTime +
				", regionCode=" + regionCode +
				", gis='" + gis + '\'' +
				", isMap=" + isMap +
				", regionName='" + regionName + '\'' +
				", parentId=" + parentId +
				", pid='" + pid + '\'' +
				", orgCode='" + orgCode + '\'' +
				", orgAlias='" + orgAlias + '\'' +
				", orgRep='" + orgRep + '\'' +
				", orgStatus='" + orgStatus + '\'' +
				", proName='" + proName + '\'' +
				", op_User_Id=" + op_User_Id +
				", op_pid='" + op_pid + '\'' +
				'}';
	}
}