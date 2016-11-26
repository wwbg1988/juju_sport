package com.juju.sport.logger.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.juju.sport.logger.dto.AccessLog;

public class AccessLoggerInterceptor implements HandlerInterceptor {

	protected static final Logger logger = Logger.getLogger("access");
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		try { 
			log(request, obj);
		}catch(Throwable e) {
			e.printStackTrace();
		}
		return true;
	}

	private void log(HttpServletRequest request, Object obj) {
		// HandlerMethod handler = (HandlerMethod)obj;
		
		AccessLog accessLog = new AccessLog();
		accessLog.setRequestURL(request.getRequestURL().toString());
		accessLog.setRequestURI(request.getRequestURI());
		accessLog.setRemoteIp(request.getHeader("X-Real-IP"));
		accessLog.setHost(request.getHeader("Host"));
		accessLog.setParameterMap(request.getParameterMap());
		accessLog.setContentType(request.getContentType());
		accessLog.setContextLength(request.getContentLength());
		accessLog.setContextPath(request.getContextPath());	
		accessLog.setMethod(request.getMethod());
		accessLog.setAccessTime(format.format(new Date()));
		// accessLog.setControllerName(handler.getBean().getClass().getSimpleName());
		// accessLog.setMethodName(handler.getMethod().getName());
		Gson gson = new Gson();
		String logObj = gson.toJson(accessLog, AccessLog.class);
		logger.info(logObj);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object obj, Exception exception)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj, ModelAndView arg3) throws Exception {

	}
}
