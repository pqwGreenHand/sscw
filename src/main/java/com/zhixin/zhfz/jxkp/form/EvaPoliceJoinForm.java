package com.zhixin.zhfz.jxkp.form;

/**
 * Created by wangly on 2018/5/10.
 */
public class EvaPoliceJoinForm {
private String policeJoinId;
private String evaId;

    public String getPoliceJoinId() {
        return policeJoinId;
    }

    public void setPoliceJoinId(String policeJoinId) {
        this.policeJoinId = policeJoinId;
    }

    public String getEvaId() {
        return evaId;
    }

    public void setEvaId(String evaId) {
        this.evaId = evaId;
    }

    @Override
    public String toString() {
        return "EvaPoliceJoinForm{" +
                "policeJoinId='" + policeJoinId + '\'' +
                ", evaId='" + evaId + '\'' +
                '}';
    }
}
