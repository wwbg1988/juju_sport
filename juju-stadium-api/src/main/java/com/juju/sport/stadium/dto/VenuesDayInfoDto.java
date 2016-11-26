package com.juju.sport.stadium.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class VenuesDayInfoDto implements Serializable
{
    @Getter
    @Setter
    private String venuesid;

    @Getter
    @Setter
    private String daytime;

    /**   
    * headcount: 场馆最大人数   
    */
    @Getter
    @Setter
    private Integer headcount;

    /**   
    * onliner: 场馆当前人数
    */
    @Getter
    @Setter
    private Integer onliner;

    /**   
    * state: 场馆状态1：绿色2：橙色3：红色
    */
    @Getter
    @Setter
    private Integer state;

    /**   
    * name 场馆名称
    */
    @Getter
    @Setter
    private String name;

    /**
     * 场馆轮播图
     */
    //    @Getter
    //    @Setter
    //    private List<String> imgurl;

    /**   
    * spaceprice: 场馆对应时段的场地价格   
    */
    //    @Getter
    //    @Setter
    //    private Map<String, String> spaceprice;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Integer stat;

}
