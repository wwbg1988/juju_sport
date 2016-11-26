package com.juju.sport.home.interceptor;

import com.juju.sport.common.util.SpringContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.Objects;


public class UnitTestCaseInterceptor implements HandlerInterceptor {

	protected static final Log logger = LogFactory.getLog(UnitTestCaseInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
        boolean isDebug = Boolean.valueOf(request.getParameter("debug"));
	    if(isDebug){
            String beanName = request.getParameter("beanName");
            Object bean = SpringContextUtil.getBean(beanName);
            for(Method m : bean.getClass().getDeclaredMethods()){
                response.getWriter().print(m.getName());
            }
        }
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
