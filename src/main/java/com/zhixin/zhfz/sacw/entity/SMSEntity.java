package com.zhixin.zhfz.sacw.entity;

import java.util.Arrays;

public class SMSEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String[] phones;
    private String message;

    @Override
    public String toString() {
        return "SMSEntity [phones=" + Arrays.toString(phones) + ", message=" + message + "]";
    }

    public String[] getPhones() {
        return phones;
    }

    public void setPhones(String[] phones) {
        this.phones = phones;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
