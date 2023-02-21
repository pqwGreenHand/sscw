package com.zhixin.zhfz.common.form;

import java.util.HashSet;
import java.util.Set;


public class UserRoleForm {

	private static final long serialVersionUID = 1L;

	private Integer userId;

    private Integer roleId;
    
    private String roleIDSet;

    private Set<Integer> userRoleSet;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleIDSet() {
		return roleIDSet;
	}

	public void setRoleIDSet(String roleIDSet) {
		this.roleIDSet = roleIDSet;
	}

	public Set<Integer> getUserRoleSet() {
		if(userRoleSet == null){
			userRoleSet = new HashSet<Integer>();
		}else{
			userRoleSet.clear();
		}
		if(roleIDSet!=null && !roleIDSet.isEmpty()){
			System.out.println("获取选择角色id集合   roleIDSet ==="+roleIDSet);
//			String [] ss = roleIDSet.split(",", -1);
			String [] ss = roleIDSet.split(",");
			for(String s :ss ){
				if(s !=null && !s.isEmpty()){
					userRoleSet.add(Integer.valueOf(s));
				}
			}
		}
		return userRoleSet;
	}

	public void setUserRoleSet(Set<Integer> userRoleSet) {
		this.userRoleSet = userRoleSet;
	}

	@Override
	public String toString() {
		return "UserRoleForm [userId=" + userId + ", roleId=" + roleId + ", roleIDSet=" + roleIDSet + ", userRoleSet="
				+ userRoleSet + "]";
	}
    
    
	
}
