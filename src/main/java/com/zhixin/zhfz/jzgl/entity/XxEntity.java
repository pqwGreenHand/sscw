package com.zhixin.zhfz.jzgl.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class XxEntity implements Serializable {

	private static final long serialVersionUID = 6526622176736290275L;

	private Long id; // 系统通知id
	private String xxbt;// 消息标题
	private String xxnr;// 消息内容
	private Long xxlx;// '消息类型:0系统通知 1立案不存 2逾期不存 3有案不查 4逾期不还 5卷宗损毁 6卷宗遗失 7卷宗缺失
						// 8卷宗错混 9归还逾期 10立案不办 99其他',
	private Long yhId;// 接收用户ID
	private Long cjyhId; // 发送人ID
	private Long ajxxId;// 案件id
	private Long jzxxId;// 卷宗id
	/** 案件名称 */
	private String ajmc;
	/** 案件编号 */
	private String ajbh;
	/** xm 用户姓名 */
	private String xm;
	private String fsr;
	/** 卷宗编号 */
	private String jzbh;
	private Integer xxdj;// 等级1黄2橙 3红
	private Integer xxly; // 消息来源0是预警1是通知
	private Integer zt;// 状态 0 未处理 1 已处理 2已过期
	private Timestamp fssj;// 发送时间
	private String clnr;// 确认处理内容
	private String zrr;// 确认处理内容
	private String realName;// 保管人
	private String gmxs;
	private Date clsj; // 处理时间

	private String opPid;
	private Integer opUserId;
	
	public String getGmxs() {
		return gmxs;
	}

	public void setGmxs(String gmxs) {
		this.gmxs = gmxs;
	}

	public String getAjbh() {
		return ajbh;
	}

	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}

	public String getFsr() {
		return fsr;
	}

	public void setFsr(String fsr) {
		this.fsr = fsr;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getZrr() {
		return zrr;
	}

	public void setZrr(String zrr) {
		this.zrr = zrr;
	}

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getJzbh() {
		return jzbh;
	}

	public void setJzbh(String jzbh) {
		this.jzbh = jzbh;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getXxbt() {
		return xxbt;
	}

	public void setXxbt(String xxbt) {
		this.xxbt = xxbt;
	}

	public String getXxnr() {
		return xxnr;
	}

	public void setXxnr(String xxnr) {
		this.xxnr = xxnr;
	}

	public Long getXxlx() {
		return xxlx;
	}

	public void setXxlx(Long xxlx) {
		this.xxlx = xxlx;
	}

	public Long getYhId() {
		return yhId;
	}

	public void setYhId(Long yhId) {
		this.yhId = yhId;
	}

	public Long getCjyhId() {
		return cjyhId;
	}

	public void setCjyhId(Long cjyhId) {
		this.cjyhId = cjyhId;
	}

	public Long getAjxxId() {
		return ajxxId;
	}

	public void setAjxxId(Long ajxxId) {
		this.ajxxId = ajxxId;
	}

	public Long getJzxxId() {
		return jzxxId;
	}

	public void setJzxxId(Long jzxxId) {
		this.jzxxId = jzxxId;
	}

	public Integer getXxdj() {
		return xxdj;
	}

	public void setXxdj(Integer xxdj) {
		this.xxdj = xxdj;
	}

	public Integer getXxly() {
		return xxly;
	}

	public void setXxly(Integer xxly) {
		this.xxly = xxly;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Timestamp getFssj() {
		return fssj;
	}

	public void setFssj(Timestamp fssj) {
		this.fssj = fssj;
	}

	public String getClnr() {
		return clnr;
	}

	public void setClnr(String clnr) {
		this.clnr = clnr;
	}

	public Date getClsj() {
		return clsj;
	}

	public void setClsj(Date clsj) {
		this.clsj = clsj;
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
		return "XxEntity [id=" + id + ", xxbt=" + xxbt + ", xxnr=" + xxnr + ", xxlx=" + xxlx + ", yhId=" + yhId
				+ ", cjyhId=" + cjyhId + ", ajxxId=" + ajxxId + ", jzxxId=" + jzxxId + ", ajmc=" + ajmc + ", ajbh="
				+ ajbh + ", xm=" + xm + ", fsr=" + fsr + ", jzbh=" + jzbh + ", xxdj=" + xxdj + ", xxly=" + xxly
				+ ", zt=" + zt + ", fssj=" + fssj + ", clnr=" + clnr + ", zrr=" + zrr + ", realName=" + realName
				+ ", clsj=" + clsj + ", opPid=" + opPid + ", opUserId=" + opUserId + "]";
	}

}