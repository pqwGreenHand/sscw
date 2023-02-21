package com.zhixin.zhfz.common.entity;

public class ComboboxEntity {

	private String id = "";

	private String value = "";
	
	private String groupName;
	
	private String status;

	public ComboboxEntity() {
	}

	public ComboboxEntity(String id, String value) {
		this.id = id;
		this.value = value;
	}

	public ComboboxEntity(Long id, String value) {
		this.id = id.toString();
		this.value = value;
	}
	
	

	@Override
	public String toString() {
		return "ComboboxEntity [id=" + id + ", value=" + value + ", groupName=" + groupName + ", status=" + status
				+ "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
