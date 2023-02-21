package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * gjxxLog
 * 
 * @author cuichengwei
 *
 */
public class GjxxLogEntity implements Serializable {

	private static final long serialVersionUID = 9167522016942681895L;

	private Long id; // 主键id

	private Long userId; // 用户id

	private String czlx; // 操作类型

	private String content; // 操作内容

	private Date cjDate; // 创建时间

	private String loginName;

	private Integer gjzt;

	private Long gjxxId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCzlx() {
		return czlx;
	}

	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCjDate() {
		return cjDate;
	}

	public void setCjDate(Date cjDate) {
		this.cjDate = cjDate;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getGjzt() {
		return gjzt;
	}

	public void setGjzt(Integer gjzt) {
		this.gjzt = gjzt;
	}

	public Long getGjxxId() {
		return gjxxId;
	}

	public void setGjxxId(Long gjxxId) {
		this.gjxxId = gjxxId;
	}

	@Override
	public String toString() {
		return "GjxxLogEntity [id=" + id + ", userId=" + userId + ", czlx=" + czlx + ", content=" + content
				+ ", cjDate=" + cjDate + ", loginName=" + loginName + ", gjzt=" + gjzt + ", gjxxId=" + gjxxId + "]";
	}

}
