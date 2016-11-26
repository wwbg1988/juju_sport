package com.juju.sport.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.admin.home.cache.PageDataCache;
import com.juju.sport.base.dto.PageData;
import com.juju.sport.base.service.IHomePageConfigSerivce;
import com.juju.sport.common.constants.SessionConstants;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.game.dto.RaceInfoDto;
import com.juju.sport.game.service.IRaceService;


@Controller
@RequestMapping(value = "/api/home")
public class IndexController {

    @Autowired
    private PageDataCache pageDataCache;

    @Autowired
    private IHomePageConfigSerivce iHomePageConfigSerivce;
    
    @Autowired
    private IRaceService iRaceService;
    
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
    /**
     * 加载首页数据
     */
    @ResponseBody
    @RequestMapping(value = "/index.do")
    public Response<Map<String,List<PageData>>> index(){
    	Response<Map<String,List<PageData>>> result = new Response<Map<String,List<PageData>>>();
    	Map<String, List<PageData>> dataList = new HashMap<String, List<PageData>>();
    	
    	//
		String keyCode = "com.juju.sports.api.home.index.do";
		if(redisTemplate.opsForValue().get(keyCode)!=null){
			dataList = (Map<String,List<PageData>>)redisTemplate.opsForValue().get(keyCode);
		}else{
			dataList = iHomePageConfigSerivce.findAll();
			redisTemplate.opsForValue().set(keyCode, dataList, 1, TimeUnit.HOURS);
		}
    	
    	//recommend-venues(推荐场馆)，free-venues(免费场馆),hot-venues(热门场馆)
    	//list-team(战队情报)，hot-team(最牛战队),hot-person(最牛选手)
    	result.setData(dataList);
        return result;//iHomePageConfigSerivce.findAll();
    }
    /**
     * 
    	 * 此方法描述的是：赛事推送列表
    	 * @author: cwftalus@163.com
    	 * @version: 2015年4月17日 上午9:06:22
     */
    @ResponseBody
    @RequestMapping(value = "/eventList.do")
    public Response<PageResult<RaceInfoDto>> eventList(PageQuery page){
    	Response<PageResult<RaceInfoDto>> result = new Response<PageResult<RaceInfoDto>>();
    	
    	PageResult<RaceInfoDto> dateList =  iRaceService.findBy(RaceInfoDto.InfoType.RACE.getInfoType(),page);
    	
    	result.setData(dateList);
        return result;
    }
    
    /**
     * 
    	 * 此方法描述的是：首页新闻列表
    	 * @author: cwftalus@163.com
    	 * @version: 2015年4月17日 上午11:30:21
     */
    @RequestMapping(value = "/newsList.do")
    @ResponseBody
    public Response<PageResult<RaceInfoDto>> newsList(PageQuery page){
    	Response<PageResult<RaceInfoDto>> result = new Response<PageResult<RaceInfoDto>>();

    	PageResult<RaceInfoDto> datePageList =  iRaceService.findBy(RaceInfoDto.InfoType.NEWS.getInfoType(),page);
    	List<RaceInfoDto> dateList = datePageList.getResults();
    	List<RaceInfoDto> tList = new ArrayList<RaceInfoDto>();
    	for(RaceInfoDto raceInfoDto : dateList){
    		RaceInfoDto raceInfoObj = BeanUtils.createBeanByTarget(raceInfoDto, RaceInfoDto.class);
    		raceInfoObj.setShowTime(DateUtils.format(raceInfoDto.getCreateTime(), DateUtils.YMD_POINT));
    		tList.add(raceInfoObj);
    	}
    	PageResult<RaceInfoDto> pageResult = new PageResult<RaceInfoDto>();
    	pageResult.setCurrPage(datePageList.getCurrPage());
    	datePageList.setPageSize(datePageList.getPageSize());
    	datePageList.setTotal(datePageList.getTotal());
    	pageResult.setResults(tList);
    	result.setData(pageResult);;
        return result;
    }

}
