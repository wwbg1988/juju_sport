package com.juju.sport.user.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.juju.sport.base.dto.ServiceTypeDto;
import com.juju.sport.base.dto.SportTypeDto;

@ToString
public class VenusInfoDto implements Serializable{

	@Setter
	@Getter
	private String id;
	
	@Setter
	@Getter
	private String userAccountId;
	
	@Setter
	@Getter
	private String userAccount;
	
	@Setter
	@Getter
	private Integer provinceid;
	
	@Setter
	@Getter
	private String provinceName;
	
	@Setter
	@Getter
	private Integer cityid;
	
	@Setter
	@Getter
	private String cityName;
	
	@Setter
	@Getter
	private Integer countryid;
	
	@Setter
	@Getter
	private String countryName;
	
	@Setter
	@Getter
	private String address;
	
	@Setter
	@Getter
	private String contacts;
	
	@Setter
	@Getter
	private String mobileNo;
	
	@Setter
	@Getter
	private String descs;
	
	@Setter
	@Getter
	private Integer chargeType;
	
	@Setter
	@Getter
	private String chargeTypeName;
	
	//0 : 免费  1:收费   
	public enum ChargeType {
        FREE(0, "免费"),
        CHARGE(1, "收费");
        
        private int nCode;

        private String description;

        // 构造函数，枚举类型只能为私有
        private ChargeType(Integer _nCode, String _description) {
            this.nCode = _nCode;
            this.description = _description;
        }
        
        public int getInfoType() {
            return this.nCode;
        }

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}    
            
    }
	
	@Setter
	@Getter
	private String venueType;
	
	@Setter
	@Getter
	private String venueTypeNames;
	
	@Setter
	@Getter
	private String otherServices;
	
	@Setter
	@Getter
	private String email;
	
	@Setter
	@Getter
	private String nickName;    //场馆名称
	
	@Setter
	@Getter
	private Integer userLevel;
	
	@Setter
	@Getter
	private Integer userScore;
	
	@Setter
	@Getter
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createTime;

	@Setter
	@Getter
	private Date lastUpdateTime;
	
	@Setter
	@Getter
	private Integer stat;
	
	@Getter
	@Setter
	private String venueImg;
	
	@Getter
	@Setter
	private Integer maxNum;
	
	@Getter
	@Setter
	private List<SportTypeDto> sportTypeList;

	@Getter
	@Setter
	private List<ServiceTypeDto> serviceTypeList;
	
	@Getter
	@Setter
	private String teamType;
	
	@Getter
	@Setter
	private String isFalse; 
	
	@Getter
	@Setter
	private long startNum;
	
	@Getter
	@Setter
	private int pageSize;
	
	@Getter
	@Setter
	private Double xLocation;;
	
	@Getter
	@Setter
	private Double yLocation;
	
	@Setter
	@Getter
	private Integer spaceNum;
	

}
