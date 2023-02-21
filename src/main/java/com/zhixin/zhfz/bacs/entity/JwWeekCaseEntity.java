package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;


/**
 * 警务-案件数量
 * @author wgh
 *
 */
public class JwWeekCaseEntity implements Serializable {

	private static final long serialVersionUID = -5103704590064454356L;


	private String type;

    private int num;
    
    private String date;
    
    public JwWeekCaseEntity(){}
    
    public JwWeekCaseEntity(String type,int num,String date){
    	this.type = type;
    	this.num = num;
    	this.date = date;
    }
    

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "JwWeekCaseEntity [type=" + type + ", num=" + num + ", date=" + date + "]";
	}

	
	
}