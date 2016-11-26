package com.juju.sport.user.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.juju.sport.base.mapper.ServiceTypeMapper;
import com.juju.sport.base.pojo.ServiceType;
import com.juju.sport.base.pojo.ServiceTypeExample;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.mapper.UsersInfoMapper;
import com.juju.sport.user.mapper.UsersMapper;
import com.juju.sport.user.pojo.Users;
import com.juju.sport.user.pojo.UsersExample;
import com.juju.sport.user.pojo.VenuesInfo;

/**
 * Created by jam on 2015-03-18.
 */
@Repository
public class UserDao extends MyBatisBaseDao<Users>{


	@Override
	public Object getMapper() {
		return mapperUsers;
	}
    
    @Autowired
    @Getter
    private UsersMapper mapperUsers;
    
    @Autowired
    @Getter
    private UsersInfoMapper mapperUserInfo;
    
    @Autowired
    @Getter
    private ServiceTypeMapper serviceTypeMapper;


  
    
	public Users selectUsersByUserId(String userId) {
		 return mapperUsers.selectByPrimaryKey(userId);
	}
	

	
	public UsersDto  selectUsersByUserAccountId(String userId) {
		 return mapperUserInfo.selectUsersByUserAccountId(userId);
	}
	
	public int updateUsers(Users users) {
		UsersExample example = new UsersExample();
		UsersExample.Criteria criteria = example.createCriteria();
		criteria.andUserAccountIdEqualTo(users.getUserAccountId()); 
		users.setCreateTime(new Date());
		users.setLastUpdateTime(new Date());
		users.setStat(DataStatus.ENABLED);
		//return mapperUsers.updateByPrimaryKey(users);
		return mapperUsers.updateByExampleSelective(users, example);
		
	}
	

	public int insertUsers(Users users, String userAccountId) {
		users.setId(UUIDGenerator.getUUID());
		users.setCreateTime(new Date());
		users.setLastUpdateTime(new Date());
		users.setStat(DataStatus.ENABLED);
		users.setUserAccountId(userAccountId);  //把用户账号ID塞进去
		 return mapperUsers.insert(users);	
	}
    

  
    

    public Users getAllUsers(String id){
    	UsersExample example = new UsersExample();
    	UsersExample.Criteria criteria = example.createCriteria();
    	criteria.andUserAccountIdEqualTo(id);
    	criteria.andStatEqualTo(1);
    	List<Users> list = mapperUsers.selectByExample(example);
    	if(list!=null&&list.size()>0){
    		return mapperUsers.selectByExample(example).get(0);
    	}else{
    		return null;
    	}
    }
    
    /**
     * 
    	 * 此方法描述的是：只根据userId 查询用户信息 返回List数据
    	 * @author: cwftalus@163.com
    	 * @version: 2015年4月3日 上午9:50:17
     */
    public List<Users> queryUsersBy(List<String> userIds){
    	UsersExample example = new UsersExample();
    	UsersExample.Criteria criteria = example.createCriteria();
    	
    	criteria.andUserAccountIdIn(userIds);
    	
    	List<Users> results = mapperUsers.selectByExample(example);
    	return results;
    }
    /**
     * 
    	 * 此方法描述的是：只根据userId 查询用户信息 返回Map<String,Users> 数据
    	 * @author: cwftalus@163.com
    	 * @version: 2015年4月3日 上午9:50:17
     */
    public HashMap<String,Users> queryUsersMapBy(List<String> userIds){
    	List<Users> results = queryUsersBy(userIds);
    	HashMap<String,Users> userMap = new HashMap<String, Users>();
    	for(Users users : results){
    		userMap.put(users.getUserAccountId(), users);
    	}
    	return userMap;
    }
    
    public ServiceType findServiceById(String id){
    	ServiceTypeExample example = new ServiceTypeExample();
    	ServiceTypeExample.Criteria criteria = example.createCriteria();
    	if(!StringUtils.isEmpty(id)){
    		criteria.andIdEqualTo(id);
    		return serviceTypeMapper.selectByPrimaryKey(id);
    	}else{
    		return null;
    	}
    	
    }

	public VenuesInfo selectStadiumByUserAccountId(String userAccountId) {
		 return mapperUserInfo.selectStadiumByUserAccountId(userAccountId);
	}


	public int logicDeleteById(String id) {
		Users users = new Users();
		users.setId(id);
		users.setStat(DataStatus.DISABLED);		
		return mapperUsers.updateByPrimaryKeySelective(users);
	}



	public Users selectUsesByUserAccountId(String userAccountId) {
    	UsersExample example = new UsersExample();
    	UsersExample.Criteria criteria = example.createCriteria();
    	criteria.andUserAccountIdEqualTo(userAccountId);
    	criteria.andStatEqualTo(1);
    	List<Users> list = mapperUsers.selectByExample(example);
    	if(list!=null&&list.size()>0){
    		return mapperUsers.selectByExample(example).get(0);
    	}else{
    		return null;
    	}
    }

}
