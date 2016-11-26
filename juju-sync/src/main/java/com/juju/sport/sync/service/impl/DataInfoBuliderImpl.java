package com.juju.sport.sync.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.util.StringUtils;
import com.juju.sport.sync.constants.OptType;
import com.juju.sport.sync.dao.DataSyncDao;
import com.juju.sport.sync.dao.jdbc.JdbcDateInfoDao;
import com.juju.sport.sync.model.DataInfo;
import com.juju.sport.sync.pojo.DataSync;
import com.juju.sport.sync.service.IDataInfoBulider;

@Service
public class DataInfoBuliderImpl implements IDataInfoBulider {
	
	protected static final Log logger = LogFactory.getLog(DataInfoBuliderImpl.class);
	
	@Autowired
	private JdbcDateInfoDao dateInfoDao;
	
	@Autowired
	private DataSyncDao syncDao;
	
	@Override
	public List<DataInfo> populateDataInfo(DataSync dbSync) {
		List<DataInfo> results = new ArrayList<DataInfo>();
		
		List<Map<String, Object>> dbInfos = dateInfoDao.findByDBSync(dbSync);
		if(CollectionUtils.isEmpty(dbInfos) && 
				(StringUtils.isEmpty(dbSync.getOptType()) 
						|| dbSync.getOptType().equals(OptType.Update.getActionName())
						|| dbSync.getOptType().equals(OptType.New.getActionName()))){
			//记录 无法找到对应表中数据的异常。
			logEmptyRecordError(dbSync, "无法找到对应表中数据");
			return null;
		}else if(CollectionUtils.isEmpty(dbInfos) && dbSync.getOptType().equals(OptType.Delete.getActionName())){
			//删除数据记录
			populateDeleteDataInfo(dbSync, results);
		}else{
			populateData(dbSync, results, dbInfos);
		}
		for(DataInfo info : results){
			info.setDbSyncId(dbSync.getId());
		}
		
		return results;
	}

	private void populateDeleteDataInfo(DataSync dbSync, List<DataInfo> results) {
		DataInfo info = new DataInfo();
		info.setOptType(OptType.Delete);
		info.setKeyName(dbSync.getKeyName());
		info.setKeyValue(dbSync.getKeyValue());
		info.setTableName(dbSync.getTableName());
		info.setColumnValue(dbSync.getKeyName(), dbSync.getKeyValue());		
		
		results.add(info);
	}
	
	private void populateData(DataSync dbSync, List<DataInfo> results,
			List<Map<String, Object>> dbInfos) {
		for(Map<String, Object> dbinfo : dbInfos){
			DataInfo info = new DataInfo();
			info.setOptType(OptType.getActionByName(dbSync.getOptType()));
			info.setTableName(dbSync.getTableName());
			info.setKeyName(dbSync.getKeyName());
			info.setKeyValue(dbSync.getKeyValue());
		
			for(String key : dbinfo.keySet()){
				info.setColumnValue(key, dbinfo.get(key));
			}			
			results.add(info);
		}
	}

	private void logEmptyRecordError(DataSync dbSync, String errorStr) {
		dbSync.setErrInfo(errorStr);
		syncDao.updateByPrimaryKeySelective(dbSync);	
	}
 
}
