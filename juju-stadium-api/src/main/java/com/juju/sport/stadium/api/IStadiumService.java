package com.juju.sport.stadium.api;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.base.dto.MessagesDto;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.user.dto.UserAccountDto;
import com.juju.sport.user.dto.VenusInfoDto;



/**
 * Created by jam on 15-03-24.
 * @param <UserAccountDto>
 */

@Service
public interface IStadiumService {
    /**
     * 查询场馆评价
     */
	public PageResult<MessagesDto> selectMessagebyUserName(MessagesDto messagesDto,PageQuery page);
	/**
     * 查询场馆消息
     */
	public PageResult<MessagesDto> findvenuesMessage(MessagesDto messagesDto,PageQuery page);
    /**
     * 根据场馆ID查询场馆基础信息
     */
	public List<VenusInfoDto> selectVenusInfoByUserAccountId(String userAccountId);
    /**
     * 更改场馆信息
     */
	public int updateVenusInfo(VenusInfoDto venusInfoDto);
    /**
     * 新增场馆信息
     * @param stadiumType2 
     * @param stadiumType 
     */
	public int insertVenusInfo(VenusInfoDto venusInfoDto, String stadiumType, String otherService);
	/**
     * 将原来场馆运动类型stat设置为0
     * @by jam 2015_04_17
     */
	public int updateSportType(String venuesInfoId);
	/**
     * 将原来场馆附加服务类型stat设置为0
     * @by jam 2015_04_17
     */
	public int updateServiceType(String venuesInfoId);
	/**
     * 插入运动类型
     * @by jam 2015_04_17
     */
	public int insertSportType(String stadiumType, String venuesInfoId);
	/**
     * 插入外加服务类型
     * @by jam 2015_04_17
     */
	public int insertServiceType(String otherService, String venuesInfoId);
	/**
     * 注册场馆
     * @by jam 2015_04_17
     */
	public UserAccountDto register(VenusInfoDto venusInfoDto, UserAccountDto userAccountDto);

	/**
     * 根据VenusInfoId查询运动类型
     * @by jam 2015_04_17
     */	
	//public List<VenuesSportTypeDto> selectSportTypeByVenusInfoId(String id);
	/**
     * 根据VenusInfoId查询服务类型
     * @by jam 2015_04_17
     */
	//public List<VenuesServiceTypeDto> selectServiceTypeByVenusInfoId(String id);
	//根据VenusInfoId查询运动类型和服务类型
	public VenusInfoDto selectVenusInfo(VenusInfoDto venusInfoDto);
	
	public VenusInfoDto showVenues(String vid);

/*    *//**
     * 登录验证
     * @param loginName             用户名
     * @param loginPassword         密码
     * @return                      用户信息
     * **//*
    public UserAccountDto checkUser(String loginName, String loginPassword);

    *//**
     * 联合登陆方法
     * @return 返回user_account表主键ID
     *//*
    public String thirdUserAccount(UserAccountDto userAccountDto);*/
    }


