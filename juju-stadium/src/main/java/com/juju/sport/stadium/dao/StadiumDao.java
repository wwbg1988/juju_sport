package com.juju.sport.stadium.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.base.dto.MessagesDto;
import com.juju.sport.base.dto.ServiceTypeDto;
import com.juju.sport.base.dto.SportTypeDto;
import com.juju.sport.base.mapper.MessageExMapper;
import com.juju.sport.base.mapper.MessagesMapper;
import com.juju.sport.base.mapper.VenuesSerViceTypeMapper;
import com.juju.sport.base.mapper.VenuesSportTypeMapper;
import com.juju.sport.base.pojo.Messages;
import com.juju.sport.base.pojo.MessagesExample;
import com.juju.sport.base.pojo.VenuesSerViceType;
import com.juju.sport.base.pojo.VenuesSerViceTypeExample;
import com.juju.sport.base.pojo.VenuesSportType;
import com.juju.sport.base.pojo.VenuesSportTypeExample;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.stadium.mapper.VenuesSportExMapper;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.mapper.UserAccountMapper;
import com.juju.sport.user.mapper.VenuesInfoMapper;
import com.juju.sport.user.pojo.UserAccount;
import com.juju.sport.user.pojo.VenuesInfo;
import com.juju.sport.user.pojo.VenuesInfoExample;

@Repository
public class StadiumDao extends MyBatisBaseDao<VenuesInfo> {
	// @Autowired
	// @Getter
	// private StadiumMapper mapper;

	@Autowired
	@Getter
	private VenuesInfoMapper mapper;

	@Autowired
	@Getter
	private UserAccountMapper mappers;

	@Autowired
	@Getter
	private MessageExMapper messageExmapper;

	@Autowired
	@Getter
	private MessagesMapper messagesmapper;
	
	@Autowired
	@Getter
	private VenuesSportTypeMapper venuesSportTypeMapper;
	
	@Autowired
	@Getter
	private VenuesSerViceTypeMapper venuesSerViceTypeMapper;
	
	@Autowired
	@Getter
	private VenuesSportExMapper venuesSportExMapper;
	
	
	public UserAccount register(VenuesInfo venuesInfo, UserAccount user) {
		user.setId(UUIDGenerator.getUUID());
		user.setCreateTime(new java.util.Date());
		user.setLastUpdateTime(new java.util.Date());
		user.setStat(DataStatus.ENABLED);

		venuesInfo.setId(UUIDGenerator.getUUID());
		venuesInfo.setUserAccountId(user.getId());
		venuesInfo.setCreateTime(new java.util.Date());
		venuesInfo.setLastUpdateTime(new java.util.Date());
		venuesInfo.setStat(DataStatus.ENABLED);
		venuesInfo.setNickName(user.getUserAccount());

		VenuesSportType venuesSportType = new VenuesSportType();
		if(venuesInfo.getVenueType() != null){
		String [] str1=venuesInfo.getVenueType().split(",");
		for(int i = 0;i < str1.length; i++){
			venuesSportType.setId(UUIDGenerator.getUUID());
			//venuesSportType.setVenuesInfoId(venuesInfo.getUserAccountId());
			venuesSportType.setSportTypeId(str1[i]);			
			venuesSportType.setCreateTime(new Date());
			venuesSportType.setLastUpdateTime(new Date());
			venuesSportType.setStat(DataStatus.ENABLED);
			venuesSportType.setVenuesInfoId(venuesInfo.getId());
			venuesSportTypeMapper.insert(venuesSportType);
		}
		}
//		int flag1 = mapper.insertSelective(venuesInfo);
//		int flag2 = mappers.insertSelective(user);
		int flag1 = mapper.insert(venuesInfo);
		int flag2 = mappers.insert(user);
		if (flag1 > 0 && flag2 > 0) {
			return user;
		} else {
			return null;
		}

	}
	public List<Messages> selectMessagebyUserName(Messages messages,
		PageQuery page) {
		MessagesExample example = new MessagesExample();
		MessagesExample.Criteria criteria = example.createCriteria();
		long starNum = page.getStartNum();
		int pageSize = page.getPageSize();
		if (null == messages.getUserAccount()){
			return  messageExmapper.selectStadiumMessages(starNum,pageSize);
		}
		criteria.andUserAccountEqualTo(messages.getUserAccount());
		criteria.andMsgTypeEqualTo(2);   	//消息类型为场馆消息
		example.setOrderByClause("date desc limit " + page.getStartNum() + ", " + page.getPageSize());
		return messagesmapper.selectByExample(example);
	}
	
