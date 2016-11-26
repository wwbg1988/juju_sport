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
public class MedalDto implements Serializable{

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private Integer type;
	
	@Getter
	@Setter
	private String typeName;

	@Getter
	@Setter
    private Integer level;

	@Getter
	@Setter
    private String name;

	@Getter
	@Setter
    private String logo;

	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Integer stat;
	
	public enum MedalType	//定义枚举列出新闻
    {
        USER(0, "用户勋章"),
        
        TEAM(1, "战队勋章");	

     // 定义私有变量
        private Integer nCode ;
        
        private String description;
        
        private static Map<Integer, String> teamPositionMap = new HashMap<Integer, String>();

        static{
        	for(MedalType status : MedalType.values()){
        		teamPositionMap.put(status.getInfoType(), status.getDescription());
        	}
        }

        // 构造函数，枚举类型只能为私有
        private MedalType(Integer _nCode, String _description) {
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
