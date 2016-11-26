package com.juju.sport.stadium.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.stadium.dao.SpaceDao;
import com.juju.sport.stadium.dao.SpaceOpenTimeDao;
import com.juju.sport.stadium.dto.SpaceDto;
import com.juju.sport.stadium.dto.SpaceListDto;
import com.juju.sport.stadium.dto.SpaceOpenTimeDto;
import com.juju.sport.stadium.pojo.Space;
import com.juju.sport.stadium.pojo.SpaceOpenTime;
import com.juju.sport.stadium.service.ISpaceService;


@Service
public class SpaceServiceImpl implements ISpaceService{
	
	@Autowired
	private SpaceDao spaceDao;
	
	@Autowired
	private SpaceOpenTimeDao SpaceOpenTimeDao;
	
	@Override
	public String saveOrUpdate(SpaceDto spaceDto){
		Space space = BeanUtils.createBeanByTarget(spaceDto, Space.class);
		if(StringUtils.isEmpty(space.getId())) {
			String id = UUIDGenerator.getUUID();
			space.setId(id);
			space.setCreateTime(new Date());
			space.setLastUpdateTime(new Date());
			space.setStat(1);
			spaceDao.insert(space);
			return id;
		} else {
			spaceDao.updateByPrimaryKey(space);
			return "0";
		}
	}

	@Override
	public void delete(SpaceDto spaceDto) {
		Space space = BeanUtils.createBeanByTarget(spaceDto, Space.class);
		if(StringUtils.isEmpty(space.getId())) {
			space.setStat(0);
			spaceDao.updateByPrimaryKey(space);
		}
	}

	@Override
	public List<SpaceDto> findBy(SpaceDto spaceDto) {
		List<Space> list = spaceDao.findBy(spaceDto);
		List<SpaceDto> results = BeanUtils.createBeanListByTarget(list, SpaceDto.class);
		return results;
	}

	@Override
	public int findById(SpaceOpenTimeDto spaceOpenTimeDto) {
		return spaceDao.findById(spaceOpenTimeDto);
	}

	@Override
	public void saveOrUpdateOpenTime(SpaceOpenTimeDto spaceOpenTimeDto) {
			if(StringUtils.isEmpty(spaceOpenTimeDto.getId())){
				spaceDao.insertOpen(spaceOpenTimeDto);
			}
	}

	@Override
	public PageResult<SpaceListDto> findAll(SpaceDto spaceDto,PageQuery page) {
		List<SpaceListDto> list = spaceDao.findList(spaceDto,page);
		int total = spaceDao.findCountByUserAccount(spaceDto);
		if(page!=null){
		page.setTotal(total);
		return new PageResult<SpaceListDto>(page, list);
		}else{
			PageQuery pages = new PageQuery();
			pages.setTotal(total);
			return new PageResult<SpaceListDto>(pages, list);
		}
		
	}

	@Override
	public void deleteOnTime(SpaceOpenTimeDto spaceOpenTimeDto) {
		SpaceOpenTime spaceOpen = BeanUtils.createBeanByTarget(spaceOpenTimeDto, SpaceOpenTime.class);
		if(StringUtils.isEmpty(spaceOpenTimeDto.getSpaceId())){
			spaceOpen.setStat(0);
			SpaceOpenTimeDao.updateTime(spaceOpenTimeDto);
		}
	}

