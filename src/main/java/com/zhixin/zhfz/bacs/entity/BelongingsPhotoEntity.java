package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

/**
 * 随身物品照片
 */
public class BelongingsPhotoEntity {

    private long id;
    private long belongingsId;     //随身储物主表id
    private String url;                //物品拍照图片路径
    private String uuid;                //uuid
    private String webUrl;
    private int areaId;                //办案中心id
    private Date createdTime;        //创建时间
    private Date updatedTime;        //修改时间

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBelongingsId() {
        return belongingsId;
    }

    public void setBelongingsId(long belongingsId) {
        this.belongingsId = belongingsId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "BelongingsPhotoEntity{" +
                "id=" + id +
                ", belongingsId=" + belongingsId +
                ", url='" + url + '\'' +
                ", uuid='" + uuid + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", areaId=" + areaId +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
