package com.juju.sport.home.controller;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.tools.RandomPicTools;

@Controller
@RequestMapping(value = "/draw")
public class DrawRandomController {
	@RequestMapping(value = "/drawRandom.do")
	@ResponseBody
	public void drawRandom(HttpServletResponse reponse,HttpServletRequest request, HttpSession session) {
		RandomPicTools randomPicTools = new RandomPicTools();
		int width = 80;// 图片宽
		int height = 26;// 图片高
		int lineSize = 40;// 干扰线数量
		int stringNum = 4;// 随机产生字符数量
		session = request.getSession();
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		g.setColor(randomPicTools.getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			randomPicTools.drowLine(g, width, height);
		}
		// 绘制随机字符
		String randomString = "";
		for (int i = 1; i <= stringNum; i++) {
			randomString = randomPicTools.drowString(g, randomString, i);
		}
		session.removeAttribute(DataStatus._ORDERCODE_);
		session.setAttribute(DataStatus._ORDERCODE_, randomString);
		g.dispose();
		try {
			// 将内存中的图片通过流动形式输出到客户端
			ImageIO.write(image, "JPEG", reponse.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
