package com.juju.sport.base.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.juju.sport.common.dto.Pair;
import com.juju.sport.common.model.ListResult;

import lombok.Getter;
import lombok.Setter;

public enum Job {
	
	Null(-1, "无"),
	
	Student(0, "学生"),
	
	Worker(1, "上班族");
	
	 private static Map<Integer, String> jobMap = new HashMap<Integer, String>();

    static{
    	for(Job status : Job.values()){
    		jobMap.put(status.getValue(), status.getDescription());
    	}
    }
	
	@Getter
	@Setter
	private Integer value;
	
	@Getter
	@Setter
	private String description;
	
	
	Job(Integer value, String description) {
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
