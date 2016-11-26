package com.juju.sport.home.controller.m.api;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.dto.DateDto;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.home.dto.m.OrderDates;
import com.juju.sport.home.dto.m.OrderTimes;
import com.juju.sport.home.dto.m.SpaceTimeDto;
import com.juju.sport.stadium.dto.SpaceDto;
import com.juju.sport.stadium.dto.SpaceOpenTimeDto;
import com.juju.sport.stadium.service.ISpaceOpenTimeService;
import com.juju.sport.stadium.service.ISpaceService;
@Controller
@RequestMapping(value = "/api/m/preOrder")
public class PreOrderMController {
	
	@Autowired
	private ISpaceService iSpaceService;
	
	@Autowired
	private ISpaceOpenTimeService iSpaceOpenTimeService;
	
	public static void main(String[] args) throws Exception {
		String json = "["
				+ "{'startTime':'10','openWeek':4,'price':0,'spaceName':'DDDXXX','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'5a703aa8-7877-4519-a58f-fe9072fcb565','endTime':'18'},"
//				+ "{'startTime':'02','openWeek':3,'price':50,'spaceName':'aaaa','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'6bc9ecc3-6f5e-4bb9-b603-0b741a57b798','endTime':'05'},"
//				+ "{'startTime':'00','openWeek':5,'price':0,'spaceName':'33','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'9bb973fa-6ea5-4d7e-8930-b558ccfc0e56','endTime':'03'},"
//				+ "{'startTime':'00','openWeek':1,'price':23,'spaceName':'sdsdd','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'3ea902df-c6e8-42ab-ba03-e3e9459afe0d','endTime':'03'},"
//				+ "{'startTime':'00','openWeek':1,'price':20,'spaceName':'aaaa','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'6bc9ecc3-6f5e-4bb9-b603-0b741a57b798','endTime':'03'}"
				+ "{'startTime':'04','openWeek':4,'price':200,'spaceName':'dasdsadsa','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'226729a5-1108-4014-b540-46fd231c3a52','endTime':'11'}"
//				+ "{'startTime':'10','openWeek':5,'price':0,'spaceName':'DDDXXX','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'5a703aa8-7877-4519-a58f-fe9072fcb565','endTime':'18'},"
//				+ "{'startTime':'10','openWeek':3,'price':0,'spaceName':'DDDXXX','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'5a703aa8-7877-4519-a58f-fe9072fcb565','endTime':'18'},"
//				+ "{'startTime':'10','openWeek':2,'price':122,'spaceName':'EEERRR','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'73e00163-79cd-4137-a88b-ec4e3be36948','endTime':'18'},{'startTime':'00','openWeek':2,'price':20,'spaceName':'aaaa','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'6bc9ecc3-6f5e-4bb9-b603-0b741a57b798','endTime':'03'},{'startTime':'12','openWeek':6,'price':20000,'spaceName':'dasdsadsa','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'226729a5-1108-4014-b540-46fd231c3a52','endTime':'15'},{'startTime':'00','openWeek':4,'price':0,'spaceName':'33','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'9bb973fa-6ea5-4d7e-8930-b558ccfc0e56','endTime':'03'},{'startTime':'13','openWeek':1,'price':30,'spaceName':'33','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'9bb973fa-6ea5-4d7e-8930-b558ccfc0e56','endTime':'14'},{'startTime':'12','openWeek':1,'price':0,'spaceName':'WEWEWE','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'675fe0d6-fa25-4666-9760-d66dcb1104d4','endTime':'15'},{'startTime':'05','openWeek':1,'price':0,'spaceName':'篮球','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'1da6f75e-f1da-4a8a-ad57-a06b36e7bcc8','endTime':'22'},{'startTime':'12','openWeek':5,'price':20000,'spaceName':'dasdsadsa','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'226729a5-1108-4014-b540-46fd231c3a52','endTime':'15'},{'startTime':'04','openWeek':1,'price':200,'spaceName':'dasdsadsa','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'226729a5-1108-4014-b540-46fd231c3a52','endTime':'11'},{'startTime':'10','openWeek':7,'price':122,'spaceName':'EEERRR','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'73e00163-79cd-4137-a88b-ec4e3be36948','endTime':'18'},{'startTime':'10','openWeek':6,'price':122,'spaceName':'EEERRR','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'73e00163-79cd-4137-a88b-ec4e3be36948','endTime':'18'},{'startTime':'15','openWeek':1,'price':0,'spaceName':'WEWEWE','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'675fe0d6-fa25-4666-9760-d66dcb1104d4','endTime':'17'},{'startTime':'06','openWeek':2,'price':0,'spaceName':'33','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'9bb973fa-6ea5-4d7e-8930-b558ccfc0e56','endTime':'07'},{'startTime':'10','openWeek':6,'price':0,'spaceName':'DDDXXX','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'5a703aa8-7877-4519-a58f-fe9072fcb565','endTime':'18'},{'startTime':'04','openWeek':2,'price':0,'spaceName':'33','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'9bb973fa-6ea5-4d7e-8930-b558ccfc0e56','endTime':'05'},{'startTime':'08','openWeek':1,'price':0,'spaceName':'WEWEWE','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'675fe0d6-fa25-4666-9760-d66dcb1104d4','endTime':'11'},{'startTime':'12','openWeek':4,'price':20000,'spaceName':'dasdsadsa','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'226729a5-1108-4014-b540-46fd231c3a52','endTime':'15'},{'startTime':'05','openWeek':3,'price':0,'spaceName':'篮球','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'1da6f75e-f1da-4a8a-ad57-a06b36e7bcc8','endTime':'22'},{'startTime':'02','openWeek':1,'price':0,'spaceName':'33','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'9bb973fa-6ea5-4d7e-8930-b558ccfc0e56','endTime':'05'},{'startTime':'10','openWeek':5,'price':122,'spaceName':'EEERRR','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'73e00163-79cd-4137-a88b-ec4e3be36948','endTime':'18'},{'startTime':'04','openWeek':2,'price':200,'spaceName':'dasdsadsa','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'226729a5-1108-4014-b540-46fd231c3a52','endTime':'11'},{'startTime':'10','openWeek':4,'price':122,'spaceName':'EEERRR','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'73e00163-79cd-4137-a88b-ec4e3be36948','endTime':'18'},{'startTime':'10','openWeek':3,'price':122,'spaceName':'EEERRR','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'73e00163-79cd-4137-a88b-ec4e3be36948','endTime':'18'},{'startTime':'04','openWeek':3,'price':200,'spaceName':'dasdsadsa','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'226729a5-1108-4014-b540-46fd231c3a52','endTime':'11'},{'startTime':'10','openWeek':7,'price':0,'spaceName':'DDDXXX','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'5a703aa8-7877-4519-a58f-fe9072fcb565','endTime':'18'},{'startTime':'00','openWeek':2,'price':0,'spaceName':'33','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'9bb973fa-6ea5-4d7e-8930-b558ccfc0e56','endTime':'03'},{'startTime':'12','openWeek':7,'price':20000,'spaceName':'dasdsadsa','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'226729a5-1108-4014-b540-46fd231c3a52','endTime':'15'},{'startTime':'10','openWeek':2,'price':0,'spaceName':'DDDXXX','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'5a703aa8-7877-4519-a58f-fe9072fcb565','endTime':'18'},{'startTime':'10','openWeek':1,'price':0,'spaceName':'DDDXXX','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'5a703aa8-7877-4519-a58f-fe9072fcb565','endTime':'18'},{'startTime':'10','openWeek':1,'price':122,'spaceName':'EEERRR','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'73e00163-79cd-4137-a88b-ec4e3be36948','endTime':'18'},{'startTime':'02','openWeek':2,'price':50,'spaceName':'aaaa','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'6bc9ecc3-6f5e-4bb9-b603-0b741a57b798','endTime':'05'},{'startTime':'05','openWeek':2,'price':0,'spaceName':'篮球','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'1da6f75e-f1da-4a8a-ad57-a06b36e7bcc8','endTime':'22'},{'startTime':'00','openWeek':1,'price':200,'spaceName':'dasdsadsa','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'226729a5-1108-4014-b540-46fd231c3a52','endTime':'03'},"
//				+ "{'startTime':'00','openWeek':3,'price':0,'spaceName':'33','userAccountId':'1b444c45-3af5-4f9e-92af-d84cb233d84f','spaceId':'9bb973fa-6ea5-4d7e-8930-b558ccfc0e56','endTime':'03'}"
				+ "]";
		
//		iSpaceOpenTimeService.
		
		//判断是否属于当前的日期 
		List<SpaceTimeDto> resultList  = parseSpaceFromJson(json);

		List<SpaceTimeDto> dataList = new ArrayList<SpaceTimeDto>();
		for(SpaceTimeDto spaceTimeDto : resultList){
			String start = spaceTimeDto.getStartTime();
			String end = spaceTimeDto.getEndTime();
			Integer startI = Integer.valueOf(start);
			Integer endI = Integer.valueOf(end);
			for(int s = startI;s<endI;s++){
				SpaceTimeDto tempSpaceTimeDto = new SpaceTimeDto();
				tempSpaceTimeDto = BeanUtils.createBeanByTarget(spaceTimeDto, SpaceTimeDto.class);
				StringBuffer sb = new StringBuffer();
				sb.append(s).append(":00-").append(s+1).append(":00");
				tempSpaceTimeDto.setOrderTime(sb.toString());
				dataList.add(tempSpaceTimeDto);	
			}
		}

		HashMap<String,List<SpaceTimeDto>> spaceTimeMap = new HashMap<String,List<SpaceTimeDto>>();
		for(SpaceTimeDto spaceTimeDto : dataList){
			List<SpaceTimeDto> arraySpaceTimeList = new ArrayList<SpaceTimeDto>();
			String keyCode = spaceTimeDto.getOpenWeek()+"_"+spaceTimeDto.getOrderTime();
			if(spaceTimeMap.containsKey(keyCode)){
				arraySpaceTimeList = spaceTimeMap.get(keyCode);
				arraySpaceTimeList.add(spaceTimeDto);
			}else{
				arraySpaceTimeList.add(spaceTimeDto);
			}
			spaceTimeMap.put(keyCode, arraySpaceTimeList);
		}


		List<OrderDates> orderDates = new ArrayList<OrderDates>();
		List<DateDto> dateList = DateUtils.queryDayAndWeek(new Date(), 7);//7天数据
		for(DateDto dateDto : dateList){
			OrderDates orDates = new OrderDates();
			List<OrderTimes> orderTimes = new ArrayList<OrderTimes>();
			Short dayOfWeek = DateUtils.dayForWeek(DateUtils.parse(dateDto.getDate(), DateUtils.YMD_DASH));
			for(SpaceTimeDto spaceTimeDto : dataList){
				OrderTimes orTimes = new OrderTimes();
				orTimes.setOrderTime(spaceTimeDto.getOrderTime());
				String tKeyCode = dayOfWeek+"_"+spaceTimeDto.getOrderTime();
				orTimes.setSpaceLists(spaceTimeMap.get(tKeyCode));
				orderTimes.add(orTimes);
			}
			orDates.setOrderDate(dateDto.getDate());
			orDates.setOrderTimes(orderTimes);
			orderDates.add(orDates);
		}
		System.out.println("orderDates"+new Gson().toJson(orderDates));
		
	}
	
