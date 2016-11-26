package com.juju.sport.family.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.family.dao.SportExaminationDao;
import com.juju.sport.family.dto.SportExaminationDto;
import com.juju.sport.family.pojo.SportExamination;
import com.juju.sport.family.service.ISportExaminationService;


@Service
public class ISportExaminationServiceImpl implements ISportExaminationService {

	@Autowired
	public SportExaminationDao sdao;
	
	@Override
	public List<SportExaminationDto> getSportExaminationList() {
		// TODO Auto-generated method stub
		List<SportExamination>  lse=sdao.getSportExamination();
		List<SportExaminationDto> lsdto=new ArrayList<SportExaminationDto>();
		for(int i=0;i<lse.size();i++){
			SportExamination se=lse.get(i);
			SportExaminationDto sdto=new SportExaminationDto();
			BeanUtils.copyProperties(se,sdto);
			lsdto.add(sdto);
		}
		return lsdto;
	}

	@Override
	public SportExaminationDto getSportExaminationByid(String id) {
		// TODO Auto-generated method stub
		SportExamination se = sdao.getSportExaminationByid(id);
		SportExaminationDto sdto=new SportExaminationDto();
		BeanUtils.copyProperties(se,sdto);
		return sdto;
	}

}
