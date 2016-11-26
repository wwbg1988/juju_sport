package com.juju.sport.user.dao;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.juju.sport.base.dto.ServiceTypeDto;
import com.juju.sport.base.dto.SportTypeDto;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.user.dto.VenuesQuery;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.mapper.VenuesInfoExMapper;
import com.juju.sport.user.mapper.VenuesInfoMapper;
import com.juju.sport.user.pojo.VenuesInfo;
import com.juju.sport.user.pojo.VenuesInfoExample;

@Repository
public class VenuesInfoDao extends MyBatisBaseDao<VenuesInfo>
{

    @Autowired
    @Getter
    private VenuesInfoMapper mapper;

    @Autowired
    @Getter
    private VenuesInfoExMapper mapperEx;

    public int findVenusCount(VenusInfoDto venusInfoDto)
    {
        return mapperEx.findCount(venusInfoDto);
    }

    public List<VenuesInfo> findPageByAdd(VenusInfoDto venusInfoDto, PageQuery page)
    {
        VenuesInfoExample example = new VenuesInfoExample();
        VenuesInfoExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(venusInfoDto.getCityid()))
        {
            criteria.andCityidEqualTo(venusInfoDto.getCityid());
        }
        if (!StringUtils.isEmpty(venusInfoDto.getCountryid()))
        {
            criteria.andCountryidEqualTo(venusInfoDto.getCountryid());
        }
        if (!StringUtils.isEmpty(venusInfoDto.getChargeType()))
        {
            criteria.andChargeTypeEqualTo(venusInfoDto.getChargeType());
        }
        if (!StringUtils.isEmpty(venusInfoDto.getId()))
        {
            criteria.andIdEqualTo(venusInfoDto.getId());
        }

        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (page != null)
        {
            example.setOrderByClause("create_time desc limit " + page.getStartNum() + ", "
                + page.getPageSize() + "");
        }
        List<VenuesInfo> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results;
    }

    public List<VenusInfoDto> findVenusByAdd(VenusInfoDto param, PageQuery page)
    {
        List<VenusInfoDto> result = new ArrayList<VenusInfoDto>();
        param.setStartNum(page.getStartNum());
        param.setPageSize(page.getPageSize());
        List<VenuesInfo> tempList = mapperEx.findVenusInfo(param);
        List<VenusInfoDto> tempDtoList = null;
        if (tempList != null && tempList.size() > 0)
        {
            tempDtoList = BeanUtils.createBeanListByTarget(tempList, VenusInfoDto.class);
            //    		for(VenuesInfo venusInfo:tempList){
            //    			List<SportType> sportList = mapperEx.findVenusSportType(venusInfo.getId());
            //    			List<ServiceType> serviceList = mapperEx.findVenusServiceType(venusInfo.getId());
            //    			venusInfo.setSportTypeList(sportList);
            //    			venusInfo.setServiceTypeList(serviceList);
            //    			result.add(venusInfo);
            //        	}
            for (VenusInfoDto venusInfoDto : tempDtoList)
            {
                List<SportTypeDto> sportList = mapperEx.findVenusSportType(venusInfoDto.getId());
                List<ServiceTypeDto> serviceList = mapperEx.findVenusServiceType(venusInfoDto.getId());
                venusInfoDto.setSportTypeList(sportList);
                venusInfoDto.setServiceTypeList(serviceList);
                result.add(venusInfoDto);
            }
        }

        return result;
    }

    public List<VenuesInfo> findPageLikeName(VenusInfoDto venusInfoDto, PageQuery page)
    {
        VenuesInfoExample example = new VenuesInfoExample();
        VenuesInfoExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(venusInfoDto.getNickName()))
        {
            criteria.andNickNameLike("%" + venusInfoDto.getNickName() + "%");
        }

        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (page != null)
        {
            example.setOrderByClause("create_time desc limit " + page.getStartNum() + ", "
                + page.getPageSize() + "");
        }
        List<VenuesInfo> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results;
    }

    public int findCountPageLikeName(VenusInfoDto venusInfoDto)
    {
        VenuesInfoExample example = new VenuesInfoExample();
        VenuesInfoExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(venusInfoDto.getNickName()))
        {
            criteria.andNickNameLike("%" + venusInfoDto.getNickName() + "%");
        }
        if (!StringUtils.isEmpty(venusInfoDto.getProvinceid() + ""))
        {
            criteria.andProvinceidEqualTo(venusInfoDto.getProvinceid());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
    }

    public List<VenuesInfo> findByAdd(VenusInfoDto venusInfoDto)
    {
        VenuesInfoExample example = new VenuesInfoExample();
        VenuesInfoExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(venusInfoDto.getCityid()))
        {
            criteria.andCityidEqualTo(venusInfoDto.getCityid());
        }
        if (!StringUtils.isEmpty(venusInfoDto.getCountryid()))
        {
            criteria.andCountryidEqualTo(venusInfoDto.getCountryid());
        }

        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<VenuesInfo> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results;
    }

    public VenuesInfo findByName(VenusInfoDto venusInfoDto)
    {
        VenuesInfoExample example = new VenuesInfoExample();
        VenuesInfoExample.Criteria criteria = example.createCriteria();
        criteria.andNickNameEqualTo(venusInfoDto.getNickName());
        List<VenuesInfo> result = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }

    public List<VenusInfoDto> findNameBySportType(VenusInfoDto venusInfoDto)
    {
        List<VenuesInfo> tempList = mapperEx.findNameBySportType(venusInfoDto);
        if (tempList != null && tempList.size() > 0)
        {
            List<VenusInfoDto> result = new ArrayList<VenusInfoDto>();
            result = BeanUtils.createBeanListByTarget(tempList, VenusInfoDto.class);
            return result;
        }
        return null;
    }

    public List<VenuesInfo> findByServiceType(VenusInfoDto venusInfoDto)
    {
        return null;
    }

    public List<VenusInfoDto> findByPage(VenuesQuery query, PageQuery page)
    {
        if (StringUtils.isEmpty(query.getUserAccount()))
        {
            query.setUserAccount(null);
        }
        else
        {
            query.setUserAccount("%" + query.getUserAccount() + "%");
        }

        if (StringUtils.isEmpty(query.getNickName()))
        {
            query.setNickName(null);
        }
        else
        {
            query.setNickName("%" + query.getNickName() + "%");
        }

        return mapperEx.findByPage(query, page);
    }

    public long count(VenuesQuery query)
    {
        return mapperEx.count(query);
    }

    public void logicDelById(String id)
    {
        VenuesInfo venus = new VenuesInfo();
        venus.setId(id);
        venus.setStat(DataStatus.DISABLED);
        mapper.updateByPrimaryKeySelective(venus);
    }

    /**     
     * findViInfoByID：查询场馆信息
     * @param id 场馆账户ID
     * @return  List<VenusInfoDto> 场馆信息
     * @author Vincent
     * @date 2015年5月25日 下午2:33:00	 
     */
    public List<VenusInfoDto> findViInfoByUserID(String id)
    {
        VenuesInfoExample example = new VenuesInfoExample();
        VenuesInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserAccountIdEqualTo(id);
        List<VenuesInfo> result = mapper.selectByExample(example);

        List<VenusInfoDto> viDto = new ArrayList<VenusInfoDto>();
        viDto = BeanUtils.createBeanListByTarget(result, VenusInfoDto.class);
        return viDto;

    }
    public String updateSpaceNum(String userId,boolean flag){
    	if(!StringUtils.isEmpty(userId)){
    		//查询
        	VenuesInfo tempVenus = findSpaceNum(userId);
        	if(tempVenus!=null){
        		int counts = tempVenus.getSpaceNum();
        		//修改	
            	if(flag==true){
            		//场地数+1的情况
            		tempVenus.setSpaceNum(counts+1);
            		updateNum(tempVenus);
            	}else{
            		//场地数-1的情况,如果场地数为0
            		if(counts==0){
            			tempVenus.setSpaceNum(counts);
            			updateNum(tempVenus);
            		}else{
            			//场地数-1的情况,如果场地数大于0
            			tempVenus.setSpaceNum(counts-1);
            			updateNum(tempVenus);
            		}
            		
            	}
        	}else{
        		return "500";
        	}
        	
        	return "200";
    	}else{
    		return "500";
    	}
    	
    }
    private VenuesInfo findSpaceNum(String userId){
    	VenuesInfoExample example = new VenuesInfoExample();
        VenuesInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserAccountIdEqualTo(userId);
    	return mapper.selectByExample(example)!=null?mapper.selectByExample(example).get(0):null;
    }
    private void updateNum(VenuesInfo venuesInfo){
    	 mapper.updateByPrimaryKey(venuesInfo);
    }
}
