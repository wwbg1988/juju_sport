package com.juju.sport.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.base.dto.SportTypeDto;
import com.juju.sport.base.dto.WuweiUserDto;
import com.juju.sport.base.service.ISportTypeService;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.Response;
import com.juju.sport.home.util.SessionUtil;
import com.juju.sport.user.dto.LoginUserDto;

/**
 * 
 * 此类描述的是：运动类型基础数据管理
 * 
 * @author: cwftalus@163.com
 * @version: 2015年3月24日 下午2:18:07
 **/
@Controller
@RequestMapping(value = "/sportType")
public class SportTypeController {
	protected static final Log logger = LogFactory.getLog(SportTypeController.class);

	@Autowired
	private ISportTypeService iSportTypeService;

	/**
	 * 
	 * 此方法描述的是：根据参数获取体育项目信息
	 * 
	 * @author: wangxiongdx@163.com
	 * @version: 2015年3月24日 下午2:15:51
	 */
	
	@RequestMapping(value = "/wuweiUserlist.do")
    @ResponseBody
    public Response<List<WuweiUserDto>> list(WuweiUserDto wuweiUserDto)
    {
        Response<List<WuweiUserDto>> results = new Response<List<WuweiUserDto>>();
        QinJiaConnectInfo qinJiaConnectInfo = new QinJiaConnectInfo();

        Map map_push_info = new HashMap();
        map_push_info.put("email", "wuweitree@163.com"); // 开发者账号，必选项
        map_push_info.put("devpwd", "wuwei123"); // 开发者密码，必选项
        map_push_info.put("appkey", "733a1dfc-d717-49e6-8ea9-3d700e1988b2"); // 应用的appkey，必选项
        map_push_info.put("from", "123456"); // 发送方账号，必选项，该账号必须存在
        map_push_info.put("to_type", "0"); // 发送对象类型，必选项。0发往用户，1发往聊天室，2发往群
        List to_id_list = new ArrayList();
        to_id_list.add("asdf");
        map_push_info.put("to_id", to_id_list); // 给发送的id,50个以内为佳，最多不要超过100个
        map_push_info.put("save", "1"); // 此条数据是否保存（离线消息和历史记录），必选项。0不保存；1保存
        map_push_info.put("msg_type", "0"); // 发送消息类型，必选项。0文本
        map_push_info.put("text", "哈哈哈哈哈哈哈哈"); // 发送内容

        JSONObject aa = qinJiaConnectInfo.pushInformation(map_push_info);
        // JSONObject aa = qinJiaConnectInfo.getHisData();

        String error_code = aa.getString("errcode");
        logger.info("-------------error_code=" + error_code);

        if ("200".equals(error_code))
        {
            results.setMessage("success");
        }
        else
        {
            results.setMessage("errcode=" + error_code);
        }

        return results;
    }
	
	
	
	@RequestMapping(value = "/list.do")
	@ResponseBody
	public Response<List<SportTypeDto>> list(SportTypeDto sportTypeDto) {
		Response<List<SportTypeDto>> results = new  Response<List<SportTypeDto>>();
		List<SportTypeDto> result = iSportTypeService.findBy(sportTypeDto);
		results.setData(result);
		return results;
	}
	/**
	 *此方法获取所有的体育类别 
	 */
	@RequestMapping(value="/findAll.do")
	@ResponseBody
	public Response<List<SportTypeDto>> findAll(){
		Response<List<SportTypeDto>> results = new  Response<List<SportTypeDto>>();
		List<SportTypeDto> result = iSportTypeService.findAll();
		results.setData(result);
		return results;
		
	}
	/**
	 * 按照场地体育项目来甄别场地类型
	 */
	@RequestMapping(value="/findByStad.do")
	@ResponseBody
	public Response<List<SportTypeDto>>findByStad(HttpSession session){
		Response<List<SportTypeDto>> results = new  Response<List<SportTypeDto>>();
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		if(loginUserDto!=null){
			List<SportTypeDto> result = iSportTypeService.findByStad(loginUserDto.getId());
			results.setData(result);
		}
		return  results;
	}
}
