package com.juju.sport.home.controller.m.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.base.dto.MessageListDto;
import com.juju.sport.base.dto.MessagesDto;
import com.juju.sport.base.service.IMessageService;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.home.util.SessionUtil;
import com.juju.sport.user.api.IUserService;
import com.juju.sport.user.dto.LoginUserDto;
import com.juju.sport.user.dto.UsersDto;

@Controller
@RequestMapping(value = "/api/m/messages")
public class MessageMController {

	@Autowired
	private IMessageService iMessageService;

	@Autowired
	private IUserService iUserService;

	/**
	 * 消息对外接口
	 * **/
	@ResponseBody
	@RequestMapping("/loadMessages.do")
	public Response<PageResult<MessagesDto>> loadMessages(MessagesDto messagesDto, HttpSession session, PageQuery page) {
		Response<PageResult<MessagesDto>> results = new Response<PageResult<MessagesDto>>();
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		messagesDto.setMsgFromId(loginUserDto.getId());
		MessageListDto messageListDto = iMessageService.findAllMessageBy(messagesDto, page);
		if (messageListDto.getMessList() != null && messageListDto.getMessList().size() > 0) {
			List<MessagesDto> listMess = new ArrayList<MessagesDto>();
			for (MessagesDto tempMess : messageListDto.getMessList()) {
				String tempDate = DateUtils.format(tempMess.getMsgTime(),DateUtils.YMD_DASH);
				tempMess.setShowMsgTime(tempDate);
				UsersDto users = iUserService.selectUsersByUserAccountId(tempMess.getMsgFromId());
				if (users != null) {
					tempMess.setUserImg(users.getUserImage());
				}
				listMess.add(tempMess);
			}
			PageResult<MessagesDto> result = new PageResult<MessagesDto>(messageListDto.getPage(), listMess);
			results.setData(result);
			return results;
		}
		return null;
	}
}
