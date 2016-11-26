package com.juju.sport.family.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juju.sport.family.dao.SportIntroductionDao;
import com.juju.sport.family.dto.SportIntroductionDto;
import com.juju.sport.family.pojo.SportIntroduction;
import com.juju.sport.family.service.ISportIntroductionService;

@Service
public class ISportIntroductionServiceImpl implements ISportIntroductionService {

	@Autowired
	public SportIntroductionDao sdao;
	
	@Override
	public List<SportIntroductionDto> findAll() {
		// TODO Auto-generated method stub
		List<SportIntroduction> lsi = sdao.findAll();
		List<SportIntroductionDto> lsid = new ArrayList<SportIntroductionDto>();
		for(int i=0;i<lsi.size();i++){
			SportIntroduction si = lsi.get(i);
			SportIntroductionDto sid = new SportIntroductionDto();
			BeanUtils.copyProperties(si,sid);
			lsid.add(sid);
		}
		return lsid;
	}

	@Override
	public List<SportIntroductionDto> findByPro(int type, String sport,
			int environmental, int sex) {
		// TODO Auto-generated method stub
		List<SportIntroduction> lsi = sdao.findByPro(type, sport, environmental, sex);
		List<SportIntroductionDto> lsid = new ArrayList<SportIntroductionDto>();
		for(int i=0;i<lsi.size();i++){
			SportIntroduction si = lsi.get(i);
			SportIntroductionDto sid = new SportIntroductionDto();
			BeanUtils.copyProperties(si,sid);
			lsid.add(sid);
		}
		return lsid;
	}

	@Override
	public SportIntroductionDto findbyId(String id) {
		// TODO Auto-generated method stub
		SportIntroduction si = sdao.findbyId(id);
		SportIntroductionDto sid = new SportIntroductionDto();
		BeanUtils.copyProperties(si,sid);
		return sid;
	}

}
