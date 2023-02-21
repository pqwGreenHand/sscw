package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * jzg_gx
 * @author xdp
 *
 */
public class JzgGxEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1112242575606628794L;

	/** id */
	private Long id;

	/** jzg_id 所属卷宗柜id */
	private Long jzgId;

	/** jzg_lie_id 所属卷宗柜列id */
	private Long jzgLieId;

	/** jzg_gmxx_id 所属卷宗柜柜门id */
	private Long jzgGmxxId;

	/** user_id 命令编号 */
	private Long userId;
	
	/** user_id 命令编号 */
	private String userIds;
	
	/** 卷宗柜 名称 */
	private String mc;
	
	/** 用户名 */
	private String realName;
	/** 登录名 */
	private String loginName;
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/** 用户名 */
	private String xsbh;
	

	public String getXsbh() {
		return xsbh;
	}

	public void setXsbh(String xsbh) {
		this.xsbh = xsbh;
	}

	/** cjsj 创建时间 */
	private Timestamp cjsj;

	@Override
	public String toString() {
		return "UserGmxxMysqlEntity{" +
				"id=" + id +
				", jzgId=" + jzgId +
				", jzgLieId=" + jzgLieId +
				", jzgGmxxId=" + jzgGmxxId +
				", userId=" + userId +
				", userIds='" + userIds + '\'' +
				", mc='" + mc + '\'' +
				", realName='" + realName + '\'' +
				", loginName='" + loginName + '\'' +
				", xsbh='" + xsbh + '\'' +
				", cjsj=" + cjsj +
				", gmxs=" + gmxs +
				'}';
	}

	/** 是否显示该用户**/
	private int gmxs;

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJzgId() {
		return jzgId;
	}

	public void setJzgId(Long jzgId) {
		this.jzgId = jzgId;
	}

	public Long getJzgLieId() {
		return jzgLieId;
	}

	public void setJzgLieId(Long jzgLieId) {
		this.jzgLieId = jzgLieId;
	}

	public Long getJzgGmxxId() {
		return jzgGmxxId;
	}

	public void setJzgGmxxId(Long jzgGmxxId) {
		this.jzgGmxxId = jzgGmxxId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Timestamp getCjsj() {
		return cjsj;
	}

	public void setCjsj(Timestamp cjsj) {
		this.cjsj = cjsj;
	}


	public int getGmxs() {
		return gmxs;
	}

	public void setGmxs(int gmxs) {
		this.gmxs = gmxs;
	}
}
