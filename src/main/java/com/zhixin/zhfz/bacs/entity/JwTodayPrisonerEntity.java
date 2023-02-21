package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;


/**
 * 警务-犯人数量
 * @author wgh
 *
 */
public class JwTodayPrisonerEntity implements Serializable {

	private static final long serialVersionUID = -5103704590064454356L;


	private String type;

    private int num;
    
    public JwTodayPrisonerEntity(){}
    
    public JwTodayPrisonerEntity(String type,int num){
    	this.type = type;
    	this.num = num;
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

	@Override
	public String toString() {
		return "JwTodayCaseEntity [type=" + type + ", num=" + num + "]";
	}

    
    
	
}