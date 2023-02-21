package com.zhixin.zhfz.jxkp.form;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangly on 2018/4/19.
 */
public class EvaParamForm implements Serializable{
    private Integer caseNo;
    private String caseName;
    private Integer caseType;
    private String caseItem;
    private String bar;
    private String xyr;
    private Integer oraniztionId;
    private Integer createrId;
    private Date handleTimeStart;
    private Date handleTimeEnd;
    private Date examineTimeStart;
    private Date examineTimeEnd;

    public Integer getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(Integer caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Integer getCaseType() {
        return caseType;
    }

    public void setCaseType(Integer caseType) {
        this.caseType = caseType;
    }

    public String getCaseItem() {
        return caseItem;
    }

    public void setCaseItem(String caseItem) {
        this.caseItem = caseItem;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getXyr() {
        return xyr;
    }

    public void setXyr(String xyr) {
        this.xyr = xyr;
    }

    public Integer getOraniztionId() {
        return oraniztionId;
    }

    public void setOraniztionId(Integer oraniztionId) {
        this.oraniztionId = oraniztionId;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    public Date getHandleTimeStart() {
        return handleTimeStart;
    }

    public void setHandleTimeStart(Date handleTimeStart) {
        this.handleTimeStart = handleTimeStart;
    }

    public Date getHandleTimeEnd() {
        return handleTimeEnd;
    }

    public void setHandleTimeEnd(Date handleTimeEnd) {
        this.handleTimeEnd = handleTimeEnd;
    }

    public Date getExamineTimeStart() {
        return examineTimeStart;
    }

    public void setExamineTimeStart(Date examineTimeStart) {
        this.examineTimeStart = examineTimeStart;
    }

    public Date getExamineTimeEnd() {
        return examineTimeEnd;
    }

    public void setExamineTimeEnd(Date examineTimeEnd) {
        this.examineTimeEnd = examineTimeEnd;
    }

    @Override
    public String toString() {
        return "EvaParamForm{" +
                "caseNo=" + caseNo +
                ", caseName='" + caseName + '\'' +
                ", caseType=" + caseType +
                ", caseItem='" + caseItem + '\'' +
                ", bar='" + bar + '\'' +
                ", xyr='" + xyr + '\'' +
                ", oraniztionId=" + oraniztionId +
                ", createrId=" + createrId +
                ", handleTimeStart=" + handleTimeStart +
                ", handleTimeEnd=" + handleTimeEnd +
                ", examineTimeStart=" + examineTimeStart +
                ", examineTimeEnd=" + examineTimeEnd +
                '}';
    }
}
