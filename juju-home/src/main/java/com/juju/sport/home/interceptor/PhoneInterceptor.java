package com.juju.sport.home.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.juju.sport.common.constants.SessionConstants;
import com.juju.sport.user.dto.LoginUserDto;

public class PhoneInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
			String userId =request.getParameter("usersIds");
			if(!StringUtils.isEmpty(userId)&&!"".equals(userId)){
				LoginUserDto loginUserDto = new LoginUserDto();
				loginUserDto.setId(userId);
				loginUserDto.setType(1);
				request.getSession().setAttribute(SessionConstants.LOGIN_USER_INFO,loginUserDto);
				
			}
			String raceId=request.getParameter("eventId");
			if(!StringUtils.isEmpty(raceId)&&!"".equals(raceId)){
				request.getSession().setAttribute("raceDetails", raceId);
			}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
