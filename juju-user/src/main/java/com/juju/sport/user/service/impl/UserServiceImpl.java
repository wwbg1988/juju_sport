package com.juju.sport.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.base.dao.SportTypeDao;
import com.juju.sport.base.dto.CardInfoDto;
import com.juju.sport.base.service.ICardInfoService;
import com.juju.sport.common.constants.HttpApiReturnCode;
import com.juju.sport.common.digest.MD5Coder;
import com.juju.sport.common.exception.SystemException;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.user.api.IUserService;
import com.juju.sport.user.dao.UserAccountDao;
import com.juju.sport.user.dao.UserDao;
import com.juju.sport.user.dao.VenuesInfoDao;
import com.juju.sport.user.dto.UserAccountDto;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.pojo.DeviceUsers;
import com.juju.sport.user.pojo.UserAccount;
import com.juju.sport.user.pojo.Users;
import com.juju.sport.user.pojo.VenuesInfo;

/**
 * Created by jam on 2015-3-18.
 */

@Service
public class UserServiceImpl implements IUserService
{

    @Autowired
    private UserDao userDao;

    @Autowired
    private SportTypeDao sportTypeDao;

    @Autowired
    private VenuesInfoDao venuesInfoDao;

    @Autowired
    private UserAccountDao userAccountDao;

    @Autowired
    private ICardInfoService iCardInfoService;

    @Override
    public UserAccountDto register(UserAccountDto userAccountDto)
    {
        UserAccount user = new UserAccount();
        BeanUtils.copyProperties(userAccountDto, user);
        UserAccount user_ = new UserAccount();
        user_ = userAccountDao.register(user);
        UserAccountDto userAccountDto_ = new UserAccountDto();
        BeanUtils.copyProperties(user_, userAccountDto_);
        return userAccountDto_;
    }

    @Override
    public int checkrepeat(String name)
    {
        return userAccountDao.checkrepeat(name);
    }

    @Override
    public UsersDto selectUsersByUserId(String userId)
    {
        UsersDto usersDto = new UsersDto();
        Users users = new Users();
        users = userDao.selectUsersByUserId(userId);
        BeanUtils.copyProperties(users, usersDto);
        return usersDto;
    }

    @Override
    public UserAccountDto selectUserAccountByUserId(String userId)
    {
        UserAccountDto userAccountDto = new UserAccountDto();
        UserAccount useraccount = new UserAccount();
        useraccount = userAccountDao.selectUserAccountByUserId(userId);
        if (useraccount != null)
        {
            BeanUtils.copyProperties(useraccount, userAccountDto);
        }
        return userAccountDto;
    }

    @Override
    public UsersDto selectUsersByUserAccountId(String userId)
    {
        //		UsersDto usersDto = new UsersDto();
        //		Users users=new Users();
        //		users= userDao.selectUsersByUserAccountId(userId);
        //		if(users!=null){
        //        BeanUtils.copyProperties(users,usersDto);
        //        return usersDto;
        //        }else{
        //		return null;}
        return userDao.selectUsersByUserAccountId(userId);
    }

    @Override
    public int updateUsers(UsersDto usersDto)
    {
        Users users = new Users();
        BeanUtils.copyProperties(usersDto, users);
        return userDao.updateUsers(users);
    }

    @Override
    public int updateUserAccount(UserAccountDto userAccountDto)
    {
        UserAccount userAccount = new UserAccount();
        BeanUtils.copyProperties(userAccountDto, userAccount);
        return userAccountDao.updateUserAccount(userAccount);
    }

    @Override
    public int insertUsers(UsersDto usersDto, String userAccountId)
    {
        Users users = new Users();
        BeanUtils.copyProperties(usersDto, users);
        return userDao.insertUsers(users, userAccountId);
    }

    @Override
    public UserAccountDto checkUser(String loginName, String loginPassword)
    {
        UserAccount userAccount = null;
        if (!StringUtils.isEmpty(loginName) && !StringUtils.isEmpty(loginPassword))
        {
            try
            {
                MD5Coder.encodeMD5Hex(loginPassword);
            }
            catch (Exception e)
            {
                throw new SystemException(e.getMessage(), e);
            }
            userAccount = userAccountDao.findByUsernameAndPassword(loginName, loginPassword);
        }
        UserAccountDto userAccountDto = changToDto(userAccount);
        return userAccountDto;
    }

