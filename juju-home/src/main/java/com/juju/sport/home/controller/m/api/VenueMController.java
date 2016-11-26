package com.juju.sport.home.controller.m.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.Response;
import com.juju.sport.stadium.dto.SpaceDto;
import com.juju.sport.stadium.service.ISpaceService;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.service.IUserVenuesInfoService;

/**
 * 
	 * 此类描述的是：给移动端提供的场馆数据查询
	 * @author: cwftalus@163.com
	 * @version: 2015年4月30日 下午2:06:06
 */
@Controller
@RequestMapping(value = "/api/m/venue")
public class VenueMController {
	
	private static final  double EARTH_RADIUS = 6378137;//赤道半径(单位m) 
	
	@Autowired
	private ISpaceService iSpaceService;
	
	@Autowired
	private IUserVenuesInfoService iUserVenuesInfoService;
	
	@ResponseBody
	@RequestMapping("/query.do")
	public Response<PageResult<VenusInfoDto>> queryVenuesByPage(Double longitude,Double latitude){
		Response<PageResult<VenusInfoDto>> resulto = new Response<PageResult<VenusInfoDto>>();
		Response<List<Map.Entry<Double,VenusInfoDto>>> result = new Response<List<Map.Entry<Double,VenusInfoDto>>>();

		List<VenusInfoDto> dataList = iUserVenuesInfoService.findAllBy();
		HashMap<Double,VenusInfoDto> hashMap = new HashMap<Double,VenusInfoDto>(); 
		for(VenusInfoDto venusInfo : dataList){
			Double range = Double.MAX_VALUE;
//			System.out.println(venusInfo+"==="+venusInfo.getXLocation()+"=========="+venusInfo.getYLocation());
			if(venusInfo.getXLocation()!=null && venusInfo.getYLocation()!=null){
				range = LantitudeLongitudeDist(longitude, latitude, venusInfo.getXLocation(), venusInfo.getYLocation());
			}
//			System.out.println(range+"venusInfo"+venusInfo);
			hashMap.put(range, venusInfo);
		}

		List<Map.Entry<Double,VenusInfoDto>> venusInfoDtos =  new ArrayList<Map.Entry<Double,VenusInfoDto>>(hashMap.entrySet());
		//排序
		Collections.sort(venusInfoDtos, new Comparator<Map.Entry<Double,VenusInfoDto>>() {   
		    public int compare(Map.Entry<Double,VenusInfoDto> o1, Map.Entry<Double,VenusInfoDto> o2) {       
		        return (o1.getKey()).compareTo(o2.getKey());
		    }
		});
		List<VenusInfoDto> objs = new ArrayList<VenusInfoDto>();
		for(Map.Entry<Double,VenusInfoDto> mapO : venusInfoDtos){
			objs.add(mapO.getValue());
		}

		if(objs.size()>10){
			objs.subList(0, 10);
		}
		//根据场馆信息查找该场馆下面的场地信息 判断是否可以预定 START
		List<String> userAccountIds = new ArrayList<String>();
		for(VenusInfoDto vensInfoDto : objs){
			String userAccountId = vensInfoDto.getUserAccountId();
			userAccountIds.add(userAccountId);
			/*SpaceDto spaceDto = new SpaceDto();
			spaceDto.setUserAccountId(userAccountId);
			List<SpaceDto> list = iSpaceService.findBy(spaceDto);
			if(list.size()>0){
				vensInfoDto.setIsFalse("0");
			}else{
				vensInfoDto.setIsFalse("1");
		}*/
		}
		List<SpaceDto> spacesList = iSpaceService.findSpacesBy(userAccountIds);
		HashMap<String,SpaceDto> spaceMap = ListToMap(spacesList);
		
		for(VenusInfoDto objDto : objs){
			if(spaceMap.containsKey(objDto.getUserAccountId())){
				objDto.setIsFalse("0");
			}else{
				objDto.setIsFalse("1");
			}
		}
		
		//---------------------------END--------------------------
		PageResult<VenusInfoDto> data = new PageResult<VenusInfoDto>();
		data.setTotal(objs.size());
		//data.setTotal(1);
		data.setPageSize(10);
		data.setResults(objs);
		
		resulto.setData(data);
		return resulto;
	}
	
	
	private HashMap<String,SpaceDto> ListToMap(List<SpaceDto> spacesList) {
		HashMap<String,SpaceDto> map = new HashMap<String,SpaceDto>(); 
		 for(SpaceDto s:spacesList){
			 map.put(s.getUserAccountId(),s);
		 }
		 return map;
	}


	/** 
     * 基于余弦定理求两经纬度距离 
     * @param lon1 第一点的精度 
     * @param lat1 第一点的纬度 
     * @param lon2 第二点的精度 
     * @param lat2 第二点的纬度 
     * @return 返回的距离，单位km 
     * */  
    public static double LantitudeLongitudeDist(double lon1, double lat1,double lon2, double lat2) {  
        double radLat1 = rad(lat1);  
        double radLat2 = rad(lat2);  
  
        double radLon1 = rad(lon1);  
        double radLon2 = rad(lon2);  
  
        if (radLat1 < 0)  
            radLat1 = Math.PI / 2 + Math.abs(radLat1);// south  
        if (radLat1 > 0)  
            radLat1 = Math.PI / 2 - Math.abs(radLat1);// north  
        if (radLon1 < 0)  
            radLon1 = Math.PI * 2 - Math.abs(radLon1);// west  
        if (radLat2 < 0)  
            radLat2 = Math.PI / 2 + Math.abs(radLat2);// south  
        if (radLat2 > 0)  
            radLat2 = Math.PI / 2 - Math.abs(radLat2);// north  
        if (radLon2 < 0)  
            radLon2 = Math.PI * 2 - Math.abs(radLon2);// west  

        double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);  
        double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);  
        double z1 = EARTH_RADIUS * Math.cos(radLat1);  
  
        double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);  
        double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);  
        double z2 = EARTH_RADIUS * Math.cos(radLat2);  
  
        double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)+ (z1 - z2) * (z1 - z2));  
        //余弦定理求夹角  
        double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));  
        double dist = theta * EARTH_RADIUS;  
        return dist;  
    }	
	
	  /** 
     * 转化为弧度(rad) 
     * */  
    private static double rad(double d){  
       return d * Math.PI / 180.0;  
    } 
	
}
