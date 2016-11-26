package com.juju.sport.family.dao;

import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.family.mapper.LetterMapper;
import com.juju.sport.family.pojo.Letter;
import com.juju.sport.family.pojo.LetterExample;



@Repository
public class LetterDao extends MyBatisBaseDao<Letter>{

	@Autowired
    @Getter
    private LetterMapper lmapper;
	
	public Letter getLetter(){
		LetterExample le = new LetterExample();
		LetterExample.Criteria criteria = le.createCriteria();
		criteria.andLetterIsNotNull();
		List<Letter> lh = lmapper.selectByExample(le);
		if(lh.size()>0){
			return  lh.get(0);
		}
		return null;
	}
	
	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return null;
	}


}
