package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.util.Date;

public class JzlxRateEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8767803033946860473L;
	private Integer jzlx;
    private Integer jzlxCount;
    private  String  organization;
	private Integer count;
	private Integer userCount;
    private String mc;
    private Integer jzgId;
	private Date zxsj;

    public Integer getJzgId() {
		return jzgId;
	}

	public void setJzgId(Integer jzgId) {
		this.jzgId = jzgId;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}
    public Integer getJzlx() {
        return jzlx;
    }

    public void setJzlx(Integer jzlx) {
        this.jzlx = jzlx;
    }

    public Integer getJzlxCount() {
        return jzlxCount;
    }

    public void setJzlxCount(Integer jzlxCount) {
        this.jzlxCount = jzlxCount;
    }

    @Override
	public String toString() {
		return "JzlxRateEntity [jzlx=" + jzlx + ", jzlxCount=" + jzlxCount + ", zxsj=" + zxsj + ", organization=" + organization
				+ ", count=" + count + ", userCount=" + userCount + ", mc=" + mc + ", jzgId=" + jzgId + "]";
	}

	public Date getZxsj() {
		return zxsj;
	}

	public void setZxsj(Date zxsj) {
		this.zxsj = zxsj;
	}
}
