package com.zhixin.zhfz.bacs.form;


import com.zhixin.zhfz.bacs.entity.BelongEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class BelongAppForm implements Serializable{

	/**
	 * 随身物品
	 */
	private static final long serialVersionUID = 6068170122207873128L;
	private Integer caseId;
	private Integer areaId;
	private Integer serialId;
	private Integer lockerId;
	private  MultipartFile[] files ;

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	private List<BelongEntity> list;

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getSerialId() {
		return serialId;
	}

	public void setSerialId(Integer serialId) {
		this.serialId = serialId;
	}

	public Integer getLockerId() {
		return lockerId;
	}

	public void setLockerId(Integer lockerId) {
		this.lockerId = lockerId;
	}

	public List<BelongEntity> getList() {
		return list;
	}

	public void setList(List<BelongEntity> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "BelongAppForm{" +
				"caseId=" + caseId +
				", areaId=" + areaId +
				", serialId=" + serialId +
				", lockerId=" + lockerId +
				", list=" + list +
				'}';
	}
}