package com.zhixin.zhfz.bacs.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * @program: zhfz
 * @description: 笔录word模板添加参数
 * @author: jzw
 * @create: 2019-02-21 15:40
 **/

public class RecordTemplateForm {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Integer sortNo;

    private MultipartFile wordFile;

    private Integer type;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Long userId = 0L;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    @Override
    public String toString() {
        return "RecordTemplateForm [id = " + id + ",sortNo = "+ this.sortNo + ",wordFile = " + ",type = " + this.type + "]";
    }

    public MultipartFile getWordFile() {
        return wordFile;
    }

    public void setWordFile(MultipartFile wordFile) {
        this.wordFile = wordFile;
    }

    private String opPid;
    private Integer opUserId;

    public String getOpPid() {
        return opPid;
    }

    public void setOpPid(String opPid) {
        this.opPid = opPid;
    }

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }
}
