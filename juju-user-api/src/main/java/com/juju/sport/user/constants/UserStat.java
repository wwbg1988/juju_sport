package com.juju.sport.user.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import com.juju.sport.common.dto.Pair;
import com.juju.sport.common.model.ListResult;

public enum UserStat {

	Disable(0, "禁用"),
	
	Enable(1, "正常");
	
	private static Map<Integer, String> jobMap = new HashMap<Integer, String>();

    static{
    	for(UserStat status : UserStat.values()){
    		jobMap.put(status.getValue(), status.getDescription());
    	}
    }
	
	@Getter
	@Setter
	private Integer value;
	
	@Getter
	@Setter
	private String description;
	
	
	UserStat(Integer value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public static String getDesc(Integer value) {
		return jobMap.get(value);
	}
	
	public static ListResult<Pair<Integer, String>> getComboData() {
		List<Pair<Integer, String>> results = new ArrayList<Pair<Integer, String>>();
		
		for(Integer key : jobMap.keySet()){
			Pair<Integer, String> pair = new Pair<Integer, String>(key, jobMap.get(key));
			results.add(pair);
		}
		return new ListResult<Pair<Integer, String>>(results);
		
	}
}
