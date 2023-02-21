package com.zhixin.zhfz.bacs.entity;

import java.io.Serializable;

/*
 * 手环表单
 * */
public class OneToOneForm implements Serializable {
	
	private static final long serialVersionUID = 5577128090368009959L;

    private String path1;
    
    private String path2;

	public String getPath1() {
		return path1;
	}

	public void setPath1(String path1) {
		this.path1 = path1;
	}

	public String getPath2() {
		return path2;
	}

	public void setPath2(String path2) {
		this.path2 = path2;
	} 
}
