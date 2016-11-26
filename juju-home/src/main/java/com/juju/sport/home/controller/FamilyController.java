package com.juju.sport.home.controller;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.family.FamilyTool;
import com.juju.sport.family.dto.HealthDto;
import com.juju.sport.family.dto.LetterDto;
import com.juju.sport.family.dto.SpecificationsDto;
import com.juju.sport.family.dto.SportExaminationDto;
import com.juju.sport.family.dto.SportIntroductionDto;
import com.juju.sport.family.pojo.SportIntroduction;
import com.juju.sport.family.service.IHealthService;
import com.juju.sport.family.service.ILetterService;
import com.juju.sport.family.service.ISpecificationsService;
import com.juju.sport.family.service.ISportExaminationService;
import com.juju.sport.family.service.ISportIntroductionService;


@Controller
@RequestMapping(value = "/family")
public class FamilyController {

	protected static final Log logger = LogFactory.getLog(FamilyController.class);
	
	@Autowired
	private IHealthService ihservice;
	
	@Autowired
	private ILetterService ilservice;
	
	@Autowired
	private ISpecificationsService ispecificationsService;
	
	@Autowired
	private ISportExaminationService isportExaminationService;
	
	@Autowired
	private ISportIntroductionService isportIntroductionService;
	
	
	@RequestMapping(value = "/getHeath.do")
	@ResponseBody
	public HealthDto getHeath() {
		HealthDto result = ihservice.findAll();
		return result;
	}
	
	@RequestMapping(value = "/getLetter.do")
	@ResponseBody
	public LetterDto getLetter() {
		LetterDto result = ilservice.findLetter();
		return result;
	}
	
	@RequestMapping(value = "/getSpecifications.do")
	@ResponseBody
	public String getSpecifications(SpecificationsDto sd) {
		SpecificationsDto sdto = ispecificationsService.findByPro(sd.getSex(), sd.getAge());
		return FamilyTool.createTableByLetter(sd,sdto);
	}
	
	
	@RequestMapping(value = "/getSportExaminationList.do")
	@ResponseBody
	public String getSportExaminationList() {
		List<SportExaminationDto> lsdto = isportExaminationService.getSportExaminationList();
		return FamilyTool.createTableBySExam(lsdto);
	}
	
	@RequestMapping(value = "/getSportIntroductionList.do")
	@ResponseBody
	public String getSportIntroductionList(SportIntroduction si) {
		List<SportIntroductionDto> lsidto = new ArrayList<SportIntroductionDto>();
		if(si.getSex()==null){
			si.setSex(-1);
		}
		lsidto = isportIntroductionService.findByPro(si.getType(), si.getSport(), si.getEnvironmental(), si.getSex());
		return FamilyTool.createTableByIntro(lsidto);
	}
	
	@RequestMapping(value = "/getSportIntroductionListAll.do")
	@ResponseBody
	public String getSportIntroductionListAll() {
		List<SportIntroductionDto> lsidto = new ArrayList<SportIntroductionDto>();
		lsidto = isportIntroductionService.findAll();
		return FamilyTool.createTableByIntro(lsidto);
	}
	
	@RequestMapping(value = "/getSportIntroductionDetail.do")
	public String getSportIntroductionDetail(String id,HttpServletRequest request) {
		SportIntroductionDto lsidto = isportIntroductionService.findbyId(id);
		request.setAttribute("lsidto", lsidto);
		return "/family/introDetail";
	}
	
	
	
}
