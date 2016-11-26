package com.juju.sport.user.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.user.dao.UserVenuesInfoDao;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.pojo.VenuesInfo;
import com.juju.sport.user.service.IUserVenuesInfoService;

/*
 * 
 * 此类描述的是：场馆具体的信息接口实现类
 * @author: cwftalus@163.com
 * @version: 2015年4月7日 下午5:29:40
 */

@Service
public class UserVenuesInfoServiceImpl implements IUserVenuesInfoService {

	@Autowired
	private UserVenuesInfoDao userVenuesInfoDao;
	
	@Override
	public List<VenusInfoDto> findBy(List<String> userIds) {
		List<VenuesInfo> results = userVenuesInfoDao.findBy(userIds);
		List<VenusInfoDto> venusInfoList = BeanUtils.createBeanListByTarget(results,VenusInfoDto.class);
		return venusInfoList;
	}
	
	@Override
	public HashMap<String,VenusInfoDto> findByToMap(List<String> userIds) {
		HashMap<String,VenusInfoDto> venusInfo = new HashMap<String, VenusInfoDto>();
		List<VenusInfoDto> venusInfoList = findBy(userIds);
		Iterator<VenusInfoDto> its = venusInfoList.iterator();
		while(its.hasNext()){
			VenusInfoDto venusInfoDto = its.next();
			venusInfo.put(venusInfoDto.getUserAccountId(), venusInfoDto);
		}
		return venusInfo;
	}

	@Override
	public VenusInfoDto findById(String id) {
		VenuesInfo venuesInfo =  userVenuesInfoDao.findById(id);
		VenusInfoDto venusInfoDto = new VenusInfoDto();
		if(venuesInfo!=null){
			BeanUtils.copyProperties(venuesInfo, venusInfoDto);
			return venusInfoDto;
		}else{
			return null;
		}
	}

	@Override
	public VenusInfoDto findByOwnerAccountId(String ownerAccountId) {
		List<VenuesInfo> resultList = userVenuesInfoDao.findByOwnerAccountId(ownerAccountId);
		List<VenusInfoDto> dataList =  BeanUtils.createBeanListByTarget(resultList, VenusInfoDto.class);
		if(dataList!=null && dataList.size()>0){
			return dataList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<VenusInfoDto> findAllBy() {
		List<VenuesInfo> resultList = userVenuesInfoDao.findAllBy();
		List<VenusInfoDto> dataList =  BeanUtils.createBeanListByTarget(resultList, VenusInfoDto.class);
		return dataList;
	}

}
