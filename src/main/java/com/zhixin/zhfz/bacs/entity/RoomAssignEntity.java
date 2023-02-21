package com.zhixin.zhfz.bacs.entity;

import java.util.Date;

public class RoomAssignEntity {

    /**
     * CREATE TABLE `ba_room_assign` (
     * `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
     * `serial_id` INT ( 11 ) NOT NULL,
     * `room_id` INT ( 11 ) NOT NULL COMMENT '0登记室,1走廊,2三合一室,3研判室,4询问室,5讯问室,6醒酒室,7辨认室,8等候室,卫生间,信息采集室,人身安全检查室,暂存物品保管室,值班室,刻录室',
     * `status` INT ( 11 ) NOT NULL,
     * `police_id` INT ( 11 ) NOT NULL,
     * `created_time` datetime NOT NULL COMMENT '创建时间',
     */

    private Long id;

    private Long serialId;

    private Long roomId;

    private Integer status;

    private Long policeId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPoliceId() {
        return policeId;
    }

    public void setPoliceId(Long policeId) {
        this.policeId = policeId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
