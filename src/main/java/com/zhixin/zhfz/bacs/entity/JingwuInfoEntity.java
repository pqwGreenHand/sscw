package com.zhixin.zhfz.bacs.entity;

import com.zhixin.zhfz.common.entity.OrganizationEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * 警务信息
 * */
public class JingwuInfoEntity implements Serializable {

	private static final long serialVersionUID = -5103704590064454356L;


	private Integer id;

    private String orgName;

    private List<JwWeekCaseEntity> weekCases=new ArrayList<JwWeekCaseEntity>();
    
    private List<JwTodayPrisonerEntity> todayPrisoners=new ArrayList<JwTodayPrisonerEntity>();
    
    public JingwuInfoEntity(){
    }
    
    public JingwuInfoEntity(OrganizationEntity org){
    	if(org!=null){
    		this.id=org.getId();
    		this.orgName=org.getOrganization();
    	}
    }
    
    public void addWeekCase(JwWeekCaseEntity entity){
    	if(weekCases==null){
    		weekCases=new ArrayList<JwWeekCaseEntity>();
    	}
    	weekCases.add(entity);
    }
    
    public void addTodayPrisoner(JwTodayPrisonerEntity entity){
    	if(todayPrisoners==null){
    		todayPrisoners=new ArrayList<JwTodayPrisonerEntity>();
    	}
    	todayPrisoners.add(entity);
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<JwWeekCaseEntity> getWeekCases() {
		return weekCases;
	}

	public void setWeekCases(List<JwWeekCaseEntity> weekCases) {
		this.weekCases = weekCases;
	}

	public List<JwTodayPrisonerEntity> getTodayPrisoners() {
		return todayPrisoners;
	}

	public void setTodayPrisoners(List<JwTodayPrisonerEntity> todayPrisoners) {
		this.todayPrisoners = todayPrisoners;
	}

	@Override
	public String toString() {
		return "JingwuInfoEntity [id=" + id + ", orgName=" + orgName + ", weekCases=" + weekCases + ", todayPrisoners="
				+ todayPrisoners + "]";
	}
	
    
    
}