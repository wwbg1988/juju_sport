package com.juju.sport.user.api;


import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.user.dto.UserAccountDto;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.dto.VenusInfoDto;

/**
 * Created by jam on 15-3-18.
 */

@Service
public interface IUserService {
     /**
      * 注册用户
      * **/
    public UserAccountDto register(UserAccountDto userAccountDto);
    /**
     * 检查用户名是否重复
     * **/
    public int checkrepeat(String name);
    /**
     * 根據ID查找用戶基礎信息
     * **/
    public UsersDto selectUsersByUserId(String userId);
    /**
     * 根據用户账号ID查找用戶基礎信息
     * **/
    public UsersDto selectUsersByUserAccountId(String userId);
    
    /**
     * 根据卡号查询用户信息
     * **/
    public UserAccountDto selectUsersByCardNo(String cardNo);
    /**
     * 根據ID查找用戶账户信息
     * **/
    public UserAccountDto selectUserAccountByUserId(String userId);
    /**
     * 更新用戶基礎信息
     * @param userAccountId 
     * **/
    public int updateUsers(UsersDto usersDto);
    /**
     * 更新用戶账户信息
     * **/
    public int updateUserAccount(UserAccountDto userAccountDto);
    /**
     * 插入用戶基礎信息
     * @param userAccountId 
     * **/
    public int insertUsers(UsersDto usersDto, String userAccountId);


    /**
     * 登录验证
     * @param loginName             用户名
     * @param loginPassword         密码
     * @return                      用户信息
     * **/
    public UserAccountDto checkUser(String loginName, String loginPassword);

    /**
     * 联合登陆方法
     * @return 返回user_account表主键ID
     */
    public String thirdUserAccount(UserAccountDto userAccountDto);
    
    /**
     * 寻找场馆
     */
    public List<VenusInfoDto> findByAdd(VenusInfoDto venusInfo);
    
    public List<VenusInfoDto> findNameBySportType(VenusInfoDto venusInfoDto);
    
    /**
     * 精确寻找场馆
     */
    public String findSearchByName(VenusInfoDto venusInfo);
    
    /**
     * 第三方登录后加入users表
     */
    public void insertOrUpdateUsersByThird(UsersDto usersDto);

    /**
     * 按照服务来找场馆
     * @param param
     * @return
     */
    public PageResult<VenusInfoDto> findByServiceType(VenusInfoDto param,PageQuery page);
    
    /**
     * 按照名称模糊匹配场馆
     */
    public PageResult<VenusInfoDto> findLikeName(VenusInfoDto param,PageQuery page);
    

    /**
     * 根据场馆里accountId查寻场馆信息
     */
	public VenusInfoDto selectStadiumByUserAccountId(String userAccountId);
	
	/**
     * 根据场馆里accountId查寻场馆信息
     */
	public UsersDto selectUsesByUserAccountId(String userAccountId);

    
    }

