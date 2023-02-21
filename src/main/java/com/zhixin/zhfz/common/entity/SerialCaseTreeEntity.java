package com.zhixin.zhfz.common.entity;

import java.io.Serializable;

public class SerialCaseTreeEntity implements Serializable {
    private static final long serialVersionUID = -7594405946578813737L;
    private Long serialId;
    private String personName;
    private Integer caseId;
    private String caseName;

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
}
