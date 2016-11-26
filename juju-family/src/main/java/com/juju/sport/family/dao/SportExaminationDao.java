package com.juju.sport.family.dao;

import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.family.mapper.SportExaminationMapper;
import com.juju.sport.family.pojo.SportExamination;
import com.juju.sport.family.pojo.SportExaminationExample;

@Repository
public class SportExaminationDao extends MyBatisBaseDao<SportExamination>{
	
	@Autowired
    @Getter
    private SportExaminationMapper smapper;

	public List<SportExamination> getSportExamination(){
		SportExaminationExample see =new SportExaminationExample();
		SportExaminationExample.Criteria criteria = see.createCriteria();
		criteria.andIdIsNotNull();
		List<SportExamination> ls=smapper.selectByExample(see);
		return ls;
	}
	
	public SportExamination getSportExaminationByid(String id){
		return smapper.selectByPrimaryKey(id);
	}
	
	
	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
