package com.juju.sport.family.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.juju.sport.family.dto.SportIntroductionDto;


@Service
public interface ISportIntroductionService {
	public List<SportIntroductionDto> findAll();
	public List<SportIntroductionDto> findByPro(int type,String sport,int environmental,int sex);
	public SportIntroductionDto findbyId(String id);
}
