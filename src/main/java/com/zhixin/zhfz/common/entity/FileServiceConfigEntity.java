package com.zhixin.zhfz.common.entity;

import java.io.Serializable;

/**
 * @program: zhfz
 * @description: 文件服务
 * @author: jzw
 * @create: 2019-03-11 11:16
 **/

public class FileServiceConfigEntity implements Serializable {

    /**
     * `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
     * `sys_type` VARCHAR ( 32 ) NOT NULL COMMENT '系统类型 aj 案件 ba 办案场所 sa 涉案 jg 卷管',
     * `sys_id` INT ( 11 ) DEFAULT NULL COMMENT '场所id',
     * `service_url` VARCHAR ( 256 ) NOT NULL COMMENT '文件服务地址',
     * `file_save_path` VARCHAR ( 256 ) NOT NULL COMMENT '文件保存地址',
     * `desc` VARCHAR ( 255 ) DEFAULT NULL COMMENT '描述',
     * `is_enable` INT ( 1 ) DEFAULT '1' COMMENT '是否可以用',
     **/

    private Long id;

    private String sysType;

    private Long sysId;

    private String serviceUrl;

    private String fileSavePath;

    private String desc;

    private Integer isEnable;

    private String areaName;

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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getFileSavePath() {
        return fileSavePath;
    }

    public void setFileSavePath(String fileSavePath) {
        this.fileSavePath = fileSavePath;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }


}
