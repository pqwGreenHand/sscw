package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhixin.zhfz.common.utils.ControllerTool;

/**
 * 实体类XtrzEntity table: xtrz
 * 
 */
public class XtrzEntity implements Serializable {
	/** Serial UID */
	private static final long serialVersionUID = -3209155541809058706L;

	/** id */
	private Long id;

	/** userId 用户ID */
	private Long userId;

	/** czlx 操作类型 0 登录 1 新增 2修改 3 删除 */
	private Long czlx;

	/** ysj 原始数据 */
	private String ysj;

	/** xsj 现数据 */
	private String xsj;

	/** czsj 操作时间 */
	private Timestamp czsj;

	/** obj 传入类型 */
	private Object obj;

	/** yhXm 用户姓名 */
	private String yhXm;

	public XtrzEntity(Long czlx, Object obj, HttpServletRequest request, HttpServletResponse response) {
		super();
		this.userId = Long.parseLong(ControllerTool.getUserID(request) + "");
		this.czlx = czlx;
		this.obj = obj;
	}

	public XtrzEntity() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the czlx
	 */
	public Long getCzlx() {
		return this.czlx;
	}

	/**
	 * @param czlx
	 *            the czlx to set
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
	 * @param czsj
	 *            the czsj to set
	 */
	public void setCzsj(Timestamp czsj) {
		this.czsj = czsj;
	}

	/**
	 * @return the ysj
	 */
	public String getYsj() {
		return ysj;
	}

	/**
	 * @param ysj
	 *            the ysj to set
	 */
	public void setYsj(String ysj) {
		this.ysj = ysj;
	}

	/**
	 * @return the xsj
	 */
	public String getXsj() {
		return xsj;
	}

	/**
	 * @param xsj
	 *            the xsj to set
	 */
	public void setXsj(String xsj) {
		this.xsj = xsj;
	}

	/**
	 * @return the obj
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * @param obj
	 *            the obj to set
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * @return the yhXm
	 */
	public String getYhXm() {
		return yhXm;
	}

	/**
	 * @param yhXm
	 *            the yhXm to set
	 */
	public void setYhXm(String yhXm) {
		this.yhXm = yhXm;
	}

	/**
	 * 覆盖父类toString方法
	 */

	@Override
	public String toString() {
		return "XtrzMysqlEntity [id=" + id + ", userId=" + userId + ", czlx=" + czlx + ", ysj=" + ysj + ", xsj=" + xsj
				+ ", czsj=" + czsj + ", obj=" + obj + ", yhXm=" + yhXm + "]";
	}

}