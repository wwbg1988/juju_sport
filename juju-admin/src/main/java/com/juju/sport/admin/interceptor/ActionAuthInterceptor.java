package com.juju.sport.admin.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.juju.sport.admin.manager.cache.RoleActionCache;
import com.juju.sport.admin.manger.dto.AdminUserLoginInfoDto;
import com.juju.sport.common.constants.SessionConstants;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.JsonUtil;

public class ActionAuthInterceptor implements HandlerInterceptor {

	protected static final Log logger = LogFactory.getLog(ActionAuthInterceptor.class);
	
	@Autowired
	private RoleActionCache roleActionCache;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		String uri = request.getRequestURI();
		HttpSession session = request.getSession();
		if("/admin/login/login.do".equals(uri) || "/admin/login/index.do".equals(uri)){
			return true;
		}
		
		AdminUserLoginInfoDto user = (AdminUserLoginInfoDto)session.getAttribute(SessionConstants.ADMIN_USER_ID);
		
		if(user == null || user.getRole() == null) {
			return false;
		}
		String roleId = user.getRole().getId();
		List<String> allAction = roleActionCache.getAll();
		
		//不在action表中的uri不做权限判断
		if(!allAction.contains(uri)) {
			 return true;
		}
		List<String> ownActions = roleActionCache.get(roleId);
		if(ownActions.contains(uri)) {
			return true;
		}
		
		try {
        	response.setContentType("application/json");
        	response.setCharacterEncoding("utf-8");
			response.getWriter().print(JsonUtil.getJsonStr(new RequestResult(false, "您无权限访问该功能！")));			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		};
		
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj, ModelAndView view) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

}
