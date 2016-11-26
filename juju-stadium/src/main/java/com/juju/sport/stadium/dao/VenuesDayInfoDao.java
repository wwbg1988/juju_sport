package com.juju.sport.stadium.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.stadium.dto.VenuesDayInfoListDto;
import com.juju.sport.stadium.mapper.VenuesDayInfoListMapper;
import com.juju.sport.stadium.mapper.VenuesDayInfoMapper;
import com.juju.sport.stadium.pojo.VenuesDayInfo;
import com.juju.sport.stadium.pojo.VenuesDayInfoExample;
import com.juju.sport.stadium.pojo.VenuesDayInfoList;
import com.juju.sport.stadium.pojo.VenuesDayInfoListExample;

@Repository
public class VenuesDayInfoDao extends MyBatisBaseDao<VenuesDayInfo>
{
    @Getter
    @Autowired
    private VenuesDayInfoMapper mapper;

    @Getter
    @Autowired
    private VenuesDayInfoListMapper venuesDayInfoListMapper;

    /**     
     * findVenuesPwd：查询当日场地信息
     * @param sid 场馆ID
     * @return VenuesDayInfo 场馆信息
     * @author Vincent
     * @date 2015年5月25日 下午1:35:41	 
     */
    public List<VenuesDayInfo> findVenuesPwd(String sid)
    {
        String ymd = DateUtils.format(new Date(), DateUtils.YMD);
        VenuesDayInfoExample example = new VenuesDayInfoExample();
        VenuesDayInfoExample.Criteria criteria = example.createCriteria();
        criteria.andVenuesidEqualTo(sid);
        criteria.andDaytimeEqualTo(ymd);

        List<VenuesDayInfo> vdInfo = mapper.selectByExample(example);
        if (vdInfo != null && vdInfo.size() > 0)
        {
            return mapper.selectByExample(example);

        }
        else
        {
            VenuesDayInfo defaultInfo = new VenuesDayInfo();
            defaultInfo.setVenuesid(sid);
            defaultInfo.setDaytime(ymd);
            defaultInfo.setLastUpdateTime(new Date());
            defaultInfo.setCreateTime(new Date());
            defaultInfo.setState(1);
            defaultInfo.setOnliner(0);
            defaultInfo.setStat(DataStatus.ENABLED);
            vdInfo.add(defaultInfo);
            mapper.insert(defaultInfo);
            return vdInfo;
        }

    }

    /**     
     * updateOnliner：入场人数加一
     * @param sid 设备号
     * @param addFlag 是否增加 
     * @author Vincent
     * @date 2015年5月26日 下午2:43:33	 
     */
    public int updateOnliner(String sid, boolean addFlag)
    {
        String ymd = DateUtils.format(new Date(), DateUtils.YMD);
        VenuesDayInfoExample example = new VenuesDayInfoExample();
        VenuesDayInfoExample.Criteria criteria = example.createCriteria();
        criteria.andVenuesidEqualTo(sid);
        criteria.andDaytimeEqualTo(ymd);

        List<VenuesDayInfo> venuesList = mapper.selectByExample(example);

        if (!CollectionUtils.isEmpty(venuesList))
        {
            VenuesDayInfo vdinfo = venuesList.get(0);
            if (null != vdinfo.getOnliner())
            {
                VenuesDayInfoExample updateExample = new VenuesDayInfoExample();
                VenuesDayInfoExample.Criteria updateCriteria = updateExample.createCriteria();
                updateCriteria.andVenuesidEqualTo(sid);
                updateCriteria.andDaytimeEqualTo(ymd);
                //入场人数加1
                if (addFlag)
                {
                    vdinfo.setOnliner(vdinfo.getOnliner() + 1);
                }
                else
                {
                    if (vdinfo.getOnliner() > 0)
                    {
                        vdinfo.setOnliner(vdinfo.getOnliner() - 1);
                    }
                }
                return mapper.updateByExample(vdinfo, updateExample);

            }
        }
        return 0;
    }

    /**     
     * insertTouchCardLog：记录数卡入场日志
     * @param sid 设备号
     * @param addFlag 是否增加 
     * @author Vincent
     * @date 2015年5月26日 下午2:43:33    
     */
    public int insertTouchCardLog(VenuesDayInfoListDto dto)
    {

        VenuesDayInfoList venuesDayInfo = BeanUtils.createBeanByTarget(dto, VenuesDayInfoList.class);
        return venuesDayInfoListMapper.insert(venuesDayInfo);

    }

    /**     
     * findLogByCardID：查询刷卡入场日志记录
     * @param cardno  卡ID
     * @return  刷卡日志记录
     * @author Vincent
     * @date 2015年5月27日 上午9:30:48	 
     */
    public VenuesDayInfoListDto findLogByCardID(String cardno)
    {
        String ymd = DateUtils.format(new Date(), DateUtils.YMD_DASH);
        VenuesDayInfoListExample example = new VenuesDayInfoListExample();
        VenuesDayInfoListExample.Criteria criteria = example.createCriteria();
        criteria.andCardNoEqualTo(cardno);
        criteria.andDaytimeEqualTo(ymd);
        example.setOrderByClause("create_time");
        List<VenuesDayInfoList> logList = venuesDayInfoListMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(logList) && logList.size()>0)
        {
            VenuesDayInfoList venuesDayInfoLis = new VenuesDayInfoList();
            venuesDayInfoLis=logList.get(logList.size()-1);
            VenuesDayInfoListDto venuesDayLogDto = BeanUtils.createBeanByTarget(venuesDayInfoLis, VenuesDayInfoListDto.class);
            
            return venuesDayLogDto;
        }
        else
        {
            return null;
        }
    }

}
