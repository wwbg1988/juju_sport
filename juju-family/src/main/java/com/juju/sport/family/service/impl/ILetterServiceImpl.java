package com.juju.sport.family.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juju.sport.family.dao.LetterDao;
import com.juju.sport.family.dto.LetterDto;
import com.juju.sport.family.pojo.Letter;
import com.juju.sport.family.service.ILetterService;


@Service
public class ILetterServiceImpl implements ILetterService {

	@Autowired
	public LetterDao ldao;
	
	@Override
	public LetterDto findLetter() {
		// TODO Auto-generated method stub
		Letter letter = ldao.getLetter();
		LetterDto ldto = new LetterDto();
		BeanUtils.copyProperties(letter,ldto);
		return ldto;
	}

}
