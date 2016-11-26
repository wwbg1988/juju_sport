package com.juju.sport.admin.manager.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.juju.sport.admin.manager.dao.ActionDao;
import com.juju.sport.admin.manager.dao.RoleActionDao;
import com.juju.sport.admin.manager.pojo.Action;
import com.juju.sport.admin.manger.dto.ActionDto;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.StringUtils;

@Service
public class RoleActionCache {

	@Autowired
	private RoleActionDao roleActionDao;
	
	@Autowired
	private ActionDao actionDao;
	
	private List<String> allAction = new ArrayList<>();
	
	private LoadingCache<String, List<ActionDto>> roleActionCache = CacheBuilder.newBuilder().build(
			
			new CacheLoader<String, List<ActionDto>>() {										
					
					public List<ActionDto> load(String roleId){
						List<Action> actions = actionDao.findByRoleId(roleId);
						if(CollectionUtils.isEmpty(actions)){
							return null;
						}
						List<ActionDto> results = BeanUtils.createBeanListByTarget(actions, ActionDto.class);
					    return results;
					}

				}
			);
	
	public List<String> get(String roleId) {
		List<ActionDto> actions = roleActionCache.getUnchecked(roleId);
		if(CollectionUtils.isEmpty(actions)) {
			return null;
		}
		
		List<String> ownActions = new ArrayList<String>();
		for(ActionDto action : actions) {
			ownActions.add(action.getAction());
		}
		return ownActions;
	}
	
	public List<String> getAll() {
		if(CollectionUtils.isEmpty(allAction)) {
			refreshAll();
			return allAction;			
		}
		return allAction;
	}
	
	public void set(String roleId, List<ActionDto> actions) {		
		roleActionCache.put(roleId, actions);
	}
	
	public void refresh(String roleId) {
		roleActionCache.refresh(roleId);
		refreshAll();
	}
	
	public void refresh() {
		roleActionCache.invalidateAll();
		refreshAll();
	}
	
	public void invalidate(String roleId) {
		roleActionCache.invalidate(roleId);
	}
	
	private void refreshAll() {
		List<Action> actions = actionDao.findAll();
		if(!CollectionUtils.isEmpty(actions)) {
			allAction.clear();
			for(Action action : actions) {
				if(StringUtils.isNotEmpty(action.getAction())){
					allAction.add(action.getAction().trim());
				}			
			}
		}
	}
}
