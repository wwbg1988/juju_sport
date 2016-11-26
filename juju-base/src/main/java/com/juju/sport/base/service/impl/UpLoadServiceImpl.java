package com.juju.sport.base.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juju.sport.base.service.IUpLoadService;

@Service
public class UpLoadServiceImpl implements IUpLoadService {
	@Override
	public String upLoad(String filePath, MultipartFile file) {
		try {
			file.transferTo(new File(filePath));
		} catch (Exception e) {
			return "405";
		}
		    return "200";
			
	}
}
