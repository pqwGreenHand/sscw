package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: zhfz
 * @description: 人员图片管理实体
 * @author: jzw
 * @create: 2019-03-07 09:23
 **/

public class RecognizePersonConfigEntity implements Serializable {
    
    /**
     * `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
     * `name` VARCHAR ( 64 ) DEFAULT NULL COMMENT '姓名',
     * `certificate_type_id` INT ( 11 ) DEFAULT '111',
     * `certificate_no` VARCHAR ( 32 ) DEFAULT NULL COMMENT '证件号码',
     * `census_register` VARCHAR ( 64 ) DEFAULT NULL COMMENT '籍贯',
     * `birth` date DEFAULT NULL,
     * `age` INT ( 3 ) NOT NULL DEFAULT '0' COMMENT '年龄',
     * `sex` INT ( 1 ) DEFAULT '1' COMMENT '0未知的性别 1男 2女 9未说明的性别',
     * `photo_type` INT ( 1 ) DEFAULT NULL COMMENT '照片类型或来源',
     * `skin` VARCHAR ( 12 ) DEFAULT NULL COMMENT '肤色 黄 偏黑 黑 白.。。',
     * `shape` VARCHAR ( 12 ) DEFAULT NULL COMMENT '体型 瘦 中等 微胖 胖',
     * `hair_length` VARCHAR ( 12 ) DEFAULT NULL COMMENT '发长',
     * `hair_color` VARCHAR ( 12 ) DEFAULT NULL COMMENT '发色',
     * `eye_style` VARCHAR ( 12 ) DEFAULT NULL COMMENT '眼型 双眼皮，单眼皮',
     * `face_style` VARCHAR ( 12 ) DEFAULT NULL COMMENT '脸型',
     * `has_picture` INT ( 1 ) DEFAULT '0' COMMENT '是否有图片，1有0无',
     * `photo_uuid` VARCHAR ( 40 ) DEFAULT NULL,
     * `other_property` VARCHAR ( 128 ) DEFAULT NULL COMMENT '其他特征',
     * `created_time` datetime NOT NULL COMMENT '创建时间',
     * `clothing` VARCHAR ( 32 ) DEFAULT NULL COMMENT'服装；帆装'
     **/


    private Long id;

    private String name;

    private Long certificateTypeId;

    private String certificateNo;

    private String censusRegister;

    private Date birth;

    private Integer age;

    private Integer sex;

    private Integer photoType;

    private String skin;

    private String shape;

    private String hairLength;

    private String hairColor;

    private String eyeStyle;

    private String faceStyle;

    private Integer hasPhoto;

    private String photoUuid;

    private String otherProperty;

    private Date createdTime;

    private String clothing;

    private String photoName;

    private String bindUp;

    public String getBindUp() {
        return bindUp;
    }

    public void setBindUp(String bindUp) {
        this.bindUp = bindUp;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
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

    public Long getCertificateTypeId() {
        return certificateTypeId;
    }

    public void setCertificateTypeId(Long certificateTypeId) {
        this.certificateTypeId = certificateTypeId;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getCensusRegister() {
        return censusRegister;
    }

    public void setCensusRegister(String censusRegister) {
        this.censusRegister = censusRegister;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getPhotoType() {
        return photoType;
    }

    public void setPhotoType(Integer photoType) {
        this.photoType = photoType;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getHairLength() {
        return hairLength;
    }

    public void setHairLength(String hairLength) {
        this.hairLength = hairLength;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getEyeStyle() {
        return eyeStyle;
    }

    public void setEyeStyle(String eyeStyle) {
        this.eyeStyle = eyeStyle;
    }

    public String getFaceStyle() {
        return faceStyle;
    }

    public void setFaceStyle(String faceStyle) {
        this.faceStyle = faceStyle;
    }

    public Integer getHasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(Integer hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public String getPhotoUuid() {
        return photoUuid;
    }

    public void setPhotoUuid(String photoUuid) {
        this.photoUuid = photoUuid;
    }

    public String getOtherProperty() {
        return otherProperty;
    }

    public void setOtherProperty(String otherProperty) {
        this.otherProperty = otherProperty;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getClothing() {
        return clothing;
    }

    public void setClothing(String clothing) {
        this.clothing = clothing;
    }

}
