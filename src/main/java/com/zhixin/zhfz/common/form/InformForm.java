package com.zhixin.zhfz.common.form;


import java.io.Serializable;

/**
 * @prgram: zhfz
 * @Description: 通知表单参数
 * @Auther: xcf
 * @Date: 2019-04-16 18:38
 */
public class InformForm implements Serializable {

    private static final long serialVersionUID = 1916695701450960156L;

    private Long id;

    private String systemName;
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Override
    public String toString() {
        return "InformForm{" +
                "id=" + id +
                ", systemName='" + systemName + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
