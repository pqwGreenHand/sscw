package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;

/*
 * 手环表单
 * */
public class ImageForm implements Serializable {

	private static final long serialVersionUID = 5577128090368009959L;

	private String imageData;

	private Long serialId;
	private Integer securityId;

	private String serialNo;
	private Long jzxxId;// 卷宗id
	private Long ajxxId;// 案件id
	private Integer czlx;// 操作类型  1存 2取
	private Integer sjr;// 存取卷民警 userId

	public String getCzid() {
		return czid;
	}

	public void setCzid(String czid) {
		this.czid = czid;
	}

	private String czid;// czid
	private String uuid ;
	private Long urinalysisId;
	@Override
	public String toString() {
		return "ImageForm [imageData=" + imageData + ", serialId=" + serialId
				+ ", urinalysisId=" + urinalysisId
				+ ", serialNo=" + serialNo + ", jzxxId=" + jzxxId + ", ajxxId=" + ajxxId
				+ ", czlx=" + czlx+ ", sjr=" + sjr+ ", uuid=" + uuid+ ", czid=" + czid+ "]";
	}

	public Long getUrinalysisId() {
		return urinalysisId;
	}

	public void setUrinalysisId(Long urinalysisId) {
		this.urinalysisId = urinalysisId;
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	public Long getSerialId() {
		return serialId;
	}

	public void setSerialId(Long serialId) {
		this.serialId = serialId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Long getJzxxId () {
		return jzxxId;
	}

	public void setJzxxId (Long jzxxId) {
		this.jzxxId = jzxxId;
	}

	public Long getAjxxId() {return ajxxId;}

	public void setAjxxId(Long ajxxId){this.ajxxId = ajxxId;}

	public Integer getCzlx() {return czlx;}
	public void setCzlx(Integer czlx){this.czlx = czlx;}
	public Integer getSjr() {return sjr;}
	public void setSjr(Integer sjr){this.sjr = sjr;}
	public void setUuid(String uuid){this.uuid = uuid;}
	public String getUuid(){return uuid;}

	public Integer getSecurityId() {
		return securityId;
	}

	public void setSecurityId(Integer securityId) {
		this.securityId = securityId;
	}
}
