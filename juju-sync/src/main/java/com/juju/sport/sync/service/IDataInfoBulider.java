package com.juju.sport.sync.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.sync.model.DataInfo;
import com.juju.sport.sync.pojo.DataSync;

@Service
public interface IDataInfoBulider {

	List<DataInfo> populateDataInfo(DataSync dbSync);
}
