package com.juju.sport.home.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.base.dto.MessageListDto;
import com.juju.sport.base.dto.MessagesDto;
import com.juju.sport.base.service.IMessageService;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.home.util.SessionUtil;
import com.juju.sport.order.dto.OrderDto;
import com.juju.sport.order.service.IOrderService;
import com.juju.sport.user.api.IUserService;
import com.juju.sport.user.dto.LoginUserDto;
import com.juju.sport.user.dto.UserAccountDto;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.service.IUserVenuesInfoService;

@Controller
@RequestMapping(value = "/api/message")
public class MessageController {
	
	@Autowired
	private IMessageService iMessageService;
	
	@Autowired
	private IUserService iUserService;
	@Autowired
	private IUserVenuesInfoService iUserVenuesInfoService;
	
	@Autowired
	private IOrderService iOrderService;
	
	
	@ResponseBody
	@RequestMapping("/insert.do")
	public Response<MessagesDto> insertMess(HttpSession session,MessagesDto messagesDto){
		Response<MessagesDto> result = new Response<MessagesDto>();
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		if(loginUserDto==null){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("用户未登录");
				return result;
		}
		if(loginUserDto.getType()!=1){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("场馆用户不能进行评论");
				return result;
		}
		if(messagesDto.getMsgContent()==null||"".equals(messagesDto.getMsgContent())){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("不能提交空的评论");
			return result;
		}
		if(messagesDto.getMsgContent().length()>140){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("评论不能超过140字");
			return result;
		}
		int diff = checkDateTime(loginUserDto.getId(), MessagesDto.MsgType.COMMENTINFO.getInfoType());
		if( diff<= 1){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("评论速度太快，休息一下吧");
			return result;
		}

		messagesDto.setMsgFromId(loginUserDto.getId());
		UsersDto usersDto =iUserService.selectUsersByUserAccountId(loginUserDto.getId());
		messagesDto.setUserAccount(usersDto.getNickName());
//		if(usersDto!=null){
//			messagesDto.setUserImg(usersDto.getUserImage());
//			if(StringUtils.isEmpty(usersDto.getRealName())){
//				messagesDto.setUserAccount(usersDto.getNickName());
//			}else{
//				messagesDto.setUserAccount(usersDto.getRealName());	
//			}
//		}
		Date date = new Date();
		messagesDto.setMsgType(5);
		messagesDto.setMsgStatus(0);
		messagesDto.setMsgTime(date);
		messagesDto.setShowMsgTime(DateUtils.format(date, DateUtils.YMD_DASH));
		messagesDto.setAttachment("");
		messagesDto.setLastUpdateTime(date);
		iMessageService.insert(messagesDto);
		
		result.setData(messagesDto);
		result.setStatus(DataStatus.HTTP_SUCCESS);
		return result;
	}
	
	//获取用户最近的评论时间和现在的时间进行比较
	public int checkDateTime(String userAccountId,int msgType){
		List<MessagesDto> result = iMessageService.queryMessagesBy(userAccountId,msgType);
		int diff = 2;
		if(result!=null && result.size()>0){
			MessagesDto messageDto = result.get(0);
			diff = DateUtils.minDiff(new Date(), messageDto.getCreateTime());
		}
		return diff;
	}
	
	@ResponseBody
	@RequestMapping("/findOrderByMess.do")
	public Response<PageResult<MessagesDto>> findOrderByMess(@RequestParam("orderId")String orderId,PageQuery page){
		Response<PageResult<MessagesDto>> result2=  new Response<PageResult<MessagesDto>>();
		OrderDto orderDto = iOrderService.findOne(orderId);
		if(orderDto!=null){
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.setMsgToId(orderDto.getOwnerAccountId());
			messagesDto.setMsgType(5);
			MessageListDto messageListDto = iMessageService.findAllMessageBy(messagesDto, page);
			if(messageListDto.getMessList()!=null&&messageListDto.getMessList().size()>0){
				List<MessagesDto> listMess = new ArrayList<MessagesDto>();
				for(MessagesDto tempMess : messageListDto.getMessList()){
					String tempDate = DateUtils.format(tempMess.getMsgTime(), DateUtils.YMD_DASH);
					tempMess.setShowMsgTime(tempDate);
					UsersDto users = iUserService.selectUsersByUserAccountId(tempMess.getMsgFromId());
					if(users!=null){
						tempMess.setUserImg(users.getUserImage());
					}
					listMess.add(tempMess);
				}
				PageResult<MessagesDto> result = new PageResult<MessagesDto>(messageListDto.getPage(),listMess);
				result2.setData(result);
				return result2;
			}
			
		}else{
			result2.setStatus(DataStatus.HTTP_FAILE);
			result2.setMessage("查询场馆信息失败");
			return result2;
		}
		return result2;
	}
	
