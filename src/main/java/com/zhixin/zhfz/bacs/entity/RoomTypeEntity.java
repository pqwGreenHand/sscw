package com.zhixin.zhfz.bacs.entity;

public class RoomTypeEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	//ba_room_type表新增字段op_pid,op_user_id
	private String op_pid;//当前操作人员所在单位PID  1_2_3
	private int op_user_id;//当前操作人员ID 

	@Override
	public String toString() {
		return "RoomTypeEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", op_pid='" + op_pid + '\'' +
				", op_user_id=" + op_user_id +
				'}';
	}

	public String getOp_pid() {
		return op_pid;
	}

	public void setOp_pid(String op_pid) {
		this.op_pid = op_pid;
	}

	public int getOp_user_id() {
		return op_user_id;
	}

	public void setOp_user_id(int op_user_id) {
		this.op_user_id = op_user_id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
