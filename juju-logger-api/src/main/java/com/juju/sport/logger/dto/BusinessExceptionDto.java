package com.juju.sport.logger.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

import com.juju.sport.common.exception.BusinessException;
import com.juju.sport.common.util.UUIDGenerator;

/**
 * Created by rkzhang on 14-9-20.
 */
@ToString
public class BusinessExceptionDto implements Serializable{
	
	private static final long serialVersionUID = 7179127717232115242L;

	public static final String REDIS_KEY = "logger:BusinessExceptionDto";

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String errorCode;

    @Getter
    @Setter
    private String errorMessage;

    @Getter
    @Setter
    private String errorStackTrace;

    @Getter
    @Setter
    private String requestUrl;

    @Getter
    @Setter
    private String requestParams;

    @Getter
    @Setter
    private String loginUserAccount;

    @Getter
    @Setter
    private Date createTime;
    
    public static BusinessExceptionDto createBusinessException(BusinessException ex){
    	BusinessExceptionDto dto = new BusinessExceptionDto();
    	dto.setId(UUIDGenerator.getUUID());
    	dto.setCreateTime(new Date());
    	dto.setErrorCode(ex.getDiscription().getCode());
    	dto.setErrorMessage(ex.getDiscription().getMessage());
    	return dto;
    }
}