	@ResponseBody
	@RequestMapping("/findMessByOwner.do")
	public Response<PageResult<MessagesDto>> findMessByOwner(MessagesDto messagesDto,PageQuery page){
		Response<PageResult<MessagesDto>> results = new Response<PageResult<MessagesDto>>();
		messagesDto.setMsgType(5);
		MessageListDto messageListDto = iMessageService.findAllMessageBy(messagesDto, page);
		if(messageListDto.getMessList()!=null&&messageListDto.getMessList().size()>0){
			List<MessagesDto> listMess = new ArrayList<MessagesDto>();
			for(MessagesDto tempMess : messageListDto.getMessList()){
				String tempDate = DateUtils.format(tempMess.getMsgTime(), DateUtils.YMD_DASH);
				tempMess.setShowMsgTime(tempDate);
				UsersDto users = iUserService.selectUsersByUserAccountId(tempMess.getMsgFromId());
				if(users!=null){
					tempMess.setUserImg(users.getUserImage());
				}else{
					UserAccountDto userAccountDto = iUserService.selectUserAccountByUserId(tempMess.getMsgFromId());
					if(userAccountDto!=null){
						tempMess.setUserAccount(userAccountDto.getUserAccount());
					}
				}
				listMess.add(tempMess);
			}
			PageResult<MessagesDto> result = new PageResult<MessagesDto>(messageListDto.getPage(),listMess);
			results.setData(result);
		}
		return results;
	}
	
	@ResponseBody
	@RequestMapping("/findVenusMess.do")
	public Response<PageResult<MessagesDto>> findVenusMess(HttpSession session,PageQuery page){
		Response<PageResult<MessagesDto>> results = new Response<PageResult<MessagesDto>>();
		MessagesDto messagesDto = new MessagesDto();
		String userId="";
		LoginUserDto loginUserDto = null;
		if(SessionUtil.getLoginSession(session)!=null){
			loginUserDto = (LoginUserDto) SessionUtil.getLoginSession(session);
			if(loginUserDto.getType()!=2){
				results.setStatus(DataStatus.HTTP_FAILE);
	        	results.setMessage("405");
				return results;
			}
			userId=loginUserDto.getId();
		}else{
			results.setStatus(DataStatus.HTTP_FAILE);
        	results.setMessage("406");
			return results;
		}
		messagesDto.setMsgToId(userId);
		messagesDto.setMsgType(5);
 		MessageListDto messageListDto = iMessageService.findAllMessageBy(messagesDto, page);
		if(messageListDto.getMessList()!=null&&messageListDto.getMessList().size()>0){
			List<MessagesDto> listMess = new ArrayList<MessagesDto>();
			for(MessagesDto tempMess : messageListDto.getMessList()){
				UsersDto users = iUserService.selectUsersByUserAccountId(tempMess.getMsgFromId());
				if(users!=null){
					tempMess.setUserImg(users.getUserImage());
				}
				if(tempMess.getMsgTime()!=null){
					tempMess.setShowMsgTime(DateUtils.format(tempMess.getMsgTime(), DateUtils.YMD_DASH));
				}
				listMess.add(tempMess);
			}
			PageResult<MessagesDto> result = new PageResult<MessagesDto>(messageListDto.getPage(),listMess);
			results.setData(result);
			return results;
		}
			return null;
		
		
	}
	
	
	@RequestMapping(value="findComplaint.do")
	@ResponseBody
	
	public Response<VenusInfoDto> findComplaint(HttpSession session){
		Response<VenusInfoDto> result = new Response<VenusInfoDto>();
		String userId="";
		LoginUserDto loginUserDto = null;
		if(SessionUtil.getLoginSession(session)!=null){
			loginUserDto = (LoginUserDto) SessionUtil.getLoginSession(session);
			if(loginUserDto.getType()!=2){
				result.setStatus(DataStatus.HTTP_FAILE);
	        	result.setMessage("405");
				return result;
			}
			userId=loginUserDto.getId();
		}else{
			result.setStatus(DataStatus.HTTP_FAILE);
        	result.setMessage("406");
			return result;
		}
		
		VenusInfoDto venusInfoDto = iUserVenuesInfoService.findById(userId);
		if(venusInfoDto!=null){
			if(venusInfoDto.getUserScore()==null){
				venusInfoDto.setUserScore(5);
			}
			result.setData(venusInfoDto);
		}
		return result;
	}

}
