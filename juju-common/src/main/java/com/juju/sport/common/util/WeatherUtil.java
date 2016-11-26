package com.juju.sport.common.util;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.dto.Future;
import com.juju.sport.common.dto.WeatherDto;
import com.juju.sport.common.dto.WeatherMap;

public class WeatherUtil {
	
	protected static final Log logger = LogFactory.getLog(WeatherUtil.class);
	
	public static HashMap<String, Future> loadWeatherApi() {
		String wetherApi = PropertiesUtils.getProperty("weather.api.url");
		String jsonData = HttpClientUtil.sendGetRequest(wetherApi,
				DataStatus.DECODECHARSET);
		logger.info("wetherApi"+wetherApi);
		logger.info("jsonData"+jsonData);
		WeatherDto infoDto = JsonUtil
				.getObjFromJson(jsonData, WeatherDto.class);
		if(infoDto!=null && infoDto.getResult()!=null){
			if(infoDto.getResult().getFuture()!=null){
				for (Future future : infoDto.getResult().getFuture()) {
					WeatherMap.weatherMap.put(future.getDate(), future);
				}							
			}
		}
		return WeatherMap.weatherMap;
	}
}
