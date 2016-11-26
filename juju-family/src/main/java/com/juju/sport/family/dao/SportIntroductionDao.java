package com.juju.sport.family.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.family.mapper.SportIntroductionMapper;
import com.juju.sport.family.pojo.SportIntroduction;
import com.juju.sport.family.pojo.SportIntroductionExample;



@Repository
public class SportIntroductionDao extends MyBatisBaseDao<SportIntroduction>{
	
	@Autowired
    @Getter
    private SportIntroductionMapper smapper;
	
	public List<SportIntroduction> findAll() {
		
		SportIntroductionExample sie = new SportIntroductionExample();
		SportIntroductionExample.Criteria criteria = sie.createCriteria();
		criteria.andIdIsNotNull();
		List<SportIntroduction> ls = smapper.selectByExample(sie);
		return ls;
	}
	
	public List<SportIntroduction> findByPro(int type, String sport,
			int environmental, int sex) {

		SportIntroductionExample sie = new SportIntroductionExample();
		SportIntroductionExample.Criteria criteria = sie.createCriteria();
		if(type!=-1){
			criteria.andTypeEqualTo(type);
		}
		if(!"请选择".equals(sport)){
			criteria.andSportEqualTo(sport);
		}
		if(environmental!=-1){
			criteria.andEnvironmentalEqualTo(environmental);
		}
		if(sex!=-1){
			criteria.andSexEqualTo(sex);
		}
		List<SportIntroduction> ls = smapper.selectByExample(sie);
		return ls;
	}
	
	public SportIntroduction findbyId(String id) {

		return smapper.selectByPrimaryKey(id);
	}

	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
