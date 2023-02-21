package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

/**
 * @program: zhfz
 * @description: 笔录类型答案实体
 * @author: jzw
 * @create: 2019-02-22 14:26
 **/

public class RecordTypeAnswerEntity implements Serializable {
    /**
     * `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
     * `knowledge_q_interrogate_id` INT ( 11 ) NOT NULL COMMENT '问题ID',
     * `content` VARCHAR ( 512 ) NOT NULL COMMENT '内容',
     * `sort_no` INT ( 11 ) NOT NULL DEFAULT '1' COMMENT '显示时的排序号，默认为1',
     **/

    private Long id;

    private Long questionId;

    private String content;

    private String sortNo;

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("RecordTypeAnswerEntity [");
        buffer.append("id = ").append(id)
                .append(", questionId = ").append(questionId)
                .append(", content = ").append(content)
                .append(", sortNo").append(sortNo)
                .append("]");
        return buffer.toString();
    }

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