	public long selectAllMessages(){
		MessagesExample example = new MessagesExample();
		MessagesExample.Criteria criteria = example.createCriteria();
		criteria.andMsgTypeEqualTo(2);   //消息类型为场馆消息
		return messagesmapper.countByExample(example);
	}

	public List<VenuesInfo> selectVenusInfoByUserAccountId(String userAccountId) {
		VenuesInfoExample example = new VenuesInfoExample();
		VenuesInfoExample.Criteria criteria = example.createCriteria();
		criteria.andUserAccountIdEqualTo(userAccountId);
		return  mapper.selectByExample(example);   
	}

	public int updateVenusInfo(VenuesInfo venuesInfo) {
		VenuesInfoExample example = new VenuesInfoExample();
		venuesInfo.setLastUpdateTime(new Date());    //修改更新时间
		venuesInfo.setStat(DataStatus.ENABLED); 
		VenuesInfoExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(venuesInfo.getId());
		return  mapper.updateByExampleSelective(venuesInfo, example);
	}

	public int insertVenusInfo(VenuesInfo venuesInfo, String stadiumType, String otherService) {
		venuesInfo.setId(UUIDGenerator.getUUID());
		venuesInfo.setLastUpdateTime(new Date());
		venuesInfo.setCreateTime(new Date());
		venuesInfo.setStat(DataStatus.ENABLED); 		
		//venuesInfo.setUserAccountId(user.getId());
		//venuesInfo.setNickName(user.getUserAccount());
		
		int result = mapper.insert(venuesInfo);		
		VenuesSportType venuesSportType = new VenuesSportType();
		String [] str1=stadiumType.split(",");
		for(int i = 0;i < str1.length; i++){
			venuesSportType.setId(UUIDGenerator.getUUID());
			//venuesSportType.setVenuesInfoId(venuesInfo.getUserAccountId());
			venuesSportType.setSportTypeId(str1[i]);			
			venuesSportType.setCreateTime(new Date());
			venuesSportType.setLastUpdateTime(new Date());
			venuesSportType.setStat(DataStatus.ENABLED);
			venuesSportType.setVenuesInfoId(venuesInfo.getId());
			venuesSportTypeMapper.insert(venuesSportType);
		}
		
		VenuesSerViceType venuesSerViceType = new VenuesSerViceType();
		//String serviceType = venuesInfo.getOtherServices();
		String [] str2=otherService.split(",");
		for(int i = 0;i < str2.length; i++){
		venuesSerViceType.setId(UUIDGenerator.getUUID());
		//venuesSerViceType.setVenuesInfoId(venuesInfo.getUserAccountId());
		venuesSerViceType.setServiceTypeId(str2[i]);		
		venuesSerViceType.setCreateTime(new Date());
		venuesSerViceType.setLastUpdateTime(new Date());
		venuesSerViceType.setStat(DataStatus.ENABLED);
		venuesSerViceType.setVenuesInfoId(venuesInfo.getId());
		venuesSerViceTypeMapper.insert(venuesSerViceType);
		}
		return result;
	}

	public int updateSportType(String venuesInfoId) {
		VenuesSportType sportType = new VenuesSportType();
		sportType.setLastUpdateTime(new Date());
		//sportType.setVenuesInfoId(venuesInfoId);
		sportType.setStat(DataStatus.DISABLED);
		VenuesSportTypeExample example = new VenuesSportTypeExample();
		VenuesSportTypeExample.Criteria criteria = example.createCriteria();
		//criteria.andStatEqualTo(DataStatus.DISABLED);
		criteria.andVenuesInfoIdEqualTo(venuesInfoId);
		return venuesSportTypeMapper.updateByExampleSelective(sportType, example);
	}
	
