package com.juju.sport.home.controller.http;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.admin.home.cache.CacheDataLoad;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.constants.HttpApiReturnCode;
import com.juju.sport.common.jdbc.DynamicDataSourceHolder;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.home.dto.RestRespone;
import com.juju.sport.order.constants.OrderStatus;
import com.juju.sport.order.dto.OrderBuyDto;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.service.IOrderItemsService;
import com.juju.sport.order.service.IOrderService;
import com.juju.sport.stadium.dto.VenuesDayInfoDto;
import com.juju.sport.stadium.dto.VenuesDayInfoListDto;
import com.juju.sport.stadium.dto.VenuesPwdDto;
import com.juju.sport.stadium.service.IVenuesApiService;
import com.juju.sport.user.api.IUserService;
import com.juju.sport.user.dto.UserAccountDto;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.service.IVenuesInfoService;

/**		
 * <p>Title: VenuesHttpApiController </p>
 * <p>Description: 场馆设备接口API</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年5月21日 下午1:48:31	
 * @version 1.0
 */

@Controller
@RequestMapping(value = "/api/rest")
public class VenuesHttpApiController
{

    @Autowired
    private IVenuesApiService venuesApiService;
    @Autowired
    private IVenuesInfoService venuesInfoService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderItemsService orderItemsService;

    @Autowired
    private IOrderService orderService;

    /**     
     * venuesInfo：查询场馆信息
     * @param sid 设备编号
     * @param seq 验证序列号
     * @param sidpwd 序列号与设备密码进行MD5之后的密文
     * @param time 接收到用户请求，格式为yyyyMMddHHmmss
     * @return VenuesATMDto 场馆信息
     * @author Vincent
     * @date 2015年5月21日 下午4:38:40	 
     */
    @RequestMapping(value = "/venues/info.do")
    @ResponseBody       
    public RestRespone<VenuesDayInfoDto> venuesInfo(@RequestParam("sid") String sid,
        @RequestParam("seq") String seq, @RequestParam("sidPwd") String sidpwd)
    {
        RestRespone<VenuesDayInfoDto> restRes = new RestRespone<VenuesDayInfoDto>();
        VenuesPwdDto venuesPwdDto = CacheDataLoad.getVenuesPwd(sid);
        if(null ==venuesPwdDto || venuesPwdDto.getOwnerAccountId() ==null){
            restRes.setDescribe("设备无对应场馆信息");
            restRes.setReturnCode(HttpApiReturnCode.VENUES_NOT_FOUND);
            return restRes;
        }
                
        VenuesDayInfoDto vdiDto = venuesApiService.queryVenuesInfo(venuesPwdDto.getOwnerAccountId());
        if (null != vdiDto)
        {
            VenusInfoDto viDto = venuesInfoService.findByID(venuesPwdDto.getOwnerAccountId());
            if (null != viDto && null != viDto.getMaxNum())
            {
                //场馆容纳最大人数
                vdiDto.setVenuesid(venuesPwdDto.getOwnerAccountId());
                vdiDto.setName(viDto.getNickName());
                vdiDto.setHeadcount(viDto.getMaxNum());
                restRes.setData(vdiDto);
                restRes.setDescribe("成功");
                restRes.setReturnCode(HttpApiReturnCode.SUCCESS_CODE);
            }
            else
            {
                restRes.setDescribe("访问外围设备失败——参数校验");
                restRes.setReturnCode(HttpApiReturnCode.PERPHERAL_REQ_FAIL);
            }

        }
        else
        {
            restRes.setDescribe("获取信息失败  设备信息不存在");
            restRes.setReturnCode(HttpApiReturnCode.GET_INFO_FAIL);
        }
        return restRes;

        /** 需求变更
        String imgURL="http://192.168.1.70/stadium/images/img1.jpg|http://192.168.1.70/stadium/images/img2.jpg|http://192.168.1.70/stadium/images/img3.jpg";
        vdiDto.setImgUrl(viDto.getImgUrl);
        stringtoList(imgURL);
        vdiDto.setImgurl(stringtoList(imgURL));
        
        //场馆收费
        if(viDto.getChargeType()==1){
            HashMap<String, String> spaceprice = new HashMap<String, String>();
            System.err.println(JsonUtil.getJsonStr(vat));
        }**/
    }

