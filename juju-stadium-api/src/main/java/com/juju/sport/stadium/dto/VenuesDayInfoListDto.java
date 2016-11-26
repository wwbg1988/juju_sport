package com.juju.sport.stadium.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class VenuesDayInfoListDto implements Serializable
{
            
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String venuesid;

    @Getter
    @Setter
    private String cardNo;

    @Getter
    @Setter
    private String daytime;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date lastUpdateTime;
    
    @Getter
    @Setter
    private Integer useType;

    @Getter
    @Setter
    private Integer stat;

}
