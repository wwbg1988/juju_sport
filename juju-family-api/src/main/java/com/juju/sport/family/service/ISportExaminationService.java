package com.juju.sport.family.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.family.dto.SportExaminationDto;

@Service
public interface ISportExaminationService {
	public List<SportExaminationDto> getSportExaminationList();
	public SportExaminationDto getSportExaminationByid(String id);
}
