package com.juju.sport.user.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import com.juju.sport.user.dto.VenusInfoDto;

/*
 * 
 * 此类描述的是：场馆具体的信息
 * @author: cwftalus@163.com
 * @version: 2015年4月7日 下午5:29:13
 */

@Service
public interface IUserVenuesInfoService {
	public List<VenusInfoDto> findBy(List<String> userIds);

	HashMap<String, VenusInfoDto> findByToMap(List<String> userIds);
	
	VenusInfoDto findById(String userId);

	public VenusInfoDto findByOwnerAccountId(String ownerAccountId);
	/**
	 * 查询所有的场馆信息
	 * **/
	public List<VenusInfoDto> findAllBy();

}
