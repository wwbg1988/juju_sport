package com.juju.sport.common.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class PageResult<T> {
	
	public PageResult(){
		
	}
	
	public PageResult(long total, int currPage, int pageSize, List<T> results){
		this.total = total;
		this.pageSize = pageSize;
		this.currPage = currPage;
	}
	
	public PageResult(long total, int pageSize, List<T> results){
		this.pageSize = pageSize;
		this.total = total;
		currPage = 1;
	}
	
	public PageResult(PageQuery query,  List<T> results){
		this.total = query.getTotal();
		this.pageSize = query.getPageSize();
		this.currPage = query.getCurrPage();
		this.results = results;
	}

	@Setter
	@Getter
	private long total;
	
	@Setter
	@Getter
	private int pageSize;
	
	@Setter
	@Getter
	private int currPage = 1;
	
	@Setter
	@Getter
	private List<T> results;
	
	public long getStartNum(){
		return  pageSize * (currPage - 1);
	}
}
