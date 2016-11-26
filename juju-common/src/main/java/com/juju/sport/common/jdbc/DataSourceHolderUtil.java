package com.juju.sport.common.jdbc;

public class DataSourceHolderUtil {

	public static void setToMaster() {
		DynamicDataSourceHolder.putDataSource("master");
	}
	
	public static void setToSlave() {
		DynamicDataSourceHolder.putDataSource("slave");
	}
}
