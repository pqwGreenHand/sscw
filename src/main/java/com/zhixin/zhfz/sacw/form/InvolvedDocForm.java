package com.zhixin.zhfz.sacw.form;

import java.io.Serializable;

public class InvolvedDocForm implements Serializable {

    private static final long serialVersionUID = 273697158321246266L;

    private int number;//编号
    private String name;//名称
    private int type;//1:Word 2:Excel
    private long userId;
    private Long involvedId;//物品ID
    private Long recordId;//出入库记录ID

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getInvolvedId() {
        return involvedId;
    }

    public void setInvolvedId(Long involvedId) {
        this.involvedId = involvedId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        return "InvolvedDocForm{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", userId=" + userId +
                ", involvedId=" + involvedId +
                ", recordId=" + recordId +
                '}';
    }
}
