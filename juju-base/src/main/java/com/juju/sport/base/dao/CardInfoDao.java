package com.juju.sport.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.base.mapper.CardInfoMapper;
import com.juju.sport.base.pojo.CardInfo;
import com.juju.sport.base.pojo.CardInfoExample;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

@Repository
public class CardInfoDao extends MyBatisBaseDao<CardInfo> {
	@Autowired
	@Getter
	private CardInfoMapper mapper;

	public CardInfo queryCardInfoByCardNo(String cardNo) {
		CardInfoExample example = new CardInfoExample();
		CardInfoExample.Criteria criteria = example.createCriteria();

		criteria.andCardNoEqualTo(cardNo);
		
		List<CardInfo> resultList = mapper.selectByExample(example);
		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0);
		} else {
			return null;
		}
	}
}
