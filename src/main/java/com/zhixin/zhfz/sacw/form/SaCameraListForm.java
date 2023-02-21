package com.zhixin.zhfz.sacw.form;

public class SaCameraListForm {
    private static final long serialVersionUID = 1L;
    private String[] name;
    private String[] ip;
    private String[] channel;
    private String[] camera_no;
    private Integer[] type;
    private String[] screen;
    private Integer[] download;
    private Integer[] location_id;


    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String[] getIp() {
        return ip;
    }

    public void setIp(String[] ip) {
        this.ip = ip;
    }

    public String[] getChannel() {
        return channel;
    }

    public void setChannel(String[] channel) {
        this.channel = channel;
    }

    public String[] getCamera_no() {
        return camera_no;
    }

    public void setCamera_no(String[] camera_no) {
        this.camera_no = camera_no;
    }

    public Integer[] getType() {
        return type;
    }

    public void setType(Integer[] type) {
        this.type = type;
    }

    public String[] getScreen() {
        return screen;
    }

    public void setScreen(String[] screen) {
        this.screen = screen;
    }

    public Integer[] getDownload() {
        return download;
    }

    public void setDownload(Integer[] download) {
        this.download = download;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer[] getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer[] location_id) {
        this.location_id = location_id;
    }
}
