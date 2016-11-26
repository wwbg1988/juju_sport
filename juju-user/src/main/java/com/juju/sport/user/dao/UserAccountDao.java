package com.juju.sport.user.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.user.mapper.UserAccountMapper;
import com.juju.sport.user.pojo.UserAccount;
import com.juju.sport.user.pojo.UserAccountExample;

@Repository
public class UserAccountDao extends MyBatisBaseDao<UserAccount>{
    @Autowired
    @Getter
    private UserAccountMapper mapper;
    public UserAccount register(UserAccount user){
        user.setId(UUIDGenerator.getUUID());
        user.setCreateTime(new Date());
    	user.setLastUpdateTime(new Date());
    	user.setStat(DataStatus.ENABLED);
        int flag = mapper.insert(user);
        if(flag>0){
        return user;   
        }else{
        return null;
        }
    }

    public int checkrepeat(String name){
    	UserAccountExample example = new UserAccountExample();
    	UserAccountExample.Criteria criteria = example.createCriteria();
    	criteria.andUserAccountEqualTo(name);
    	List<UserAccount> results = mapper.selectByExample(example);
    	if(results==null){
    		return 0; 
    	}else{
    		return results.size();
    	}
    }
	public UserAccount selectUserAccountByUserId(String userId) {
		 return mapper.selectByPrimaryKey(userId);
	}
	public int updateUserAccount(UserAccount userAccount) {
		userAccount.setCreateTime(new Date());
		userAccount.setLastUpdateTime(new Date());
		userAccount.setStat(DataStatus.ENABLED);
		return mapper.updateByPrimaryKey(userAccount);	
	}
	
	  public UserAccount findByUsernameAndPassword(String accountName,String password){
	        UserAccountExample example = new UserAccountExample();
	        UserAccountExample.Criteria criteria = example.createCriteria();
	        criteria.andPasswordEqualTo(password);
	        criteria.andUserAccountEqualTo(accountName);
	        criteria.andStatEqualTo(DataStatus.ENABLED);
	        List<UserAccount> results = mapper.selectByExample(example);
	        return CollectionUtils.isEmpty(results) ? null : results.get(0);

	    }
	    public UserAccount ThirdLogin(UserAccount userAccount){
	    	UserAccountExample example = new UserAccountExample();
	    	UserAccountExample.Criteria criteria = example.createCriteria();
	    	criteria.andThirdLoginEqualTo(userAccount.getThirdLogin());
	    	criteria.andThirdTypeEqualTo(userAccount.getThirdType());
	    	List<UserAccount> results = mapper.selectByExample(example);
	    	return CollectionUtils.isEmpty(results) ? null : results.get(0);
	    }

	    public int registerForThird(UserAccount user){
	    	user.setCreateTime(new Date());
	    	user.setLastUpdateTime(new Date());
	    	user.setStat(DataStatus.ENABLED);
	        return mapper.insert(user);
	    }
	    
	    public int logicDeleteById(String id) {
	    	UserAccount userAccount = new UserAccount();
	    	userAccount.setId(id);
	    	userAccount.setStat(DataStatus.DISABLED);
	    	return mapper.updateByPrimaryKeySelective(userAccount);
	    }
	    
	    public int enableRecord(String id) {
	    	UserAccount userAccount = new UserAccount();
	    	userAccount.setId(id);
	    	userAccount.setStat(DataStatus.ENABLED);
	    	return mapper.updateByPrimaryKeySelective(userAccount);
	    }

		public UserAccount selectUsersByCardNo(String cardNo) {
	    	UserAccountExample example = new UserAccountExample();
	    	UserAccountExample.Criteria criteria = example.createCriteria();
	    	
	    	criteria.andCardNoEqualTo(cardNo);
	    	
	    	criteria.andStatEqualTo(DataStatus.ENABLED);
	    	
	    	
	    	List<UserAccount> results = mapper.selectByExample(example);
	    	return CollectionUtils.isEmpty(results) ? null : results.get(0);
		}
}
