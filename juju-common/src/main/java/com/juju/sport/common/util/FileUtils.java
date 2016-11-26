package com.juju.sport.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;


public class FileUtils extends org.apache.commons.io.FileUtils{

	public static String getUploadFileName(String fileName) {
		String tempFile = fileName.substring(fileName.lastIndexOf(".")+1);
		return UUIDGenerator.getUUID32Bit() + "." + tempFile;
	}
	
	public static String uploadImage(MultipartFile myfile) throws IOException {
		//如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解  
        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解  
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String context = "upload" + "/" + format.format(new Date());
        String realPath = PropertiesUtils.getProperty("upload.url");
        realPath= "E:/share";
        String fileName = FileUtils.getUploadFileName(myfile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath + "/" + context, fileName));  
        return context + "/" + fileName;
	}
	
	public static void main(String[] args) {
		System.out.println(FileUtils.getUploadFileName("aaa.jsp"));
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		System.out.println(format.format(new Date()));
	}
	
}
