/**
 * 
 */
package com.juju.sport.stadium.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.stadium.dao.VenuesDayInfoDao;
import com.juju.sport.stadium.dao.VenuesPwdDao;
import com.juju.sport.stadium.dto.VenuesDayInfoDto;
import com.juju.sport.stadium.dto.VenuesDayInfoListDto;
import com.juju.sport.stadium.dto.VenuesPwdDto;
import com.juju.sport.stadium.pojo.VenuesDayInfo;
import com.juju.sport.stadium.pojo.VenuesPwd;
import com.juju.sport.stadium.service.IVenuesApiService;

/**		
 * <p>Title: VenuesApiServiceImpl </p>
 * <p>Description: 场馆 API接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Alex	
 * @date 2015年5月22日 上午9:58:28	
 * @version 1.0
 */
@Service("iVenuesApiService")
public class VenuesApiServiceImpl implements IVenuesApiService
{

    @Autowired
    VenuesPwdDao venuesPwdDao;

    @Autowired
    VenuesDayInfoDao venuesDayInfoDao;

    /** 
    * (non-Javadoc)   
    * @see com.juju.sport.stadium.service.IVenuesApiService#queryVenuesPwd(java.lang.String)   
    */
    @Override   
    public VenuesPwdDto queryVenuesPwd(String sid)
    {
        List<VenuesPwd> list = venuesPwdDao.findVenuesPwd(sid);
        List<VenuesPwdDto> results = BeanUtils.createBeanListByTarget(list, VenuesPwdDto.class);

        VenuesPwdDto venuesPwd = null;
        if (null != results && results.size() > 0)
        {
            venuesPwd = results.get(0);
        }
        return venuesPwd;

    }

    /** 
    * (non-Javadoc)   
    * @see com.juju.sport.stadium.service.IVenuesApiService#queryVenuesInfo(java.lang.String)   
    */
    @Override
    public VenuesDayInfoDto queryVenuesInfo(String sid)
    {

        VenuesDayInfoDto vdiDto = new VenuesDayInfoDto();
        List<VenuesDayInfo> list = venuesDayInfoDao.findVenuesPwd(sid);
        VenuesDayInfo vdInfo =null;
        if (!CollectionUtils.isEmpty(list))
        {
            vdInfo = list.get(0);
            vdiDto.setDaytime(vdInfo.getDaytime());
            vdiDto.setState(vdInfo.getState());
            vdiDto.setOnliner(vdInfo.getOnliner());
            vdiDto.setCreateTime(vdInfo.getCreateTime());
           
            /**场馆信息
            vdiDto.setHeadcount(1);
            vdiDto.setImgurl(null);
            vdiDto.setSpaceprice(null);
            **/
            return vdiDto;
        }else{
            return vdiDto;
        }       

    }
    
    @Override
    public int updateOnliner(String sid,boolean addFlag){
        
        return venuesDayInfoDao.updateOnliner(sid,addFlag);
        
        
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.juju.sport.stadium.service.IVenuesApiService#insertTouchCardLog(com.juju.sport.stadium.dto.VenuesDayInfoListDto)   
     */
    @Override
    public int insertTouchCardLog(VenuesDayInfoListDto dto)
    {
        return venuesDayInfoDao.insertTouchCardLog(dto);
    }
    
    /** 
     * (non-Javadoc)   
     * @see com.juju.sport.stadium.service.IVenuesApiService#insertTouchCardLog(com.juju.sport.stadium.dto.VenuesDayInfoListDto)   
     */
    @Override
    public VenuesDayInfoListDto findLogByCardID(String cardno)
    {
        return venuesDayInfoDao.findLogByCardID(cardno);
    }
    

}
