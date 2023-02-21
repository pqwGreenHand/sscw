package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.Date;

public class RoomEntity implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3630382802534105699L;
	
	public static final String INSERT_TYPE = "add";
	public static final String EDIT_TYPE = "edit";
	public static final String DELETE_TYPE = "delete";
	public static final String RMI_TYPE = "rmi";
	public static final String UPLOAD_TYPE = "upload";
	public static final String DOWNLOAD_TYPE = "download";
	public static final String OTHER_TYPE = "other";


	private Integer id;

    private String name;

    private Integer roomTypeId;

    private Integer roomGroupId;

    private String description;

    private Integer interrogateAreaId;

    private Date createdTime;

    private Date updatedTime;
    
    private String type;
    
    private String gname;

    private String axis;

    private String triggerNo;
    
    private Integer sid;
    
    private String ips;
    
    private Integer volume;
    
    private String ledAddress;
    
    private Integer countNum;//仅批量添加统计用

	private String opPid;
	private String tdRoomId;
	private Integer opUserId;

	public String getTdRoomId() {
		return tdRoomId;
	}

	public void setTdRoomId(String tdRoomId) {
		this.tdRoomId = tdRoomId;
	}

	public String getOpPid() {
		return opPid;
	}

	public void setOpPid(String opPid) {
		this.opPid = opPid;
	}

	public Integer getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(Integer opUserId) {
		this.opUserId = opUserId;
	}
	public String getAxis() {
		return axis;
	}

	public void setAxis(String axis) {
		this.axis = axis;
	}

	public String getTriggerNo() {
		return triggerNo;
	}

	public void setTriggerNo(String triggerNo) {
		this.triggerNo = triggerNo;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public String getLedAddress() {
		return ledAddress;
	}

	public void setLedAddress(String ledAddress) {
		this.ledAddress = ledAddress;
	}

	private Integer isActive;
    public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "RoomEntity [id=" + id + ", name=" + name + ", roomTypeId=" + roomTypeId + ", roomGroupId=" + roomGroupId
				+ ", description=" + description + ", interrogateAreaId=" + interrogateAreaId + ", createdTime="
				+ createdTime + ", updatedTime=" + updatedTime + ", type=" + type + ", gname=" + gname + ", sid=" + sid
				+ ", ips=" + ips + ", volume=" + volume + ", ledAddress=" + ledAddress + ", countNum=" + countNum
				+ ", isActive=" + isActive + "]";
	}

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getRoomGroupId() {
        return roomGroupId;
    }

    public void setRoomGroupId(Integer roomGroupId) {
        this.roomGroupId = roomGroupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getInterrogateAreaId() {
        return interrogateAreaId;
    }

    public void setInterrogateAreaId(Integer interrogateAreaId) {
        this.interrogateAreaId = interrogateAreaId;
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

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCountNum() {
		return countNum;
	}

	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}
	
	
}