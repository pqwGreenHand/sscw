package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class LawEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    private String type;
    private String name;
    private String chapter;
    private String content;
    private String lawNameOfBar;

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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLawNameOfBar() {
        return lawNameOfBar;
    }

    public void setLawNameOfBar(String lawNameOfBar) {
        this.lawNameOfBar = lawNameOfBar;
    }

    @Override
    public String toString() {
        return "LawEntity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", chapter='" + chapter + '\'' +
                ", content='" + content + '\'' +
                ", lawNameOfBar='" + lawNameOfBar + '\'' +
                '}';
    }
}
