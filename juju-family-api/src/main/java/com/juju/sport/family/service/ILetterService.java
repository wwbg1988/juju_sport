package com.juju.sport.family.service;

import org.springframework.stereotype.Service;

import com.juju.sport.family.dto.LetterDto;

@Service
public interface ILetterService {
	public LetterDto findLetter();
}
