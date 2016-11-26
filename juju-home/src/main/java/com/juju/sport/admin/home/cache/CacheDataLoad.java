/**
 * 
 */
package com.juju.sport.admin.home.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.juju.sport.common.util.SpringContextUtil;
import com.juju.sport.stadium.service.IVenuesApiService;
import com.juju.sport.stadium.dto.VenuesPwdDto;

/**		
 * <p>Title: CacheDataLoad </p>
 * <p>Description: 初始化加载缓存数据 默认加载设备信息</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年5月27日 下午1:58:00	
 * @version 1.0
 */
public class CacheDataLoad
{

    private final static IVenuesApiService venuesApiService = SpringContextUtil.getBean("iVenuesApiService");

    private final static LoadingCache<String, Object> dataCache = CacheBuilder.newBuilder()
        .expireAfterAccess(30, TimeUnit.MINUTES)
        .build(new CacheLoader<String, Object>()
        {       
            @Override
            public Object load(String key) throws Exception
            {
                return venuesApiService.queryVenuesPwd(key);
            }
        });

    private CacheDataLoad()
    {
    }

    /**     
     * getVenuesPwd：缓存读取设备信息
     * @param key SID
     * @return 场馆设备信息
     * @author Vincent
     * @date 2015年5月28日 上午11:24:23	 
     */
    public static VenuesPwdDto getVenuesPwd(String key)
    {
        try
        {
            VenuesPwdDto dto = (VenuesPwdDto) dataCache.getUnchecked(key);
            return dto;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static void main(String[] args)
    {
        CacheDataLoad.getVenuesPwd("1903515d-82d7-4057-bf3b-586bcbb33a3b");
    }

}
