package com.zhixin.zhfz.common.restful.entity;

import java.io.Serializable;

/**
 * 标采嫌疑人信息
 * @author xdp
 *
 */
public class PersonInfoEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1293266875958560095L;

	private String id;
	
	private String certificateType;
	
	private String certificateTypeHZ;
	
	private String certificateTypeName;
	
	private String certificateNo;
	
	private String name;
	
	private String sex;
	
	private String sexHZ;
	
	private String age;
	
	private String birth;
	
	private String country;
	
	private String countryHZ;
	
	private String nation;
	
	private String nationHZ;

	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\", \"certificateType\":\"" + certificateType + "\", \"certificateTypeHZ\":\""
				+ certificateTypeHZ + "\", \"certificateTypeName\":\"" + certificateTypeName + "\", \"certificateNo\":\""
				+ certificateNo + "\", \"name\":\"" + name + "\", \"sex\":\"" + sex + "\", \"sexHZ\":\"" + sexHZ + "\", \"age\":\"" + age + "\", \"birth\":\""
				+ birth + "\", \"country\":\"" + country + "\", \"countryHZ\":\"" + countryHZ + "\", \"nation\":\"" + nation + "\", \"nationHZ\":\""
				+ nationHZ + "\"}";
	}

 

	 

	 
	
	
	
	
	
	
	
	
}
