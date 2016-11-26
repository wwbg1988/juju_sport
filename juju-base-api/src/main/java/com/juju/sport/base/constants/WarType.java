package com.juju.sport.base.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.juju.sport.common.dto.Pair;
import com.juju.sport.common.model.ListResult;

import lombok.Getter;
import lombok.Setter;

/**
 * 对战类型
 * @author rkzhang
 */
public enum WarType {
	
	Personal(1, "个人对战"),
	
	Team(2, "团队对战");
	
	 private static Map<Integer, String> warTypeMap = new HashMap<Integer, String>();

    static{
    	for(WarType status : WarType.values()){
    		warTypeMap.put(status.getValue(), status.getDescription());
    	}
    }
	
	@Getter
	@Setter
	private Integer value;
	
	@Getter
	@Setter
	private String description;
	
	
	WarType(Integer value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public static String getTypeDesc(Integer value) {
		return warTypeMap.get(value);
	}
	
	public static ListResult<Pair<Integer, String>> getComboData() {
		List<Pair<Integer, String>> results = new ArrayList<Pair<Integer, String>>();
		
		for(Integer key : warTypeMap.keySet()){
			Pair<Integer, String> pair = new Pair<Integer, String>(key, warTypeMap.get(key));
			results.add(pair);
		}
		return new ListResult<Pair<Integer, String>>(results);
		
	}
}
