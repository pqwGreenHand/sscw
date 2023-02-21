package com.zhixin.zhfz.glpt.form;

import java.io.Serializable;
import java.util.Date;

/**
 * @prgram: zhfz
 * @Description: 人员查询页面返回参数
 * @Auther: xcf
 * @Date: 2019-04-18 15:08
 */
public class PersonTrialResp implements Serializable {

    private static final long serialVersionUID = 7665782377527760822L;

    private String fs;

    private Date fsTime;

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public Date getFsTime() {
        return fsTime;
    }

    public void setFsTime(Date fsTime) {
        this.fsTime = fsTime;
    }

    @Override
    public String toString() {
        return "PersonTrialResp{" +
                "fs='" + fs + '\'' +
                ", fsTime=" + fsTime +
                '}';
    }
}
