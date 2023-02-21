package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;

public class LawForm implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String type;
    private String name;
    private String chapter;
    private String content;

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

    @Override
    public String toString() {
        return "LawForm{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", chapter='" + chapter + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
