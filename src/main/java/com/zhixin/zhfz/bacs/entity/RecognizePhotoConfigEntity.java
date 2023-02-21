package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: zhfz
 * @description: 辨认人员相片
 * @author: jzw
 * @create: 2019-03-08 14:28
 **/

public class RecognizePhotoConfigEntity implements Serializable {

    /**
     * `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
     * `uuid` VARCHAR ( 38 ) NOT NULL,
     * `name` VARCHAR ( 40 ) DEFAULT NULL COMMENT '姓名',
     * `path` VARCHAR ( 128 ) DEFAULT NULL COMMENT '证件号码',
     * `description` VARCHAR ( 128 ) DEFAULT '0',
     * `created_time` datetime NOT NULL COMMENT '创建时间',
     **/
    private Long id;

    private String uuid;

    private String name;

    private String url;

    private String description;

    private Date createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
