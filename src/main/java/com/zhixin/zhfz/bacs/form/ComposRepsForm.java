package com.zhixin.zhfz.bacs.form;

import java.io.Serializable;

/**
 * @program: zhfz
 * @description: 合成作战返回实体
 * @author: xcf
 * @create: 2019-05-28 16:59
 **/

public class ComposRepsForm  {

    private String imgSrc;

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    @Override
    public String toString() {
        return "ComposRepsForm{" + "imgSrc='" + imgSrc + '\'' + '}';
    }
}
