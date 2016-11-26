package com.juju.sport.sync.handler;

import com.juju.sport.sync.model.DataInfo;

public interface IDataHandler {
	
	void create(DataInfo dataInfo);
	
	void update(DataInfo dataInfo);
	
	void delete(DataInfo dataInfo);
	
}
