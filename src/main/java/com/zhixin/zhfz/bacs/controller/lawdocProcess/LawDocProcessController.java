package com.zhixin.zhfz.bacs.controller.lawdocProcess;

import com.zhixin.zhfz.bacs.entity.LawDocProcessEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.form.LawDocForm;
import com.zhixin.zhfz.bacs.services.lawdoc.ILawDocProcessService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.common.Base64Util;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.LoginForm;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.services.role.IRoleService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.PowerCacheUtil;
import com.zhixin.zhfz.sacw.common.BaseConfig;
import com.zhixin.zhfz.sacw.common.FreemarkerUtil;
import com.zhixin.zhfz.sacw.common.OfficeUtil;
import com.zhixin.zhfz.sacw.common.Utils;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
	@RequestMapping("/zhfz/lawdocProcess")
public class LawDocProcessController {

	@Autowired
	private ILawDocProcessService lawDocProcessService;
	
	@Autowired
	private ISerialService serialService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IOrganizationService organizationService;
	@RequestMapping("/preview")
	@ResponseBody
	public MessageEntity officeView(@RequestBody LawDocForm form, HttpServletRequest request, HttpSession session)
			throws IOException, TemplateException, ParseException {
		String officeDir = BaseConfig.getInstance().getOfficeFileDir();
		String templatePath = BaseConfig.getInstance().getTemplateDir();
		LawDocProcessEntity result = lawDocProcessService.getProcessData(form, request,session);
		String fileName = result.getTempFileName();
		String xmlFileName = result.getXmlFileName();
		String officeFile = officeDir + fileName;
		// 生成文件
		FreemarkerUtil.process(templatePath, xmlFileName, officeFile, result.getData());

		// 生成预览文件
		String htmlname = Utils.getUniqueId() + ".html";
		String converPath = officeDir + htmlname;

		int fileType = result.getFileType();// 1:word；2:execl
		if (fileType == 1) {
			OfficeUtil.wordTohtml(officeFile, converPath);
		} else if (fileType == 2) {
			OfficeUtil.excelTohtml(officeFile, converPath);
		} else {
			return new MessageEntity().addIsError(true).addContent("类型错误!");
		}
		return new MessageEntity().addIsError(false).addContent(htmlname);
	}
	@RequestMapping(value = "/downloadparam")
	@ResponseBody
	public String downloadparam(HttpSession session,@RequestParam Map<String,Object> param) {
		session.setAttribute("downloadparam", param);
		return "1";
	}
	@RequestMapping(value = "/downloadBase64")
	@ResponseBody
	public MessageEntity exportBase64(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LawDocForm form = new LawDocForm();
		request.setCharacterEncoding("UTF-8");
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String userId = request.getParameter("userId");
		String serialNo = request.getParameter("serialNo");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String areaId=request.getParameter("areaId");
		String serialId=request.getParameter("serialId");
		//String belongingsId=request.getParameter("belongingsId");
		String policeId=request.getParameter("policeId");

		String serialUUID = request.getParameter("serialUUID");
		String count = request.getParameter("count");
		/**
		 * 如果需要session，请自行传递账号密码
		 */
		String userName=request.getParameter("userName");
		String passWord=request.getParameter("passWord");
		LoginForm login = new LoginForm();
		/**
		 * 重新登陆
		 */
		login.setUsername(userName);
		login.setPassword(passWord);
		login(login,request,response);
		if(serialId!=null && !serialId.equals("null")){
			form.setSerialId(Long.valueOf(serialId));
		}
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		if(id!=null) {
			session.setAttribute("securityid", id);
			session.setAttribute("appRecordId", id);
		}
		SerialEntity serial = new SerialEntity();
		serial.setSerialNo(serialNo);
		serial = serialService.getSerialByNo(serial);
		form.setName(name);
		form.setNumber(Integer.parseInt(number));
		if(type!=null&&!"".equals(type)){
			form.setType(Integer.parseInt(type));
		}
		if(userId!=null&&!"".equals(userId)){
			form.setUserId(Long.parseLong(userId));
		}
		if(serialNo!=null&&!"".equals(serialNo)){
			form.setSerialNo(serialNo);
		}
		if (serial.getId() != null && !"".equals(serial.getId())) {
			form.setSerialId(serial.getId());
		}
		form.setEndTime(endTime);
		form.setStartTime(startTime);
		if (areaId != null && !"".equals(areaId)) {
			form.setAreaId(Integer.parseInt( areaId));
		}
		if (userName != null && !"".equals(userName)) {
			form.setUserName(userName);
		}
		if (passWord != null && !"".equals(passWord)) {
			form.setPassWord(passWord);
		}
		if(policeId!=null&&!"".equals(policeId)){
			form.setPoliceId(Long.parseLong(policeId));
		}
		if(serialUUID!=null&&!"".equals(serialUUID)){
			form.setSerialUUID(serialUUID);
		}
		if(count!=null&&!"".equals(count)){
			form.setCount(Integer.parseInt(count));
		}

		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-download");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		LawDocProcessEntity result = lawDocProcessService.getProcessData(form, request,session);
		String downFileName = result.getDownFileName();
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
		} else {
			downFileName = URLEncoder.encode(downFileName, "UTF-8");
		}
		response.addHeader("Content-Disposition", "attachment;filename=" + downFileName);
		String xmlFileName = result.getXmlFileName();
		URL url = Thread.currentThread().getContextClassLoader().getResource("");
		String templatePath = url.getPath() + "template";
		System.out.println("exportReport getData=" + result.getData());
		String officeDir = BaseConfig.getInstance().getOfficeFileDir();
		String officeFile = officeDir + result.getDownFileName();
//        PrintWriter out =  new PrintWriter(officeFile);
		FreemarkerUtil.process(templatePath, xmlFileName, officeFile,result.getData());
//		File f=new File(officeFile);
//		response.setHeader("content-length",String.valueOf(f.length()));
//        FreemarkerUtil.process(templatePath, xmlFileName, result.getData(), out);
//		return new MessageEntity().addCallbackData(Base64Util.encodeBase64File(officeFile));
		String base64 = Base64Util.encodeBase64File(officeFile);
		return new MessageEntity().addCode(1).addIsError(false).addCallbackData(base64);
	}
	
	@RequestMapping(value = "/download")
	public void export(HttpServletRequest request, HttpServletResponse response/*, HttpSession session*/)
			throws Exception {
		LawDocForm form = new LawDocForm();
		request.setCharacterEncoding("UTF-8");
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String userId = request.getParameter("userId");
		String belongingsId = request.getParameter("dataId");
		String serialNo = request.getParameter("serialNo");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String areaId=request.getParameter("areaId");
		String serialId=request.getParameter("serialId");
		//String belongingsId=request.getParameter("belongingsId");
		String policeId=request.getParameter("policeId");

		String serialUUID = request.getParameter("serialUUID");
		String count = request.getParameter("count");
		/**
		 * 如果需要session，请自行传递账号密码
		 */
		String userName=request.getParameter("userName");
		String passWord=request.getParameter("passWord");
		LoginForm login = new LoginForm();
		/**
		 * 重新登陆
		 */
		login.setUsername(userName);
		login.setPassword(passWord);
		login(login,request,response);
		if(serialId!=null && !serialId.equals("null")&& !serialId.equals("")){
			form.setSerialId(Long.valueOf(serialId));
		}
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		if(id!=null) {
			session.setAttribute("securityid", id);
			session.setAttribute("appRecordId", id);
		}
//		SerialEntity serial = new SerialEntity();
//		serial.setSerialNo(serialNo);
//		serial = serialService.getSerialByNo(serial);
		form.setName(name);
		form.setNumber(Integer.parseInt(number));
		if(type!=null&&!"".equals(type)){
			form.setType(Integer.parseInt(type));	
		}
		if(userId!=null&&!"".equals(userId)){
			form.setUserId(Long.parseLong(userId));
		}
		if(serialNo!=null&&!"".equals(serialNo)){
			form.setSerialNo(serialNo);
		}
//		if (serial.getId() != null && !"".equals(serial.getId())) {
//			form.setSerialId(serial.getId());
//		}
		form.setEndTime(endTime);
		form.setStartTime(startTime);
		if (areaId != null && !"".equals(areaId)) {
			form.setAreaId(Integer.parseInt( areaId));
		}
		if (userName != null && !"".equals(userName)) {
			form.setUserName(userName);
		}
		if (passWord != null && !"".equals(passWord)) {
			form.setPassWord(passWord);
		}
//		if(belongingsId!=null&&!"".equals(belongingsId)){
//			form.setBelongingsId(Long.parseLong(belongingsId));
//		}
		if(policeId!=null&&!"".equals(policeId)){
			form.setPoliceId(Long.parseLong(policeId));
		}
		if(serialUUID!=null&&!"".equals(serialUUID)){
			form.setSerialUUID(serialUUID);
		}
		if(count!=null&&!"".equals(count)){
			form.setCount(Integer.parseInt(count));
		}
		if(belongingsId!=null&&!"".equals(belongingsId)){
			form.setBelongingsId(Integer.parseInt(belongingsId));
		}

		response.reset();
		// response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("交班记录", "UTF-8") + ".xls");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-download");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		LawDocProcessEntity result = lawDocProcessService.getProcessData(form, request,session);
		String downFileName = result.getDownFileName();
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
		} else {
			downFileName = URLEncoder.encode(downFileName, "UTF-8");
		}
		response.addHeader("Content-Disposition", "attachment;filename=" + downFileName);
		String xmlFileName = result.getXmlFileName();
		PrintWriter out = response.getWriter();
		URL url = Thread.currentThread().getContextClassLoader().getResource("");
		//System.out.println("exportReport getPath=" + url.getPath());
		String templatePath = url.getPath() + "template";
		//System.out.println("111getData"+result.getData());
		//System.err.println(templatePath+"---------------"+xmlFileName+"================="+result.getData());
		FreemarkerUtil.process(templatePath, xmlFileName, result.getData(), out);
	}

	/**
	 * 重新登陆
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private void login(LoginForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PowerCacheUtil powerCacheUtil=new PowerCacheUtil();
		boolean flag = true;
		try {
			UserEntity user = userService.getUserByLoginNameAndPassword(form.getUsername(), form.getPassword());
			if (user == null) {
				System.out.println("用户为空");
			} else {
				Collection<RoleEntity> roleList = roleService.getUserRolesByUserID(user.getId());
				RoleEntity roleEntity = (roleList != null && roleList.size() > 0) ? roleList.iterator().next() : null;
				if (roleEntity == null) {
					System.out.println("用户角色为空");
				}
				List<AuthorityEntity> userAuths = powerCacheUtil.getUserAuthorityById(user.getId().longValue());
				if (userAuths == null || userAuths.size() == 0) {
					System.out.println("用户权限为空");
				}
				SessionInfo sessionInfo = new SessionInfo();
				sessionInfo.setUser(user);
				sessionInfo.setRole(roleEntity);
				UserEntity user1 =  sessionInfo.getUser();
				String clientIP = getRemoteHost(request);
				sessionInfo.setClientIP(clientIP);
				organizationService.refreshSessionOrg(sessionInfo,form.getAreaId());
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("sessionInfo", sessionInfo);
			}
			List<AuthorityEntity> authoritys = powerCacheUtil.getUserAllSysAutontity(user.getId().longValue());
			flag = (authoritys != null && authoritys.size() > 0);
		} catch (Exception e) {
			System.out.println("登陆失败"+e);
		}
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
}
