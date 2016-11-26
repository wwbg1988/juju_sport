package com.juju.sport.sync.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.DateRegExp;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.sync.mapper.DataInfoMapper;
import com.juju.sport.sync.model.DataInfo;
import com.juju.sport.sync.pojo.DataSync;


@Repository
public class DataInfoDao extends MyBatisBaseDao<DataInfo> {
	
	protected static final Log logger = LogFactory.getLog(DataInfoDao.class);

	@Autowired
	private DataInfoMapper mapper;
	
	public List<Map<String, Object>> findByDBSync(DataSync sync){
		return mapper.findByDBSync(sync);
	}
	
	public int update(DataInfo dataInfo){
		Assert.notNull(dataInfo);
		Assert.isTrue(StringUtils.isNotEmpty(dataInfo.getKeyName()));
		Assert.isTrue(StringUtils.isNotEmpty(dataInfo.getKeyValue()));
		return mapper.update(populateUpdateSql(dataInfo));
	}
	
	public int delete(DataInfo dataInfo){
		Assert.notNull(dataInfo);
		Assert.isTrue(StringUtils.isNotEmpty(dataInfo.getKeyName()));
		Assert.isTrue(StringUtils.isNotEmpty(dataInfo.getKeyValue()));
		return mapper.delete(dataInfo);
	}
	
	public int insert(DataInfo dataInfo){
		Assert.notNull(dataInfo);
		Assert.isTrue(StringUtils.isNotEmpty(dataInfo.getKeyName()));
		Assert.isTrue(StringUtils.isNotEmpty(dataInfo.getKeyValue()));
		return mapper.save(populateInsertSql(dataInfo));
	}
	
	public boolean isExist(DataInfo dataInfo){
		return mapper.count(dataInfo) > 0 ? true : false;
	}

	//-----------------------
	
	private String populateInsertSql(DataInfo dataInfo) {
		StringBuilder insertSql = new StringBuilder();
		StringBuilder columns = new StringBuilder();
		StringBuilder values = new StringBuilder();
		insertSql.append("insert into ").append(dataInfo.getTableName()).append("( ");
		
		int i = 0;
		for(String column : dataInfo.getColumnNames()){
			if(i > 0){
				columns.append(",");
				values.append(",");
			}
			columns.append(column);
			Object val = dataInfo.getColumnValue(column);
			if(val != null && val instanceof Date){
				Date dv = (Date)val;
				val = DateRegExp.yyyy_MM_dd_HH_mm_ss.format(dv);
			}
			values.append(val == null || "null".equals(val) ? "null" : "'" + val.toString() + "'");
			i++;
		}
		insertSql.append(columns.toString()).append(" ) values( ");
		insertSql.append(values.toString()).append(" )");
		logger.info("insert sql --- " + insertSql.toString());
		return insertSql.toString();
	}
	
	private String populateUpdateSql(DataInfo dataInfo) {
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("update ").append(dataInfo.getTableName()).append(" set ");
		
		int i = 0;
		for(String column : dataInfo.getColumnNames()){
			if(column.equals(dataInfo.getKeyName())){
				continue;
			}
			if(i > 0){
				updateSql.append(",");
			}
			Object val = dataInfo.getColumnValue(column);
			if(val != null && val instanceof Date){
				Date dv = (Date)val;
				val = DateRegExp.yyyy_MM_dd_HH_mm_ss.format(dv);
			}
			updateSql.append(column).append("=");
			updateSql.append(val == null || "null".equals(val) ? "null" : "'" + val.toString() + "'");
			i++;
		}
		updateSql.append(" where ").append(dataInfo.getKeyName()).append("='").append(dataInfo.getKeyValue()).append("'");
		logger.info("update sql --- " + updateSql.toString());
		return updateSql.toString();
	}

	@Override
	public Object getMapper() {
		return mapper;
	}

}
