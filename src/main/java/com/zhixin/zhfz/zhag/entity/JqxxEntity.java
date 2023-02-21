package com.zhixin.zhfz.zhag.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * table ag_jqxx
 * @author Admin
 *
 */
public class JqxxEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5904528197325017529L;

	private Long id;
	private long total;//总数
	private String jjdwdm;// 接警单位代码
	private String jjdwmc;// 接警单位名称
	private Integer jjdwid;// 接警单位id
	private String jjrjh;// 接警人警号
	private Integer jjrid;// 接警人id
	private String jjrxm;// 接警人姓名
	private Timestamp jjsj;// 接警时间
	private String jjfs;// 接警方式 110/120/122接警/自接警
	private String jqbh;// 警情编号
	private String sldwid;// 受理单位id
	private String sldwdm;// 受理单位代码
	private String sldwmc;// 受立单位名称
	private String slrjh;// 受理人警号 用，分隔
	private String slrid;// 受理人id 用，分隔
	private String slrxm;// 受理人姓名 用，分隔
	private Timestamp slsj; // '受理时间
	private String jqlb;// 警情类别
	private String jqlbdm;// 警情类别代码
	private String cjdwid;// 处警单位id
	private String cjrid;// 处警人id 用，分隔
	private String cjdwmc;// 处警单位名称
	private String cjdwdm;// 处警单位代码
	private String cjrjh;// 处警人警号 用，分隔
	private String cjrxm;// 处警人姓名 用，分隔
	private String zyaq;// '主要案情
	private Timestamp afsj;// 案发时间
	private String afdd;// 案发地点
	private String jjjqdm;// 接警警区代码
	private String jjjqmc;// 接警警区名称
	private String cljg;// 处理结果
	private String cljgdm;// 处理结果代码
	private String cjz;// 创建者
	private Timestamp gxsj;// 更新时间
	private Timestamp cjsj;// 创建时间
	private Timestamp jcjsj;// 处警时间
	private Date jqStartDate; // 接警开始时间
	private Date jqEndDate; // 接警结束时间

	public Date getJqStartDate() {
		return jqStartDate;
	}

	public void setJqStartDate(Date jqStartDate) {
		this.jqStartDate = jqStartDate;
	}

	public Date getJqEndDate() {
		return jqEndDate;
	}

	public void setJqEndDate(Date jqEndDate) {
		this.jqEndDate = jqEndDate;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Timestamp getJcjsj() {
		return jcjsj;
	}

	public void setJcjsj(Timestamp jcjsj) {
		this.jcjsj = jcjsj;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getJjdwdm() {
		return jjdwdm;
	}
	public void setJjdwdm(String jjdwdm) {
		this.jjdwdm = jjdwdm;
	}
	public String getJjdwmc() {
		return jjdwmc;
	}
	public void setJjdwmc(String jjdwmc) {
		this.jjdwmc = jjdwmc;
	}
	public Integer getJjdwid() {
		return jjdwid;
	}
	public void setJjdwid(Integer jjdwid) {
		this.jjdwid = jjdwid;
	}
	public String getJjrjh() {
		return jjrjh;
	}
	public void setJjrjh(String jjrjh) {
		this.jjrjh = jjrjh;
	}
	public Integer getJjrid() {
		return jjrid;
	}
	public void setJjrid(Integer jjrid) {
		this.jjrid = jjrid;
	}
	public String getJjrxm() {
		return jjrxm;
	}
	public void setJjrxm(String jjrxm) {
		this.jjrxm = jjrxm;
	}
	public Timestamp getJjsj() {
		return jjsj;
	}
	public void setJjsj(Timestamp jjsj) {
		this.jjsj = jjsj;
	}
	public String getJjfs() {
		return jjfs;
	}
	public void setJjfs(String jjfs) {
		this.jjfs = jjfs;
	}
	public String getJqbh() {
		return jqbh;
	}
	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}
	public String getSldwid() {
		return sldwid;
	}
	public void setSldwid(String sldwid) {
		this.sldwid = sldwid;
	}
	public String getSldwdm() {
		return sldwdm;
	}
	public void setSldwdm(String sldwdm) {
		this.sldwdm = sldwdm;
	}
	public String getSldwmc() {
		return sldwmc;
	}
	public void setSldwmc(String sldwmc) {
		this.sldwmc = sldwmc;
	}
	public String getSlrjh() {
		return slrjh;
	}
	public void setSlrjh(String slrjh) {
		this.slrjh = slrjh;
	}
	public String getSlrid() {
		return slrid;
	}
	public void setSlrid(String slrid) {
		this.slrid = slrid;
	}
	public String getSlrxm() {
		return slrxm;
	}
	public void setSlrxm(String slrxm) {
		this.slrxm = slrxm;
	}
	public Timestamp getSlsj() {
		return slsj;
	}
	public void setSlsj(Timestamp slsj) {
		this.slsj = slsj;
	}
	public String getJqlb() {
		return jqlb;
	}
	public void setJqlb(String jqlb) {
		this.jqlb = jqlb;
	}
	public String getJqlbdm() {
		return jqlbdm;
	}
	public void setJqlbdm(String jqlbdm) {
		this.jqlbdm = jqlbdm;
	}
	public String getCjdwid() {
		return cjdwid;
	}
	public void setCjdwid(String cjdwid) {
		this.cjdwid = cjdwid;
	}
	public String getCjrid() {
		return cjrid;
	}
	public void setCjrid(String cjrid) {
		this.cjrid = cjrid;
	}
	public String getCjdwmc() {
		return cjdwmc;
	}
	public void setCjdwmc(String cjdwmc) {
		this.cjdwmc = cjdwmc;
	}
	public String getCjdwdm() {
		return cjdwdm;
	}
	public void setCjdwdm(String cjdwdm) {
		this.cjdwdm = cjdwdm;
	}
	public String getCjrjh() {
		return cjrjh;
	}
	public void setCjrjh(String cjrjh) {
		this.cjrjh = cjrjh;
	}
	public String getCjrxm() {
		return cjrxm;
	}
	public void setCjrxm(String cjrxm) {
		this.cjrxm = cjrxm;
	}
	public String getZyaq() {
		return zyaq;
	}
	public void setZyaq(String zyaq) {
		this.zyaq = zyaq;
	}
	public Timestamp getAfsj() {
		return afsj;
	}
	public void setAfsj(Timestamp afsj) {
		this.afsj = afsj;
	}
	public String getAfdd() {
		return afdd;
	}
	public void setAfdd(String afdd) {
		this.afdd = afdd;
	}
	public String getJjjqdm() {
		return jjjqdm;
	}
	public void setJjjqdm(String jjjqdm) {
		this.jjjqdm = jjjqdm;
	}
	public String getJjjqmc() {
		return jjjqmc;
	}
	public void setJjjqmc(String jjjqmc) {
		this.jjjqmc = jjjqmc;
	}
	public String getCljg() {
		return cljg;
	}
	public void setCljg(String cljg) {
		this.cljg = cljg;
	}
	public String getCljgdm() {
		return cljgdm;
	}
	public void setCljgdm(String cljgdm) {
		this.cljgdm = cljgdm;
	}
	public String getCjz() {
		return cjz;
	}
	public void setCjz(String cjz) {
		this.cjz = cjz;
	}
	public Timestamp getGxsj() {
		return gxsj;
	}
	public void setGxsj(Timestamp gxsj) {
		this.gxsj = gxsj;
	}
	public Timestamp getCjsj() {
		return cjsj;
	}
	public void setCjsj(Timestamp cjsj) {
		this.cjsj = cjsj;
	}

	@Override
	public String toString() {
		return "JqxxEntity{" +
				"id=" + id +
				", jjdwdm='" + jjdwdm + '\'' +
				", jjdwmc='" + jjdwmc + '\'' +
				", jjdwid=" + jjdwid +
				", jjrjh='" + jjrjh + '\'' +
				", jjrid=" + jjrid +
				", jjrxm='" + jjrxm + '\'' +
				", jjsj=" + jjsj +
				", jjfs='" + jjfs + '\'' +
				", jqbh='" + jqbh + '\'' +
				", sldwid='" + sldwid + '\'' +
				", sldwdm='" + sldwdm + '\'' +
				", sldwmc='" + sldwmc + '\'' +
				", slrjh='" + slrjh + '\'' +
				", slrid='" + slrid + '\'' +
				", slrxm='" + slrxm + '\'' +
				", slsj=" + slsj +
				", jqlb='" + jqlb + '\'' +
				", jqlbdm='" + jqlbdm + '\'' +
				", cjdwid='" + cjdwid + '\'' +
				", cjrid='" + cjrid + '\'' +
				", cjdwmc='" + cjdwmc + '\'' +
				", cjdwdm='" + cjdwdm + '\'' +
				", cjrjh='" + cjrjh + '\'' +
				", cjrxm='" + cjrxm + '\'' +
				", zyaq='" + zyaq + '\'' +
				", afsj=" + afsj +
				", afdd='" + afdd + '\'' +
				", jjjqdm='" + jjjqdm + '\'' +
				", jjjqmc='" + jjjqmc + '\'' +
				", cljg='" + cljg + '\'' +
				", cljgdm='" + cljgdm + '\'' +
				", cjz='" + cjz + '\'' +
				", gxsj=" + gxsj +
				", cjsj=" + cjsj +
				", jcjsj=" + jcjsj +
				'}';
	}
}
