package com.zhixin.zhfz.sacw.entity;

import java.sql.Timestamp;
import java.util.Date;

public class InvolvedEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name; //名称
    private String detail_count; //数量
    private String unit;  //单位
    private double weight; //重量
    private double worth;  //价值(元)
    private String description;  //特征
    private Integer involved_type_id;  //物品类型
    private Integer input_type_id;  //入库方式:扣押、查封、冻结等
    private String barcode;  //条形码信息，自动生成
    private Integer law_case_id;  //案件信息
    private String orgName; //所属部门
    private String involved_owner;  //物品持有人
    private Integer status;  //待入库、2保管中、3已出库
    private Timestamp expired_time;  //保存时间
    private Integer register_user_id; //登记人员ID
    private String registerUserName;//登记人姓名
    private Date created_time;  //创建时间
    private Date updated_time;  //更新时间
    private Date outputTime;
    private String number; //物品编码
    private String caseNo;  //案件编号
    private String caseName;//案件名称
    private String typeName;//类型字典值
    private String fs; //入出库方式
    private Date time; //入出库时间
    private String inputCode; //入库方式字典值
    private String url; //入库图片地址
    private String outputCount;//已经出库数
    private String leftCount;//剩余数量
    private Integer type;//物品种类 1:涉案物品 2:无主物品
    private int areaId;
    private Integer shelfId;
    private Integer locationId;
    private Integer warehouseId;
    private String warehouseName;
    private String getPoliceName;
    private String getUnit;
    private String outputType;
    private Integer involvedId;
    private String uuid;
    //物品存放位置
    private String location;
    private long recordId;
    private String labelNo;
    private int labelId;
    /*  private String uuid;//物品添加时的uuid

     */
    private String op_pid;


    public String getOp_pid() {
        return op_pid;
    }

    public void setOp_pid(String op_pid) {
        this.op_pid = op_pid;
    }



    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getInvolvedId() {
        return involvedId;
    }

    public void setInvolvedId(Integer involvedId) {
        this.involvedId = involvedId;
    }

    public Date getOutputTime() {
        return outputTime;
    }

    public void setOutputTime(Date outputTime) {
        this.outputTime = outputTime;
    }

    public String getGetPoliceName() {
        return getPoliceName;
    }

    public void setGetPoliceName(String getPoliceName) {
        this.getPoliceName = getPoliceName;
    }

    public String getGetUnit() {
        return getUnit;
    }

    public void setGetUnit(String getUnit) {
        this.getUnit = getUnit;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getType() {
        return type;
    }

    public String getRegisterUserName() {
        return registerUserName;
    }

    public void setRegisterUserName(String registerUserName) {
        this.registerUserName = registerUserName;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(String outputCount) {
        this.outputCount = outputCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail_count() {
        return detail_count;
    }

    public void setDetail_count(String detail_count) {
        this.detail_count = detail_count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWorth() {
        return worth;
    }

    public void setWorth(double worth) {
        this.worth = worth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInvolved_type_id() {
        return involved_type_id;
    }

    public void setInvolved_type_id(Integer involved_type_id) {
        this.involved_type_id = involved_type_id;
    }

    public Integer getInput_type_id() {
        return input_type_id;
    }

    public void setInput_type_id(Integer input_type_id) {
        this.input_type_id = input_type_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getLaw_case_id() {
        return law_case_id;
    }

    public void setLaw_case_id(Integer law_case_id) {
        this.law_case_id = law_case_id;
    }

    public String getInvolved_owner() {
        return involved_owner;
    }

    public void setInvolved_owner(String involved_owner) {
        this.involved_owner = involved_owner;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(Timestamp expired_time) {
        this.expired_time = expired_time;
    }

    public Integer getRegister_user_id() {
        return register_user_id;
    }

    public void setRegister_user_id(Integer register_user_id) {
        this.register_user_id = register_user_id;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Date updated_time) {
        this.updated_time = updated_time;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }


    public String getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(String leftCount) {
        this.leftCount = leftCount;
    }

    public Integer getShelfId() {
        return shelfId;
    }

    public void setShelfId(Integer shelfId) {
        this.shelfId = shelfId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getLabelNo() {
        return labelNo;
    }

    public void setLabelNo(String labelNo) {
        this.labelNo = labelNo;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    @Override
    public String toString() {
        return "InvolvedEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", detail_count='" + detail_count + '\'' +
                ", unit='" + unit + '\'' +
                ", weight=" + weight +
                ", worth=" + worth +
                ", description='" + description + '\'' +
                ", involved_type_id=" + involved_type_id +
                ", input_type_id=" + input_type_id +
                ", barcode='" + barcode + '\'' +
                ", law_case_id=" + law_case_id +
                ", involved_owner='" + involved_owner + '\'' +
                ", status=" + status +
                ", expired_time=" + expired_time +
                ", register_user_id=" + register_user_id +
                ", registerUserName='" + registerUserName + '\'' +
                ", created_time=" + created_time +
                ", updated_time=" + updated_time +
                ", caseNo='" + caseNo + '\'' +
                ", caseName='" + caseName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", fs='" + fs + '\'' +
                ", time=" + time +
                ", inputCode='" + inputCode + '\'' +
                ", url='" + url + '\'' +
                ", outputCount='" + outputCount + '\'' +
                ", leftCount='" + leftCount + '\'' +
                ", type=" + type +
                ", warehouseName=" + warehouseName +
                ", orgName='" + orgName + '\'' +
                ", shelfId=" + shelfId +
                ", locationId=" + locationId +
                ", warehouseId=" + warehouseId +
                ", location='" + location + '\'' +
                ", recordId=" + recordId +
                ", labelNo='" + labelNo + '\'' +
                '}';
    }
}
