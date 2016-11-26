/**
 * 
 */
package com.juju.sport.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.base.dto.SportTypeDto;
import com.juju.sport.base.dto.WuweiUserDto;
import com.juju.sport.base.mapper.SportTypeMapper;
import com.juju.sport.base.mapper.WuweiUserMapper;
import com.juju.sport.base.pojo.SportType;
import com.juju.sport.base.pojo.SportTypeExample;
import com.juju.sport.base.pojo.WuweiUser;
import com.juju.sport.base.pojo.WuweiUserExample;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

/**		
 * <p>Title: WuweiUserDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Lawliet	
 * @date 2015年6月5日 上午10:43:42	
 * @version 1.0
 * <p>修改人：Lawliet</p>
 * <p>修改时间：2015年6月5日 上午10:43:42</p>
 * <p>修改备注：</p>
 */
@Repository
public class WuweiUserDao extends MyBatisBaseDao<SportType> 
{

   
  
    
    @Autowired
    private WuweiUserMapper mapper;
    
     /** 
     * (non-Javadoc)   
     * @see com.juju.sport.common.mybatis.MyBatisBaseDao#getMapper()   
     */
    @Override
    public Object getMapper()
    {
        // TODO 添加方法注释
        return null;
    }

    
    public List<WuweiUser> findBy(WuweiUserDto wuweiUserDto) {
        WuweiUserExample example = new WuweiUserExample();
        WuweiUserExample.Criteria criteria = example.createCriteria();
      //  criteria.andIdEqualTo(DataStatus.ENABLED);
       // example.setOrderByClause(" id ASC ");
        return mapper.selectByExample(example);
    }
    
    public List<WuweiUser> findAll(){
        WuweiUserExample example = new WuweiUserExample();
        WuweiUserExample.Criteria criteria = example.createCriteria();
      //  criteria.andIdEqualTo(DataStatus.ENABLED);
       // example.setOrderByClause(" id ASC ");
        return mapper.selectByExample(example);
    }
    
    public List<WuweiUser> findUserDetail(WuweiUserDto wuweiUserDto){
        WuweiUserExample example = new WuweiUserExample();
        WuweiUserExample.Criteria criteria = example.createCriteria();
        
        example.setName(wuweiUserDto.getName());
        example.setAge(wuweiUserDto.getAge());
        example.setAddress(wuweiUserDto.getAddress());
        return mapper.findUserDetail(example);
    }
    
}

