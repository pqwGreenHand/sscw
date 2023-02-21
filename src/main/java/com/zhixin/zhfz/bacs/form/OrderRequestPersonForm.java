package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;
import java.util.Date;

public class OrderRequestPersonForm  implements Serializable {
    private static final long serialVersionUID = 1461140198356139999L;

    private Integer id;

    private String name;

    private Integer certificateTypeId;

    private String certificateNo;

    private Integer sex;

    private Integer orderRequestId;
    private Integer areaId;

    private String description;

    private Date createdTime;

    public Integer getEisJuvenile() {
        return eisJuvenile;
    }

    public void setEisJuvenile(Integer eisJuvenile) {
        this.eisJuvenile = eisJuvenile;
    }

    public Integer getEisGravida() {
        return eisGravida;
    }

    public void setEisGravida(Integer eisGravida) {
        this.eisGravida = eisGravida;
    }

    public Integer getEgravidaMonth() {
        return egravidaMonth;
    }

    public void setEgravidaMonth(Integer egravidaMonth) {
        this.egravidaMonth = egravidaMonth;
    }

    public Integer getEisGravidaProve() {
        return eisGravidaProve;
    }

    public void setEisGravidaProve(Integer eisGravidaProve) {
        this.eisGravidaProve = eisGravidaProve;
    }

    public Integer getEisDisease() {
        return eisDisease;
    }

    public void setEisDisease(Integer eisDisease) {
        this.eisDisease = eisDisease;
    }

    public Integer getEisDiseaseProve() {
        return eisDiseaseProve;
    }

    public void setEisDiseaseProve(Integer eisDiseaseProve) {
        this.eisDiseaseProve = eisDiseaseProve;
    }

    private Date updatedTime;

    private Integer eisJuvenile;

    private Integer eisGravida;

    private Integer egravidaMonth;

    private Integer eisGravidaProve;

    private Integer eisDisease;

    private Integer eisDiseaseProve;

    private String personInfo;

    private String other;

    private int country;

    private int nation;

    private int orderPersonId;

    private int personId;
    private String personType;

    private Integer isJuvenile;

    private Integer isGravida;

    private Integer isGravidaProve;

    private String gravidaMonth;

    private Integer isDisease;

    private Integer isDiseaseProve;
    

    public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public Integer getIsJuvenile() {
		return isJuvenile;
	}

	public void setIsJuvenile(Integer isJuvenile) {
		this.isJuvenile = isJuvenile;
	}

	public Integer getIsGravida() {
		return isGravida;
	}

	public void setIsGravida(Integer isGravida) {
		this.isGravida = isGravida;
	}

	public Integer getIsGravidaProve() {
		return isGravidaProve;
	}

	public void setIsGravidaProve(Integer isGravidaProve) {
		this.isGravidaProve = isGravidaProve;
	}

	public String getGravidaMonth() {
		return gravidaMonth;
	}

	public void setGravidaMonth(String gravidaMonth) {
		this.gravidaMonth = gravidaMonth;
	}

	public Integer getIsDisease() {
		return isDisease;
	}

	public void setIsDisease(Integer isDisease) {
		this.isDisease = isDisease;
	}

	public Integer getIsDiseaseProve() {
		return isDiseaseProve;
	}

	public void setIsDiseaseProve(Integer isDiseaseProve) {
		this.isDiseaseProve = isDiseaseProve;
	}

	public Integer getId() {
        return id;
    }

    public int getOrderPersonId() {
        return orderPersonId;
    }

    public void setOrderPersonId(int orderPersonId) {
        this.orderPersonId = orderPersonId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCertificateTypeId() {
        return certificateTypeId;
    }

    public void setCertificateTypeId(Integer certificateTypeId) {
        this.certificateTypeId = certificateTypeId;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getOrderRequestId() {
        return orderRequestId;
    }

    public void setOrderRequestId(Integer orderRequestId) {
        this.orderRequestId = orderRequestId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(String personInfo) {
        this.personInfo = personInfo;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getNation() {
        return nation;
    }

    public void setNation(int nation) {
        this.nation = nation;
    }

    public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@Override
    public String toString() {
        return "OrderRequestPersonForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", certificateTypeId=" + certificateTypeId +
                ", certificateNo='" + certificateNo + '\'' +
                ", sex=" + sex +
                ", orderRequestId=" + orderRequestId +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", eisJuvenile=" + eisJuvenile +
                ", eisGravida=" + eisGravida +
                ", egravidaMonth=" + egravidaMonth +
                ", eisGravidaProve=" + eisGravidaProve +
                ", eisDisease=" + eisDisease +
                ", eisDiseaseProve=" + eisDiseaseProve +
                ", personInfo='" + personInfo + '\'' +
                ", other='" + other + '\'' +
                ", country=" + country +
                ", nation=" + nation +
                ", orderPersonId=" + orderPersonId +
                ", areaId=" + areaId +
                ", personId=" + personId +
                '}';
    }
}
