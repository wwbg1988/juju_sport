package com.juju.sport.home.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.juju.sport.common.exception.BusinessException;
import com.juju.sport.common.exception.SystemException;


/**
 * 统一异常处理器
 * @author rkzhang
 */
public class ExceptionHandler implements HandlerExceptionResolver  {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		 	Map<String, Object> model = new HashMap<String, Object>();  
	        model.put("ex", ex);  
	        ex.printStackTrace();
	        // 根据不同错误转向不同页面  
	        if(ex instanceof BusinessException) {  
	            return new ModelAndView("/error/business-error", model);  
	        }else if(ex instanceof SystemException) {  
	            return new ModelAndView("/error/system-error", model);  
	        } else {  
	            return new ModelAndView("/error/error", model);  
	        }  
	}
}