	public int updateServiceType(String venuesInfoId) {
		VenuesSerViceType serviceType = new VenuesSerViceType();
		serviceType.setLastUpdateTime(new Date());
		//serviceType.setVenuesInfoId(venuesInfoId);
		serviceType.setStat(DataStatus.DISABLED);
		VenuesSerViceTypeExample example = new VenuesSerViceTypeExample();
		VenuesSerViceTypeExample.Criteria criteria = example.createCriteria();
		//criteria.andStatEqualTo(DataStatus.DISABLED);
		criteria.andVenuesInfoIdEqualTo(venuesInfoId);
		return venuesSerViceTypeMapper.updateByExampleSelective(serviceType, example);
	}

	public int insertSportType(String stadiumType,String venuesInfoId) {
		String [] sportType = stadiumType.split(",");
		VenuesSportType venuesSportType = new VenuesSportType();
		int flag = 0;
		for(String str : sportType){
		venuesSportType.setSportTypeId(str);
		venuesSportType.setId(UUIDGenerator.getUUID());
		venuesSportType.setVenuesInfoId(venuesInfoId);
		venuesSportType.setCreateTime(new Date());
		venuesSportType.setLastUpdateTime(new Date());
		venuesSportType.setStat(DataStatus.ENABLED);
		flag = venuesSportTypeMapper.insert(venuesSportType);
		flag++;
		}
		return flag;
	}

	public int insertServiceType(String otherService,String venuesInfoId) {
		String [] serviceType = otherService.split(",");
		VenuesSerViceType venuesSerViceType = new VenuesSerViceType();
		int flag = 0;
		for(String str : serviceType){
			venuesSerViceType.setServiceTypeId(str);
			venuesSerViceType.setId(UUIDGenerator.getUUID());
			venuesSerViceType.setVenuesInfoId(venuesInfoId);
			venuesSerViceType.setCreateTime(new Date());
			venuesSerViceType.setLastUpdateTime(new Date());
			venuesSerViceType.setStat(DataStatus.ENABLED);
			flag = venuesSerViceTypeMapper.insert(venuesSerViceType);
			flag++;
		}
		return flag;
	}
	
	//查询场馆所有信息，包括运动类型和服务类型
	public VenusInfoDto selectVenusInfo(VenusInfoDto venusInfoDto) {
//		return venuesSportExMapper.selectVenusSportInfo(id);
		List<SportTypeDto> sportTypeList =venuesSportExMapper.selectVenusSportInfo(venusInfoDto.getId());
		List<ServiceTypeDto> serviceTypeList = venuesSportExMapper.selectVenusServiceInfo(venusInfoDto.getId());
		venusInfoDto.setSportTypeList(sportTypeList);
		venusInfoDto.setServiceTypeList(serviceTypeList);
		return venusInfoDto;
	}
	//查看场馆信息
	public List<Messages> selectvenuesMessage(MessagesDto messagesDto,PageQuery page) {
		MessagesExample example = new MessagesExample();
		MessagesExample.Criteria criteria = example.createCriteria();
		
		criteria.andMsgToIdEqualTo(messagesDto.getMsgToId());
		
//		criteria.andMsgTypeEqualTo(1);
		example.setOrderByClause("msg_time desc limit " + page.getStartNum() + ", " + page.getPageSize());
		return messagesmapper.selectByExample(example);
	}
	//查看场馆信息总条数（分页用）
	public long selectMessagesNum(MessagesDto messagesDto) {
		MessagesExample example = new MessagesExample();
		MessagesExample.Criteria criteria = example.createCriteria();
		
		criteria.andMsgToIdEqualTo(messagesDto.getMsgToId());

//		criteria.andMsgTypeEqualTo(1);
		return messagesmapper.countByExample(example);
	}
	
	public VenuesInfo showVenues(String vid) {
		VenuesInfoExample example = new VenuesInfoExample();
		VenuesInfoExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(vid);
		List<VenuesInfo> resultList = mapper.selectByExample(example);
		if(resultList!=null && resultList.size()>0){
			return resultList.get(0);
		}else{
			return null;
		}
	}

}
