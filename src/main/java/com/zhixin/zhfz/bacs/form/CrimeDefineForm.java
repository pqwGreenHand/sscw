package com.zhixin.zhfz.bacs.form;

/**
 * 案件类型表单
 */
public class CrimeDefineForm implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String codeClass;
    private String codeClassDesc;
    private String code;
    private String codeDesc;
    private String spellCode;
    private int sortNo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCodeClass() {
        return codeClass;
    }

    public void setCodeClass(String codeClass) {
        this.codeClass = codeClass;
    }

    public String getCodeClassDesc() {
        return codeClassDesc;
    }

    public void setCodeClassDesc(String codeClassDesc) {
        this.codeClassDesc = codeClassDesc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public String getSpellCode() {
        return spellCode;
    }

    public void setSpellCode(String spellCode) {
        this.spellCode = spellCode;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    @Override
    public String toString() {
        return "CrimeDefineForm [id=" + id + ", codeClass=" + codeClass + ", codeClassDesc=" + codeClassDesc + ", code=" + code
                + ", codeDesc=" + codeDesc + ", spellCode=" + spellCode + ", sortNo=" + sortNo + "]";
    }

}
