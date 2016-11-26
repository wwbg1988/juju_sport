package com.juju.sport.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.base.dao.CardInfoDao;
import com.juju.sport.base.dto.CardInfoDto;
import com.juju.sport.base.pojo.CardInfo;
import com.juju.sport.base.service.ICardInfoService;
import com.juju.sport.common.util.BeanUtils;

@Service
public class CardInfoServiceImpl implements ICardInfoService{
	@Autowired
	private CardInfoDao cardInfoDao;

	@Override
	public void saveCardInfo(CardInfoDto cardInfoDto) {
		CardInfo cardInfo = BeanUtils.createBeanByTarget(cardInfoDto, CardInfo.class);
		cardInfoDao.insert(cardInfo);
	}
	
	@Override
	public int updateCardInfo(CardInfoDto cardInfoDto) {
		CardInfo cardInfo = BeanUtils.createBeanByTarget(cardInfoDto, CardInfo.class);
		return cardInfoDao.updateByPrimaryKey(cardInfo);
	}

	@Override
	public CardInfoDto queryCardInfoByCardNo(String cardNo) {
		CardInfo cardInfo = cardInfoDao.queryCardInfoByCardNo(cardNo);
		if(cardInfo!=null){
			CardInfoDto cardInfoDto  = BeanUtils.createBeanByTarget(cardInfo, CardInfoDto.class);
			return cardInfoDto;
		}else{
			return null;
		}
	}
}
