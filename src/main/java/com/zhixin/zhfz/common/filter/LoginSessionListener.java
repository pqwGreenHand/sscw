package com.zhixin.zhfz.common.filter;

import com.zhixin.zhfz.common.entity.SessionInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class LoginSessionListener implements HttpSessionListener {

	private static Map<String,HttpSession> sessionMap = new ConcurrentHashMap<String,HttpSession>();  

	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession ss=event.getSession();
		sessionMap.put(ss.getId(), ss);
		
		ServletContext ctx = event.getSession().getServletContext();  
		HttpSessionContext httpSessionContext=event.getSession().getSessionContext();
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession ss=event.getSession();
		sessionMap.remove(ss.getId());
	}
	
	public synchronized static List<HttpSession> getAllSession(){
		List<HttpSession> list=new ArrayList<HttpSession>();
		list.addAll(sessionMap.values());
		return list;
	}

	public synchronized static Map<String,AtomicInteger> getOnlineUserRoleCount(){
		Map<String,AtomicInteger> map=new HashMap<String,AtomicInteger>();
		for(HttpSession session:sessionMap.values()){
			SessionInfo info = (SessionInfo) session.getAttribute("sessionInfo");
			if (info != null && info.getRole()!=null && info.getRole().getName()!=null) {
				AtomicInteger ai=map.get(info.getRole().getName());
				if(ai==null){
					ai=new AtomicInteger(1);
					map.put(info.getRole().getName(), ai);
				}else{
					ai.addAndGet(1);
				}
			}
		}
		return map;
	}
}
