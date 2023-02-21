package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;
import java.util.Date;

public class OrdeAddPersonForm  implements Serializable {
    private static final long serialVersionUID = 1461140198356139999L;

    private Integer id;

    private String person_name;
    private String name;

    private Integer certificateTypeId;

    private String certificateNo;
    private Integer person_certificate_type;
    private String person_certificate_no;
    private int person_country;
    private int person_nation;
    private String person_personInfo;

    private Integer person_sex;
    private Integer sex;

    private Integer orderRequestId;
    private Integer areaId;

    private String description;

    private Date createdTime;

    private String ajbh;

    private String rybh;

    private String ajmc;
    private String jzyms;
    private Integer jkb;
    private String sfcrgfxdq;

    private String zjdz;
    private String zhdz;
    private Integer sfsjyqldaj;
    private String xyrtw;
    private String zjfjrq;
    private String mgsf;
    private String j3gysfyjjcgqk;
    private Integer sfymjjcs;

    public String getZjdz() {
        return zjdz;
    }

    public void setZjdz(String zjdz) {
        this.zjdz = zjdz;
    }

    public String getZhdz() {
        return zhdz;
    }

    public void setZhdz(String zhdz) {
        this.zhdz = zhdz;
    }

    public Integer getSfsjyqldaj() {
        return sfsjyqldaj;
    }

    public void setSfsjyqldaj(Integer sfsjyqldaj) {
        this.sfsjyqldaj = sfsjyqldaj;
    }

    public String getXyrtw() {
        return xyrtw;
    }

    public void setXyrtw(String xyrtw) {
        this.xyrtw = xyrtw;
    }

    public String getZjfjrq() {
        return zjfjrq;
    }

    public void setZjfjrq(String zjfjrq) {
        this.zjfjrq = zjfjrq;
    }

    public String getMgsf() {
        return mgsf;
    }

    public void setMgsf(String mgsf) {
        this.mgsf = mgsf;
    }

    public String getSfcrgfxdq() {
        return sfcrgfxdq;
    }

    public void setSfcrgfxdq(String sfcrgfxdq) {
        this.sfcrgfxdq = sfcrgfxdq;
    }

    public String getJ3gysfyjjcgqk() {
        return j3gysfyjjcgqk;
    }

    public void setJ3gysfyjjcgqk(String j3gysfyjjcgqk) {
        this.j3gysfyjjcgqk = j3gysfyjjcgqk;
    }

    public Integer getSfymjjcs() {
        return sfymjjcs;
    }

    public void setSfymjjcs(Integer sfymjjcs) {
        this.sfymjjcs = sfymjjcs;
    }

    public String getJzyms() {
        return jzyms;
    }

    public void setJzyms(String jzyms) {
        this.jzyms = jzyms;
    }

    public Integer getJkb() {
        return jkb;
    }

    public void setJkb(Integer jkb) {
        this.jkb = jkb;
    }


    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	

	public Integer getPerson_certificate_type() {
		return person_certificate_type;
	}

	public void setPerson_certificate_type(Integer person_certificate_type) {
		this.person_certificate_type = person_certificate_type;
	}

	public String getPerson_certificate_no() {
		return person_certificate_no;
	}

	public void setPerson_certificate_no(String person_certificate_no) {
		this.person_certificate_no = person_certificate_no;
	}

	public int getPerson_country() {
		return person_country;
	}

	public void setPerson_country(int person_country) {
		this.person_country = person_country;
	}

	public int getPerson_nation() {
		return person_nation;
	}

	public void setPerson_nation(int person_nation) {
		this.person_nation = person_nation;
	}

	public String getPerson_personInfo() {
		return person_personInfo;
	}

	public void setPerson_personInfo(String person_personInfo) {
		this.person_personInfo = person_personInfo;
	}

	public Integer getPerson_sex() {
		return person_sex;
	}

	public void setPerson_sex(Integer person_sex) {
		this.person_sex = person_sex;
	}

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
        return "OrdeAddPersonForm{" +
                "id=" + id +
                ", person_name='" + person_name + '\'' +
                ", name='" + name + '\'' +
                ", certificateTypeId=" + certificateTypeId +
                ", certificateNo='" + certificateNo + '\'' +
                ", person_certificate_type=" + person_certificate_type +
                ", person_certificate_no='" + person_certificate_no + '\'' +
                ", person_country=" + person_country +
                ", person_nation=" + person_nation +
                ", person_personInfo='" + person_personInfo + '\'' +
                ", person_sex=" + person_sex +
                ", sex=" + sex +
                ", orderRequestId=" + orderRequestId +
                ", areaId=" + areaId +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", ajbh='" + ajbh + '\'' +
                ", rybh='" + rybh + '\'' +
                ", ajmc='" + ajmc + '\'' +
                ", jzyms='" + jzyms + '\'' +
                ", jkb=" + jkb +
                ", sfcrgfxdq=" + sfcrgfxdq +
                ", zjdz='" + zjdz + '\'' +
                ", zhdz='" + zhdz + '\'' +
                ", sfsjyqldaj=" + sfsjyqldaj +
                ", xyrtw='" + xyrtw + '\'' +
                ", zjfjrq='" + zjfjrq + '\'' +
                ", mgsf='" + mgsf + '\'' +
                ", j3gysfyjjcgqk=" + j3gysfyjjcgqk +
                ", sfymjjcs=" + sfymjjcs +
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
                ", personId=" + personId +
                ", personType='" + personType + '\'' +
                ", isJuvenile=" + isJuvenile +
                ", isGravida=" + isGravida +
                ", isGravidaProve=" + isGravidaProve +
                ", gravidaMonth='" + gravidaMonth + '\'' +
                ", isDisease=" + isDisease +
                ", isDiseaseProve=" + isDiseaseProve +
                '}';
    }
}
