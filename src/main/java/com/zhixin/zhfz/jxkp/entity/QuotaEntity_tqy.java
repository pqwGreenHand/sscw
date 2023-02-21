package com.zhixin.zhfz.jxkp.entity;

public class QuotaEntity_tqy {
	private int id;
	private String item;
	private String scoring_stand;
	private int pointsvalue;
	private int type;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getScoring_stand() {
		return scoring_stand;
	}
	public void setScoring_stand(String scoring_stand) {
		this.scoring_stand = scoring_stand;
	}
	public int getPointsvalue() {
		return pointsvalue;
	}
	public void setPointsvalue(int pointsvalue) {
		this.pointsvalue = pointsvalue;
	}
	public QuotaEntity_tqy(int id, String item, String scoring_stand, int pointsvalue,int type) {
		super();
		this.id = id;
		this.item = item;
		this.scoring_stand = scoring_stand;
		this.pointsvalue = pointsvalue;
		this.type = type;
	}
	public QuotaEntity_tqy() {}
	
}
