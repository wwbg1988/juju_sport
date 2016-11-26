package com.juju.sport.base.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ToString
public class PageData implements Serializable{
	
	public PageData(){
		
	}

	public PageData(String title, String image, String description){
		this.title = title;
		this.image = image;
		this.description = description;
	}
	
	@Setter
	@Getter
	private String id;
	
	@Setter
	@Getter
	private String title;
	
	@Setter
	@Getter
	private String image;
	
	@Setter
	@Getter
	private String description;

    @Setter
    @Getter
    private float score;

    @Getter
    @Setter
    private String date;

    @Getter
    @Setter
    private String href;

    @Getter
    @Setter
    private String node;
    
    @Getter
    @Setter
    private String accountId;
    
    @Getter
    @Setter
    private String venuesId;

    @Getter
    @Setter
    private List<String> subImage = new ArrayList<String>();

    @Getter
    @Setter
    private List<PageData> children = new ArrayList<PageData>();
	
}
