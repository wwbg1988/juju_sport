package com.juju.sport.sync.service;

import java.util.List;

import com.juju.sport.sync.model.DataInfo;


public interface IDataSyncService {

	List<DataInfo> findNeedSendData(Integer maxLength);
	
	List<DataInfo> findNeedReSendData(Integer start, Integer maxRecord);
	
	int countNeedReSendData();
	
	void syncData(DataInfo data);
	
}
