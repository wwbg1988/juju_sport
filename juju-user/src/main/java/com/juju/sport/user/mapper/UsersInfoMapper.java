package com.juju.sport.user.mapper;

import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.pojo.VenuesInfo;

public interface UsersInfoMapper {
	UsersDto selectUsersByUserAccountId(String userId);
	//int updateUsersByUserAccount(Users users,String userAccountId);
	VenuesInfo selectStadiumByUserAccountId(String userAccountId);
}