	@Override
	public SpaceListDto findUpdateInfos(SpaceDto spaceDto) {
		List<SpaceListDto> list = spaceDao.findList(spaceDto,null);
		if (list != null && list.size() > 0) {
			SpaceListDto spaceListDto = list.get(0);
			int startOpenWeek = 0;
			int startTime = -1;
			int endTime = -1;
			int endOpenWeek=0;
			int price=0;
			
			SpaceOpenTimeDto resultTimeDto = null;
			List<SpaceOpenTimeDto> resultOpenList = new ArrayList<SpaceOpenTimeDto>();
			
			for(int i=0;i<spaceListDto.getSpaceOpenList().size();i++){
				
				SpaceOpenTimeDto spaceOpenTimeDto =spaceListDto.getSpaceOpenList().get(i);
				
				//如果值为空
				if(startOpenWeek!=0&&startTime!=-1&&endTime!=-1){
					//如果时间不一样
					if(startTime!=spaceOpenTimeDto.getStartHour()||endTime!=spaceOpenTimeDto.getEndHour()){
						resultTimeDto = new SpaceOpenTimeDto();
						resultTimeDto.setStartHour(startTime);
						resultTimeDto.setEndHour(endTime);
						resultTimeDto.setEndOpenWeek((short)endOpenWeek);
						resultTimeDto.setOpenWeek((short)startOpenWeek);
						resultTimeDto.setPrice(price);
						resultOpenList.add(resultTimeDto);
						
						startOpenWeek = spaceOpenTimeDto.getOpenWeek();
						endOpenWeek = spaceOpenTimeDto.getOpenWeek();
						startTime = spaceOpenTimeDto.getStartHour();
						endTime = spaceOpenTimeDto.getEndHour();
						price = spaceOpenTimeDto.getPrice();
						if(i==spaceListDto.getSpaceOpenList().size()-1){
							resultTimeDto = new SpaceOpenTimeDto();
							resultTimeDto.setStartHour(startTime);
							resultTimeDto.setEndHour(endTime);
							resultTimeDto.setEndOpenWeek((short)endOpenWeek);
							resultTimeDto.setOpenWeek((short)startOpenWeek);
							resultTimeDto.setPrice(price);
							resultOpenList.add(resultTimeDto);
						}
					}else{
						endOpenWeek = spaceOpenTimeDto.getOpenWeek();
						if(i==spaceListDto.getSpaceOpenList().size()-1){
							resultTimeDto = new SpaceOpenTimeDto();
							resultTimeDto.setStartHour(startTime);
							resultTimeDto.setEndHour(endTime);
							resultTimeDto.setEndOpenWeek((short)endOpenWeek);
							resultTimeDto.setOpenWeek((short)startOpenWeek);
							resultTimeDto.setPrice(price);
							resultOpenList.add(resultTimeDto);
							}
						}
					
				}else{
					startOpenWeek = spaceOpenTimeDto.getOpenWeek();
					startTime = spaceOpenTimeDto.getStartHour();
					endTime = spaceOpenTimeDto.getEndHour();
					endOpenWeek = spaceOpenTimeDto.getOpenWeek();
					price = spaceOpenTimeDto.getPrice();
					if(i==spaceListDto.getSpaceOpenList().size()-1){
						resultTimeDto = new SpaceOpenTimeDto();
						resultTimeDto.setStartHour(startTime);
						resultTimeDto.setEndHour(endTime);
						resultTimeDto.setEndOpenWeek((short)endOpenWeek);
						resultTimeDto.setOpenWeek((short)startOpenWeek);
						resultTimeDto.setPrice(price);
						resultOpenList.add(resultTimeDto);
					}
				}
				
				
			}
			spaceListDto.setSpaceOpenList(resultOpenList);
			return spaceListDto;
		} else {
			return null;
		}
	}

	@Override
	public void updateSpaceInfos(SpaceDto spaceDto) {
		spaceDao.updateSpace(spaceDto);
	}

	@Override
	public void deleteSpaceInfos(SpaceDto spaceDto) {
		spaceDao.deleteSpace(spaceDto);
	}

	@Override
	public int findCountByName(SpaceDto spaceDto) {
		List<Space> list =  spaceDao.findCountName(spaceDto);
		if(list!=null&&list.size()>0){
			return list.size();
		}
		return 0;
	}

	@Override
	public List<SpaceDto> findBy(List<String> spaceIds,String ownerAccountId) {
		List<Space> list = spaceDao.findBy(spaceIds,ownerAccountId);
		List<SpaceDto> results = BeanUtils.createBeanListByTarget(list, SpaceDto.class);
		return results;
	}

	@Override
	public int findCheckDate(SpaceOpenTimeDto spaceOpenTimeDto) {
		return spaceDao.findCheckDate(spaceOpenTimeDto);
	}

	@Override
	public List<SpaceDto> findSpacesBy(List<String> userAccountIds) {
		List<Space> list = spaceDao.findSpacesBy(userAccountIds);
		List<SpaceDto> result = BeanUtils.createBeanListByTarget(list, SpaceDto.class);
		return result;
	}



}
