package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * zjxx
 * 
 * @author cuichengwei
 *
 */
public class ZjxxEntity implements Serializable {

	private static final long serialVersionUID = -3861756474300489348L;

	private Long id; // 主键id

	private String ajxxId; // 案件id

	private String jzxxId; // 卷宗id

	private String yhId; // 接收人

	private String zrrId; // 发送人

	private String wtlx; // 问题类型
	
	private String wtlxName; // 问题类型

	private String wtjy; // 问题简要

	private String wtnr; // 问题内容

	private Date fssj; // 发送时间

	private Date cjDate; // 创建时间

	private String ajmc;

	private String ajbh;

	private String jzbh;

	private String zrr; // 接收人

	private String realName; // 发送人

	private String zbmjXm; // 主办民警姓名

	private Date startTime;

	private Date endTime;

	private String opPid;
	private Integer opUserId;
	
	private String zgsj; // 整改时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAjxxId() {
		return ajxxId;
	}

	public void setAjxxId(String ajxxId) {
		this.ajxxId = ajxxId;
	}

	public String getJzxxId() {
		return jzxxId;
	}

	public void setJzxxId(String jzxxId) {
		this.jzxxId = jzxxId;
	}

	public String getYhId() {
		return yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}

	public String getZrrId() {
		return zrrId;
	}

	public void setZrrId(String zrrId) {
		this.zrrId = zrrId;
	}

	public String getWtlx() {
		return wtlx;
	}

	public void setWtlx(String wtlx) {
		this.wtlx = wtlx;
	}
    
	public String getWtlxName() {
		return wtlxName;
	}

	public void setWtlxName(String wtlxName) {
		this.wtlxName = wtlxName;
	}

	public String getWtjy() {
		return wtjy;
	}

	public void setWtjy(String wtjy) {
		this.wtjy = wtjy;
	}

	public String getWtnr() {
		return wtnr;
	}

	public void setWtnr(String wtnr) {
		this.wtnr = wtnr;
	}

	public Date getFssj() {
		return fssj;
	}

	public void setFssj(Date fssj) {
		this.fssj = fssj;
	}

	public Date getCjDate() {
		return cjDate;
	}

	public void setCjDate(Date cjDate) {
		this.cjDate = cjDate;
	}

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public String getAjbh() {
		return ajbh;
	}

	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}

	public String getJzbh() {
		return jzbh;
	}

	public void setJzbh(String jzbh) {
		this.jzbh = jzbh;
	}

	public String getZrr() {
		return zrr;
	}

	public void setZrr(String zrr) {
		this.zrr = zrr;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getZbmjXm() {
		return zbmjXm;
	}

	public void setZbmjXm(String zbmjXm) {
		this.zbmjXm = zbmjXm;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getZgsj() {
		return zgsj;
	}

	public void setZgsj(String zgsj) {
		this.zgsj = zgsj;
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
		return "ZjxxEntity [id=" + id + ", ajxxId=" + ajxxId + ", jzxxId=" + jzxxId + ", yhId=" + yhId + ", zrrId="
				+ zrrId + ", wtlx=" + wtlx + ", wtlxName=" + wtlxName + ", wtjy=" + wtjy + ", wtnr=" + wtnr + ", fssj=" + fssj + ", cjDate="
				+ cjDate + ", ajmc=" + ajmc + ", ajbh=" + ajbh + ", jzbh=" + jzbh + ", zrr=" + zrr + ", realName="
				+ realName + ", zbmjXm=" + zbmjXm + ", startTime=" + startTime + ", endTime=" + endTime + ", zgsj="
				+ zgsj + ",opPid="+opPid+",opUserId="+opUserId+"]";
	}

}
