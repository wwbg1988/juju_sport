package com.juju.sport.home.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.base.dto.MessagesDto;
import com.juju.sport.base.service.IMessageService;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.Response;
import com.juju.sport.home.util.SessionUtil;


@Controller
@RequestMapping(value="/myMessage")
public class MyMessageController {

	@Autowired
	private IMessageService iMessageService;

	@RequestMapping(value = "/findMyMessageby.do")
	@ResponseBody
	// 查看我的消息（带查询）
	public Response<PageResult<MessagesDto>> findMyMessageby(HttpSession session,MessagesDto messagesDto,PageQuery page) {
		// 只允许登录用户进行操作
		/*if (session.getAttribute(SessionConstants.LOGIN_USER_INFO) == null || "".equals(session.getAttribute(SessionConstants.LOGIN_USER_INFO))) {
			return JsonUtil.getJsonStr(new RequestResult(true, "非登陆用户不允许查询!"));
		}*/
		/*if (session.getAttribute(SessionConstants.LOGIN_USER_INFO) == null || "".equals(session.getAttribute(SessionConstants.LOGIN_USER_INFO))) {
			//return JsonUtil.getJsonStr(new RequestResult(true, "非登陆用户不允许查询!"));
			return "405";
		}*/
		Response<PageResult<MessagesDto>> result = new Response<PageResult<MessagesDto>>();
		if(SessionUtil.getLoginSession(session) == null){
			//return "405"; 
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("405");
			return result;

		}
		//List<MessagesDto> messageslist = iMessageService.selectMessageby(messagesDto,page);
		PageResult<MessagesDto> pageList = iMessageService.selectMessageby(messagesDto,page);
		//Gson gson = new Gson();
		if (pageList != null) {
			result.setData(pageList);
			result.setMessage("200");
			return result;
			//return gson.toJson(pageList);

		}
		//return gson.toJson("406");
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("406");
		return result;
	}

}
