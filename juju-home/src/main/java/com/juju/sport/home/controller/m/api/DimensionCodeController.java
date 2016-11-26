package com.juju.sport.home.controller.m.api;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.MatrixToImageWriter;
import com.juju.sport.common.util.PropertiesUtils;
import com.juju.sport.common.util.RandomCode;
import com.juju.sport.common.util.TwoDimensionCode;
import com.juju.sport.home.dto.TicketDto;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.service.IOrderItemsService;

/**
 * 
 * 此类描述的是：二维码生成接口 对外
 * 
 * @author: cwftalus@163.com
 * @version: 2015年4月30日 下午1:32:31
 */
@Controller
@RequestMapping(value = "/api/m/code")
public class DimensionCodeController {
	
	@Autowired
	private IOrderItemsService iOrderItemsService;
	
	/**
	 * 消息对外接口
	 * **/
	@ResponseBody
	@RequestMapping("/create.do")
	public Response<TicketDto> loadMessages(String codeNo) {
		Response<TicketDto> result = new Response<TicketDto>();
		String fileName = codeNo + ".png";
		StringBuffer filePath = new StringBuffer(PropertiesUtils.getProperty("upload.url"));
		String tempPath = ("/upload/"+DateUtils.format(new Date(), DateUtils.YMD)+"/");
		filePath.append(tempPath);
        File file =new File(filePath.toString());
        //如果文件夹不存在则创建    
        if(!file.exists() && !file.isDirectory()) {
            file.mkdir();    
        }		
		filePath.append(codeNo).append(".png");
		
		
		int width = 200; // 二维码图片宽度
		int height = 200; // 二维码图片高度
		String format = "png";// 二维码的图片格式
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");	// 内容所使用字符集编码
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(codeNo, BarcodeFormat.QR_CODE, width, height, hints);
			// 生成二维码
			File outputFile = new File(filePath.toString());
			try {
				MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 生成二维码
		

		
//		TwoDimensionCode.encoderQRCode(codeNo, filePath.toString(), "png");

		OrderItemsDto orderItemsDto = new OrderItemsDto();
		orderItemsDto.setOrderNo(codeNo);
		List<OrderItemsDto> resultList = iOrderItemsService.findListBy(orderItemsDto);
		if(resultList!=null && resultList.size()>0){
			orderItemsDto = resultList.get(0);
		}

//		String randCode = RandomCode.getEnRandomCode(1)+RandomCode.getRandomCodeByNumber(4);
//		if(!StringUtils.isEmpty(orderItemsDto.getRandCode())){
//			randCode = orderItemsDto.getRandCode();
//		}else{
//			iOrderItemsService.updateOrderItemRandCodeById(randCode.toUpperCase(),orderItemsDto.getId());;
//		}

		TicketDto ticketDto = new TicketDto();
		ticketDto.setDimensionImage(tempPath + fileName);
		if(!StringUtils.isEmpty(orderItemsDto.getRandCode())){
			ticketDto.setRandCode(orderItemsDto.getRandCode().toUpperCase());	
		}else{
			String randCode = RandomCode.getEnRandomCode(1)+RandomCode.getRandomCodeByNumber(4);
			iOrderItemsService.updateOrderItemRandCodeById(randCode.toUpperCase(),"",orderItemsDto.getId());;
			ticketDto.setRandCode(randCode.toUpperCase());
		}
		
		result.setData(ticketDto);
		
		return result;
	}

}
