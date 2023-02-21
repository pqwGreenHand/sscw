package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Map;

public class LawDocProcessEntity implements Serializable {

    private static final long serialVersionUID = -3209220984599504620L;

    private int fileType;
    private String tempFileName;//预览需要生成的临时文件名
    private String xmlFileName;//生成文件所需xml文件
    private String downFileName;//下载定义的文件名
    private Map data;//模板对应的数据
    private String serialNo;
    private Long userId;
    private Long serialId;

    private long belongingsId;
    private Long policeId;

    public Long getPoliceId() {
        return policeId;
    }

    public void setPoliceId(Long policeId) {
        this.policeId = policeId;
    }

    public long getBelongingsId() {
        return belongingsId;
    }

    public void setBelongingsId(long belongingsId) {
        this.belongingsId = belongingsId;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getTempFileName() {
        return tempFileName;
    }

    public void setTempFileName(String tempFileName) {
        this.tempFileName = tempFileName;
    }

    public String getXmlFileName() {
        return xmlFileName;
    }

    public void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public String getDownFileName() {
        return downFileName;
    }

    public void setDownFileName(String downFileName) {
        this.downFileName = downFileName;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSerialId() {
		return serialId;
	}

	public void setSerialId(Long serialId) {
		this.serialId = serialId;
	}

    private String serialUUID;
    private Integer count;

    public String getSerialUUID() {
        return serialUUID;
    }

    public void setSerialUUID(String serialUUID) {
        this.serialUUID = serialUUID;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