	public static List<SpaceTimeDto> parseSpaceFromJson(String jsonData){
		Type listType = new TypeToken<List<SpaceTimeDto>>(){}.getType();
		Gson gson = new Gson();
		List<SpaceTimeDto> resources = gson.fromJson(jsonData, listType);
		return resources;
	}
	
	public static List<SpaceTimeDto> loadSpaceTimeDtoList(List<SpaceTimeDto> resultList){
		List<SpaceTimeDto> dataList = new ArrayList<SpaceTimeDto>();
		for(SpaceTimeDto spaceTimeDto : resultList){
			String start = spaceTimeDto.getStartTime();
			String end = spaceTimeDto.getEndTime();
			Integer startI = Integer.valueOf(start);
			Integer endI = Integer.valueOf(end);
			for(int s = startI;s<endI;s++){
				SpaceTimeDto tempSpaceTimeDto = new SpaceTimeDto();
				tempSpaceTimeDto = BeanUtils.createBeanByTarget(spaceTimeDto, SpaceTimeDto.class);
				StringBuffer sb = new StringBuffer();
				sb.append(s).append(":00-").append(s+1).append(":00");
				tempSpaceTimeDto.setOrderTime(sb.toString());
				dataList.add(tempSpaceTimeDto);	
			}
		}
		return dataList;
	}
	
