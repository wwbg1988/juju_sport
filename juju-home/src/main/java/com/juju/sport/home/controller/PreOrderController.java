package com.juju.sport.home.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.dto.DateDto;
import com.juju.sport.common.dto.Future;
import com.juju.sport.common.dto.WeatherMap;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.common.util.WeatherUtil;
import com.juju.sport.home.util.SessionUtil;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.service.IOrderItemsService;
import com.juju.sport.stadium.dto.SpaceDto;
import com.juju.sport.stadium.dto.SpaceOpenTimeDto;
import com.juju.sport.stadium.service.ISpaceOpenTimeService;
import com.juju.sport.stadium.service.ISpaceService;
import com.juju.sport.user.dto.LoginUserDto;

/**
 * 
	 * 此类描述的是：场馆对应场地的选择页面 选择完成之后提交订单
	 * @author: cwftalus@163.com
	 * @version: 2015年4月10日 上午11:48:44
 */
@Controller
@RequestMapping(value = "/preOrder")
public class PreOrderController {
	@Autowired
	private ISpaceService iSpaceService;
	
	@Autowired
	private ISpaceOpenTimeService iSpaceOpenTimeService;

	@Autowired
	private IOrderItemsService iOrderItemsService;	
	
	
	@RequestMapping(value = "/weatherList.do")
	@ResponseBody
	public Response<Future> weatherList(String orderTime) {
		Response<Future> result = new Response<Future>();
		if(WeatherMap.weatherMap.isEmpty()){
			WeatherUtil.loadWeatherApi();
		}
		Future weathInfo = WeatherMap.weatherMap.get(orderTime.replace("-", ""));
		if(weathInfo==null){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("数据异常错误");
			return result;
		}
		result.setData(weathInfo);
		return result;//dateList;
	}
	
