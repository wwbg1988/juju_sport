package com.juju.sport.admin.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.juju.sport.admin.manager.cache.RoleCache;
import com.juju.sport.admin.manager.dao.AdminAccountDao;
import com.juju.sport.admin.manager.pojo.AdminAccount;
import com.juju.sport.admin.manager.service.IAdminAccountService;
import com.juju.sport.admin.manger.dto.AdminAccountDto;
import com.juju.sport.admin.manger.dto.AdminAccountQuery;
import com.juju.sport.admin.manger.dto.AdminUserLoginInfoDto;
import com.juju.sport.admin.manger.dto.ResetPasswdQuery;
import com.juju.sport.admin.manger.dto.RoleDto;
import com.juju.sport.common.digest.MD5Coder;
import com.juju.sport.common.exception.SystemException;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.redis.RedisKeyResolver;
import com.juju.sport.common.redis.WdRedisDao;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.StringUtils;

@Service
public class AdminAccountServiceImpl implements IAdminAccountService {

	@Autowired
	private AdminAccountDao adminAccountDao;
	
	@Autowired
	private RoleCache roleCache;
	
	@Autowired
	private WdRedisDao<AdminUserLoginInfoDto> loginInfoRedisDao;
	
	@Autowired
	private RedisKeyResolver<AdminUserLoginInfoDto> redisKeyResolver;

	@Override
	public AdminAccountDto findAdminAccountByUsername(String username) {
		AdminAccount account = adminAccountDao.findByUsername(username);
		AdminAccountDto accountInfo = changTo(account);
		
		return accountInfo;
	}

	@Override
	public long countAll() {
		return adminAccountDao.countAll();
	}

	@Override
	public PageResult<AdminAccountDto> findAllByPage(PageQuery page) {
		List<AdminAccount> accounts = adminAccountDao.findAllByPage(page);
		List<AdminAccountDto> results = new ArrayList<AdminAccountDto>();
		
		for(AdminAccount account : accounts){
			AdminAccountDto accountDto = changTo(account);
			RoleDto roleDto = roleCache.get(accountDto.getRoleId());
			if(roleDto != null) {
				accountDto.setRoleName(roleDto.getRoleName());
			}
			results.add(accountDto);
		}
		
		return new PageResult<>(page, results);
	}
	
	private AdminAccountDto changTo(AdminAccount account) {
		if(account == null){
			return null;
		}
		AdminAccountDto accountInfo = new AdminAccountDto();
		BeanUtils.copyProperties(account, accountInfo);
		return accountInfo;
	}

	@Override
	public PageResult<AdminAccountDto> find(AdminAccountQuery query, PageQuery page) {
		List<AdminAccount> results = adminAccountDao.find(query, page);
		List<AdminAccountDto> accounts = BeanUtils.createBeanListByTarget(results, AdminAccountDto.class);
		for(AdminAccountDto account : accounts) {
			RoleDto roleDto = roleCache.get(account.getRoleId());
			if(roleDto != null) {
				account.setRoleName(roleDto.getRoleName());
			}
		}
		long total = adminAccountDao.count(query);
		page.setTotal(total);
		return new PageResult<AdminAccountDto>(page, accounts);
	}

	@Override
	public RequestResult save(AdminAccountDto account) {
		AdminAccount obj = BeanUtils.createBeanByTarget(account, AdminAccount.class);
		if(StringUtils.isEmpty(obj.getPasswd())){
			obj.setPasswd(obj.getUserName());
		}
		try {
			String passwd = MD5Coder.encodeMD5Hex(obj.getPasswd());
			obj.setPasswd(passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		adminAccountDao.insert(obj);
		return new RequestResult(true, "保存成功!");
	}

	@Override
	public RequestResult update(AdminAccountDto account) {
		AdminAccount obj = BeanUtils.createBeanByTarget(account, AdminAccount.class);
		adminAccountDao.updateByPrimaryKeySelective(obj);
		return new RequestResult(true, "更新成功!");
	}

	@Override
	public RequestResult delete(String accountId) {
		AdminAccount account = adminAccountDao.selectByPrimaryKey(accountId);
		adminAccountDao.logicDelete(accountId);
		if(account != null) {
			String key = redisKeyResolver.getRedisKey(AdminUserLoginInfoDto.class, account.getUserName());
			loginInfoRedisDao.delete(key);		
		}
		return new RequestResult(true, "删除成功!");
	}

	@Override
	public RequestResult resetPasswd(String accountId) {
		AdminAccount account = adminAccountDao.selectByPrimaryKey(accountId);
		if(Objects.equal(account, null)) {
			throw new SystemException("数据异常，账号不存在！");
		}
		
		String newPasswd = null;
		try {
			newPasswd = MD5Coder.encodeMD5Hex(account.getUserName());
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e.getMessage());
		}
		updatePasswd(account, newPasswd);		
		
		return new RequestResult(true, "重置成功!");
	}

	@Override
	public RequestResult resetPasswd(ResetPasswdQuery query) {
		AdminAccount account = adminAccountDao.findByUsername(query.getAccountCode());
		if(Objects.equal(account, null)) {
			 return new RequestResult(false, "登陆数据异常，登陆账号不存在！"); 
		}
		String oldPasswd = null;
		String newPasswd = null;
		try {
			oldPasswd = MD5Coder.encodeMD5Hex(query.getOldPW());
			newPasswd = MD5Coder.encodeMD5Hex(query.getNewPW());
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		if(!Objects.equal(oldPasswd, account.getPasswd())){
			return new RequestResult(false, "旧密码输入不正确！"); 
		}
		
		updatePasswd(account, newPasswd);		
		
		return new RequestResult(true, "修改成功!");
	}

	private void updatePasswd(AdminAccount account, String newPasswd) {
		account.setPasswd(newPasswd);
		adminAccountDao.updateByPrimaryKey(account);
		String key = redisKeyResolver.getRedisKey(AdminUserLoginInfoDto.class, account.getUserName());
		loginInfoRedisDao.delete(key);
	}

}
