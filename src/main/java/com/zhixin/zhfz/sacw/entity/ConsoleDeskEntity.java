package com.zhixin.zhfz.sacw.entity;

import java.io.Serializable;
import java.util.Date;

/*
控制台展示统计数据
 */
public class ConsoleDeskEntity implements Serializable {


    private static final long serialVersionUID = 1L;
    private Integer id;
    private long total;//总数<id column="id" property="id" />
    private long drk;//待入库数量
    private long bgz;//保存中数量

    private long yck;//已出库
    private long jcy;//检察院

    private long fy;//法院
    private long ls;//临时出库

    private int orgId;//所属单位id
    private int status;//状态

    private String name;//物品名称
    private String wareName;//仓库名称

    private String orgName;//所属单位名字
    private Date created_time;

    private int detail_count;
    private String unit;

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getDrk() {
        return drk;
    }

    public void setDrk(long drk) {
        this.drk = drk;
    }

    public long getBgz() {
        return bgz;
    }

    public void setBgz(long bgz) {
        this.bgz = bgz;
    }

    public long getYck() {
        return yck;
    }

    public void setYck(long yck) {
        this.yck = yck;
    }

    public long getJcy() {
        return jcy;
    }

    public void setJcy(long jcy) {
        this.jcy = jcy;
    }

    public long getFy() {
        return fy;
    }

    public void setFy(long fy) {
        this.fy = fy;
    }

    public long getLs() {
        return ls;
    }

    public void setLs(long ls) {
        this.ls = ls;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getDetail_count() {
        return detail_count;
    }

    public void setDetail_count(int detail_count) {
        this.detail_count = detail_count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "ConsoleDeskEntity{" +
                "id=" + id +
                ", total=" + total +
                ", drk=" + drk +
                ", bgz=" + bgz +
                ", yck=" + yck +
                ", jcy=" + jcy +
                ", fy=" + fy +
                ", ls=" + ls +
                ", orgId=" + orgId +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", wareName='" + wareName + '\'' +
                ", orgName='" + orgName + '\'' +
                '}';
    }
}
