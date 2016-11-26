package com.juju.sport.admin.home.cache;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.juju.sport.base.dto.PageData;
import com.juju.sport.base.service.IHomePageConfigSerivce;

/**
 * 首页配置对象缓存
 * @author rkzhang
 *
 */
@Service
public class PageDataCache {

	@Autowired
	private IHomePageConfigSerivce configService;
	
	private Map<String, List<PageData>> all;
	
	private LoadingCache<String, List<PageData>> pageDataCache = CacheBuilder.newBuilder().build(
			
			new CacheLoader<String, List<PageData>>() {															
					public List<PageData> load(String key){						
					    return configService.findByKey(key);
					}
				}
			);
	
	/**
	 * 根据key获取页面配置对象
	 * @param key
	 * @return
	 */
	public List<PageData> findByKey(String key){
		return pageDataCache.getUnchecked(key);
	}
	
	public void set(String key, List<PageData> actions) {		
		pageDataCache.put(key, actions);
	}
	
	public void refresh(String key) {
		pageDataCache.refresh(key);
	}
	
	public void refreshAll() {
		all = configService.findAll();
	}
	
	public void invalidate(String key) {
		pageDataCache.invalidate(key);
	}
	
	/**
	 * 获取所有页面对象
	 * @return
	 */
	public Map<String, List<PageData>>  findAll() {
		if(all != null) {
			return all;
		} else {
			all = configService.findAll();
		}
		return all;
	}
}
