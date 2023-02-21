package com.zhixin.zhfz.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhixin.zhfz.common.entity.SessionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionTimeoutFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionTimeoutFilter.class);

	public void init(FilterConfig arg0) throws ServletException {

	}

	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) srequest;
		HttpServletResponse response = (HttpServletResponse) sresponse;
		String url = request.getServletPath();
		logger.debug("### NoSessionFilter filter ### " + url);
		if (url.indexOf("file/upload.do") > -1 || url.indexOf("common/fileServiceConfig/download.do") > -1
		|| url.indexOf("/zhfz/common/weboffice/read.jsp") > -1 || url.indexOf("/zhfz/bacs/serial/downloadWordBase64.do") > -1
		|| url.indexOf("/zhfz/common/weboffice/webofficeocx.js") > -1 || url.indexOf("/zhfz/bacs/serial/downloadWord.do") > -1
				|| url.indexOf("/zhfz/lawdocProcess/download.do") > -1 || url.indexOf("/zhfz/common/weboffice/webofficeocx.js") > -1 || url.indexOf("/zhfz/bacs/serial/downloadWord.do") > -1
				|| url.indexOf("/zhfz/sacw/involveddoc/download.do") > -1
				|| url.indexOf("/zhfz/bacs/urinalysis/download.do") > -1
				|| url.indexOf("/zhfz/bacs/iriscollection/imageshow.do") > -1

		) {
			// 特殊放行的URL
			logger.info("特殊放行的URL：" + url);
			chain.doFilter(srequest, sresponse);
		} else {
			SessionInfo info = (SessionInfo) request.getSession().getAttribute("sessionInfo");
			if (info != null) {
				chain.doFilter(srequest, sresponse);
			} else {
				logger.info("拦截未登录的URL：" + url);
				if(url.indexOf("/bacsapp") > -1)
					response.sendRedirect(request.getContextPath() + "/app-login.jsp");
				else
					response.sendRedirect(request.getContextPath() + "/login.jsp");
			}
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

}
