package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

/**
 * @program: zhfz
 * @description: 案件性质问题实体
 * @author: jzw
 * @create: 2019-02-23 16:01
 **/

public class CaseNatureQuestionEntity implements Serializable {

    /**
     * `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
     * 	`crime_type_id` INT ( 11 ) NOT NULL COMMENT '犯罪类型ID',
     * 	`content` VARCHAR ( 1024 ) NOT NULL COMMENT '内容',
     * 	`sort_no` INT ( 11 ) NOT NULL COMMENT '显示时的排序号，默认为1',
     * 	`is_major` INT ( 11 ) NOT NULL,
     * 	`crime_name` VARCHAR ( 255 ) DEFAULT NULL,
     **/

    private Long id;

    private Long typeId;

    private String content;

    private Integer sortNo;

    private Integer isMajor;

    private String crimeName;

    private String typeName;
    private String op_Pid;
    private Integer op_User_Id;

    public String getOp_Pid() {
        return op_Pid;
    }

    public void setOp_Pid(String op_Pid) {
        this.op_Pid = op_Pid;
    }

    public Integer getOp_User_Id() {
        return op_User_Id;
    }

    public void setOp_User_Id(Integer op_User_Id) {
        this.op_User_Id = op_User_Id;
    }
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("CaseNatureQuestionEntity [");
        buffer.append("id = ").append(id)
                .append(", typeId = ").append(typeId)
                .append(", content = ").append(content)
                .append(", sortNo = ").append(sortNo)
                .append(", crimeName = ").append(crimeName)
                .append(", typeName = ").append(typeName)
                .append(", isMajor = ").append(isMajor)
                .append("]");
        return buffer.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getIsMajor() {
        return isMajor;
    }

    public void setIsMajor(Integer isMajor) {
        this.isMajor = isMajor;
    }

    public String getCrimeName() {
        return crimeName;
    }

    public void setCrimeName(String crimeName) {
        this.crimeName = crimeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
