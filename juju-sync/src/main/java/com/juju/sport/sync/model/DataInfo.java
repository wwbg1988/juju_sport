package com.juju.sport.sync.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.ToString;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juju.sport.common.exception.SystemException;
import com.juju.sport.common.util.DateRegExp;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.sync.constants.OptType;

@ToString
public class DataInfo {
	
	protected static final Log logger = LogFactory.getLog(DataInfo.class);
	
	private Integer dbSyncId;

	private String tableName;
	
	private String keyName;
	
	private String keyValue;
	
	private OptType optType;
	
	private String errorInfo;
	
	private boolean isNotExist = false;
	
	private Map<String, Object> columnValues = new HashMap<String, Object>();
	
	public Object getColumnValue(String columnName){
		return columnValues.get(StringUtils.upperCase(columnName));
	}
	
	public String getColumnStringValue(String columnName){
		Object obj = columnValues.get(StringUtils.upperCase(columnName));
		return obj != null ? obj.toString().trim() : null;
	}
	
	public Integer getColumnIntegerValue(String columnName){
		Object obj = columnValues.get(StringUtils.upperCase(columnName));
		return obj != null ? Integer.parseInt(obj.toString().trim()) : null;
	}
	
	public Double getColumnDoubleValue(String columnName){
		Object obj = columnValues.get(StringUtils.upperCase(columnName));
		return obj != null ? Double.parseDouble(obj.toString().trim()) : null;
	}
	
	public Date getColumnDateValue(String columnName, String format) {
		try{
			Object obj = columnValues.get(StringUtils.upperCase(columnName));
			if(obj instanceof Date){
				return (Date)obj;
			}
			if(StringUtils.isNotEmpty(format)){
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				return obj != null ? dateFormat.parse(obj.toString().trim()) : null;
			}else{
				return obj != null ? DateRegExp.yyyy_MM_dd_HH_mm_ss.parse(obj.toString().trim()) : null;
			}
		}catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
		
	}
	
	public Date getColumnDateValue(String columnName){
		try{
			Object obj = columnValues.get(StringUtils.upperCase(columnName));
			if(obj instanceof Date){
				return (Date)obj;
			}
		
			return obj != null ? DateRegExp.yyyy_MM_dd_HH_mm_ss.parse(obj.toString().trim()) : null;
		}catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
	}
	
	public void setColumnValue(String columnName, Object value){
		columnValues.put(StringUtils.upperCase(columnName), value);
	}
	
	public Set<String> getColumnNames(){
		return columnValues.keySet();
	}
	
	public Integer getColumnsNum(){
		return columnValues.size();
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public OptType getOptType() {
		return optType;
	}

	public void setOptType(OptType optType) {
		this.optType = optType;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	
	public Integer getDbSyncId() {
		return dbSyncId;
	}

	public void setDbSyncId(Integer dbSyncId) {
		this.dbSyncId = dbSyncId;
	}
	
	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public boolean isNotExist() {
		return isNotExist;
	}

	public void setNotExist(boolean isNotExist) {
		this.isNotExist = isNotExist;
	}
	
}
