package com.zhixin.zhfz.common.controller.common;

import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.LoginForm;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.services.role.IRoleService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.PowerCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = {"/"})
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IOrganizationService organizationService;

	@RequestMapping(value = {"/login"})
	@ResponseBody
	public MessageEntity login(@RequestBody LoginForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("++++++++login++++++UserForm=" + form);
		PowerCacheUtil powerCacheUtil=new PowerCacheUtil();
		boolean flag = true;
		try {
			UserEntity user = userService.getUserByLoginNameAndPassword(form.getUsername(), form.getPassword());
			logger.info("login user=" + user);
			if (user == null) {
				return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("用户名或密码错误！");
			} else {
				Collection<RoleEntity> roleList = roleService.getUserRolesByUserID(user.getId());
				RoleEntity roleEntity = (roleList != null && roleList.size() > 0) ? roleList.iterator().next() : null;
				if (roleEntity == null) {
					return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("该用户未绑定角色！");
				}
				List<AuthorityEntity> userAuths = powerCacheUtil.getUserAuthorityById(user.getId().longValue());
				if (userAuths == null || userAuths.size() == 0) {
					return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("该用户无任何权限！");
				}
				SessionInfo sessionInfo = new SessionInfo();
				sessionInfo.setUser(user);
				sessionInfo.setRole(roleEntity);
				UserEntity user1 =  sessionInfo.getUser();
				logger.info("login user1=" + user1);
				String clientIP = getRemoteHost(request);
				sessionInfo.setClientIP(clientIP);
				logger.info("[[[[[[[[[[[[[[ login client ip:" + clientIP + " ]]]]]]]]]]]]]]]]");
				/*if (roleEntity != null && roleEntity.getBacsDataAuth() != RoleEntity.DATA_AUTH_FULL) {*/
				organizationService.refreshSessionOrg(sessionInfo,form.getAreaId());
				/*}*/
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("sessionInfo", sessionInfo);
			}
			List<AuthorityEntity> authoritys = powerCacheUtil.getUserAllSysAutontity(user.getId().longValue());
			flag = (authoritys != null && authoritys.size() > 0);
		} catch (Exception e) {
			logger.error("Error on login! " + form, e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("服务器内部错误！");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("登录成功！")
				.addCallbackData(flag);
	}

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

	@RequestMapping(value = "/logout")
	@ResponseBody
	public void logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			session.removeAttribute("userInfo");
			session.invalidate();
			System.err.println(request.getContextPath());
			response.sendRedirect(request.getContextPath() + "/newpage/login.jsp");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
