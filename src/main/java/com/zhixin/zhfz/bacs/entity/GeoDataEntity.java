/*
 * Copyright (C), 2016-2016 
 * FileName: GeoDataEntity.java
 * auto create by wangguhua
 * Author:   
 * Date:     2016-8-23 16:19:38
 * Description: GeoDataEntity实体类   
 */
 
package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;


/**
 * 实体类GeoDataEntity table: geo_data
 * 
 * @author wangguhua
 */
public class GeoDataEntity implements Serializable {
    /** Serial UID */
    private static final long serialVersionUID = 1L;
    
    /** id  */
    private Long id;
    
    /** regionCode region 表 code */
    private Long regionCode;
    
    /** isActive 状态 0 无效,1有效 */
    private Integer isActive;
    
    /** datas 坐标数据 */
    private String datas;
    
    /** nameCp 名称显示坐标数据 */
    private String nameCp;
    
    /** type Polygon,MultiPolygon */
    private String type;
    
    /** childNum 数量 */
    private Integer childNum;
    
    private String regionName;

    private String opPid;
    private Integer opUserId;
    /**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the regionCode
     */
    public Long getRegionCode() {
        return this.regionCode;
    }

    /**
     * @param regionCode the regionCode to set
     */
    public void setRegionCode(Long regionCode) {
        this.regionCode = regionCode;
    }
    
    /**
     * @return the isActive
     */
    public Integer getIsActive() {
        return this.isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
    
    /**
     * @return the datas
     */
    public String getDatas() {
        return this.datas;
    }

    /**
     * @param datas the datas to set
     */
    public void setDatas(String datas) {
        this.datas = datas;
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * @return the childNum
     */
    public Integer getChildNum() {
        return this.childNum;
    }

    /**
     * @param childNum the childNum to set
     */
    public void setChildNum(Integer childNum) {
        this.childNum = childNum;
    }

	public String getNameCp() {
		return nameCp;
	}

	public void setNameCp(String nameCp) {
		this.nameCp = nameCp;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

    public String getOpPid() {
        return opPid;
    }

    public void setOpPid(String opPid) {
        this.opPid = opPid;
    }

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }

    @Override
    public String toString() {
        return "GeoDataEntity{" +
                "id=" + id +
                ", regionCode=" + regionCode +
                ", isActive=" + isActive +
                ", datas='" + datas + '\'' +
                ", nameCp='" + nameCp + '\'' +
                ", type='" + type + '\'' +
                ", childNum=" + childNum +
                ", regionName='" + regionName + '\'' +
                ", opPid='" + opPid + '\'' +
                ", opUserId=" + opUserId +
                '}';
    }
}