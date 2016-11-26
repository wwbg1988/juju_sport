package com.juju.sport.base.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传Service
 * @author by jam  20150326
 */
@Service
public interface IUpLoadService {
	public String upLoad(String filePath,MultipartFile userImage);
}
