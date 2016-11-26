package com.juju.sport.home.util;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import com.juju.sport.common.constants.SessionConstants;
import com.juju.sport.user.dto.LoginUserDto;

public class SessionUtil implements Serializable{
	public static LoginUserDto getLoginSession(HttpSession session){
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)!=null){
			return (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
		}else{
			return null;
		}
	}
}
