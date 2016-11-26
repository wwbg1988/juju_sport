package com.juju.sport.sync.dao.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.juju.sport.sync.pojo.DataSync;

public class JdbcDateInfoDao extends JdbcDaoSupport{

	public List<Map<String, Object>> findByDBSync(DataSync dbSync){	
		StringBuilder sql = new StringBuilder("select * from ");
		sql.append(dbSync.getTableName()).append(" where ").append(dbSync.getKeyName()).append("=? ");
	
		Object[] params = new Object[1];							
		params[0] = dbSync.getKeyValue();		
	
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql.toString(), params);
		return rows;
	}
}
