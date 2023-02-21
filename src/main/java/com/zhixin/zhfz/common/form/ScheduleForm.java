package com.zhixin.zhfz.common.form;

import java.io.Serializable;

/**
 * @prgram: zhfz
 * @Description: 待办页面参数
 * @Auther: xcf
 * @Date: 2019-04-16 21:43
 */
public class ScheduleForm implements Serializable {

    private static final long serialVersionUID = 4951111363545842390L;

    private Long id;

    private String systemName;

    private String handlerContent;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getHandlerContent() {
        return handlerContent;
    }

    public void setHandlerContent(String handlerContent) {
        this.handlerContent = handlerContent;
    }
}
