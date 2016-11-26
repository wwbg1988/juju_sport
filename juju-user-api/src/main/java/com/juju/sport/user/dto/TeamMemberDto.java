package com.juju.sport.user.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.juju.sport.common.dto.Pair;
import com.juju.sport.common.model.ListResult;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class TeamMemberDto implements Serializable{

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String pic;

	@Getter
	@Setter
    private String teamId;

	@Getter
	@Setter
    private String memberNum;

	@Getter
	@Setter
    private String chineseName;

	@Getter
	@Setter
    private String englishName;

	@Getter
	@Setter
    private String nickName;

	@Getter
	@Setter
    private Date dateOfBorn;

	@Getter
	@Setter
    private Boolean gender;

	@Getter
	@Setter
    private String eihnic;

	@Getter
	@Setter
    private String nativePlace;

	@Getter
	@Setter
    private String company;

	@Getter
	@Setter
    private Integer provinceId;
	
	@Getter
	@Setter
	private String provinceName;

	@Getter
	@Setter
    private Integer cityId;
	
	@Getter
	@Setter
	private String cityName;

	@Getter
	@Setter
    private Integer countryId;
	
	@Getter
	@Setter
	private String countryName;

	@Getter
	@Setter
    private Float height;

	@Getter
	@Setter
    private Float weight;

	@Getter
	@Setter
    private String telephone;

	@Getter
	@Setter
    private String mobile;

	@Getter
	@Setter
    private String email;

	@Getter
	@Setter
    private String documentNo;

	@Getter
	@Setter
    private String position;

	@Getter
	@Setter
    private Integer stat;

	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Integer teamPosition;
	
	@Getter
	@Setter
	private String teamPositionName;
	
	@Getter
	@Setter
	private String userAccount;
	
	public enum TeamPosition	//定义枚举列出新闻
    {
        NORMAL(0, "普通队员"),
        
        CAPTIAN(1, "队长");	

     // 定义私有变量
        private Integer nCode ;
        
        private String description;
        
        private static Map<Integer, String> teamPositionMap = new HashMap<Integer, String>();

        static{
        	for(TeamPosition status : TeamPosition.values()){
        		teamPositionMap.put(status.getInfoType(), status.getDescription());
        	}
        }

        // 构造函数，枚举类型只能为私有
        private TeamPosition(Integer _nCode, String _description) {
            this.nCode = _nCode;
            this.description = _description;
        }

        public Integer getInfoType() {
            return this.nCode;
        }

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}    
		
		public static String getDescription(Integer code) {
			return teamPositionMap.get(code);
		}
		
		public static ListResult<Pair<Integer, String>> getComboData() {
			List<Pair<Integer, String>> results = new ArrayList<Pair<Integer, String>>();
			
			for(Integer key : teamPositionMap.keySet()){
				Pair<Integer, String> pair = new Pair<Integer, String>(key, teamPositionMap.get(key));
				results.add(pair);
			}
			return new ListResult<Pair<Integer, String>>(results);
			
		}
            
    }
}
