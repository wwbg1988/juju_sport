package com.juju.sport.common.model;

import java.io.Serializable;

import com.juju.sport.common.constants.DataStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Response<T> implements Serializable {

	public Response(){
		
	}
	
	public Response(int status, String message,T data){
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	@Getter
	@Setter
	public int status = DataStatus.HTTP_SUCCESS;
	
	@Getter
	@Setter
	public String message;
	
	@Getter
	@Setter
	public T data;
	
}
