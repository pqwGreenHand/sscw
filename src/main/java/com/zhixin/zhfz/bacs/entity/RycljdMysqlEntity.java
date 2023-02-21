package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jet on 2017/2/21.
 * 警宗平台案件基本信息
 */
public class RycljdMysqlEntity implements Serializable {
		private Integer id;
	    private String ryid;
	    private String rybh;
	    private String cljd;
	    private String pzr;
	    private Date pzsj;
	    private String ajbh;
	    private String zbmj;
	    private long jzxxId;
	    private String bmId;
	    private long ajxxId;
	    private Date czsj;
	    private Date gxsj;
	    
	    
		public Date getGxsj() {
			return gxsj;
		}
		public void setGxsj(Date gxsj) {
			this.gxsj = gxsj;
		}
		public long getJzxxId() {
			return jzxxId;
		}
		public void setJzxxId(long jzxxId) {
			this.jzxxId = jzxxId;
		}
		public long getAjxxId() {
			return ajxxId;
		}
		public void setAjxxId(long ajxxId) {
			this.ajxxId = ajxxId;
		}
		public String getBmId() {
			return bmId;
		}
		public void setBmId(String bmId) {
			this.bmId = bmId;
		}
		public String getZbmj() {
			return zbmj;
		}
		public void setZbmj(String zbmj) {
			this.zbmj = zbmj;
		}
		public Date getCzsj() {
			return czsj;
		}
		public void setCzsj(Date czsj) {
			this.czsj = czsj;
		}
		public String getAjbh() {
			return ajbh;
		}
		public void setAjbh(String ajbh) {
			this.ajbh = ajbh;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getRyid() {
			return ryid;
		}
		public void setRyid(String ryid) {
			this.ryid = ryid;
		}
		public String getRybh() {
			return rybh;
		}
		public void setRybh(String rybh) {
			this.rybh = rybh;
		}
		public String getCljd() {
			return cljd;
		}
		public void setCljd(String cljd) {
			this.cljd = cljd;
		}
		public String getPzr() {
			return pzr;
		}
		public void setPzr(String pzr) {
			this.pzr = pzr;
		}
		public Date getPzsj() {
			return pzsj;
		}
		public void setPzsj(Date pzsj) {
			this.pzsj = pzsj;
		}
		@Override
		public String toString() {
			return "RycljdMysqlEntity [id=" + id + ", ryid=" + ryid + ", rybh=" + rybh + ", cljd=" + cljd + ", pzr="
					+ pzr + ", pzsj=" + pzsj + ", ajbh=" + ajbh + ", zbmj=" + zbmj + ", jzxxId=" + jzxxId + ", bmId="
					+ bmId + ", ajxxId=" + ajxxId + ", czsj=" + czsj + ", gxsj=" + gxsj + "]";
		}
	  
}
