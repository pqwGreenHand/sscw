/*
 * Copyright (C), 2016-2017 
 * FileName: CzrzMysqlEntity.java
 * auto create by wangguhua
 * Author:   
 * Date:     2017-4-6 16:33:44
 * Description: CzrzMysqlEntity实体类   
 */
 
package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhixin.zhfz.common.utils.ControllerTool;

/**
 * 实体类CzrzMysqlEntity table: czrz
 * 
 * @author wangguhua
 */
public class CzrzEntity implements Serializable {
    /** Serial UID */
	private static final long serialVersionUID = -2070328242815688574L;

	/** id  */
    private Long id;
    
    /** jzxxId 卷宗信息ID */
    private Long jzxxId;
    
    /** userId 用户ID */
    private Long userId;
    
    /** gmxxId 柜门ID */
    private Long gmxxId;
    
    /** czlx 操作类型 0打印 1存入 2取出 */
    private Long czlx;
    
    /** czsj 操作时间    */
    private Timestamp czsj;
    
    /** jzJzbh 卷宗编号   */
    private String jzJzbh;
    
    /** aj_ajmc 案件名称  */
    private String ajAjmc;
    
    /** gmXsbh 柜门显示箱号  */
    private String gmXsbh;
    
    /** yhXm 用户姓名   */
    private String yhXm;
    
    /** jzgMc 卷宗柜mc*/
    private String jzgMc;
    
    /** jzgId 卷宗柜ID*/
    private Long jzgId;
    
    /** z 操作类型字典值*/
    private String z;

    private Long ajxxId;
    
    private String czms;
    
    private String zplj;
    private int count;
    private String organization;
    private  String  realName;
    private  String  loginName;
    private String opPid;
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealNname(String realName) {
        this.realName = realName;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "CzrzMysqlEntity{" +
                "id=" + id +
                ", jzxxId=" + jzxxId +
                ", userId=" + userId +
                ", gmxxId=" + gmxxId +
                ", czlx=" + czlx +
                ", czsj=" + czsj +
                ", jzJzbh='" + jzJzbh + '\'' +
                ", ajAjmc='" + ajAjmc + '\'' +
                ", gmXsbh='" + gmXsbh + '\'' +
                ", yhXm='" + yhXm + '\'' +
                ", jzgMc='" + jzgMc + '\'' +
                ", jzgId=" + jzgId +
                ", z='" + z + '\'' +
                ", ajxxId=" + ajxxId +
                ", czms='" + czms + '\'' +
                ", zplj='" + zplj + '\'' +
                ", count=" + count +
                ", organization='" + organization + '\'' +
                ", realNname='" + realName + '\'' +
                ", loginName='" + loginName + '\'' +
                '}';
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getZplj() {
		return zplj;
	}

	public void setZplj(String zplj) {
		this.zplj = zplj;
	}

	public String getCzms() {
		return czms;
	}

	public void setCzms(String czms) {
		this.czms = czms;
	}

	public Long getAjxxId() {
		return ajxxId;
	}

	public void setAjxxId(Long ajxxId) {
		this.ajxxId = ajxxId;
	}

	public String getZ() {
		return z;
	}

	public void setZ(String z) {
		this.z = z;
	}

	public CzrzEntity() {
		super();
	}

	public CzrzEntity(Long jzxxId, Long gmxxId, Long czlx,HttpServletRequest request, HttpServletResponse response,Long ajxxId) {
		super();
		this.jzxxId = jzxxId;
		this.userId = Long.parseLong(ControllerTool.getUserID(request)+"");
		this.gmxxId = gmxxId;
		this.ajxxId = ajxxId;
		this.czlx = czlx;
	}

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
     * @return the jzxxId
     */
    public Long getJzxxId() {
        return this.jzxxId;
    }

    /**
     * @param jzxxId the jzxxId to set
     */
    public void setJzxxId(Long jzxxId) {
        this.jzxxId = jzxxId;
    }
        
    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
     * @return the gmxxId
     */
    public Long getGmxxId() {
        return this.gmxxId;
    }

    /**
     * @param gmxxId the gmxxId to set
     */
    public void setGmxxId(Long gmxxId) {
        this.gmxxId = gmxxId;
    }
    
    /**
     * @return the czlx
     */
    public Long getCzlx() {
        return this.czlx;
    }

    /**
     * @param czlx the czlx to set
     */
    public void setCzlx(Long czlx) {
        this.czlx = czlx;
    }
    
    /**
     * @return the czsj
     */
    public Timestamp getCzsj() {
        return this.czsj;
    }

    /**
     * @param czsj the czsj to set
     */
    public void setCzsj(Timestamp czsj) {
        this.czsj = czsj;
    }
    
	public String getJzJzbh() {
		return jzJzbh;
	}

	public void setJzJzbh(String jzJzbh) {
		this.jzJzbh = jzJzbh;
	}

	public String getAjAjmc() {
		return ajAjmc;
	}

	public void setAjAjmc(String ajAjmc) {
		this.ajAjmc = ajAjmc;
	}

	public String getGmXsbh() {
		return gmXsbh;
	}

	public void setGmXsbh(String gmXsbh) {
		this.gmXsbh = gmXsbh;
	}

	public String getYhXm() {
		return yhXm;
	}

	public void setYhXm(String yhXm) {
		this.yhXm = yhXm;
	}

	public String getJzgMc() {
		return jzgMc;
	}

	public void setJzgMc(String jzgMc) {
		this.jzgMc = jzgMc;
	}

	public Long getJzgId() {
		return jzgId;
	}

	public void setJzgId(Long jzgId) {
		this.jzgId = jzgId;
	}

	public String getOpPid() {
		return opPid;
	}

	public void setOpPid(String opPid) {
		this.opPid = opPid;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}