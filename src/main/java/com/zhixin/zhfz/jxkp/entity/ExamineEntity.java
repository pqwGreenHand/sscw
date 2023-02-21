package com.zhixin.zhfz.jxkp.entity;

/**
 * Created by wangly on 2018/5/10.
 */
public class ExamineEntity {
    private Integer id;
    private Integer quotaId;
    private Integer dutypoliceId;
    private String context;
    private Integer evaId;
    private Integer pointsValue;
    private String dutyPoliceNameAndNo;
    private  String scoringStand;


    private String jbdw;
    private String jbr;
    private String shld;
    private  String ajmc;
    private String xyr;
    private String ajxz;
    private String dfz;

    private String cpyj;
    private String cprq;
    private String jyaq;
    private String xyzj;
    private String flyj;
    private String shrq;

    private String examineLeader;

    private String lawerOpinion;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLawerOpinion() {
        return lawerOpinion;
    }

    public void setLawerOpinion(String lawerOpinion) {
        this.lawerOpinion = lawerOpinion;
    }

    public String getExamineLeader() {
        return examineLeader;
    }

    public void setExamineLeader(String examineLeader) {
        this.examineLeader = examineLeader;
    }

    public String getShrq() {
        return shrq;
    }

    public void setShrq(String shrq) {
        this.shrq = shrq;
    }

    public String getCpyj() {
        return cpyj;
    }

    public void setCpyj(String cpyj) {
        this.cpyj = cpyj;
    }

    public String getCprq() {
        return cprq;
    }

    public void setCprq(String cprq) {
        this.cprq = cprq;
    }

    public String getJyaq() {
        return jyaq;
    }

    public void setJyaq(String jyaq) {
        this.jyaq = jyaq;
    }

    public String getXyzj() {
        return xyzj;
    }

    public void setXyzj(String xyzj) {
        this.xyzj = xyzj;
    }

    public String getFlyj() {
        return flyj;
    }

    public void setFlyj(String flyj) {
        this.flyj = flyj;
    }

    public String getDfz() {
        return dfz;
    }

    public void setDfz(String dfz) {
        this.dfz = dfz;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    public String getXyr() {
        return xyr;
    }

    public void setXyr(String xyr) {
        this.xyr = xyr;
    }

    public String getAjxz() {
        return ajxz;
    }

    public void setAjxz(String ajxz) {
        this.ajxz = ajxz;
    }

    public String getJbdw() {
        return jbdw;
    }

    public void setJbdw(String jbdw) {
        this.jbdw = jbdw;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public String getShld() {
        return shld;
    }

    public void setShld(String shld) {
        this.shld = shld;
    }

    public String getScoringStand() {
        return scoringStand;
    }

    public void setScoringStand(String scoringStand) {
        this.scoringStand = scoringStand;
    }

    public String getDutyPoliceNameAndNo() {
        return dutyPoliceNameAndNo;
    }

    public void setDutyPoliceNameAndNo(String dutyPoliceNameAndNo) {
        this.dutyPoliceNameAndNo = dutyPoliceNameAndNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuotaId() {
        return quotaId;
    }

    public void setQuotaId(Integer quotaId) {
        this.quotaId = quotaId;
    }

    public Integer getDutypoliceId() {
        return dutypoliceId;
    }

    public void setDutypoliceId(Integer dutypoliceId) {
        this.dutypoliceId = dutypoliceId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getEvaId() {
        return evaId;
    }

    public void setEvaId(Integer evaId) {
        this.evaId = evaId;
    }

    public Integer getPointsValue() {
        return pointsValue;
    }

    public void setPointsValue(Integer pointsValue) {
        this.pointsValue = pointsValue;
    }

    @Override
    public String toString() {
        return "ExamineEntity{" +
                "id=" + id +
                ", quotaId=" + quotaId +
                ", dutypoliceId=" + dutypoliceId +
                ", context='" + context + '\'' +
                ", evaId=" + evaId +
                ", pointsValue=" + pointsValue +
                ", dutyPoliceNameAndNo='" + dutyPoliceNameAndNo + '\'' +
                ", scoringStand='" + scoringStand + '\'' +
                ", jbdw='" + jbdw + '\'' +
                ", jbr='" + jbr + '\'' +
                ", shld='" + shld + '\'' +
                ", ajmc='" + ajmc + '\'' +
                ", xyr='" + xyr + '\'' +
                ", ajxz='" + ajxz + '\'' +
                ", dfz='" + dfz + '\'' +
                ", cpyj='" + cpyj + '\'' +
                ", cprq='" + cprq + '\'' +
                ", jyaq='" + jyaq + '\'' +
                ", xyzj='" + xyzj + '\'' +
                ", flyj='" + flyj + '\'' +
                ", shrq='" + shrq + '\'' +
                ", examineLeader='" + examineLeader + '\'' +
                ", lawerOpinion='" + lawerOpinion + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