	public static HashMap<String,List<SpaceTimeDto>> loadSpaceTimeMap(List<SpaceTimeDto> dataList){
		HashMap<String,List<SpaceTimeDto>> spaceTimeMap = new HashMap<String,List<SpaceTimeDto>>();
		for(SpaceTimeDto spaceTimeDto : dataList){
			List<SpaceTimeDto> arraySpaceTimeList = new ArrayList<SpaceTimeDto>();
			String keyCode = spaceTimeDto.getOpenWeek()+"_"+spaceTimeDto.getOrderTime();
			if(spaceTimeMap.containsKey(keyCode)){
				arraySpaceTimeList = spaceTimeMap.get(keyCode);
				arraySpaceTimeList.add(spaceTimeDto);
			}else{
				arraySpaceTimeList.add(spaceTimeDto);
			}
			spaceTimeMap.put(keyCode, arraySpaceTimeList);
		}
		return spaceTimeMap;
	}
	
	public static List<OrderDates> loadOrderDates(List<SpaceTimeDto> dataList,HashMap<String,List<SpaceTimeDto>> spaceTimeMap) throws Exception{
		List<OrderDates> orderDates = new ArrayList<OrderDates>();
		List<DateDto> dateList = DateUtils.queryDayAndWeek(new Date(), 7);//7天数据
		for(DateDto dateDto : dateList){
			OrderDates orDates = new OrderDates();
			List<OrderTimes> orderTimes = new ArrayList<OrderTimes>();
			Short dayOfWeek = DateUtils.dayForWeek(DateUtils.parse(dateDto.getDate(), DateUtils.YMD_DASH));
			for(SpaceTimeDto spaceTimeDto : dataList){
				OrderTimes orTimes = new OrderTimes();
				orTimes.setOrderTime(spaceTimeDto.getOrderTime());
				String tKeyCode = dayOfWeek+"_"+spaceTimeDto.getOrderTime();
				orTimes.setSpaceLists(spaceTimeMap.get(tKeyCode));
				orderTimes.add(orTimes);
			}
			orDates.setOrderDate(dateDto.getDate());
			orDates.setOrderTimes(orderTimes);
			orderDates.add(orDates);
		}
		return orderDates;
	}
	
