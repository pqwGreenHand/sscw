package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

public class StatisticsEntity implements Serializable{
	
	private static final long serialVersionUID = 4914813963038328730L;
	
	private int id;

	private String date;

    private int total;
    
    private int type;//0 刑事 1 行政
    
    private int interrogateAreaId;
    
    private String betweenTime;
    
    private String betweenTimeMax;

	@Override
	public String toString() {
		return "StatisticsEntity{" +
				"id=" + id +
				", date='" + date + '\'' +
				", total=" + total +
				", type=" + type +
				", interrogateAreaId=" + interrogateAreaId +
				", betweenTime='" + betweenTime + '\'' +
				", betweenTimeMax='" + betweenTimeMax + '\'' +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getInterrogateAreaId() {
		return interrogateAreaId;
	}

	public void setInterrogateAreaId(int interrogateAreaId) {
		this.interrogateAreaId = interrogateAreaId;
	}

	public String getBetweenTime() {
		return betweenTime;
	}

	public void setBetweenTime(String betweenTime) {
		this.betweenTime = betweenTime;
	}

	public String getBetweenTimeMax() {
		return betweenTimeMax;
	}

	public void setBetweenTimeMax(String betweenTimeMax) {
		this.betweenTimeMax = betweenTimeMax;
	}
    
    


}
