package com.juju.sport.home.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
public class VenuesATMDto implements Serializable
{

    /**   
    * headcount: 场馆容纳人数      
    */
    @Setter
    @Getter
    private int headcount = 1;

    /**   
    * onliner: 场馆当前人数
    */
    @Setter
    @Getter
    private int onliner;

    /**   
    * state: 场馆状态1：绿色2：橙色3：红色
    */
    @Getter
    @Setter
    private int state;

    /**
     * 场馆轮播图
     */
    @Getter
    @Setter
    private List<String> imgurl;

    /**   
    * spaceprice: 场馆对应时段的场地价格   
    */
    @Getter
    @Setter
    private Map<String, String> spaceprice;

    /**   
    * returnCode: 返回码 
    */
    @Getter
    @Setter
    private String returnCode;

}
