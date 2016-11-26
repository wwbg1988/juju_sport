package com.juju.sport.stadium.dto;
import lombok.Setter;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

public class StadiumDto implements Serializable{
		@Getter
	    @Setter	    
	    private String id;

		@Getter
	    @Setter	
	    private String userId;

		@Getter
	    @Setter	
	    private Integer provinceid;

		@Getter
	    @Setter	
	    private Integer cityid;

		@Getter
	    @Setter	
	    private Integer countryid;

		@Getter
	    @Setter	
	    private String address;

		@Getter
	    @Setter	
	    private String contacts;

		@Getter
	    @Setter	
	    private String mobileNo;

		@Getter
	    @Setter	
	    private String descs;

		@Getter
	    @Setter	
	    private Integer chargeType;

		@Getter
	    @Setter	
	    private String venueType;

		@Getter
	    @Setter	
	    private String otherServices;

		@Getter
	    @Setter	
	    private String email;

		@Getter
	    @Setter	
	    private String nickName;

		@Getter
	    @Setter	
	    private Integer userLevel;

		@Getter
	    @Setter	
	    private Integer userScore;
		
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
