package com.juju.sport.admin.manager.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.juju.sport.admin.manager.dao.RoleDao;
import com.juju.sport.admin.manager.pojo.Role;
import com.juju.sport.admin.manger.dto.RoleDto;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.StringUtils;

@Service
public class RoleCache {
	
	@Autowired
	private RoleDao roleDao;

	private LoadingCache<String, RoleDto> roleCache = CacheBuilder.newBuilder().build(
			
			new CacheLoader<String, RoleDto>() {										
					
					public RoleDto load(String key){
					       return createArea(key);
					}

					private RoleDto createArea(String key) {
						if(StringUtils.isEmpty(key)) {
							return null;
						}							
						Role role = roleDao.selectByPrimaryKey(key);		
						return role != null ? (RoleDto)BeanUtils.createBeanByTarget(role, RoleDto.class) : null;
					}
				}
			);
	
	public RoleDto get(String key){
		try {
			return roleCache.get(key);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}
