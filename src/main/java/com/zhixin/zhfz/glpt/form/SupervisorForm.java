package com.zhixin.zhfz.glpt.form;

public class SupervisorForm {

    private Long id;

    private Integer status;

    private Long requestId;

    private String auditOrgId;

    private String auditContent;

    private Long auditUserId;

    private String opUserId;

    private Integer count;

    public SupervisorForm(){}

    public SupervisorForm(Long id, Integer status){
        this.id = id;
        this.status = status;
    }

    public static SupervisorForm of(Long id, Integer status){
        return new SupervisorForm(id,status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getAuditContent() {
        return auditContent;
    }

    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent;
    }

    public String getAuditOrgId() {
        return auditOrgId;
    }

    public void setAuditOrgId(String auditOrgId) {
        this.auditOrgId = auditOrgId;
    }

    public Long getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "SupervisorForm{" +
                "id=" + id +
                ", status=" + status +
                ", requestId=" + requestId +
                ", auditOrgId='" + auditOrgId + '\'' +
                ", auditContent='" + auditContent + '\'' +
                ", auditUserId=" + auditUserId +
                ", opUserId='" + opUserId + '\'' +
                ", count=" + count +
                '}';
    }
}
