package com.zhixin.zhfz.glpt.entity;

public class ChartEntity  implements java.io.Serializable{


    private String key;

    private int value;

    private String geoCoord;



    @Override
    public String toString() {
        return "ChartEntity [key=" + key + ", value=" + value +  ", geoCoord=" + geoCoord + "]";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getGeoCoord() {
        return geoCoord;
    }

    public void setGeoCoord(String geoCoord) {
        this.geoCoord = geoCoord;
    }

}