    /**     
     * cardTicket： 刷卡入场
     * @param cardno  卡号  LR开头：老年卡 YD开头：运动卡
     * @param sid 设备编号
     * @param seq 验证序列号
     * @param sidpwd  序列号与设备密码进行MD5之后的密文
     * @param time 接收到用户请求的时间，格式为yyyyMMddHHmmss
     * @return	 TicketInfoDto 出票信息
     * @author Vincent
     * @date 2015年5月21日 下午4:38:29	 
     */
    @RequestMapping(value = "/card/getticket.do")
    @ResponseBody
    public RestRespone<String> cardTicket(@RequestParam("cardno") String cardno,
        @RequestParam("sid") String sid, @RequestParam("seq") String seq,
        @RequestParam("sidPwd") String sidpwd)
    {

        RestRespone<String> restRes = new RestRespone<String>();
        if (StringUtils.isEmpty(cardno))
        {
            restRes.setDescribe("cardno为空");
            restRes.setReturnCode(HttpApiReturnCode.CARDNO_IS_NULL);
            return restRes;
        }
        
        VenuesDayInfoListDto cardLog = venuesApiService.findLogByCardID(cardno);
        if (null != cardLog && cardLog.getCreateTime() != null)
        {
            int diff = DateUtils.secondsDiff(new Date(), cardLog.getCreateTime());
            if (diff <= 10)
            {
                restRes.setDescribe("重复刷卡");
                restRes.setReturnCode(HttpApiReturnCode.REPEAT_CHECK_CARD);
                return restRes;
            }
        }

        //查询设备密码信息
        VenuesPwdDto vdiDto = CacheDataLoad.getVenuesPwd(sid);
        
        String owner_account_id = "";
        if (null != vdiDto && vdiDto.getOwnerAccountId() != null)
        {
            owner_account_id = vdiDto.getOwnerAccountId();
        }
        else
        {
            restRes.setDescribe("获取信息失败  设备信息不存在");
            restRes.setReturnCode(HttpApiReturnCode.GET_INFO_FAIL);
            return restRes;
        }

        //cardno  查询卡绑定用户  call mark 
        UserAccountDto userDto = userService.selectUsersByCardNo(cardno);
       
        OrderBuyDto orderBuyDto = new OrderBuyDto();

        String orderTime = DateUtils.format(new Date(), DateUtils.YMD_DASH);
        if (null != userDto)
        {
            if(StringUtils.equals(userDto.getValidCode(), HttpApiReturnCode.CARD_NOT_FOUND)){
                restRes.setDescribe("卡片不存在");
                restRes.setReturnCode(HttpApiReturnCode.CARD_NOT_FOUND);
                return restRes;
            }
            
            orderBuyDto.setTelephone(userDto.getUserAccount());
            orderBuyDto.setUserAccountId(userDto.getId());

            //PWD表查询场馆ID
            orderBuyDto.setOwnerAccountId(owner_account_id);
            orderBuyDto.setOrderTime(orderTime);
            orderBuyDto.setOrderNo(DataStatus._ORDER_ + System.currentTimeMillis());
            orderBuyDto.setOrderStatus(OrderStatus.Finished.getValue());
            Map<String,String> map = new HashMap<String,String>(); 
            map.put("telephone", userDto.getUserAccount());
            map.put("accountid", userDto.getId());
            map.put("orederno",DataStatus._ITEMS_ + System.currentTimeMillis());
            map.put("orderTime", orderTime);
            map.put("ownerAccountId", owner_account_id);
            //手机号 场馆ID
            DynamicDataSourceHolder.putDataSource("master");
            String orderId = orderService.saveTOrUpdate(orderBuyDto,map);
            
            if (StringUtils.isNotEmpty(orderId))
            {
                venuesApiService.updateOnliner(owner_account_id, true);

                VenuesDayInfoListDto vdiListDto = new VenuesDayInfoListDto();
                vdiListDto.setCardNo(cardno);
                vdiListDto.setDaytime(orderTime);
                vdiListDto.setVenuesid(owner_account_id);
                vdiListDto.setVenuesid(UUIDGenerator.getUUID());
                vdiListDto.setLastUpdateTime(new Date());
                vdiListDto.setCreateTime(new Date());
                vdiListDto.setStat(DataStatus.ENABLED);
                vdiListDto.setUseType(DataStatus.CARD_USR);
                //记录刷卡入场日志          
                venuesApiService.insertTouchCardLog(vdiListDto);
   
                restRes.setDescribe("成功");
                restRes.setData(userDto.getCardName());
                restRes.setReturnCode(HttpApiReturnCode.SUCCESS_CODE);
            }
            else
            {
                restRes.setDescribe("生成更新订单失败");
                restRes.setReturnCode(HttpApiReturnCode.GENERATE_ORDER_FAIL);
            }
        }
        else
        {
            restRes.setDescribe("card未绑定");
            restRes.setReturnCode(HttpApiReturnCode.CARD_NOT_BIND);
        }

        return restRes;
    }

