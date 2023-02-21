package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 存物
 */
public class LockerEntity implements Serializable{

	private static final long serialVersionUID = 6107046760789030997L;

	private int id;
	private String param;
	private List<LockerBoxEntity> boxs=new ArrayList<LockerBoxEntity>();
	
	public void addLockerBox(LockerBoxEntity box){
		if(boxs==null){
			boxs=new ArrayList<LockerBoxEntity>();
		}
		if(box!=null){
			boxs.add(box);
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public List<LockerBoxEntity> getBoxs() {
		return boxs;
	}
	public void setBoxs(List<LockerBoxEntity> boxs) {
		this.boxs = boxs;
	}
	@Override
	public String toString() {
		return "LockerEntity [id=" + id + ", param=" + param + ", boxs=" + boxs + "]";
	}
	
	
}
