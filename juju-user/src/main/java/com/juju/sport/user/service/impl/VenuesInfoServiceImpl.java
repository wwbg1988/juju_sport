package com.juju.sport.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.juju.sport.base.cache.IAddressesCache;
import com.juju.sport.base.cache.ISportTypeCache;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.user.dao.UserAccountDao;
import com.juju.sport.user.dao.VenuesInfoDao;
import com.juju.sport.user.dto.VenuesQuery;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.pojo.VenuesInfo;
import com.juju.sport.user.service.IVenuesInfoService;

@Service
public class VenuesInfoServiceImpl implements IVenuesInfoService {

	@Autowired
	private VenuesInfoDao venuesInfoDao;
	
	@Autowired
	private UserAccountDao userAccountDao;
	
	@Autowired
	public IAddressesCache addressesCache;
	
	@Autowired
	public ISportTypeCache sportTypeCache;
	
    @Override
    public PageResult<VenusInfoDto> find(VenuesQuery query, PageQuery page)
    {
        List<VenusInfoDto> results = venuesInfoDao.findByPage(query, page);

        if (!CollectionUtils.isEmpty(results))
        {
            for (VenusInfoDto result : results)
            {
                setExValue(result);
            }
        }
        long total = venuesInfoDao.count(query);
        page.setTotal(total);
        return new PageResult<VenusInfoDto>(page, results);
    }
    
    private void setExValue(VenusInfoDto result)
    {
        //result.setJobName(Job.getDesc(result.getJob()));
    	result.setVenueImg(process(result.getVenueImg()));
        result.setCountryName(addressesCache.findNameById(result.getCountryid()));
        result.setProvinceName(addressesCache.findNameById(result.getProvinceid()));
        result.setCityName(addressesCache.findNameById(result.getCityid()));
        if (StringUtils.isNotEmpty(result.getVenueType()))
        {
            result.setVenueTypeNames(sportTypeCache.getNameStr(result.getVenueType()));
        }
    }

	@Override
	@Transactional
	public RequestResult update(VenusInfoDto user) {
		VenuesInfo target = new VenuesInfo();
		BeanUtils.copyProperties(user, target, "userAccount",  "createTime");
		venuesInfoDao.updateByPrimaryKeySelective(target);
		
		VenuesInfo venuesInfo = venuesInfoDao.selectByPrimaryKey(target.getId());	
		if(1 == target.getStat().intValue()) {
			userAccountDao.enableRecord(venuesInfo.getUserAccountId());
		} else if (0 == target.getStat().intValue()){ 
			userAccountDao.logicDeleteById(venuesInfo.getUserAccountId());
		}
		return new RequestResult(true, "更新成功!");
	}
	

	@Override
	@Transactional
	public RequestResult deleteById(String id) {
		venuesInfoDao.logicDelById(id);
		VenuesInfo venuesInfo = venuesInfoDao.selectByPrimaryKey(id);
		if(venuesInfo != null) {
			userAccountDao.logicDeleteById(venuesInfo.getUserAccountId());
		}
		return new RequestResult(true, "禁用成功!");
	}  
	
	 /**     
     * findByID：根据场馆ID查询场馆信息 
     * @param id  场馆帐号ID
     * @return VenusInfoDto
     * @author Vincent
     * @date 2015年5月25日 下午2:43:36    
     */
    @Override
    public VenusInfoDto findByID(String id)
    {
         List<VenusInfoDto> viDtoList = venuesInfoDao.findViInfoByUserID(id);
         VenusInfoDto viDto = null;
        if (!CollectionUtils.isEmpty(viDtoList) && viDtoList.size()>0)
        {
            viDto = new VenusInfoDto();
            for (VenusInfoDto result : viDtoList)
            {
                viDto.setNickName(result.getNickName());
                viDto.setMaxNum(result.getMaxNum());
                viDto.setId(result.getId());
                viDto.setChargeType(result.getChargeType());//0:免费1:收费
                //viDto.setImgUrl("------")
            }
        }
       
        return viDto;
    }

    private String process(String image) {
		if(StringUtils.isNotEmpty(image) && image.startsWith("/")) {
			image = image.substring(1);
		}
		return image;
	}

	@Override
	public String updateSpaceNum(String userId, boolean flag) {
		return venuesInfoDao.updateSpaceNum(userId,flag);
	}

}