	@RequestMapping(value = "data.do")
	@ResponseBody	
	public Response<List<OrderDates>> findAllInfoList(String userAccountId) throws Exception{
		Response<List<OrderDates>> result = new Response<List<OrderDates>>();
		List<Map> queryList = iSpaceOpenTimeService.queryOpenSpaceList(userAccountId);
		Gson gson = new Gson();
		String jsonTxt = gson.toJson(queryList);
		List<SpaceTimeDto> resultList = parseSpaceFromJson(jsonTxt);
		List<SpaceTimeDto> dataList = loadSpaceTimeDtoList(resultList);
		HashMap<String,List<SpaceTimeDto>> spaceTimeMap = loadSpaceTimeMap(dataList);
		List<OrderDates> orderDates = loadOrderDates(dataList, spaceTimeMap);
		result.setData(orderDates);
		return result;
	}
	
	
	@RequestMapping(value = "/hourList/{ownerAccountId}.do")
	@ResponseBody
	public Response<List<String>> hourList(@PathVariable String ownerAccountId,SpaceDto spaceDto) throws Exception{
		Response<List<String>> result = new Response<List<String>>();
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
		List<String> dateList = new ArrayList<String>();
		
		for(Integer infoId : infoIds){
			StringBuffer sb = new StringBuffer();
			sb.append(infoId).append(":00-").append(infoId+1).append(":00");
			dateList.add(sb.toString());
		}
		result.setData(dateList);
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
		String sjTime = spaceDto.getSjTime().split(":")[0];
//		spaceOpenTimeDto.setOpenWeek(Short.valueOf(spaceDto.getSjWeek()));//日期 1 2 3 4 5 6 7 表示
//		spaceOpenTimeDto.setStartTime(DateUtils.parse(spaceDto.getSjTime(),DateUtils.HH));//时间  该时间 必须在配置的范围之内
		spaceOpenTimeDto.setOpenWeek(Short.valueOf(DateUtils.dayForWeek((DateUtils.parse(spaceDto.getOrderTime(),DateUtils.HH)))));//日期 1 2 3 4 5 6 7 表示
		spaceOpenTimeDto.setStartTime(DateUtils.parse(sjTime,DateUtils.HH));//时间  该时间 必须在配置的范围之内
		spaceOpenTimeDto.setOwnerAccountId(spaceDto.getOwnerAccountId());
		List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findBy(spaceOpenTimeDto);//获取场馆用户 该日期 改时间 内可用的场地数据
		
		List<String> spaceIds = new ArrayList<String>();
		int hourPrice = 0;
		HashMap<String,Integer> spaceHourPriceMap = new HashMap<String, Integer>();
		if(!spaceTimeList.isEmpty()){//获取场地Id信息
			int sjTIme = 0;//选择的开始时间
			if(!StringUtils.isEmpty(spaceDto.getSjTime())){
				sjTIme = Integer.valueOf(sjTime);//选择的开始时间
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
	
}
