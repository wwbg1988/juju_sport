package com.juju.sport.logger.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rkzhang on 14-9-20.
 */
@ToString
public class SystemExceptionDto implements Serializable{
	
	private static final long serialVersionUID = 9152230554876562365L;

	public static final String REDIS_KEY = "logger:SystemExceptionDto";

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String errorMessage;

    @Getter
    @Setter
    private String errorStackTrace;

    @Getter
    @Setter
    private Date createTime;
}
