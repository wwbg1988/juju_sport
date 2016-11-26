package com.juju.sport.admin.interceptor;

import com.juju.sport.common.util.SpringContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class UnitTestCaseInterceptor implements HandlerInterceptor {

	protected static final Log logger = LogFactory.getLog(UnitTestCaseInterceptor.class);

    public static final String DEBUG_METHOD_KEY = "debug_method_key";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
        boolean isDebug = Boolean.valueOf(request.getParameter("debug"));
        List<Method> methodList = new ArrayList<Method>();
	    if(isDebug){
            String beanName = request.getParameter("beanName");
            Object bean = SpringContextUtil.getBean(beanName);
            for(Method m : bean.getClass().getDeclaredMethods()){
                methodList.add(m);
            }
            request.setAttribute(DEBUG_METHOD_KEY,methodList);
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
