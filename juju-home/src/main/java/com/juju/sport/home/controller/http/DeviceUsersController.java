package com.juju.sport.home.controller.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.common.constants.HttpApiReturnCode;
import com.juju.sport.home.dto.RestRespone;
import com.juju.sport.user.dto.DeviceUsersDto;
import com.juju.sport.user.service.IDeviceUsersService;

@Controller
@RequestMapping(value = "/api/rest")
public class DeviceUsersController {
	
	
	@Autowired
	private IDeviceUsersService iDeviceUsersService;
	
    /**     
     * venuesInfo：查询场馆信息
     * @param sid 设备编号
     * @param seq 验证序列号
     * @param sidpwd  序列号与设备密码进行MD5之后的密文
     * @param time 接收到用户请求，格式为yyyyMMddHHmmss
     * @return String
     * @author cwftalus@163.com
     * @date 2015年5月21日 下午4:38:40	 
     */
    @RequestMapping(value = "/checkUser.do")
    @ResponseBody
    public RestRespone<String> checkUser(@RequestParam("sid") String sid, @RequestParam("seq") String seq,
        @RequestParam("sidPwd") String sidPwd, @RequestParam("userNo") String userNo)
    {   
    	RestRespone<String> result = new RestRespone<String>();
    	
    	DeviceUsersDto deviceUsersDto =  iDeviceUsersService.checkUser(userNo);
    	if(deviceUsersDto==null){
    		result.setReturnCode(HttpApiReturnCode.NOT_AUTHORIZED_REQ);
    		return result;
    	}
    	result.setReturnCode(HttpApiReturnCode.SUCCESS_CODE);
    	return result;
    }
}
