package com.juju.sport.stadium.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class VenuesPwdDto implements Serializable{
    @Getter
    @Setter 
    private String id;

    @Getter
    @Setter 
    private String sid;

    @Getter
    @Setter 
    private String pwd;

    @Getter
    @Setter 
    private Integer validity;

    @Getter
    @Setter 
    private Date createTime;

    @Getter
    @Setter 
    private Date lastUpdateTime;

    @Getter
    @Setter 
    private Integer stat;
    
    /**   
    * name 场馆名称
    */
    @Getter
    @Setter
    private String name;

    /**   
    * owner_account_id: 场馆ID 
    */
    @Getter
    @Setter
    private String ownerAccountId;

    
}