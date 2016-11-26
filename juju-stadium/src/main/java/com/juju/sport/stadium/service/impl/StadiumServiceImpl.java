package com.juju.sport.stadium.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.base.dto.MessagesDto;
import com.juju.sport.base.pojo.Messages;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.stadium.api.IStadiumService;
import com.juju.sport.stadium.dao.StadiumDao;
import com.juju.sport.stadium.dao.VenuesServiceTypeDao;
import com.juju.sport.stadium.dao.VenuesSportTypeDao;
import com.juju.sport.user.dto.UserAccountDto;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.pojo.UserAccount;
import com.juju.sport.user.pojo.VenuesInfo;

@Service
public class StadiumServiceImpl implements IStadiumService{

	    @Autowired
	    private StadiumDao stadiumDao;
	    @Autowired
	    private VenuesSportTypeDao venuesSportTypeDao;
	    @Autowired
	    private VenuesServiceTypeDao venuesServiceTypeDao;
	    @Override
	    public PageResult<MessagesDto> selectMessagebyUserName(MessagesDto messagesDto,PageQuery page){
	    	Messages messages = new Messages();
	    	BeanUtils.copyProperties( messagesDto,messages);
	    	List<Messages> messagesList = stadiumDao.selectMessagebyUserName(messages, page);
	    	List<MessagesDto> messagesDtoList =new ArrayList<MessagesDto>();
	    	messagesDtoList = BeanUtils.createBeanListByTarget(messagesList, Messages.class);
			long total = stadiumDao.selectAllMessages();
			page.setTotal(total);
	    	//return messagesDtoList;
	    	return new PageResult<MessagesDto>(page, messagesDtoList);
	    }


		@Override
		public List<VenusInfoDto> selectVenusInfoByUserAccountId(String userAccountId) {
			List<VenuesInfo> venuesInfoList= stadiumDao.selectVenusInfoByUserAccountId(userAccountId);
			List<VenusInfoDto> venusInfoDtoList  = new ArrayList<VenusInfoDto>();
			venusInfoDtoList =  BeanUtils.createBeanListByTarget(venuesInfoList, VenusInfoDto.class);
			return venusInfoDtoList;
		}


		@Override
		public int updateVenusInfo(VenusInfoDto venusInfoDto) {
			VenuesInfo venuesInfo = new VenuesInfo();
			BeanUtils.copyProperties( venusInfoDto,venuesInfo);
			int result = stadiumDao.updateVenusInfo(venuesInfo);
			return result;
		}


		@Override
		public int insertVenusInfo(VenusInfoDto venusInfoDto,String stadiumType,String otherService) {
			VenuesInfo venuesInfo = new VenuesInfo();
			BeanUtils.copyProperties( venusInfoDto,venuesInfo);
			int result = stadiumDao.insertVenusInfo(venuesInfo,stadiumType,otherService);
			return result;
		}

		@Override
		public int updateSportType(String venuesInfoId) {
			int flag = stadiumDao.updateSportType(venuesInfoId);
			return flag;
		}


		@Override
		public int updateServiceType(String venuesInfoId) {
			int flag = stadiumDao.updateServiceType(venuesInfoId);
			return flag;
		}


		@Override
		public int insertSportType(String stadiumType,String venuesInfoId) {
			int flag = stadiumDao.insertSportType(stadiumType,venuesInfoId);
			return flag;
		}


		@Override
		public int insertServiceType(String otherService,String venuesInfoId) {
			int flag = stadiumDao.insertServiceType(otherService,venuesInfoId);
			return flag;
		}


	    @Override
	    public UserAccountDto register(VenusInfoDto venusInfoDto,UserAccountDto userAccountDto) {
	        UserAccount user = new UserAccount();
	        //Stadium stadium = new Stadium();
	        VenuesInfo venuesInfo = new VenuesInfo();
	        BeanUtils.copyProperties(userAccountDto, user);
	        BeanUtils.copyProperties(venusInfoDto, venuesInfo);
	        UserAccount user_ = new UserAccount();
	        UserAccountDto userAccountDto_ = new UserAccountDto();
	        user_ =  stadiumDao.register(venuesInfo,user);	        
	        BeanUtils.copyProperties(user_, userAccountDto_);
	        return userAccountDto_;
	    }


		@Override
		public VenusInfoDto selectVenusInfo(VenusInfoDto venusInfoDto) {
//			VenuesInfo venuesInfo = new VenuesInfo();
			VenusInfoDto result = new VenusInfoDto();
			result = stadiumDao.selectVenusInfo(venusInfoDto);
			return result;
		}


		@Override
		public PageResult<MessagesDto> findvenuesMessage(MessagesDto messagesDto,PageQuery page) {
			long total = stadiumDao.selectMessagesNum(messagesDto);
			page.setTotal(total);
			List<Messages> messagesList = stadiumDao.selectvenuesMessage(messagesDto,page);
	    	List<MessagesDto> messagesDtoList =new ArrayList<MessagesDto>();
	    	messagesDtoList = BeanUtils.createBeanListByTarget(messagesList, Messages.class);			
			return new PageResult<MessagesDto>(page, messagesDtoList);
		}


		@Override
		public VenusInfoDto showVenues(String vid) {
			VenuesInfo result = stadiumDao.showVenues(vid);
			if(result!=null){
				return	BeanUtils.createBeanByTarget(result, VenusInfoDto.class);	
			}else{
				return null;
			}
		}
/*		@Override
		public List<VenuesSportTypeDto> selectSportTypeByVenusInfoId(String id) {
			List<VenuesSportType> venuesSportTypeList =  venuesSportTypeDao.selectSportTypeByVenusInfoId(id);
			List<VenuesSportTypeDto> venuesSportTypeDtoList = new ArrayList<VenuesSportTypeDto>();
			venuesSportTypeDtoList =  BeanUtils.createBeanListByTarget(venuesSportTypeList, VenuesSportTypeDto.class);
			BeanUtils.copyProperties(venuesSportTypeList, venuesSportTypeDtoList);
			return venuesSportTypeDtoList;
		}


		@Override
		public List<VenuesServiceTypeDto> selectServiceTypeByVenusInfoId(String id) {
			List<VenuesSerViceType> venuesServiceTypeList =  venuesServiceTypeDao.selectServiceTypeByVenusInfoId(id);
			List<VenuesServiceTypeDto> venuesServiceTypeDtoList = new ArrayList<VenuesServiceTypeDto>();
			venuesServiceTypeDtoList =  BeanUtils.createBeanListByTarget(venuesServiceTypeList, VenuesSportTypeDto.class);
			BeanUtils.copyProperties(venuesServiceTypeList, venuesServiceTypeDtoList);
			return venuesServiceTypeDtoList;
		}
*/

	


}
