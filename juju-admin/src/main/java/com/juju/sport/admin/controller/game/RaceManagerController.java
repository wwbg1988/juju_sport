package com.juju.sport.admin.controller.game;

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

import com.juju.sport.common.ext.ExtPageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.FileUtils;
import com.juju.sport.common.util.JsonUtil;
import com.juju.sport.common.util.PropertiesUtils;
import com.juju.sport.game.dto.RaceInfoDto;
import com.juju.sport.game.dto.RaceInfoQuery;
import com.juju.sport.game.service.IRaceManagerService;

@Controller
@RequestMapping(value="/game/race" )
public class RaceManagerController {

	protected static final Log logger = LogFactory.getLog(RaceManagerController.class);
	
	@Autowired
	public IRaceManagerService raceManagerService;

	@RequestMapping(value="/index.do")
	public String index(){
        logger.debug("visit admin order manager page");
        return "/admin/game/race-manage";
    }
	
	
	@ResponseBody
	@RequestMapping(value="/find.do")
	public PageResult<RaceInfoDto> findRaceInfo(RaceInfoQuery query, ExtPageQuery page) {
		logger.info("RaceManagerController Query : " + query);
		PageResult<RaceInfoDto> results = raceManagerService.findByPage(query, page.changeToPageQuery());
		return results;		
	}
	
	@ResponseBody
	@RequestMapping(value="/create.do")
	public String createRaceInfo(RaceInfoDto raceInfo, @RequestParam("picImage")MultipartFile myfile, HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("Create RaceInfo : " + raceInfo);
		
        if(!myfile.isEmpty()){  
        	printFileInfo(myfile);  
        	uploadImage(raceInfo, myfile, request);
        }
    	
        raceManagerService.save(raceInfo);
  
    	return JsonUtil.getJsonStr(new RequestResult(true, "200"));
	}
	
	@ResponseBody
	@RequestMapping(value="/update.do")
	public String updateRaceInfo(RaceInfoDto raceInfo, @RequestParam("picImage")MultipartFile myfile, HttpServletRequest request, HttpServletResponse response) throws IOException {
		 logger.info("Create RaceInfo : " + raceInfo);
          	 
		 if(!myfile.isEmpty()){  
			printFileInfo(myfile);  
	     	uploadImage(raceInfo, myfile, request);
	     }
         
        raceManagerService.update(raceInfo);
    
//      response.setContentType("application/json");
//    	response.setCharacterEncoding("utf-8");
    	return JsonUtil.getJsonStr(new RequestResult(true, "200"));
	}
	
	@ResponseBody
	@RequestMapping(value="/htmleditorUpload.do")
	public String htmleditorUploadInfo(RaceInfoDto raceInfo, @RequestParam("htmleditorImage")MultipartFile myfile, @RequestParam("width")String width,@RequestParam("height")String height,HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("Create RaceInfo : " + raceInfo);
		//图片的上传路径;
		String imgPath = null;
		String jsonString=null;
        if(!myfile.isEmpty()){  
        	printFileInfo(myfile);  
        	String context = "upload";
            String realPath = PropertiesUtils.getProperty("upload.url");
    		String fileDe = DateUtils.format(new Date(), DateUtils.YMD);
    		String fileName = FileUtils.getUploadFileName(myfile.getOriginalFilename());
    	    imgPath = context + "/" + fileDe + "/" + fileName;
    		logger.info("filePaht :"+realPath + "/" +fileDe + fileName);
    		File file = new File(realPath + "/" +context+"/"+fileDe);
            //如果文件夹不存在则创建    
            if(!file.exists() && !file.isDirectory()) {
                file.mkdir();    
            }
            FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath + "/" +context+"/"+fileDe , fileName));
            raceInfo.setPic(imgPath);
        }
    	
        raceManagerService.save(raceInfo);
        if((width!=""||width!=null) && (height!=""||height!=null)){
            jsonString="{success:true,data:{'url':'"+imgPath+"','title':'"+myfile.getOriginalFilename()+"','width':'"+width+"','height':'"+height+"','message':'200'}}";
        }else{
            jsonString="{success:true,data:{'url':'"+imgPath+"','title':'"+myfile.getOriginalFilename()+"','message':'200'}}";
        }
         return jsonString;
	}
	

	@ResponseBody
	@RequestMapping(value="/delete.do")
	public RequestResult deleteReceInfo(String id) {
		raceManagerService.deleteById(id);
		return new RequestResult(true, "删除成功");
	}
	
	@ResponseBody
	@RequestMapping(value="/updateContext.do")
	public RequestResult updateContext(RaceInfoDto raceInfo) {
		logger.info(raceInfo);
		raceManagerService.updateContext(raceInfo);
		return  new RequestResult(true, "修改成功");
	}
	
	private void uploadImage(RaceInfoDto raceInfo, MultipartFile myfile,
			HttpServletRequest request) throws IOException {
		//如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解  
        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解  
		String context = "upload";
        String realPath = PropertiesUtils.getProperty("upload.url");
		String fileDe = DateUtils.format(new Date(), DateUtils.YMD);
		String fileName = FileUtils.getUploadFileName(myfile.getOriginalFilename());
		logger.info("filePaht :"+realPath + "/" +fileDe + "/" + fileName);
		File file = new File(realPath + "/" +context+"/"+fileDe);
        //如果文件夹不存在则创建    
        if(!file.exists() && !file.isDirectory()) {
            file.mkdir();    
        }
//        String fileName = FileUtils.getUploadFileName(myfile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath + "/" +context+"/"+fileDe , fileName));
//        FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath + "/" + context, fileName));  
//        raceInfo.setPic(context + "/" + fileName);
        raceInfo.setPic(context + "/" + fileDe + "/" + fileName);
	}
	

	private void printFileInfo(MultipartFile myfile) {
		logger.info("文件长度: " + myfile.getSize());  
		logger.info("文件类型: " + myfile.getContentType());  
		logger.info("文件名称: " + myfile.getName());  
		logger.info("文件原名: " + myfile.getOriginalFilename());  
		logger.info("========================================");
	}
}
