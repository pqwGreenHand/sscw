package com.zhixin.zhfz.jxkp.entity;

import java.io.Serializable;

/**
 * Created by wangly on 2018/4/21.
 */
public class BatchEntity implements Serializable{
    private Integer id;
    private Integer caseType;
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaseType() {
        return caseType;
    }

    public void setCaseType(Integer caseType) {
        this.caseType = caseType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BatchEntity{" +
                "id=" + id +
                ", caseType=" + caseType +
                ", content='" + content + '\'' +
                '}';
    }
}