    private UserAccountDto changToDto(UserAccount userAccount)
    {
        if (userAccount == null)
        {
            return null;
        }
        UserAccountDto userAccountDto = new UserAccountDto();
        BeanUtils.copyProperties(userAccount, userAccountDto);
        return userAccountDto;
    }

    @Override
    public String thirdUserAccount(UserAccountDto userAccountDto)
    {
        UserAccount userAccount = new UserAccount();
        String uuId = UUIDGenerator.getUUID();
        BeanUtils.copyProperties(userAccountDto, userAccount);
        UserAccount result = userAccountDao.ThirdLogin(userAccount);
        if (result == null)
        {
            userAccount.setId(uuId);
            userAccountDao.registerForThird(userAccount);
            return uuId;
        }
        else
        {
            return result.getId();
        }
    }

    @Override
    public List<VenusInfoDto> findByAdd(VenusInfoDto venusInfo)
    {
        List<VenuesInfo> list = venuesInfoDao.findByAdd(venusInfo);
        List<VenusInfoDto> resultList = new ArrayList<VenusInfoDto>();
        VenusInfoDto venusInfoDto = null;
        if (list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                VenuesInfo result = list.get(i);
                String arr = result.getVenueType();
                String[] array = arr.split(",");
                for (int k = 0; k < array.length; k++)
                {
                    if (venusInfo.getVenueType().equals(array[k].toString()))
                    {
                        venusInfoDto = new VenusInfoDto();
                        BeanUtils.copyProperties(result, venusInfoDto);
                        resultList.add(venusInfoDto);
                        break;
                    }
                }
            }

        }
        return resultList;
    }

    @Override
    public List<VenusInfoDto> findNameBySportType(VenusInfoDto venusInfoDto)
    {
        return venuesInfoDao.findNameBySportType(venusInfoDto);
    }

    @Override
    public String findSearchByName(VenusInfoDto venusInfo)
    {
        VenuesInfo venuInfo = venuesInfoDao.findByName(venusInfo);
        if (venuInfo != null)
        {
            return venuInfo.getId();
        }
        else
        {
            return null;
        }
    }

    @Override
    public VenusInfoDto selectStadiumByUserAccountId(String userAccountId)
    {
        VenusInfoDto venusInfoDto = new VenusInfoDto();
        VenuesInfo venuesInfo = new VenuesInfo();
        venuesInfo = userDao.selectStadiumByUserAccountId(userAccountId);
        if (venuesInfo != null)
        {
            BeanUtils.copyProperties(venuesInfo, venusInfoDto);
            return venusInfoDto;
        }
        else
        {
            return null;
        }
    }

    @Override
    public PageResult<VenusInfoDto> findByServiceType(VenusInfoDto param, PageQuery page)
    {
        //		List<VenuesInfo> list = venuesInfoDao.findPageByAdd(param,page);
        //		List<VenusInfoDto> resultList = new ArrayList<VenusInfoDto>();
        //		if(list!=null&&list.size()>0){
        //			for(VenuesInfo venuesInfo:list){
        //				boolean flag= false;
        //				boolean flag2=false;
        //				boolean flag3=false;
        //				List<SportType>tempSportList = venuesInfo.getSportTypeList();
        //				if(tempSportList!=null&&tempSportList.size()>0){
        //					
        //					//如果是团队项目，现阶段只有足球场地
        //					if("1".equals(param.getTeamType())){
        //						for(SportType sportType2:tempSportList){
        //							if("1".equals(sportType2.getId())&&tempSportList.size()==1){
        //								flag3=true;
        //							}else{
        //								flag3=false;
        //							}
        //						}
        //						
        //					}else if("0".equals(param.getTeamType())){
        //						if(tempSportList.size()>1){
        //							flag3=true;
        //						}else if(!"1".equals(tempSportList.get(0).getId())){
        //							flag3=true;
        //						}else{
        //							flag3=false;
        //						}
        //					}else{
        //						flag3=true;
        //					}
        //					
        //					
        //					
        //					//在内存中计算sportType
        //					for(SportType sportType:tempSportList){
        //						if(sportType.getId().equals(param.getVenueType())){
        //							flag =true;
        //							break;
        //						}else{
        //							flag =false;
        //						}
        //					}			
        //				}
        //				if(param.getOtherServices()!=null){
        //					List<ServiceType> tempSerList = venuesInfo.getServiceTypeList();
        //					if(tempSerList!=null&&tempSerList.size()>0){
        //						//在内存中计算serviceType
        //						for(ServiceType serviceType : tempSerList){
        //							if(serviceType.getId().equals(param.getOtherServices())){
        //								flag2 = true;
        //								break;
        //							}
        //						}
        //						
        //					}
        //				}else{
        //					flag2=true;
        //				}
        //				
        //				if(flag==true&&flag2==true&&flag3==true){
        //					VenusInfoDto venusInfoDto = new VenusInfoDto();
        //					BeanUtils.copyProperties(venuesInfo, venusInfoDto);
        //					if(venusInfoDto.getUserScore()==null){
        //						venusInfoDto.setUserScore(5);
        //					}
        //					resultList.add(venusInfoDto);
        //				}
        //
        //			}

        List<VenusInfoDto> resultList = venuesInfoDao.findVenusByAdd(param, page);

        if (param.getNickName() != null && !"".equals(param.getNickName()))
        {
            Integer total = venuesInfoDao.findCountPageLikeName(param);
            page.setTotal(total);
            return new PageResult<VenusInfoDto>(page, resultList);
        }
        Integer total = venuesInfoDao.findVenusCount(param);
        page.setTotal(total);
        return new PageResult<VenusInfoDto>(page, resultList);

        //		}	
        //		return null;
    }

    @Override
    public PageResult<VenusInfoDto> findLikeName(VenusInfoDto param, PageQuery page)
    {
        List<VenuesInfo> list = venuesInfoDao.findPageLikeName(param, page);
        if (list != null)
        {
            Integer total = venuesInfoDao.findCountPageLikeName(param);
            page.setTotal(total);
            List<VenusInfoDto> tempList = BeanUtils.createBeanListByTarget(list, VenusInfoDto.class);
            List<VenusInfoDto> resultList = new ArrayList<VenusInfoDto>();
            for (VenusInfoDto venusInfoDto : tempList)
            {
                if (venusInfoDto.getUserScore() == null)
                {
                    venusInfoDto.setUserScore(5);
                }
                resultList.add(venusInfoDto);
            }
            return new PageResult<VenusInfoDto>(page, resultList);
        }
        return null;
    }

    @Override
    public void insertOrUpdateUsersByThird(UsersDto usersDto)
    {
        if (usersDto != null)
        {
            UsersDto tempUser = userDao.selectUsersByUserAccountId(usersDto.getUserAccount());
            if (tempUser == null)
            {
                Users users = new Users();
                users.setNickName(usersDto.getNickName());
                userDao.insertUsers(users, usersDto.getUserAccount());
            }
        }

    }

    /** 
    * (non-Javadoc)   
    * @see com.juju.sport.user.api.IUserService#selectUsersByCardId(java.lang.String)   
    */
    @Override
    public UserAccountDto selectUsersByCardNo(String cardNo)
    {
        UserAccountDto userAccountDto = null;
        CardInfoDto queryCardInfoDto = iCardInfoService.queryCardInfoByCardNo(cardNo);
        if (null != queryCardInfoDto && null !=queryCardInfoDto.getId())
        {

            UserAccount userAccount = userAccountDao.selectUsersByCardNo(cardNo);
            if (null == userAccount)
            {
                return null;
            }
            userAccountDto = BeanUtils.createBeanByTarget(userAccount, UserAccountDto.class);
            userAccountDto.setCardName(queryCardInfoDto.getCardName());
            return userAccountDto;
        }else{      
            userAccountDto = new UserAccountDto();
            userAccountDto.setValidCode(HttpApiReturnCode.CARD_NOT_FOUND);
            return userAccountDto;      
        }
    }
    /**
     * 
     */
	public UsersDto selectUsesByUserAccountId(String userAccountId) {
		UsersDto usersDto = new UsersDto();
		Users  users =  userDao.selectUsesByUserAccountId(userAccountId);
		if(users==null){
			return null;
		}
		usersDto = BeanUtils.createBeanByTarget(users, UsersDto.class);
		return usersDto;
	}

}