	/**
	 * 
		 * 此方法描述的是：获取当前场馆 往后顺延7天的数据 默认为当天 以及当天该场馆可用的时间起始
		 * @author: cwftalus@163.com
		 * @version: 2015年3月27日 下午2:40:24
	 */
	@RequestMapping(value = "/dateList/{ownerAccountId}.do")
	@ResponseBody
	public Response<List<DateDto>> dateList(@PathVariable String ownerAccountId) {
		Response<List<DateDto>> result = new Response<List<DateDto>>();
		//获取当前日期往后延迟7天的日期和星期
		List<DateDto> dateList = new ArrayList<DateDto>();
		try {
			dateList = DateUtils.queryDayAndWeek(new Date(), 7);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setData(dateList);
		return result;//dateList;
	}
	
	@RequestMapping(value = "/hourList/{ownerAccountId}.do")
	@ResponseBody
	public Response<List<Integer>> hourList(@PathVariable String ownerAccountId,SpaceDto spaceDto) throws Exception{
		Response<List<Integer>> result = new Response<List<Integer>>();
//		List<Integer> hourList = new ArrayList<Integer>();
		spaceDto.setOwnerAccountId(ownerAccountId);
		List<SpaceDto> spaceList = iSpaceService.findBy(spaceDto);//获取所有场地信息
		if(spaceList.isEmpty()){
			return result;
		}
		List<SpaceOpenTimeDto> spaceTimeList = loadspaceTimeList(spaceList,spaceDto);//iSpaceOpenTimeService.findBy(spaceOpenTimeDto);//获取 所有场地选中日期对应的开始时间肯结束时间
		Date date = new Date();
		String day = DateUtils.format(date, DateUtils.YMD_DASH);
		
		//判断是否属于当前的日期 
		Boolean isNow = true;
		if(day.equals(spaceDto.getOrderTime())){
			isNow = false;
		}
		List<Integer> infoIds = loadComparatorList(isNow,spaceTimeList);
		result.setData(infoIds);

		return result;
	}
	
	/**
	 * 
		 * 此方法描述的是：获取当前场馆和时间的可用数据 默认当天
		 * @author: cwftalus@163.com
		 * @version: 2015年3月27日 下午2:41:11
	 */
	@RequestMapping(value = "/spaceLists/{ownerAccountId}.do")
	@ResponseBody
	public Response<List<SpaceDto>> spaceLists(@PathVariable String ownerAccountId,SpaceDto spaceDto,HttpSession session) throws Exception{
		Response<List<SpaceDto>> result = new Response<List<SpaceDto>>();
		List<SpaceDto> spaceList = loadSpaceList(ownerAccountId);//获取该场馆是否存在场地信息
		if(spaceList.isEmpty()){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("没有对应的场地信息");
			return result;
		}
		SpaceOpenTimeDto spaceOpenTimeDto = new SpaceOpenTimeDto();
		spaceOpenTimeDto.setOpenWeek(Short.valueOf(spaceDto.getSjWeek()));//日期 1 2 3 4 5 6 7 表示
		spaceOpenTimeDto.setStartTime(DateUtils.parse(spaceDto.getSjTime(),DateUtils.HH));//时间  该时间 必须在配置的范围之内
		spaceOpenTimeDto.setOwnerAccountId(spaceDto.getOwnerAccountId());
		List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findBy(spaceOpenTimeDto);//获取场馆用户 该日期 改时间 内可用的场地数据
		
		//列出已经被预定的场地信息
//		List<ParameterDto> paramterList = new ArrayList<ParameterDto>();
//		ParameterDto parameterDto = new ParameterDto();
//		parameterDto.setDate(date);
//		parameterDto.setOrderStatus(OrderStatus.OrderWait.getValue());
//		parameterDto.setUserAccountId(userAccountId);
//		iOrderItemsService.selectCheckSpaceBuy();
		
		List<String> spaceIds = new ArrayList<String>();
		int hourPrice = 0;
		HashMap<String,Integer> spaceHourPriceMap = new HashMap<String, Integer>();
		if(!spaceTimeList.isEmpty()){//获取场地Id信息
			int sjTIme = 0;//选择的开始时间
			if(!StringUtils.isEmpty(spaceDto.getSjTime())){
				sjTIme = Integer.valueOf(spaceDto.getSjTime());//选择的开始时间
			}
			for(SpaceOpenTimeDto spceOpenTimeDto : spaceTimeList){
				int startTime = Integer.valueOf(DateUtils.format(spceOpenTimeDto.getStartTime(), DateUtils.HH));
				int endTime = Integer.valueOf(DateUtils.format(spceOpenTimeDto.getEndTime(), DateUtils.HH));
				if( sjTIme>=startTime && sjTIme < endTime){
					hourPrice = spceOpenTimeDto.getPrice();
				}
				spaceIds.add(spceOpenTimeDto.getSpaceId());
				spaceHourPriceMap.put(spceOpenTimeDto.getSpaceId(), hourPrice);
			}
		}
		List<SpaceDto> dataList = iSpaceService.findBy(spaceIds,spaceDto.getOwnerAccountId());
		List<SpaceDto> resultList = new ArrayList<SpaceDto>();
		for(SpaceDto spaceDtoL : dataList){
			int tPrice = spaceHourPriceMap.get(spaceDtoL.getId());
			spaceDtoL.setPrice(tPrice);
			resultList.add(spaceDtoL);
		}
		
		result.setData(resultList);
		return result;
	}
	
	/**
	 * 
		 * 此方法描述的是：获取当前场馆和时间的可用数据 默认当天
		 * @author: cwftalus@163.com
		 * @version: 2015年3月27日 下午2:41:11
	 */
//	@RequestMapping(value = "/spaceList/{ownerAccountId}.do")
//	@ResponseBody
//	public Response<HashMap<String,List<SpaceInfoDto>>> spaceList(@PathVariable String ownerAccountId,SpaceDto spaceDto,HttpSession session) throws Exception{
//		Response<HashMap<String,List<SpaceInfoDto>>> result = new Response<HashMap<String,List<SpaceInfoDto>>>();
//		HashMap<String,List<SpaceInfoDto>> spaceListMap = new HashMap<String,List<SpaceInfoDto>>();
//		List<SpaceDto> spaceList = loadSpaceList(ownerAccountId);//获取该场馆是否存在场地信息
//		if(spaceList.isEmpty()){
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("没有对应的场地信息");
//			result.setData(spaceListMap);
//			return result;
//		}
//		List<SpaceOpenTimeDto> spaceTimeList = loadspaceTimeList(spaceList,spaceDto);//iSpaceOpenTimeService.findBy(spaceOpenTimeDto);//获取 所有场地选中日期对应的开始时间肯结束时间
//		
//		
//		Date date = new Date();
//		String day = DateUtils.format(date, DateUtils.YMD_DASH);
//		Boolean isNow = true;
//		if(day.equals(spaceDto.getOrderTime())){
//			isNow = false;
//		}
//		List<Integer> infoIds = loadComparatorList(isNow,spaceTimeList);
//
//		//获取当前登录用户针对当天、当前场馆、当前场地 是否预订了场地信息
//		HashMap<String,Integer> ownerHashMap = loadUserOwnerSpace(session,spaceDto);	
//
//		
//		HashMap<String,Integer> userMap = new HashMap<String, Integer>();
//		//需要判断场地对应的时间是否是有效的时间
//		for(int j=0;j<spaceTimeList.size();j++){
//			SpaceOpenTimeDto spaceOpenTimeObj = spaceTimeList.get(j);
//			Integer startIV = Integer.valueOf(DateUtils.format(spaceOpenTimeObj.getStartTime(),DateUtils.HH));
//			Integer endIV = Integer.valueOf(DateUtils.format(spaceOpenTimeObj.getEndTime(),DateUtils.HH));
//			for(int s = startIV;s < endIV; s++){
//				String keyCode = spaceOpenTimeObj.getSpaceId()+"-"+s+"-"+(s+1);//有效的时间范围
//				if(ownerHashMap.containsKey(keyCode)){
//					userMap.put(keyCode,DataStatus.ISUSER);//本时间段 已经订购
//				}else{
//					userMap.put(keyCode,DataStatus.ENABLED);//在范围内可以使用	
//				}
//			}
//		}
//		
//		
//		int startT = 0;
//		int endT  = 0;	
//		for(int i=0;i<spaceList.size();i++){
//			SpaceDto spaceObj = spaceList.get(i);
//			String keyCode = spaceObj.getSpaceName();
//			startT = infoIds.get(0).getValue();
//			endT  = infoIds.get(infoIds.size()-1).getValue();
//			List<SpaceInfoDto> spaceOpenDtoList = new ArrayList<SpaceInfoDto>();
//			SpaceInfoDto spaceInfoDto = null;
//			for(int j = startT;j <= endT;j++){
//				spaceInfoDto = new SpaceInfoDto();
//				spaceInfoDto.setSpaceId(spaceObj.getId());//场地Id
//				spaceInfoDto.setHour(String.valueOf(j));
//				spaceInfoDto.setStartHour(String.valueOf(j));//开始时间
//				spaceInfoDto.setEndHour(String.valueOf(j+1));//结束时间
//				spaceInfoDto.setPrice(spaceObj.getPrice());
//				String keysCode = spaceObj.getId()+"-"+j+"-"+(j+1);
//				Integer isUser = userMap.get(keysCode);
//				if(userMap.containsKey(keysCode)){
//					spaceInfoDto.setIsUser(isUser);	
//				}
//				spaceOpenDtoList.add(spaceInfoDto);
//			}
//			spaceListMap.put(keyCode, spaceOpenDtoList);
//		}
//		result.setData(spaceListMap);
//		return result;
////		return spaceListMap;
//	}
	
	/**
	 * 
		 * 此方法描述的是：获取当前登录用户针对当天、当前场馆、当前场地 是否预订了场地信息
		 * @author: cwftalus@163.com
		 * @version: 2015年4月10日 下午12:01:20
	 */
	public HashMap<String,Integer> loadUserOwnerSpace(HttpSession session,SpaceDto spaceDto){
		HashMap<String,Integer> hashMap = new HashMap<String, Integer>();
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		if(loginUserDto!=null){
			OrderItemsDto orderItemsDto = new OrderItemsDto();
			orderItemsDto.setUserAccountId(loginUserDto.getId());//当前用户Id
			orderItemsDto.setOwnerAccountId(spaceDto.getOwnerAccountId());
			orderItemsDto.setDate(spaceDto.getOrderTime());//用户购买的日期
			List<OrderItemsDto> resultList = iOrderItemsService.findListBy(orderItemsDto);
			hashMap = ListToMap(resultList);
		}
		return hashMap;
	}
	
	/**
	 * 
		 * 此方法描述的是：内部方法 将list 转成对应的 Map 对象 
		 * @author: cwftalus@163.com
		 * @version: 2015年4月10日 下午2:09:32
	 */
	public HashMap<String,Integer> ListToMap(List<OrderItemsDto> list){
		HashMap<String,Integer> tMap = new HashMap<String,Integer>();
		if(!list.isEmpty()){
			Iterator<OrderItemsDto> its = list.iterator();
			while(its.hasNext()){
				OrderItemsDto orderItemsDto = its.next();
				String keyCode = orderItemsDto.getSpaceId()+"-"+orderItemsDto.getOrderTime()+"-"+orderItemsDto.getEndTime();//有效的时间范围
				tMap.put(keyCode, DataStatus.ISUSER);
			}
		}
		return tMap;
		
	}
	
	/**
	 * 
		 * 此方法描述的是：时间从头到尾的排序
		 * @author: cwftalus@163.com
		 * @version: 2015年3月27日 下午5:29:36
	 */
	public List<Integer> loadComparatorList(Boolean isNow,List<SpaceOpenTimeDto> spaceTimeList){
		List<Integer> hours = new ArrayList<Integer>();
		
		for(SpaceOpenTimeDto spaceOpenTimeDto: spaceTimeList){
			hours.addAll(getAllHoursBy(isNow,spaceOpenTimeDto));
		}
		
		//针对所有 hours 进行去重复处理
		List<Integer> hourList = new ArrayList<Integer>();
		for (Integer item : hours) {
			if (!hourList.contains(item)) {
				hourList.add(item);
			}
		}
		
		//进行排序
		Collections.sort(hourList, new Comparator<Integer>() {   
	    public int compare(Integer o1, Integer o2) {      
	        return (o1).compareTo(o2);//return (o2.getValue() - o1.getValue());
	    	}
		});
				
//		HashMap<String,Integer> timeMap = new HashMap<String,Integer>();
//		List<Integer> hours = new ArrayList<Integer>();
//		for(int i=0;i<spaceTimeList.size();i++){
//			SpaceOpenTimeDto spaceOpenTimeObj = spaceTimeList.get(i);
//			String start = DateUtils.format(spaceOpenTimeObj.getStartTime(),DateUtils.HH);
//			String end = DateUtils.format(spaceOpenTimeObj.getEndTime(),DateUtils.HH);
//			Integer startI = Integer.valueOf(start);
//			Integer endI = Integer.valueOf(end);
//			timeMap.put(start, startI);
//			timeMap.put(end, endI);
//		}
//		
//		List<Map.Entry<String, Integer>> infoIds =  new ArrayList<Map.Entry<String, Integer>>(timeMap.entrySet());
//		Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {   
//		    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
//		        return (o1.getKey()).toString().compareTo(o2.getKey());//return (o2.getValue() - o1.getValue());
//		    }
//		});
		
		return hourList;
	}
	
	public List<Integer> getAllHoursBy(Boolean isNow,SpaceOpenTimeDto spaceOpenTimeDto){
		List<Integer> tList = new ArrayList<Integer>();
		String start = DateUtils.format(spaceOpenTimeDto.getStartTime(),DateUtils.HH);
		String end = DateUtils.format(spaceOpenTimeDto.getEndTime(),DateUtils.HH);
		Integer startI = Integer.valueOf(start);
		Integer endI = Integer.valueOf(end);
		int hh = Integer.valueOf(DateUtils.format(new Date(),DateUtils.HH)).intValue();
		for(int s = startI;s<endI;s++){
			if(!isNow){
				if((hh>=s)){
					continue;	
				}
			}
			tList.add(s);
		}
		return tList;
	}
	
	
	//获取所有场地信息
	private List<SpaceDto> loadSpaceList(String ownerAccountId){
		SpaceDto spaceDto = new SpaceDto();
		spaceDto.setOwnerAccountId(ownerAccountId);
		List<SpaceDto> spaceList = iSpaceService.findBy(spaceDto);//获取所有场地信息
		return spaceList;
	}
	
	//获取 所有场地选中日期对应的开始时间肯结束时间
	private List<SpaceOpenTimeDto> loadspaceTimeList(List<SpaceDto> spaceList,SpaceDto spaceDto) throws Exception{
		SpaceOpenTimeDto spaceOpenTimeDto = new SpaceOpenTimeDto();
		
		List<String> spaceIds = new ArrayList<String>();
		for(int i=0;i<spaceList.size();i++){
			spaceIds.add(spaceList.get(i).getId());
		}

		spaceOpenTimeDto.setSpaceIds(spaceIds);
		if(!StringUtils.isEmpty(spaceDto.getOrderTime())){
			spaceOpenTimeDto.setOpenWeek(DateUtils.dayForWeek(DateUtils.parse(spaceDto.getOrderTime(), DateUtils.YMD_DASH)));
		}else{
			spaceOpenTimeDto.setOpenWeek(DateUtils.dayForWeek(new Date()));
		}
		spaceOpenTimeDto.setOwnerAccountId(spaceDto.getOwnerAccountId());
		List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findBy(spaceOpenTimeDto);//获取 所有场地选中日期对应的开始时间肯结束时间
		return spaceTimeList;
	}
}
