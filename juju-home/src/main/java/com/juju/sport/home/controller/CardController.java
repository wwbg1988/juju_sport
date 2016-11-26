package com.juju.sport.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.base.dto.CardInfoDto;
import com.juju.sport.base.service.ICardInfoService;
import com.juju.sport.base.service.ISmsSendService;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.digest.MD5Coder;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.RandomCode;
import com.juju.sport.user.api.IUserService;
import com.juju.sport.user.dto.UserAccountDto;
import com.juju.sport.user.dto.UsersDto;

@Controller
@RequestMapping(value = "/api")
public class CardController {
	
	@Autowired
	private ICardInfoService iCardInfoService;
	
	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private ISmsSendService iSmsSendService;
	
		/**
		 * 此方法描述的是：
		 * @author: cwftalus@163.com
		 * @version: 2015年5月25日 下午5:33:56
		 * @throws Exception 
		 */
		
	@RequestMapping(value = "/saveCard.do")
	@ResponseBody
	public Response<String> saveCard(CardInfoDto cardDto) throws Exception{
		Response<String> result = new Response<String>();
		CardInfoDto queryCardInfoDto = iCardInfoService.queryCardInfoByCardNo(cardDto.getCardNo());
		if(queryCardInfoDto==null){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("卡号不存在,请重新填写");
			return result;
		}
		cardDto.setId(queryCardInfoDto.getId());
		/**
		 * 卡登记之后还需要新增一个用户账号
		 */
		if (iUserService.checkrepeat(cardDto.getTelephone()) != 0) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("电话号码已经存在");
			return result;
		}
		
		int count = iCardInfoService.updateCardInfo(cardDto);
		if(count > 0){//修改信息成功
			String randCode = RandomCode.getRandomCode(6);
			UserAccountDto userAccountDto = new UserAccountDto();
			userAccountDto.setUserAccount(cardDto.getTelephone());
			userAccountDto.setPassword(MD5Coder.encodeMD5Hex(randCode));
			userAccountDto.setType(DataStatus.USERTYPE);
			userAccountDto.setCardNo(cardDto.getCardNo());
			UserAccountDto userAccount_ = iUserService.register(userAccountDto);
			if(userAccount_!=null){
				UsersDto usersDto = new UsersDto();
				usersDto.setUserAccountId(userAccount_.getId());
				usersDto.setNickName(DataStatus._JUJU_);
				iUserService.insertUsers(usersDto, userAccount_.getId());
				String content = "温馨提示，您的聚运动账号为:"+cardDto.getTelephone()+" 密码为:"+randCode+" 可登录后修改。";
				iSmsSendService.sendSms(0, cardDto.getTelephone(), content);
			}
		}
//		iCardInfoService.saveCardInfo(cardDto);
		return result;
	}
}
