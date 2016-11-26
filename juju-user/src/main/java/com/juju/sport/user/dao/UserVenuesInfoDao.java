package com.juju.sport.user.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.user.mapper.VenuesInfoExMapper;
import com.juju.sport.user.mapper.VenuesInfoMapper;
import com.juju.sport.user.pojo.VenuesInfo;
import com.juju.sport.user.pojo.VenuesInfoExample;
import com.juju.sport.user.pojo.VenuesInfoExample.Criteria;

/**
 * 
	 * 此类描述的是：此类描述场馆实现Dao
	 * @author: cwftalus@163.com
	 * @version: 2015年4月7日 下午5:30:28
 */

@Repository
public class UserVenuesInfoDao extends MyBatisBaseDao<VenuesInfo>{

    @Autowired
    @Getter
    private VenuesInfoMapper mapper;
    
	  @Autowired
	  @Getter
	  private VenuesInfoExMapper mapperEx;
	
	public List<VenuesInfo> findBy(List<String> userIds) {

		VenuesInfoExample example = new VenuesInfoExample();
		Criteria criteria = example.createCriteria();
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(userIds!=null && userIds.size()>0){
			criteria.andUserAccountIdIn(userIds);	
		}else{
			return null;
		}
		
		List<VenuesInfo> list = mapper.selectByExample(example);
		
		return list;
	}
	public VenuesInfo findById(String userId){
		VenuesInfoExample example = new VenuesInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		criteria.andUserAccountIdEqualTo(userId);
		List<VenuesInfo> list = mapper.selectByExample(example);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
//		return list!=null?list.get(0):null;
		
	}
	public List<VenuesInfo> findByOwnerAccountId(String ownerAccountId) {
		VenuesInfoExample example = new VenuesInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserAccountIdEqualTo(ownerAccountId);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}
	

	
	public List<VenuesInfo> findAllBy() {
		VenuesInfoExample example = new VenuesInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<VenuesInfo> list = mapperEx.findVenusByLocation();
		return list;
	}
}
