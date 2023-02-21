package com.zhixin.zhfz.common.controller.common;

import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.UserForm;
import com.zhixin.zhfz.common.services.authority.IAuthorityService;
import com.zhixin.zhfz.common.services.common.ICommonService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PowerCacheUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 各种公用list方法 不要都写UserAuthorityInterceptor例外里
 * 
 * @author wgh
 *
 */
@Controller
@RequestMapping(value = "/common")
public class CommonController {

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private IAuthorityService authorityService;

	@Autowired
	private ICommonService commonService;

	@Autowired
	private PowerCacheUtil powerCacheUtil=null;

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/getSessionInfo")
	@ResponseBody
	public SessionInfo getSessionInfo(HttpServletRequest request, HttpServletResponse response) {
		return ControllerTool.getSessionInfo(request);
	}


	@RequestMapping(value = "/listSystemMenu")
	@ResponseBody
	public List<AuthorityEntity> listSystemMenu(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return new PowerCacheUtil().getUserAllSysAutontity(Long.parseLong(ControllerTool.getUserID(request)+""));
	}

	// 根据user和系统权限 查询权限下的菜单
	@RequestMapping(value = "/listMenuByPidMvw")
	@ResponseBody
	public void listMenuByPidMvw(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("===查询菜单列表===" + request.getParameter("pid"));
		ModelAndView mvw=new ModelAndView("newpage/indexMain");
		Long pid = Long.parseLong(request.getParameter("pid")+"");
		List<MenuTreeEntity> value = new ArrayList();
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		Integer userid = sessionInfo.getUser().getId();
		List<AuthorityEntity> listmenu = authorityService.getAuthorityDataById(userid, pid);
		if(listmenu!=null &&listmenu.size()>0){
			//排序
			Collections.sort(listmenu, new Comparator<AuthorityEntity>() {
				public int compare(AuthorityEntity arg0, AuthorityEntity arg1) {
					return arg0.getId().compareTo(arg1.getId());
				}
			});
			for (int i = 0; i < listmenu.size(); i++) {
				AuthorityEntity authorityEntity = listmenu.get(i);
				if (authorityEntity.getParentId() == pid) {
					MenuTreeEntity mt = new MenuTreeEntity();
					mt.setId(authorityEntity.getId());
					mt.setParentTitle(authorityEntity.getTitle());
					mt.setSortNum(authorityEntity.getSortNumber().intValue());
					List<LowerMenuEntiry> lms = new ArrayList<>();
					for (AuthorityEntity ae : listmenu) {
						if (ae.getParentId().equals(authorityEntity.getId())) {
							LowerMenuEntiry lm = new LowerMenuEntiry();
							lm.setId(ae.getId());
							lm.setUrl(ae.getUrl());
							lm.setTitle(ae.getTitle());
							lm.setJsMethod(ae.getJsMethod());
							lm.setSortNum(ae.getSortNumber().intValue());
							lms.add(lm);
						}
					}
					mt.setLowerMenu(lms);
					value.add(mt);

				}
			}
		}
		//排序
		Collections.sort(value, new Comparator<MenuTreeEntity>() {
			@Override
			public int compare(MenuTreeEntity o1, MenuTreeEntity o2) {
				return o1.getSortNum().compareTo(o2.getSortNum());
			}
		});

		AuthorityEntity authorityEntity = authorityService.queryAuthorityById(pid);
		Map<String, Object> result = new HashMap<>();
		result.put("authInfo", authorityEntity);
		result.put("childAuth", value);
//		mvw.addObject("authInfo", authorityEntity);
//		mvw.addObject("childAuth", value);
//		mvw.setViewName("indexMain.jsp");
		try {
//			response.sendRedirect(request.getContextPath() + "/newpage/indexMain.jsp");
//			request.getRequestDispatcher(request.getContextPath() + "/newpage/indexMain.jsp").forward(request,response);
			request.setAttribute("childAuth", value);
			request.setAttribute("name","张三");
			System.err.println(request.getContextPath());
			RequestDispatcher requestDispatcher=request.getRequestDispatcher( "/newpage/indexMain.jsp");
			try {
				requestDispatcher.forward(request,response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		;
	}

	// 根据user和系统权限 查询权限下的菜单
	@RequestMapping(value = "/listMenuByPid")
	@ResponseBody
	public Map<String, Object> listMenuByPid(HttpServletRequest request) {
		System.out.println("===查询菜单列表===" + request.getParameter("pid"));
		Long pid = Long.parseLong(request.getParameter("pid")+"");
		List<MenuTreeEntity> value = new ArrayList();
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		Integer userid = sessionInfo.getUser().getId();
		List<AuthorityEntity> listmenu = authorityService.getAuthorityDataById(userid, pid);
		if(listmenu!=null &&listmenu.size()>0){
			//排序
			Collections.sort(listmenu, new Comparator<AuthorityEntity>() {
				public int compare(AuthorityEntity arg0, AuthorityEntity arg1) {
					return arg0.getId().compareTo(arg1.getId());
				}
			});
			for (int i = 0; i < listmenu.size(); i++) {
				AuthorityEntity authorityEntity = listmenu.get(i);
				if (authorityEntity.getParentId() == pid) {
					MenuTreeEntity mt = new MenuTreeEntity();
					mt.setId(authorityEntity.getId());
					mt.setParentTitle(authorityEntity.getTitle());
					mt.setSortNum(authorityEntity.getSortNumber().intValue());
					List<LowerMenuEntiry> lms = new ArrayList<>();
					for (AuthorityEntity ae : listmenu) {
						if (ae.getParentId().equals(authorityEntity.getId())) {
							LowerMenuEntiry lm = new LowerMenuEntiry();
							lm.setId(ae.getId());
							lm.setUrl(ae.getUrl());
							lm.setTitle(ae.getTitle());
							lm.setJsMethod(ae.getJsMethod());
							lm.setSortNum(ae.getSortNumber().intValue());
							lms.add(lm);
						}
					}
					mt.setLowerMenu(lms);
					value.add(mt);
				}
			}
		}
		//排序
		Collections.sort(value, new Comparator<MenuTreeEntity>() {
			@Override
			public int compare(MenuTreeEntity o1, MenuTreeEntity o2) {
				return o1.getSortNum().compareTo(o2.getSortNum());
			}
		});
		AuthorityEntity authorityEntity = authorityService.queryAuthorityById(pid);
		Map<String, Object> result = new HashMap<>();
		result.put("authInfo", authorityEntity);
		result.put("childAuth", value);
		return result;
	}

	@RequestMapping(value = "/listButtonAuthority")
	@ResponseBody
	public Map listButtonAuthority(@RequestParam(value = "pid", required = false) Long pid, HttpServletRequest request) throws Exception {
		long uid = ControllerTool.getUserID(request);
		Map result = new HashMap();
		if (pid != null) {
			result.put("page", processAuthotiryData(powerCacheUtil.getUserSysPageButtonAutontity(uid, pid)));
			result.put("grid", processAuthotiryData(powerCacheUtil.getUserSysGridButtonAutontity(uid, pid)));
		} else {
			result.put("page", processAuthotiryData(powerCacheUtil.getUserPageButtonAutontity(uid)));
			result.put("grid", processAuthotiryData(powerCacheUtil.getUserGridButtonAutontity(uid)));
		}
		result.put("navigation", processAuthotiryData(powerCacheUtil.getUserNavigationAutontity(uid)));
		return result;
	}

	private List<String> processAuthotiryData(List<AuthorityEntity> aes) {
		List<String> ids = new ArrayList<>();
		if (aes != null) {
			for (AuthorityEntity ae : aes) {
				ids.add(ae.getJsMethod());
			}
		}
		return ids;
	}

	@RequestMapping(value = "/checkIP")
	@ResponseBody
	public Boolean checkIP(HttpServletRequest request) throws Exception {
		return commonService.checkIp(getRemoteHost(request));
	}

	/**
	 * 获取本地电脑ip地址
	 * @param request
	 * @return
	 */
	private String getRemoteHost(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	@RequestMapping(value = "/listTimeoutRecode")
	@ResponseBody
	public List<Map<String, Object>> listTimeoutRecode(){
		return commonService.listTimeoutRecode();
	}

	@RequestMapping(value = "/updateTimeoutRecode")
	@ResponseBody
	public void updateTimeoutRecode(@RequestParam Integer id){
		commonService.updateTimeoutRecode(id);
	}

	@RequestMapping(value = "/listBaqOutAlarmData")
	@ResponseBody
	public List<Map<String, Object>> listBaqOutAlarmData(HttpServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("receiver_id", ControllerTool.getUserID(request));
		return commonService.listBaqOutAlarmData(param);
	}

	@RequestMapping(value = "/updateBaqOutAlarm")
	@ResponseBody
	public void updateBaqOutAlarm(HttpServletRequest request){
		commonService.updateBaqOutAlarmById(Integer.parseInt(request.getParameter("id")+""));
	}

	@RequestMapping(value = "/queryUserByCuffNo")
	@ResponseBody
	public Map<String, Object> queryUserByCuffNo(@RequestParam String cuffNo, HttpServletRequest request){
		return commonService.queryUserByCuffNo(cuffNo);
	}

	@RequestMapping(value = "/querySerialByUser12")
	@ResponseBody
	public List<Map<String, Object>> querySerialByUser12(@RequestParam Map<String, Object> param, HttpServletRequest request){
		param.put("userids", param.get("user1"));
		return commonService.querySerailByUser12(param);
	}
	@RequestMapping(value = "/getUser")
	@ResponseBody
	public UserEntity getSessionInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		if (request.getParameter("political") != null) {
			map.put("political",request.getParameter("political"));
		}
		List<UserEntity> userEntity = userService.searchAllUser(map);
		if(userEntity.size()>0){
			return userEntity.get(0);
		}else{
			return new UserEntity();
		}
	}
}