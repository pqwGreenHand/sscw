package com.zhixin.zhfz.sacw.form;

import java.util.Date;

public class InvolvedForm implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name; //名称
    private String detail_count; //数量
    private String unit;  //单位
    private double weight; //重量
    private double worth;  //价值(元)
    private String number;
    private String description;  //特征
    private Integer involved_type_id;  //物品类型
    private Integer input_type_id;  //入库方式:扣押、查封、冻结等
    private String barcode;  //条形码信息，自动生成
    private Integer law_case_id;  //案件信息
    private String involved_owner;  //物品持有人
    private Integer status;  //待入库、2保管中、3已出库
    private String expired_time;  //保存时间
    private Integer register_user_id; //登记人员ID
    private Date created_time;  //创建时间
    private Date updated_time;  //更新时间
    private Integer warehouseId;

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(String expired_time) {
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

    @Override
    public String toString() {
        return "InvolvedEntity [id=" + id + ", name=" + name + ", detail_count=" + detail_count + ", unit=" + unit
                + ", weight=" + weight + ", worth=" + worth + ", description=" + description + ", involved_type_id="
                + involved_type_id + ", input_type_id=" + input_type_id + ", barcode=" + barcode + ", law_case_id="
                + law_case_id + ", involved_owner=" + involved_owner + ", status=" + status + ", expired_time="
                + expired_time + ", register_user_id=" + register_user_id + ", created_time=" + created_time
                + ", updated_time=" + updated_time + "]";
    }


}
