package com.juju.sport.home.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.LogWriter;
import com.juju.sport.common.util.PropertiesUtils;
import com.juju.sport.home.controller.AddressesController;

public class UploadProcessorServlet extends HttpServlet{
	
	protected static final Log logger = LogFactory.getLog(UploadProcessorServlet.class);
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 设置编码
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 如果没以下两行设置的话，上传大的文件会占用很多内存，
		// 设置暂时存放的存储室 ,这个存储室,可以和最终存储文件的目录不同
		/**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tmp
		 * 格式的 然后再将其真正写到 对应目录的硬盘上
		 */
//		factory.setRepository(new File());
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);

		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			// 提交上来的信息都在这个list里面
			// 这意味着可以上传多个文件
			// 请自行组织代码 D:\LearningWorkSpace\juju_sport_html
	        Date date = new Date();
	        String fileDe = DateUtils.format(date, DateUtils.YMD);
	        StringBuffer rootPath = new StringBuffer(PropertiesUtils.getProperty("upload.url"));
	        rootPath.append(fileDe);
	        String fileName = new Date().getTime()+".jpg";
	        File file =new File(rootPath.toString());
	        //如果文件夹不存在则创建    
	        if(!file.exists() && !file.isDirectory()) {
	            file.mkdir();    
	        }	

			List<FileItem> list = upload.parseRequest(request);
			//获取上传的文件
			FileItem item = getUploadFileItem(list);
			//获取文件名
//			String filename = getUploadFileName(item);
			//真正写到磁盘上
			item.write(new File(rootPath.toString(), fileName)); // 第三方提供的
			//输出信息,前端页面获取,这里用的json格式
			PrintWriter writer = response.getWriter();

			writer.print("{status:" + DataStatus.HTTP_SUCCESS + ",message:'" + "/upload/" + fileDe +"/"+ fileName + "'}");		
			
			writer.close();
		} catch (FileUploadException e) {
			logger.error(LogWriter.getStackMsg(e.getStackTrace()));			
		} catch (Exception e) {
			logger.error(LogWriter.getStackMsg(e.getStackTrace()));
		}

	}

	private FileItem getUploadFileItem(List<FileItem> list) {
		for (FileItem fileItem : list) {
			if (!fileItem.isFormField()) {
				return fileItem;
			}
		}
		return null;
	}

	private String getUploadFileName(FileItem item) {
		// 获取路径名
		String value = item.getName();
		// 索引到最后一个反斜杠
		int start = value.lastIndexOf("/");
		// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
		String filename = value.substring(start + 1);

		return filename;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
