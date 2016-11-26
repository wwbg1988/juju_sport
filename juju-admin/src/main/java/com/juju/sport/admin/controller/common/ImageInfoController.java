package com.juju.sport.admin.controller.common;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juju.sport.base.dto.ImageInfoDto;
import com.juju.sport.base.service.IImageInfoService;
import com.juju.sport.common.ext.ExtPageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.FileUtils;
import com.juju.sport.common.util.JsonUtil;
import com.juju.sport.common.util.PropertiesUtils;
import com.juju.sport.game.dto.RaceInfoDto;

@Controller
@RequestMapping(value="/common/images")
public class ImageInfoController {

	protected static final Log logger = LogFactory.getLog(ImageInfoController.class);
	
	@Autowired
	private IImageInfoService imageInfoService;
	
	@ResponseBody
	@RequestMapping(value="/find.do")
	public PageResult<ImageInfoDto> findImage(String name, ExtPageQuery page, HttpServletRequest request) {
		logger.info("RaceInfoQuery : " + name);	
		PageResult<ImageInfoDto> pageResult = imageInfoService.findByPage(name, page.changeToPageQuery());	
		//String context = request.getContextPath();
		for(ImageInfoDto dto : pageResult.getResults()){
			dto.setInfactUrl("/" + dto.getUrl());
		}
		return pageResult;
	}
	
	@ResponseBody
	@RequestMapping(value="/create.do")
	public String createImage(ImageInfoDto image, @RequestParam("image")MultipartFile myfile, HttpServletRequest request, HttpServletResponse response) throws IOException {
		 logger.info("upload pic : " + image);

		 if(!myfile.isEmpty()){  
			printFileInfo(myfile);  
	     	uploadImage(image, myfile, request);
	     }
        
        //raceManagerService.update(raceInfo);
		imageInfoService.create(image);

    	return JsonUtil.getJsonStr(new RequestResult(true, "200"));
	}
	
	@ResponseBody
	@RequestMapping(value="/htmleditorUpload.do")
	public String htmleditorUploadInfo(ImageInfoDto image, @RequestParam("htmleditorImage")MultipartFile myfile,@RequestParam("content")String content, @RequestParam("width")String width,@RequestParam("height")String height,HttpServletRequest request, HttpServletResponse response) throws IOException {
		 logger.info("upload pic : " + image);
		//图片的上传路径;
		String imgPath = null;
		String jsonString=null;
        if(!myfile.isEmpty()){  
        	printFileInfo(myfile);  
        	String context = "upload";
            String realPath = PropertiesUtils.getProperty("upload.url");
    		String fileDe = DateUtils.format(new Date(), DateUtils.YMD);
    		String fileName = FileUtils.getUploadFileName(myfile.getOriginalFilename());
    	    imgPath = context + "/" + fileName;
    		logger.info("filePaht :"+realPath + "/"  + fileName);
    		File file = new File(realPath + "/" +context);
            //如果文件夹不存在则创建    
            if(!file.exists() && !file.isDirectory()) {
                file.mkdir();    
            }
            FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath + "/" +context , fileName));
            image.setUrl(imgPath);
            image.setName(content);
        }
        imageInfoService.create(image);
      
        if((width!=""||width!=null) && (height!=""||height!=null)){
            jsonString="{success:true,data:{'url':'"+imgPath+"','title':'"+content+"','width':'"+width+"','height':'"+height+"','message':'200'}}";
        }else{
            jsonString="{success:true,data:{'url':'"+imgPath+"','title':'"+content+"','message':'200'}}";
        }
         return jsonString;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/delete.do")
	private RequestResult delete(String id) {
		
		return imageInfoService.logicDeleteById(id);
	}
	
	private void uploadImage(ImageInfoDto image, MultipartFile myfile,
			HttpServletRequest request) throws IOException {
		//如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解  
        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解  
		String context = "upload";
        String realPath = PropertiesUtils.getProperty("upload.url");
        String fileName = FileUtils.getUploadFileName(myfile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath + "/" + context, fileName));  
        image.setUrl(context + "/" + fileName);
	}
	
	private void printFileInfo(MultipartFile myfile) {
		logger.info("文件长度: " + myfile.getSize());  
		logger.info("文件类型: " + myfile.getContentType());  
		logger.info("文件名称: " + myfile.getName());  
		logger.info("文件原名: " + myfile.getOriginalFilename());  
		logger.info("========================================");
	}
}
