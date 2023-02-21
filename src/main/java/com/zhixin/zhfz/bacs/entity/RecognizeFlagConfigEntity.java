package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: zhfz
 * @description: 人员图片管理实体
 * @author: jzw
 * @create: 2019-03-07 09:23
 **/

public class RecognizeFlagConfigEntity implements Serializable {

    /**
     *  `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
     * 	`type` VARCHAR ( 35 ) NOT NULL,
     * 	`key_name` VARCHAR ( 64 ) DEFAULT NULL COMMENT '类型',
     * 	`key_value` INT ( 11 ) DEFAULT '0',
     * 	`sort_no` INT ( 2 ) DEFAULT '0',
     */

    private Long id;

    private String type;

    private String keyName;

    private String keyValue;

    private Long sortNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public Long getSortNo() {
        return sortNo;
    }

    public void setSortNo(Long sortNo) {
        this.sortNo = sortNo;
    }
}
