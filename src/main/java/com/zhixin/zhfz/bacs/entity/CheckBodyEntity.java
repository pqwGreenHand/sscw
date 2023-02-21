package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class CheckBodyEntity implements Serializable {
    /**
     * 999体检
     */
    private static final long serialVersionUID = -3819979727323886388L;

    private Integer id;

    private Long interrogateSerialId;


    private Integer checkUserId;

    private Date checkTime;

    private Integer lawCaseId;

    private Integer interrogateAreaId;

    private String interrogateAreaName;

    private String medicalHistory;

    private Integer type;

    private Integer physical;

    private String physicalDescription;

    private Integer injury;

    private String injuryDescription;

    private String otherDescription;

    private Date createdTime;

    private Date updatedTime;

    private String weight;

    private String height;

    private String footsize;

    private String health;

    private String maritalstatus;

    private String historyhealthy;

    private String drughistory;

    private String contagion;

    private String checkstatus;

    private String languagecompetence;

    private String mouthsound;

    private String physicalactivity;

    private String doctorsignature;

    private String leadersignature;

    private String sendersignature;

    private String sendunit;

    private String remark;

    private String checkspace;

    private String urinepregnancyresult;

    private String womenlastperiodtime;

    private String bodytemperature;

    private String pulse;

    private String bloodpressure;

    private String breathing;

    private String bodysize;

    private String faceshape;

    private String bloodtype;

    private String nutritionalstatus;

    private String consciousness;

    private String words;

    private String gait;

    private String doublepupil;

    private String skinsclerayellowdye;

    private String doublelung;

    private String heartrate;

    private String heartratestatus;

    private String noise;

    private String abdomen;

    private String reboundtenderness;

    private String muscletension;

    private String liverandspleen;

    private String spinallimbs;

    private String surfaceinspection;

    private String abnormalsigns;

    private String routineblood;

    private String bultrasonic;

    private String electrocardiogram;

    private String rabat;

    private String doctoradvice;
    // 链表数据
    private String name;

    private String checkName;

    private String serialNo;

    private String sex;

    private String nation;

    private String education;

    private String birth;

    private Long personId;

    private String bodyTag;

    private String selfReported;

    private String checkedSignature;

    private Date checktimeEnd ;

    private Date checktimeStart;

    private String certificateNo;
    private String checkbodyPicturePath;
    private String checkbodyPictureContent;


    @Override
    public String toString() {
        return "CheckBodyEntity{" +
                "id=" + id +
                ", interrogateSerialId=" + interrogateSerialId +
                ", checkUserId=" + checkUserId +
                ", checkTime=" + checkTime +
                ", lawCaseId=" + lawCaseId +
                ", interrogateAreaId=" + interrogateAreaId +
                ", interrogateAreaName='" + interrogateAreaName + '\'' +
                ", medicalHistory='" + medicalHistory + '\'' +
                ", type=" + type +
                ", physical=" + physical +
                ", physicalDescription='" + physicalDescription + '\'' +
                ", injury=" + injury +
                ", injuryDescription='" + injuryDescription + '\'' +
                ", otherDescription='" + otherDescription + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", footsize='" + footsize + '\'' +
                ", health='" + health + '\'' +
                ", maritalstatus='" + maritalstatus + '\'' +
                ", historyhealthy='" + historyhealthy + '\'' +
                ", drughistory='" + drughistory + '\'' +
                ", contagion='" + contagion + '\'' +
                ", checkstatus='" + checkstatus + '\'' +
                ", languagecompetence='" + languagecompetence + '\'' +
                ", mouthsound='" + mouthsound + '\'' +
                ", physicalactivity='" + physicalactivity + '\'' +
                ", doctorsignature='" + doctorsignature + '\'' +
                ", leadersignature='" + leadersignature + '\'' +
                ", sendersignature='" + sendersignature + '\'' +
                ", sendunit='" + sendunit + '\'' +
                ", remark='" + remark + '\'' +
                ", checkspace='" + checkspace + '\'' +
                ", urinepregnancyresult='" + urinepregnancyresult + '\'' +
                ", womenlastperiodtime='" + womenlastperiodtime + '\'' +
                ", bodytemperature='" + bodytemperature + '\'' +
                ", pulse='" + pulse + '\'' +
                ", bloodpressure='" + bloodpressure + '\'' +
                ", breathing='" + breathing + '\'' +
                ", bodysize='" + bodysize + '\'' +
                ", faceshape='" + faceshape + '\'' +
                ", bloodtype='" + bloodtype + '\'' +
                ", nutritionalstatus='" + nutritionalstatus + '\'' +
                ", consciousness='" + consciousness + '\'' +
                ", words='" + words + '\'' +
                ", gait='" + gait + '\'' +
                ", doublepupil='" + doublepupil + '\'' +
                ", skinsclerayellowdye='" + skinsclerayellowdye + '\'' +
                ", doublelung='" + doublelung + '\'' +
                ", heartrate='" + heartrate + '\'' +
                ", heartratestatus='" + heartratestatus + '\'' +
                ", noise='" + noise + '\'' +
                ", abdomen='" + abdomen + '\'' +
                ", reboundtenderness='" + reboundtenderness + '\'' +
                ", muscletension='" + muscletension + '\'' +
                ", liverandspleen='" + liverandspleen + '\'' +
                ", spinallimbs='" + spinallimbs + '\'' +
                ", surfaceinspection='" + surfaceinspection + '\'' +
                ", abnormalsigns='" + abnormalsigns + '\'' +
                ", routineblood='" + routineblood + '\'' +
                ", bultrasonic='" + bultrasonic + '\'' +
                ", electrocardiogram='" + electrocardiogram + '\'' +
                ", rabat='" + rabat + '\'' +
                ", doctoradvice='" + doctoradvice + '\'' +
                ", name='" + name + '\'' +
                ", checkName='" + checkName + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", sex='" + sex + '\'' +
                ", nation='" + nation + '\'' +
                ", education='" + education + '\'' +
                ", birth='" + birth + '\'' +
                ", personId=" + personId +
                ", bodyTag='" + bodyTag + '\'' +
                ", selfReported='" + selfReported + '\'' +
                ", checkedSignature='" + checkedSignature + '\'' +
                ", checktimeEnd=" + checktimeEnd +
                ", checktimeStart=" + checktimeStart +
                ", certificateNo='" + certificateNo + '\'' +
                ", checkbodyPicturePath='" + checkbodyPicturePath + '\'' +
                ", checkbodyPictureContent='" + checkbodyPictureContent + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getInterrogateSerialId() {
        return interrogateSerialId;
    }

    public void setInterrogateSerialId(Long interrogateSerialId) {
        this.interrogateSerialId = interrogateSerialId;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getLawCaseId() {
        return lawCaseId;
    }

    public void setLawCaseId(Integer lawCaseId) {
        this.lawCaseId = lawCaseId;
    }

    public Integer getInterrogateAreaId() {
        return interrogateAreaId;
    }

    public void setInterrogateAreaId(Integer interrogateAreaId) {
        this.interrogateAreaId = interrogateAreaId;
    }

    public String getInterrogateAreaName() {
        return interrogateAreaName;
    }

    public void setInterrogateAreaName(String interrogateAreaName) {
        this.interrogateAreaName = interrogateAreaName;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPhysical() {
        return physical;
    }

    public void setPhysical(Integer physical) {
        this.physical = physical;
    }

    public String getPhysicalDescription() {
        return physicalDescription;
    }

    public void setPhysicalDescription(String physicalDescription) {
        this.physicalDescription = physicalDescription;
    }

    public Integer getInjury() {
        return injury;
    }

    public void setInjury(Integer injury) {
        this.injury = injury;
    }

    public String getInjuryDescription() {
        return injuryDescription;
    }

    public void setInjuryDescription(String injuryDescription) {
        this.injuryDescription = injuryDescription;
    }

    public String getOtherDescription() {
        return otherDescription;
    }

    public void setOtherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getFootsize() {
        return footsize;
    }

    public void setFootsize(String footsize) {
        this.footsize = footsize;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public String getHistoryhealthy() {
        return historyhealthy;
    }

    public void setHistoryhealthy(String historyhealthy) {
        this.historyhealthy = historyhealthy;
    }

    public String getDrughistory() {
        return drughistory;
    }

    public void setDrughistory(String drughistory) {
        this.drughistory = drughistory;
    }

    public String getContagion() {
        return contagion;
    }

    public void setContagion(String contagion) {
        this.contagion = contagion;
    }

    public String getCheckstatus() {
        return checkstatus;
    }

    public void setCheckstatus(String checkstatus) {
        this.checkstatus = checkstatus;
    }

    public String getLanguagecompetence() {
        return languagecompetence;
    }

    public void setLanguagecompetence(String languagecompetence) {
        this.languagecompetence = languagecompetence;
    }

    public String getMouthsound() {
        return mouthsound;
    }

    public void setMouthsound(String mouthsound) {
        this.mouthsound = mouthsound;
    }

    public String getPhysicalactivity() {
        return physicalactivity;
    }

    public void setPhysicalactivity(String physicalactivity) {
        this.physicalactivity = physicalactivity;
    }

    public String getDoctorsignature() {
        return doctorsignature;
    }

    public void setDoctorsignature(String doctorsignature) {
        this.doctorsignature = doctorsignature;
    }

    public String getLeadersignature() {
        return leadersignature;
    }

    public void setLeadersignature(String leadersignature) {
        this.leadersignature = leadersignature;
    }

    public String getSendersignature() {
        return sendersignature;
    }

    public void setSendersignature(String sendersignature) {
        this.sendersignature = sendersignature;
    }

    public String getSendunit() {
        return sendunit;
    }

    public void setSendunit(String sendunit) {
        this.sendunit = sendunit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCheckspace() {
        return checkspace;
    }

    public void setCheckspace(String checkspace) {
        this.checkspace = checkspace;
    }

    public String getUrinepregnancyresult() {
        return urinepregnancyresult;
    }

    public void setUrinepregnancyresult(String urinepregnancyresult) {
        this.urinepregnancyresult = urinepregnancyresult;
    }

    public String getWomenlastperiodtime() {
        return womenlastperiodtime;
    }

    public void setWomenlastperiodtime(String womenlastperiodtime) {
        this.womenlastperiodtime = womenlastperiodtime;
    }

    public String getBodytemperature() {
        return bodytemperature;
    }

    public void setBodytemperature(String bodytemperature) {
        this.bodytemperature = bodytemperature;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getBloodpressure() {
        return bloodpressure;
    }

    public void setBloodpressure(String bloodpressure) {
        this.bloodpressure = bloodpressure;
    }

    public String getBreathing() {
        return breathing;
    }

    public void setBreathing(String breathing) {
        this.breathing = breathing;
    }

    public String getBodysize() {
        return bodysize;
    }

    public void setBodysize(String bodysize) {
        this.bodysize = bodysize;
    }

    public String getFaceshape() {
        return faceshape;
    }

    public void setFaceshape(String faceshape) {
        this.faceshape = faceshape;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }

    public String getNutritionalstatus() {
        return nutritionalstatus;
    }

    public void setNutritionalstatus(String nutritionalstatus) {
        this.nutritionalstatus = nutritionalstatus;
    }

    public String getConsciousness() {
        return consciousness;
    }

    public void setConsciousness(String consciousness) {
        this.consciousness = consciousness;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getGait() {
        return gait;
    }

    public void setGait(String gait) {
        this.gait = gait;
    }

    public String getDoublepupil() {
        return doublepupil;
    }

    public void setDoublepupil(String doublepupil) {
        this.doublepupil = doublepupil;
    }

    public String getSkinsclerayellowdye() {
        return skinsclerayellowdye;
    }

    public void setSkinsclerayellowdye(String skinsclerayellowdye) {
        this.skinsclerayellowdye = skinsclerayellowdye;
    }

    public String getDoublelung() {
        return doublelung;
    }

    public void setDoublelung(String doublelung) {
        this.doublelung = doublelung;
    }

    public String getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(String heartrate) {
        this.heartrate = heartrate;
    }

    public String getHeartratestatus() {
        return heartratestatus;
    }

    public void setHeartratestatus(String heartratestatus) {
        this.heartratestatus = heartratestatus;
    }

    public String getNoise() {
        return noise;
    }

    public void setNoise(String noise) {
        this.noise = noise;
    }

    public String getAbdomen() {
        return abdomen;
    }

    public void setAbdomen(String abdomen) {
        this.abdomen = abdomen;
    }

    public String getReboundtenderness() {
        return reboundtenderness;
    }

    public void setReboundtenderness(String reboundtenderness) {
        this.reboundtenderness = reboundtenderness;
    }

    public String getMuscletension() {
        return muscletension;
    }

    public void setMuscletension(String muscletension) {
        this.muscletension = muscletension;
    }

    public String getLiverandspleen() {
        return liverandspleen;
    }

    public void setLiverandspleen(String liverandspleen) {
        this.liverandspleen = liverandspleen;
    }

    public String getSpinallimbs() {
        return spinallimbs;
    }

    public void setSpinallimbs(String spinallimbs) {
        this.spinallimbs = spinallimbs;
    }

    public String getSurfaceinspection() {
        return surfaceinspection;
    }

    public void setSurfaceinspection(String surfaceinspection) {
        this.surfaceinspection = surfaceinspection;
    }

    public String getAbnormalsigns() {
        return abnormalsigns;
    }

    public void setAbnormalsigns(String abnormalsigns) {
        this.abnormalsigns = abnormalsigns;
    }

    public String getRoutineblood() {
        return routineblood;
    }

    public void setRoutineblood(String routineblood) {
        this.routineblood = routineblood;
    }

    public String getBultrasonic() {
        return bultrasonic;
    }

    public void setBultrasonic(String bultrasonic) {
        this.bultrasonic = bultrasonic;
    }

    public String getElectrocardiogram() {
        return electrocardiogram;
    }

    public void setElectrocardiogram(String electrocardiogram) {
        this.electrocardiogram = electrocardiogram;
    }

    public String getRabat() {
        return rabat;
    }

    public void setRabat(String rabat) {
        this.rabat = rabat;
    }

    public String getDoctoradvice() {
        return doctoradvice;
    }

    public void setDoctoradvice(String doctoradvice) {
        this.doctoradvice = doctoradvice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getBodyTag() {
        return bodyTag;
    }

    public void setBodyTag(String bodyTag) {
        this.bodyTag = bodyTag;
    }

    public String getSelfReported() {
        return selfReported;
    }

    public void setSelfReported(String selfReported) {
        this.selfReported = selfReported;
    }

    public String getCheckedSignature() {
        return checkedSignature;
    }

    public void setCheckedSignature(String checkedSignature) {
        this.checkedSignature = checkedSignature;
    }

    public Date getChecktimeEnd() {
        return checktimeEnd;
    }

    public void setChecktimeEnd(Date checktimeEnd) {
        this.checktimeEnd = checktimeEnd;
    }

    public Date getChecktimeStart() {
        return checktimeStart;
    }

    public void setChecktimeStart(Date checktimeStart) {
        this.checktimeStart = checktimeStart;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getCheckbodyPicturePath() {
        return checkbodyPicturePath;
    }

    public void setCheckbodyPicturePath(String checkbodyPicturePath) {
        this.checkbodyPicturePath = checkbodyPicturePath;
    }

    public String getCheckbodyPictureContent() {
        return checkbodyPictureContent;
    }

    public void setCheckbodyPictureContent(String checkbodyPictureContent) {
        this.checkbodyPictureContent = checkbodyPictureContent;
    }
}
