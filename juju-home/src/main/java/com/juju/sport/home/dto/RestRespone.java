package com.juju.sport.home.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: VenuesATMDto </p>
 * <p>Description: 显示场馆信息</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年5月21日 下午4:04:28	
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RestRespone<T> implements Serializable
{


    @Getter
    @Setter
    public String returnCode;
    
    @Getter
    @Setter
    public String describe;
    
    @Getter
    @Setter
    public T data;

}
