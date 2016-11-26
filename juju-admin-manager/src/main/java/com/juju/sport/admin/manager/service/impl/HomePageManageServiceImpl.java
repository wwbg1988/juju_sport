package com.juju.sport.admin.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.admin.manager.service.IHomePageManageService;
import com.juju.sport.base.dto.HomePageConfigDto;
import com.juju.sport.base.dto.PageData;
import com.juju.sport.base.service.IHomePageConfigSerivce;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.JsonUtil;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.service.IUserVenuesInfoService;

@Service
public class HomePageManageServiceImpl implements IHomePageManageService {

	@Autowired
	private IHomePageConfigSerivce homePageConfigSerivce;
	
	@Autowired
	private IUserVenuesInfoService userVenuesInfoService;

	@Override
	public RequestResult addVenuesToHomePage(String venuesId, String pageKey) {
		VenusInfoDto venuesInfo = userVenuesInfoService.findById(venuesId);
		if(venuesInfo == null) {
			return new RequestResult(false, "无有效记录");
		}
	    PageData pageData =changeToPageData(venuesInfo);
		List<PageData> results =homePageConfigSerivce.findByKey(pageKey);
		for(PageData p:results){
		if(p.getVenuesId().equals(pageData.getVenuesId())){
			return new RequestResult(false, "该记录已存在,请勿重复添加。");
		}
		}
		String json = JsonUtil.getJsonStr(pageData);
		HomePageConfigDto config = new HomePageConfigDto();
		config.setDataKey(pageKey);
		config.setDataValue(json);
		config.setPage("home-page");
		config.setStat(1);
		int total = homePageConfigSerivce.countAll();
		config.setOrderTag(total);
		homePageConfigSerivce.save(config);
		return new RequestResult(true, "添加成功");
	}

	private PageData changeToPageData(VenusInfoDto venuesInfo) {
		PageData data = new PageData();
		data.setTitle(venuesInfo.getNickName());
		data.setVenuesId(venuesInfo.getId());
		data.setAccountId(venuesInfo.getUserAccountId());
		data.setImage(venuesInfo.getVenueImg());
		data.setDescription(venuesInfo.getDescs());
		return data;
	}
	
}
