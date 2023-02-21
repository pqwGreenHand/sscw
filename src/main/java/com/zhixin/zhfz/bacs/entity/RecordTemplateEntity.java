package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

/**
 * @program: zhfz
 * @description: 笔录word模板实体
 * @author: jzw
 * @create: 2019-02-21 10:27
 **/

public class RecordTemplateEntity implements java.io.Serializable,Cloneable  {
    /**
     * `id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT,
     * 	`name` VARCHAR ( 100 ) NOT NULL,
     * 	`word` MEDIUMBLOB,
     * 	`content` text COMMENT '问答内容',
     * 	`sort_no` INT ( 11 ) DEFAULT NULL COMMENT '访问热度',
     * 	`type` INT ( 1 ) DEFAULT '0' COMMENT '0是公用模板，1是个人模板',
     * 	`user_id` INT ( 11 ) DEFAULT NULL,
     * 	`created_time` datetime NOT NULL COMMENT '创建时间',
     **/

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;        //文档名

    private byte[] word;        //word文件流

    private String content;     //文档内容

    private Integer sortNo;     //访问热度

    private Date createdTime;   //创建时间（数据库自动生成）

    private Integer type;

    private Long userId;

    private String userName;

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
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getWord() {
        return word;
    }

    public void setWord(byte[] word) {
        this.word = word;
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
