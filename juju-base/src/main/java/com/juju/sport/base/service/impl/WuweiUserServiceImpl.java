/**
 * 
 */
package com.juju.sport.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.base.dao.SportTypeDao;
import com.juju.sport.base.dao.WuweiUserDao;
import com.juju.sport.base.dto.SportTypeDto;
import com.juju.sport.base.dto.WuweiUserDto;
import com.juju.sport.base.pojo.SportType;
import com.juju.sport.base.pojo.WuweiUser;
import com.juju.sport.base.service.IWuweiUserService;
import com.juju.sport.common.util.BeanUtils;


/**		
 * <p>Title: WuweiUserServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Lawliet	
 * @date 2015年6月5日 上午10:26:41	
 * @version 1.0
 * <p>修改人：Lawliet</p>
 * <p>修改时间：2015年6月5日 上午10:26:41</p>
 * <p>修改备注：</p>
 */
@Service
public class WuweiUserServiceImpl  implements IWuweiUserService
{

    @Autowired
    private WuweiUserDao wuweiUserDao;
    
     /** 
     * (non-Javadoc)   
     * @see com.juju.sport.base.service.IWuweiUserService#findBy(com.juju.sport.base.dto.WuweiUserDto)   
     */
    @Override
    public List<WuweiUserDto> findBy(WuweiUserDto wuweiUserDto)
    {
        List<WuweiUser> list = wuweiUserDao.findBy(wuweiUserDto);
        List<WuweiUserDto> results = BeanUtils.createBeanListByTarget(list, WuweiUserDto.class);
        return results;
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.juju.sport.base.service.IWuweiUserService#findall()   
     */
    @Override
    public List<WuweiUserDto> findall()
    {
        List<WuweiUser> list = wuweiUserDao.findAll();
        List<WuweiUserDto> results = BeanUtils.createBeanListByTarget(list, WuweiUserDto.class);
        return results;
    }


    
     /** 
     * (non-Javadoc)   
     * @see com.juju.sport.base.service.IWuweiUserService#findUserDetail(com.juju.sport.base.dto.WuweiUserDto)   
     */
    @Override
    public List<WuweiUserDto> findUserDetail(WuweiUserDto wuweiUserDto)
    {
        List<WuweiUser> list = wuweiUserDao.findUserDetail(wuweiUserDto);
        List<WuweiUserDto> results = BeanUtils.createBeanListByTarget(list, WuweiUserDto.class);
        return results;
    }

}

