package com.juju.sport.logger.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SysLoggerUtil {
	
	private static SimpleDateFormat fmate = new SimpleDateFormat("yyyyMMdd");

	public static String getTodayBusinessExceptionLoggerTableName() {
		return "wd_business_error_log_" + fmate.format(new Date());
	}
	
	public static String getTodaySystemExceptionLoggerTableName() {
		return "wd_system_error_log_" + fmate.format(new Date());
	}
}
