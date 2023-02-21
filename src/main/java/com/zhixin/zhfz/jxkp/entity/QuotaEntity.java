package com.zhixin.zhfz.jxkp.entity;

/**
 * Created by wangly on 2018/5/2.
 */
public class QuotaEntity {
    private Integer id;
    private String Item;
    private String ScoringStand;
    private Integer PointsValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getScoringStand() {
        return ScoringStand;
    }

    public void setScoringStand(String scoringStand) {
        ScoringStand = scoringStand;
    }

    public Integer getPointsValue() {
        return PointsValue;
    }

    public void setPointsValue(Integer pointsValue) {
        PointsValue = pointsValue;
    }

    @Override
    public String toString() {
        return "QuotaEntity{" +
                "id=" + id +
                ", Item='" + Item + '\'' +
                ", ScoringStand='" + ScoringStand + '\'' +
                ", PointsValue=" + PointsValue +
                '}';
    }
}
