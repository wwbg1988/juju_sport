package com.juju.sport.stadium.service;

import com.juju.sport.stadium.dto.VenuesDayInfoDto;
import com.juju.sport.stadium.dto.VenuesDayInfoListDto;
import com.juju.sport.stadium.dto.VenuesPwdDto;



public interface VenuesApiService {
    
    /**     
     * queryVenuesPwd：查询设备密码信息
     * @param sid 设备编号
     * @return VenuesPwdDto
     * @author Vincent
     * @date 2015年5月22日 下午5:42:15	 
     */
    public VenuesPwdDto queryVenuesPwd(String sid);
 
    
    /**     
     * queryVenuesInfo：查询场馆信息
     * @param dayTime 外围设备ID
     * @return VenuesDayInfoDto
     * @author Vincent
     * @date 2015年5月22日 下午5:42:17	 
     */
    public VenuesDayInfoDto queryVenuesInfo(String sid);
    
    
    
    /**     
     * updateOnliner：一句话描述方法功能
     * @param sid 外围设备ID
     * @param addFlag
     * @return 影响记录
     * @author Vincent
     * @date 2015年5月26日 下午2:56:48	 
     */
    public int  updateOnliner(String sid,boolean addFlag);
    
    
    /**     
     * insertTouchCardLog：记录刷卡入场日志
     * @param dto
     * @return int  影响记录
     * @author Vincent
     * @date 2015年5月26日 下午5:19:57	 
     */
    public int insertTouchCardLog(VenuesDayInfoListDto dto);
	
}
