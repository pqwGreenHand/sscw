package com.zhixin.zhfz.bacs.form;

/**
 * @program: zhfz
 * @description:
 * @author: jzw
 * @create: 2019-02-23 10:37
 **/

public class RecordTypeAnswerForm {

    private Long id;

    private Long questionId;

    private String content;

    private String sortNo;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }
}
