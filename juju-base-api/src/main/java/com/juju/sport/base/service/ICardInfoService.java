package com.juju.sport.base.service;

import org.springframework.stereotype.Service;

import com.juju.sport.base.dto.CardInfoDto;

@Service
public interface ICardInfoService {

	void saveCardInfo(CardInfoDto cardDto);
	
	int updateCardInfo(CardInfoDto cardDto);

	CardInfoDto queryCardInfoByCardNo(String cardNo);

}