    /**     
     * phoneTicket：手机取票
     * @param cardno  手机号或手机号后四位
     * @param ticketpwd  取票密码
     * @param sid 设备编号
     * @param seq 验证序列号
     * @param sidpwd  序列号与设备密码进行MD5之后的密文
     * @param time JuJuSports接收到用户短信的时间，格式为yyyyMMddHHmmss
     * @return TicketInfoDto 出票信息
     * @author Vincent
     * @date 2015年5月21日 下午4:40:56	 
     */
    @RequestMapping(value = "/phone/getticket.do")
    @ResponseBody
    public RestRespone<String> phoneTicket(@RequestParam("phoneno") String phoneno,
        @RequestParam("ticketpwd") String ticketpwd, @RequestParam("sid") String sid,
        @RequestParam("seq") String seq, @RequestParam("sidPwd") String sidpwd)
    {
        //order_items  手机号。验证码     NO
        RestRespone<String> restRes = new RestRespone<String>();
        OrderItemsDto orderItemsDto = new OrderItemsDto();
        orderItemsDto.setTelephone(phoneno);
        orderItemsDto.setValidCode(ticketpwd);
        List<OrderItemsDto> orderList = orderItemsService.findListBy(orderItemsDto);
        if (!CollectionUtils.isEmpty(orderList))
        {
            OrderItemsDto odDto = orderList.get(0);
            
            //实名认证未通过
            UsersDto usersDto = checkRealInfo(odDto.getUserAccountId());
            if(StringUtils.isEmpty(usersDto.getDocumentNo()) || StringUtils.isEmpty(usersDto.getCardImage())){
                restRes.setDescribe("实名认证未通过");
                restRes.setReturnCode(HttpApiReturnCode.REAL_NAME_AUTHENTICATION_FAIL);            	
            	return restRes;
            }
            
            if(odDto.getOrderStatus() ==OrderStatus.Finished.getValue()){
                restRes.setDescribe("已出票");
                restRes.setReturnCode(HttpApiReturnCode.ORDER_IS_USER);
                return restRes;
            }
            odDto.setUsrType(DataStatus.PHONE_USR);
            odDto.setOrderStatus(OrderStatus.Finished.getValue());
            orderItemsService.updateItemsInfo(odDto);
            venuesApiService.updateOnliner(odDto.getOwnerAccountId(), true);

            restRes.setDescribe("成功");
            restRes.setReturnCode(HttpApiReturnCode.SUCCESS_CODE);
        }
        else
        {
            restRes.setDescribe("订单不存在");
            restRes.setReturnCode(HttpApiReturnCode.ORDERITEMS_NOT_FOUND);
        }

        return restRes;
    }
    /**
    	 * 此方法描述的是：判断用户是否进行实名认证
    	 * @author: cwftalus@163.com
    	 * @version: 2015年6月3日 下午3:10:02
     */
    public UsersDto checkRealInfo(String userAccountId){
    	UsersDto usersDto = new UsersDto();
    	usersDto = userService.selectUsesByUserAccountId(userAccountId);
    	return usersDto; 
    }
    
    
    /**     
     * tdcTicket：二维码取票
     * @param orderNo  订单编号 13位数字
     * @param sid 设备编号
     * @param seq 验证序列号
     * @param sidpwd  序列号与设备密码进行MD5之后的密文
     * @return TicketInfoDto 出票信息
     * @author Vincent
     * @date 2015年5月21日 下午4:40:56    
     */
    @RequestMapping(value = "/tdc/getticket.do")
    @ResponseBody
    public RestRespone<String> tdcTicket(@RequestParam("orderno") String orderNo,
        @RequestParam("sid") String sid, @RequestParam("seq") String seq,
        @RequestParam("sidPwd") String sidpwd)
    {
        //order_items -->orderno  
        RestRespone<String> restRes = new RestRespone<String>();
        OrderItemsDto oiDto = orderItemsService.findByOrderNo(orderNo);
        if (null != oiDto)
        {
            //实名认证未通过
            UsersDto usersDto = checkRealInfo(oiDto.getUserAccountId());
            if(StringUtils.isEmpty(usersDto.getDocumentNo()) || StringUtils.isEmpty(usersDto.getCardImage())){
                restRes.setDescribe("实名认证未通过");
                restRes.setReturnCode(HttpApiReturnCode.REAL_NAME_AUTHENTICATION_FAIL);            	
            	return restRes;
            }
            
            if(oiDto.getOrderStatus() ==OrderStatus.Finished.getValue()){
                restRes.setDescribe("二维码已使用");
                restRes.setReturnCode(HttpApiReturnCode.ORDER_IS_USER);
                return restRes;
            }
            oiDto.setUsrType(DataStatus.CODE_USR);
            oiDto.setOrderStatus(OrderStatus.Finished.getValue());
            orderItemsService.updateItemsInfo(oiDto);
            venuesApiService.updateOnliner(oiDto.getOwnerAccountId(), true);
            restRes.setDescribe("成功");
            restRes.setReturnCode(HttpApiReturnCode.SUCCESS_CODE);
        }
        else
        {
            restRes.setDescribe("订单不存在");
            restRes.setReturnCode(HttpApiReturnCode.ORDERITEMS_NOT_FOUND);
        }
        return restRes;
    }

