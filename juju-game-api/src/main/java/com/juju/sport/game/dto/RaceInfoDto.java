package com.juju.sport.game.dto;

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

public class RaceInfoDto implements Serializable{

	public enum InfoType	//定义枚举列出新闻
    {
        RACE(1, "赛事新闻 "),
        
        NEWS(2, "热点新闻");	//1 为赛事新闻 		2是热点新闻

     // 定义私有变量
        private Integer nCode ;
        
        private String description;
        
        private static Map<Integer, String> infoTypeMap = new HashMap<Integer, String>();

        static{
        	for(InfoType status : InfoType.values()){
        		infoTypeMap.put(status.getInfoType(), status.getDescription());
        	}
        }

        // 构造函数，枚举类型只能为私有
        private InfoType(Integer _nCode, String _description) {
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
		
		public static ListResult<Pair<Integer, String>> getComboData() {
			List<Pair<Integer, String>> results = new ArrayList<Pair<Integer, String>>();
			
			for(Integer key : infoTypeMap.keySet()){
				Pair<Integer, String> pair = new Pair<Integer, String>(key, infoTypeMap.get(key));
				results.add(pair);
			}
			return new ListResult<Pair<Integer, String>>(results);
			
		}
            
    }
	
	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String sportTypeId;
	
	@Getter
	@Setter
	private Integer infoType;
	
	@Getter
	@Setter
	private String sportName;

	@Getter
	@Setter
    private String title;

	@Getter
	@Setter
    private String pic;

	@Getter
	@Setter
    private String organizers;

	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private String showTime;
	
	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Integer stat;

	@Getter
	@Setter
    private String context;
	
	
}
