/**
 * 
 */
package com.juju.sport.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.base.dto.SportTypeDto;
import com.juju.sport.base.dto.WuweiUserDto;


@Service
public interface IWuweiUserService
{
    
    
    public List<WuweiUserDto> findBy(WuweiUserDto wuweiUserDto);
    
    public List<WuweiUserDto> findall();
    
    public List<WuweiUserDto> findUserDetail(WuweiUserDto wuweiUserDto);
    
   
}

