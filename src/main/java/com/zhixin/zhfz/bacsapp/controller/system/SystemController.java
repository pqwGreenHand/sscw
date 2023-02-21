package com.zhixin.zhfz.bacsapp.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.form.UserForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.user.IUserService;

@Controller
@RequestMapping("/zhfz/bacsapp/system")
public class SystemController {
	private static final Logger logger = LoggerFactory.getLogger(SystemController.class);
	
	@Autowired
	private IUserService userService;
	
	 @Autowired
	 private IOperLogService operLogService;
	 
	
	/**
	 * 系统退出
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout")
	@ResponseBody
	public void logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			session.removeAttribute("userInfo");
			session.invalidate();
			response.sendRedirect(request.getContextPath() + "/app-login.jsp");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@RequestMapping(value = {"/editPassword"})
	@ResponseBody
	public MessageEntity login(@RequestBody UserForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			
			UserEntity entity = userService.getUserByID(form.getId());
			if(!entity.getPassword().equals(form.getOldPwd())) {
			   return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("原密码不正确！");
			}
			entity.setPassword(form.getNewPwd());
			userService.updateUserByID(entity);
			this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "app修改密码" + entity, "用户", true,OperLogEntity.SYSTEM_BACS);
		} catch (Exception e) {
			logger.error("Error on login! " + form, e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("服务器内部错误！");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("操作成功！")
				.addCallbackData(false);
	}
	
}
