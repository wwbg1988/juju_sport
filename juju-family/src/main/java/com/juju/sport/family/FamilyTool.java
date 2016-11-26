package com.juju.sport.family;

import java.text.DecimalFormat;
import java.util.List;

import com.juju.sport.family.dto.SpecificationsDto;
import com.juju.sport.family.dto.SportExaminationDto;
import com.juju.sport.family.dto.SportIntroductionDto;

public class FamilyTool {
	
	public static String createTableByLetter(SpecificationsDto sd,SpecificationsDto sdto){
		if(sdto != null){
			StringBuffer sbf = new StringBuffer();
			double cz1 = sdto.getHeight()-sd.getHeight();
			double cz2 = sdto.getWeight()-sd.getWeight();
			DecimalFormat df = new DecimalFormat("#.0");
			sbf.append("<table  border=1>");
			sbf.append("<tr><td>性别</td><td>年龄</td><td>身高均值</td><td>差值1</td><td>体重均值</td><td>差值2</td></tr>");
			sbf.append("<tr><td>"+sdto.getSex()+"</td><td>"+sdto.getAge()+"</td><td>"+sdto.getHeight()+"</td><td>"+df.format(cz1)+"</td><td>"+sdto.getWeight()+"</td><td>"+df.format(cz2)+"</td></tr>");
			sbf.append("</table>");
			return sbf.toString();
		}
		return null;
	}
	
	
	public static String createTableBySExam(List<SportExaminationDto> lsdto){
		if(lsdto.size() > 0){
			StringBuffer sbf = new StringBuffer();
			sbf.append("<thead><tr><td colspan=2 style='height:35px;'>2015年上海中考体育评分标准汇总</td></tr></thead>");
			sbf.append("<tbody>");
			for(int i=0;i<lsdto.size();i++){
				SportExaminationDto sdto = lsdto.get(i);
				//sbf.append("<tr><td>"+sdto.getType()+"</td><td><a href='/family/getSportExaminationDetail.do?id="+sdto.getId()+"'>"+sdto.getTitle()+"</a></td><td><a href='/family/getSportExaminationDetail.do?id="+sdto.getId()+"'>查看详情</a></td></tr>");
				sbf.append("<tr><td>"+sdto.getType()+"</td><td><a href='#' onclick=\"a_click(this, '"+sdto.getImgUrl()+"')\">"+sdto.getTitle()+"</a></td></tr>");
			}
			sbf.append("</tbody>");
			return sbf.toString();
		}
		return null;
	}
	
	public static String createTableByIntro(List<SportIntroductionDto> lsidto){
		if(lsidto.size() > 0){
			StringBuffer sbf = new StringBuffer();
			for(int i=0;i<lsidto.size();i++){
				SportIntroductionDto sid=lsidto.get(i);
				if(i == 0){
					sbf.append("<tr>");
				}
				if(i%3==0 && i != 0){
					sbf.append("<tr>");
				}
				sbf.append("<td width=410 ><a href='/family/getSportIntroductionDetail.do?id="+sid.getId()+"'><img src='"+sid.getImgUrl()+"' /></a></td>");
				if((i+1)%3==0){
					sbf.append("</tr>");
				}
				if(i==(lsidto.size()-1)){
					for(int a=0;a<i%3;a++ ){
						sbf.append("<td> </td>");
					}
					sbf.append("</tr>");
				}
			}
			return sbf.toString();
		}
		
		return null;
	}
	

}
