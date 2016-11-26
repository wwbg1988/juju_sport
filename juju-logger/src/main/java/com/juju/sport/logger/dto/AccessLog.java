package com.juju.sport.logger.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class AccessLog {
	
	@Getter
	@Setter
	private String accessTime;
	
	@Getter
	@Setter
	private String remoteIp;

	@Getter
	@Setter
	private String host;
	
	@Getter
	@Setter
	private String requestURL;
	
	@Getter
	@Setter
	private String requestURI;

	@Getter
	@Setter
	private String contextPath;
	
	@Getter
	@Setter
	private String contentType;
	
	@Getter
	@Setter
	private Integer contextLength;
	
	@Getter
	@Setter
	private String method;
	
	@Getter
	@Setter
	private String controllerName;
	
	@Getter
	@Setter
	private String methodName;
	
	@Getter
	@Setter
	private Map<String, String[]> parameterMap;
	
}
