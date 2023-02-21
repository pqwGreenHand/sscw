/*
 * FormName: UrinalysisEntity.java
 * auto create by wangguhua
 * Description: UrinalysisForm实体类   
 */
 
package com.zhixin.zhfz.bacs.form;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;





/**
 * 实体类UrinalysisEntity table: urinalysis
 * 
 * @author wangguhua
 */
public class UrinalysisForm implements Serializable {
    /** Serial UID */
    private static final long serialVersionUID = 1L;
    
    /** id  */
    private Long id;
    
    /** interrogateSerialId 专属编号ID */
    private Long serialId;
    
    /** lawCaseId 案件ID */
    private Long lawCaseId;
    
    /** mainUserId 主办民警ID */
    private Long mainUserId;
    
    /** checkUserId 登记民警ID */
    private Long checkUserId;
    /** checkUserId 登记民警NO */
    private String checkUserNo;
    
    /** urinalysisTime 尿检时间 */
    private Date urinalysisTime;
    
    /** interrogateAreaId 办案场所ID */
    private Integer areaId;
    
    /** url 照片地址 */
    private String url;
    
    /** description 备注 */
    private String description;
    
    /** createdTime 创建时间 */
    private Date createdTime;
    
    /** updatedTime 更新时间 */
    private Date updatedTime;
    
    //图片的转义
    private String imageData;
    //手环(无用)
    private String cuffNumber;
    
    
    private String cuffId;
    
    private String testMethod;
    
    private String result;

    private String uuid;

    private  MultipartFile[] files ;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public String getTestMethod() {
		return testMethod;
	}

	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	/**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the interrogateSerialId
     */
    public Long getSerialId() {
        return this.serialId;
    }

    /**
     * @param serialId the interrogateSerialId to set
     */
    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }
    
    /**
     * @return the lawCaseId
     */
    public Long getLawCaseId() {
        return this.lawCaseId;
    }

    /**
     * @param lawCaseId the lawCaseId to set
     */
    public void setLawCaseId(Long lawCaseId) {
        this.lawCaseId = lawCaseId;
    }
    
    /**
     * @return the mainUserId
     */
    public Long getMainUserId() {
        return this.mainUserId;
    }

    /**
     * @param mainUserId the mainUserId to set
     */
    public void setMainUserId(Long mainUserId) {
        this.mainUserId = mainUserId;
    }
    
    /**
     * @return the checkUserId
     */
    public Long getCheckUserId() {
        return this.checkUserId;
    }

    /**
     * @param checkUserId the checkUserId to set
     */
    public void setCheckUserId(Long checkUserId) {
        this.checkUserId = checkUserId;
    }
    
    /**
     * @return the urinalysisTime
     */
    public Date getUrinalysisTime() {
        return this.urinalysisTime;
    }

    /**
     * @param urinalysisTime the urinalysisTime to set
     */
    public void setUrinalysisTime(Date urinalysisTime) {
        this.urinalysisTime = urinalysisTime;
    }
    
    /**
     * @return the interrogateAreaId
     */
    public Integer getAreaId() {
        return this.areaId;
    }

    /**
     * @param areaId the interrogateAreaId to set
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
    
    /**
     * @return the url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return the createdTime
     */
    public Date getCreatedTime() {
        return this.createdTime;
    }

    /**
     * @param createdTime the createdTime to set
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    
    /**
     * @return the updatedTime
     */
    public Date getUpdatedTime() {
        return this.updatedTime;
    }

    /**
     * @param updatedTime the updatedTime to set
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
    
    
    
    
    
	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	public String getCuffNumber() {
		return cuffNumber;
	}

	public void setCuffNumber(String cuffNumber) {
		this.cuffNumber = cuffNumber;
	}



	public String getCuffId() {
		return cuffId;
	}

	public void setCuffId(String cuffId) {
		this.cuffId = cuffId;
	}
	
	

	public String getCheckUserNo() {
		return checkUserNo;
	}

	public void setCheckUserNo(String checkUserNo) {
		this.checkUserNo = checkUserNo;
	}

	@Override
	public String toString() {
		return "UrinalysisForm [id=" + id + ", serialId=" + serialId + ", lawCaseId=" + lawCaseId
				+ ", mainUserId=" + mainUserId + ", checkUserId=" + checkUserId + ", checkUserNo=" + checkUserNo
				+ ", urinalysisTime=" + urinalysisTime + ", areaId=" + areaId + ", url=" + url
				+ ", description=" + description + ", createdTime=" + createdTime + ", updatedTime=" + updatedTime
				+ ", imageData=" + imageData + ", cuffNumber=" + cuffNumber + ", cuffId=" + cuffId + ", testMethod="
				+ testMethod + ", result=" + result + "]";
	}


}