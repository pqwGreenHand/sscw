package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class DocPhotoEvidenceEntity implements Serializable{

	private static final long serialVersionUID = -7024049351172174485L;

	/**
	 *  物证照片底联说明
	 */
    private Integer id;

    private Long serialId;

    private String makeTime;

    private String makeAddress;

    private String evidenceExplain;

    private String storageAddress;

    private String checks;

    private String shootingPerson;

    private String witnessPerson;

    private String party;

    private String producer;

    private String unit;
  

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public String getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(String makeTime) {
        this.makeTime = makeTime == null ? null : makeTime.trim();
    }

    public String getMakeAddress() {
        return makeAddress;
    }

    public void setMakeAddress(String makeAddress) {
        this.makeAddress = makeAddress == null ? null : makeAddress.trim();
    }

    public String getEvidenceExplain() {
        return evidenceExplain;
    }

    public void setEvidenceExplain(String evidenceExplain) {
        this.evidenceExplain = evidenceExplain == null ? null : evidenceExplain.trim();
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress == null ? null : storageAddress.trim();
    }

    public String getChecks() {
		return checks;
	}

	public void setChecks(String checks) {
		this.checks = checks;
	}

	public String getShootingPerson() {
        return shootingPerson;
    }

    public void setShootingPerson(String shootingPerson) {
        this.shootingPerson = shootingPerson == null ? null : shootingPerson.trim();
    }

    public String getWitnessPerson() {
        return witnessPerson;
    }

    public void setWitnessPerson(String witnessPerson) {
        this.witnessPerson = witnessPerson == null ? null : witnessPerson.trim();
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party == null ? null : party.trim();
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer == null ? null : producer.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    @Override
    public String toString() {
        return "DocPhotoEvidenceEntity{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", makeTime='" + makeTime + '\'' +
                ", makeAddress='" + makeAddress + '\'' +
                ", evidenceExplain='" + evidenceExplain + '\'' +
                ", storageAddress='" + storageAddress + '\'' +
                ", checks='" + checks + '\'' +
                ", shootingPerson='" + shootingPerson + '\'' +
                ", witnessPerson='" + witnessPerson + '\'' +
                ", party='" + party + '\'' +
                ", producer='" + producer + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}