    /**     
     * 根据二维码修改用户信息
     * @param orderNo  订单编号 13位数字
     * @param documentNo 身份证编号
     * @param cardImage 身份证图片
     * @return String  修改是否成功信息
     * @author Yin
     * @date 2015年06月03日 下午15:43:26    
     */
    @RequestMapping(value = "/venues/updateInfoByTdc.do")
    @ResponseBody       
    public RestRespone<String> updateInfoByTdc(@RequestParam("orderNo") String orderNo,
        @RequestParam("documentNo") String documentNo, @RequestParam("cardImage") String cardImage){
        RestRespone<String> result = new RestRespone<String>();
        OrderItemsDto oiDto = orderItemsService.findByOrderNo(orderNo);
        if (null != oiDto){
            String userAccountId = oiDto.getUserAccountId();
            if(userAccountId != null && !userAccountId.equals("")){//StringUtils.isEmpty()
//            	UsersDto usersDto = userService.selectUsesByUserAccountId(userAccountId);
            	UsersDto usersDto = checkRealInfo(userAccountId);
            	if(usersDto==null){
            		result.setDescribe("用户基础信息不存在！");
            		result.setReturnCode(HttpApiReturnCode.USERS_NOT_BIND);
            		return result;
            	}
            	usersDto.setDocumentNo(documentNo);
            	usersDto.setCardImage(cardImage);
            	//usersDto.setLastUpdateTime(new Date());
            	int flag = userService.updateUsers(usersDto);
            	if(flag > 0){
            		result.setDescribe("修改成功！");
            		result.setReturnCode(HttpApiReturnCode.SUCCESS_CODE);
            		return result;
            	}
	            	result.setDescribe("修改失败！");
		    		result.setReturnCode(HttpApiReturnCode.DB_EXCEPTION);
            }
		            
        }
		return result;
    }
    
    /**     
     * 根据手机号和获取的短信密码修改用户信息
     * @param phoneno  手机号
     * @param ticketpwd  获取的短信密码
     * @param documentNo 身份证编号
     * @param cardImage 身份证图片
     * @return String  修改是否成功信息
     * @author Yin
     * @date 2015年06月03日 下午16:13:33    
     */
    @RequestMapping(value = "/venues/updateInfoByPhone.do")
    @ResponseBody       
    public RestRespone<String> updateInfoByPhone(@RequestParam("phoneno") String phoneno,@RequestParam("ticketpwd") String ticketpwd,
        @RequestParam("documentNo") String documentNo, @RequestParam("cardImage") String cardImage){
        RestRespone<String> result = new RestRespone<String>();
        OrderItemsDto orderItemsDto = new OrderItemsDto();
        orderItemsDto.setTelephone(phoneno);
        orderItemsDto.setValidCode(ticketpwd);
        List<OrderItemsDto> orderList = orderItemsService.findListBy(orderItemsDto);
        if (orderList.size()>0){
            String userAccountId = orderList.get(0).getUserAccountId();
            if(userAccountId != null && !userAccountId.equals("")){
            	//UsersDto usersDto = userService.selectUsesByUserAccountId(userAccountId);/
            	UsersDto usersDto = checkRealInfo(userAccountId);
            	if(usersDto==null){
            		result.setDescribe("用户基础信息不存在！");
            		result.setReturnCode(HttpApiReturnCode.USERS_NOT_BIND);
            		return result;
            	}
            	usersDto.setDocumentNo(documentNo);
            	usersDto.setCardImage(cardImage);
            	//usersDto.setLastUpdateTime(new Date());
            	int flag = userService.updateUsers(usersDto);
            	if(flag > 0){
            		result.setDescribe("修改成功！");
            		result.setReturnCode(HttpApiReturnCode.SUCCESS_CODE);
            		return result;
            	}
	            	result.setDescribe("修改失败！");
		    		result.setReturnCode(HttpApiReturnCode.DB_EXCEPTION);
            }
		            
        }
		return result;
    }
}
