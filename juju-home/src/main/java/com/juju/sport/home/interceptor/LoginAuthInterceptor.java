package com.juju.sport.home.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class LoginAuthInterceptor implements HandlerInterceptor {

	protected static final Log logger = LogFactory.getLog(LoginAuthInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
        boolean isDebug = Boolean.valueOf(request.getParameter("debug"));
        if(isDebug){
            return true;
        }
		logger.debug("login validate --- ");
		logger.debug("requestURL : " + request.getRequestURL().toString());
		logger.debug("context : " + request.getContextPath());
		
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse reponse, Object obj, Exception exception)
			throws Exception {
		
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj, ModelAndView view) throws Exception {
		
		
	}

}
