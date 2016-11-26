package com.juju.sport.home.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.Response;
import com.juju.sport.home.util.SessionUtil;
import com.juju.sport.user.api.IUserService;
import com.juju.sport.user.dto.LoginUserDto;
import com.juju.sport.user.dto.ScoreProductDto;
import com.juju.sport.user.dto.UserProductDto;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.service.IScoreProductService;
import com.juju.sport.user.service.IUserProductService;

/**
 * 
 * 此类描述的是：礼品积分等兑换控制层
 * 
 * @author: cwftalus@163.com
 * @version: 2015年4月9日 下午1:44:18
 */
@Controller
@RequestMapping(value = "/exchange")
public class ExchangeController {
	
	/**
	* 此方法描述的是：查询出所有的礼品信息
	* @author: cwftalus@163.com
	* @version: 2015年4月9日 下午1:48:32
	*/

	@Autowired
	private IScoreProductService iScoreProductService;
	
	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private IUserProductService iUserProductService;
	
	@RequestMapping(value="/query.do")
	@ResponseBody
	public Response<PageResult<ScoreProductDto>> query(ScoreProductDto scoreProductDto,PageQuery page){
		Response<PageResult<ScoreProductDto>> result = new Response<PageResult<ScoreProductDto>>();
		PageResult<ScoreProductDto> dataList = iScoreProductService.findBy(scoreProductDto, page);
		result.setData(dataList);
		return result;
	}
	
	/**
	 * 
		 * 此方法描述的是：进行兑换操作
		 * @author: cwftalus@163.com
		 * @version: 2015年4月9日 下午3:04:22
	 */
	@RequestMapping(value="/exchange.do")
	@ResponseBody
	public Response<String> exchange(HttpSession session,ScoreProductDto scoreProductDto){
		Response<String> result = new Response<String>();
		//获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto == null) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("非登陆用户不允许兑换产品!");
			return result;//JsonUtil.getJsonStr(new RequestResult(false, "非登陆用户不允许兑换产品!"));
		}
		scoreProductDto = iScoreProductService.findBy(scoreProductDto);
		if(scoreProductDto==null){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("兑换产品已经下架或取消,请重新选择!");			
			return result;//JsonUtil.getJsonStr(new RequestResult(false, "兑换产品已经下架或取消,请重新选择!"));
		}
		//判断积分是否够用
		UsersDto usersDto = iUserService.selectUsersByUserAccountId(loginUserDto.getId());
		if(scoreProductDto.getNeedScore()>usersDto.getUserScore()){//产品需要的积分》用户拥有的积分则不允许兑换
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户积分不足,不允许兑换产品!");					
			return result;//JsonUtil.getJsonStr(new RequestResult(false, "用户积分不足,不允许兑换产品!"));
		}
		
		usersDto.setUserScore(usersDto.getUserScore()-scoreProductDto.getNeedScore());
		iUserService.updateUsers(usersDto);
		
		//兑换成功之后新增到对应的表
		UserProductDto userProductDto = new UserProductDto();
		userProductDto.setScoreProductId(scoreProductDto.getId());//产品Id
		userProductDto.setProductName(scoreProductDto.getProductName());//产品名称
		userProductDto.setUserAccountId(loginUserDto.getId());//用户Id
		iUserProductService.saveOrUpdate(userProductDto);
		
		result.setMessage("用户兑换产品成功!");		
		return result;//JsonUtil.getJsonStr(new RequestResult(true, "用户兑换产品成功!"));
	}
	
}
