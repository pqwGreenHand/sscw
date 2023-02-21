package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

/**
 * @program: zhfz
 * @description: 笔录类型问题实体
 * @author: jzw
 * @create: 2019-02-22 14:12
 **/

public class RecordTypeQuestionEntity implements Serializable {

    /**
     * `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
     * `interrogate_type_id` INT ( 11 ) NOT NULL COMMENT '询问笔录类型ID',
     * `content` VARCHAR ( 512 ) NOT NULL COMMENT '内容',
     * `sort_no` INT ( 11 ) NOT NULL DEFAULT '1' COMMENT '显示时的排序号，默认为1',
     * `is_major` INT ( 11 ) NOT NULL,
     **/

    private Long id;

    private Long typeId;        //询问笔录类型ID

    private String typeName;    //类型值（code表 code_key值）

    private String content;     //内容

    private Integer sortNo = 1; //显示时的排序号，默认为1

    private Integer isMajor;

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
        StringBuffer buffer = new StringBuffer("RecordTypeQuestionEntity [");
        buffer.append("id = ").append(id)
                .append(" ,typeId = ").append(typeId)
                .append(" ,typeName = ").append(typeName)
                .append(" ,content = ").append(content)
                .append(", sortNo = ").append(sortNo)
                .append(" ,isMajor = ").append(isMajor);
        buffer.append("]");
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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


}
