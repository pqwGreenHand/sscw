package com.zhixin.zhfz.common.form;

import java.util.ArrayList;
import java.util.List;

public class IDForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	@Override
	public String toString() {
		return "IDForm [id=" + id + "]";
	}
	private Integer cid;
	
	private Integer tid;	
	public int getIntID() {
		return Integer.parseInt(this.id);
	}

	public long getLongID() {
		return Long.parseLong(this.id);
	}

	public double getDoubleID() {
		return Double.parseDouble(this.id);
	}

	public float getFloatID() {
		return Float.parseFloat(this.id);
	}
	
	
	public List<String> getIDs(String splist)
	{
		List<String> list=new ArrayList<String>();
		if(id!=null)
		{
			String ids[]=id.split(splist);
			for(String s:ids)
			{
				if(s!=null && !"".equals(s.trim()))
				{
					list.add(s);
				}
			}
		}
		
		return list;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

}
