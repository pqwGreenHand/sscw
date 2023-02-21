package com.zhixin.zhfz.common.restful.entity;

import java.io.Serializable;

/**
 * 物品
 * 
 * @author Admin
 *
 */
public class GoodsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3196803076544105331L;
	private Integer id;
	private String ajbh;
	private String rybh;
	private String jqbh;
	private String goodsName;// 物品名称
	private String goodsCount;// 物品数量
	private String description;// 物品描述
	private String isGet;// 是否领取
	private String getWay;// 领取方式
	private String getPerson;// 领取人
	private String getTime;// 领取时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAjbh() {
		return ajbh;
	}
	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}
	public String getRybh() {
		return rybh;
	}
	public void setRybh(String rybh) {
		this.rybh = rybh;
	}
	public String getJqbh() {
		return jqbh;
	}
	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(String goodsCount) {
		this.goodsCount = goodsCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsGet() {
		return isGet;
	}
	public void setIsGet(String isGet) {
		this.isGet = isGet;
	}
	public String getGetWay() {
		return getWay;
	}
	public void setGetWay(String getWay) {
		this.getWay = getWay;
	}
	public String getGetPerson() {
		return getPerson;
	}
	public void setGetPerson(String getPerson) {
		this.getPerson = getPerson;
	}
	public String getGetTime() {
		return getTime;
	}
	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}
	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\", \"ajbh\":\"" + ajbh + "\", \"rybh\":\"" + rybh + "\", \"jqbh\":\""+ jqbh +"\", \"goodsName\":\"" + goodsName+"\", \"goodsCount\":\"" + goodsCount+"\","
				+ "\"description\":\"" + description + "\", \"isGet\":\"" + isGet + "\", \"getWay\":\"" + getWay + "\", \"getPerson\":\""+ getPerson +"\", \"getTime\":\"" + getTime+"\","
				+ "}";
	}

	
}
