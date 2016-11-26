package com.juju.sport.stadium.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.stadium.dto.SpaceDto;
import com.juju.sport.stadium.dto.SpaceListDto;
import com.juju.sport.stadium.dto.SpaceOpenTimeDto;
import com.juju.sport.stadium.mapper.SpaceMapper;
import com.juju.sport.stadium.mapper.SpaceOpenTimeMapper;
import com.juju.sport.stadium.mapper.SpacesMapper;
import com.juju.sport.stadium.mapper.SpacesOpenTimeMapper;
import com.juju.sport.stadium.pojo.Space;
import com.juju.sport.stadium.pojo.SpaceExample;

@Repository
public class SpaceDao extends MyBatisBaseDao<Space> {
	@Autowired
	@Getter
	private SpaceMapper mapper;

	@Autowired
	@Getter
	private SpacesMapper spacesMapper;

	@Autowired
	@Getter
	private SpacesOpenTimeMapper spacesOpenTimeMapper;

	@Autowired
	@Getter
	private SpaceOpenTimeMapper spaceOpenTimeMapper;

	public List<Space> findBy(SpaceDto spaceDto) {
		SpaceExample example = new SpaceExample();
		SpaceExample.Criteria criteria = example.createCriteria();
		// 名称
		if (!StringUtils.isEmpty(spaceDto.getSpaceName())) {
			criteria.andSpaceNameEqualTo(spaceDto.getSpaceName());
		}
		// ID
		if (!StringUtils.isEmpty(spaceDto.getId())) {
			criteria.andIdEqualTo(spaceDto.getId());
		}
		// 场地所属人员
		if (!StringUtils.isEmpty(spaceDto.getOwnerAccountId())) {
			criteria.andUserAccountIdEqualTo(spaceDto.getOwnerAccountId());
		}
		// 场地属于那种体育类型的
		if (!StringUtils.isEmpty(spaceDto.getSportId())) {
			criteria.andSportIdEqualTo(spaceDto.getSportId());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}

	public List<Space> findCountName(SpaceDto spaceDto) {
		SpaceExample example = new SpaceExample();
		SpaceExample.Criteria criteria = example.createCriteria();
		if (!StringUtils.isEmpty(spaceDto.getSpaceName())) {
			criteria.andSpaceNameEqualTo(spaceDto.getSpaceName());
		}
		if (!StringUtils.isEmpty(spaceDto.getUserAccountId())) {
			criteria.andUserAccountIdEqualTo(spaceDto.getUserAccountId());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}

	public Integer findById(SpaceOpenTimeDto spaceOpenTimeDto) {
		return spacesOpenTimeMapper.findCheckTime(spaceOpenTimeDto);
	}
	public Integer findCheckDate(SpaceOpenTimeDto spaceOpenTimeDto){
		return spacesOpenTimeMapper.findCheckDate(spaceOpenTimeDto);
		
	}

	public void insertOpen(SpaceOpenTimeDto spaceOpenTimeDto) {
		spaceOpenTimeDto.setId(UUIDGenerator.getUUID());
		spaceOpenTimeDto.setCreateTime(new Date());
		spaceOpenTimeDto.setLastUpdateTime(new Date());
		spaceOpenTimeDto.setStat(1);

		spacesOpenTimeMapper.insertOpen(spaceOpenTimeDto);
	}

	public List<SpaceListDto> findList(SpaceDto spaceDto, PageQuery page) {
		SpaceExample example = new SpaceExample();
		SpaceExample.Criteria criteria = example.createCriteria();
		if (!StringUtils.isEmpty(spaceDto.getUserAccountId())) {
			criteria.andUserAccountIdEqualTo(spaceDto.getUserAccountId());
		}
		if (!StringUtils.isEmpty(spaceDto.getId())) {
			criteria.andIdEqualTo(spaceDto.getId());
		}
		if (!StringUtils.isEmpty(spaceDto.getSpaceName())) {
//			criteria.andSpaceNameEqualTo(spaceDto.getSpaceName());
			criteria.andSpaceNameLike(spaceDto.getSpaceName());
		}

		criteria.andStatEqualTo(1);
		if (page != null) {
			example.setOrderByClause("create_time desc limit "
					+ page.getStartNum() + ", " + page.getPageSize() + "");
		}
		List<Space> spaceList = mapper.selectByExample(example);
		if (spaceList != null) {
			List<SpaceListDto> resultList = new ArrayList<SpaceListDto>();
			for (Space space : spaceList) {
				SpaceListDto spaceListDto = new SpaceListDto();
				// SpaceOpenTimeExample exampleOpen = new
				// SpaceOpenTimeExample();

				// SpaceOpenTimeExample.Criteria criterias =
				// exampleOpen.createCriteria();
				// criterias.andSpaceIdEqualTo(space.getId());
				// List<SpaceOpenTime> oList =
				// spaceOpenTimeMapper.selectByExample(exampleOpen);

				SpaceOpenTimeDto param = new SpaceOpenTimeDto();
				param.setSpaceId(space.getId());
				List<SpaceOpenTimeDto> sList = spacesOpenTimeMapper
						.findAllOpen(param);

				SpaceDto spaceDtos = new SpaceDto();
				BeanUtils.copyProperties(space, spaceDtos);
				spaceListDto.setSpaceDto(spaceDtos);
				spaceListDto.setSpaceOpenList(sList);
				resultList.add(spaceListDto);
			}
			return resultList;
		} else {
			return null;
		}
	}

	public int findCountByUserAccount(SpaceDto spaceDto) {
		return spacesMapper.findCountSpaceById(spaceDto);
	}

	public void updateSpace(SpaceDto spaceDto) {
		spacesMapper.updateSpace(spaceDto);
	}

	public void deleteSpace(SpaceDto spaceDto) {
		spacesMapper.deleteSpace(spaceDto);
		SpaceOpenTimeDto spaceOpenTimeDto = new SpaceOpenTimeDto();
		spaceOpenTimeDto.setSpaceId(spaceDto.getId());
		spacesOpenTimeMapper.deleteAllOpen(spaceOpenTimeDto);
	}
	
	public List<Space> findBy(List<String> spaceIds,String ownerAccountId) {
		SpaceExample example = new SpaceExample();
		SpaceExample.Criteria criteria = example.createCriteria();

		if (spaceIds!=null && spaceIds.size()>0) {
			criteria.andIdIn(spaceIds);
		}

		criteria.andUserAccountIdEqualTo(ownerAccountId);
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}

	public List<Space> findSpacesBy(List<String> userAccountIds) {
		SpaceExample example = new SpaceExample();
		SpaceExample.Criteria criteria = example.createCriteria();

		if (userAccountIds!=null && userAccountIds.size()>0) {
			criteria.andUserAccountIdIn(userAccountIds);
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
		 
	}
}
