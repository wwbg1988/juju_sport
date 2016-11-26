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

import com.juju.sport.base.dto.SpaceMessagesDto;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.Response;
import com.juju.sport.home.util.SessionUtil;
import com.juju.sport.stadium.service.IComplaintService;
import com.juju.sport.user.dto.LoginUserDto;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.service.IUserVenuesInfoService;

@Controller
@RequestMapping(value="/complaint")
public class ComplaintController {
	
	@Autowired
	private IComplaintService iComplaintService;
	@Autowired
	private IUserVenuesInfoService iUserVenuesInfoService;
	
	@RequestMapping(value="/insertComplaint.do")
	@ResponseBody
	public Response<String> insertComplaint(HttpSession session,SpaceMessagesDto spaceMessagesDto,@RequestParam("randomPic") String randomPic){
		
		 Response<String> result = new Response<String>();
		 if(!DataStatus._RANDOMPIC_.equals(randomPic)){
			 String randomPicSession = session.getAttribute("RandomCode").toString().toLowerCase();
		        if(!randomPic.toLowerCase().equals(randomPicSession)){
		        	result.setStatus(DataStatus.HTTP_FAILE);
		        	result.setMessage("401");
		            return result;
		        }
		 }
		
		String userId="";
		LoginUserDto loginUserDto = null;
		if(SessionUtil.getLoginSession(session)==null){
			result.setStatus(DataStatus.HTTP_FAILE);
        	result.setMessage("406");
			return result;
		}
		List<String> temp = new ArrayList<String>();
		temp.add(userId);
		List<VenusInfoDto> list = iUserVenuesInfoService.findBy(temp);
		if(list!=null&&list.size()>0){
			String tempName= list.get(0).getNickName();
			spaceMessagesDto.setUserAccount(tempName);
		}
		spaceMessagesDto.setMsgTime(new Date());
		spaceMessagesDto.setMsgFromId(userId);
		 iComplaintService.insertComplaint(spaceMessagesDto);
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("200");
			return result;
	}

}
