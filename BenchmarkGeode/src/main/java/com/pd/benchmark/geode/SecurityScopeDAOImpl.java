package com.pd.benchmark.geode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

public class SecurityScopeDAOImpl {
	private JdbcTemplate jdbcTemplate;
	private final static String CAGS_SECURITY_SCOPE_FILTER = "SELECT cag FROM benchmark.security_control WHERE cag in(%s) AND security_scope IN (%s) ORDER BY cag";
	private final static String CAG_LIST_TEMPLATE = ",'%s'";
	
	public SecurityScopeDAOImpl() {
		jdbcTemplate=new JdbcTemplate(dataSource());
	}

	public Map<String, String> getAllowedCagsForSecurityScopes(List<String> cags, String scopes){
		Iterator<String> iterator = cags.iterator();
		
		String cagsStr ="";
		while(iterator.hasNext()) {
			String cag = iterator.next();
			cagsStr = cagsStr + String.format(CAG_LIST_TEMPLATE, cag);
		}
		cagsStr = cagsStr.substring(1);
		String queryStr = String.format(CAGS_SECURITY_SCOPE_FILTER, cagsStr, scopes);
		List<String> listAllowedCags = jdbcTemplate.queryForList(queryStr, String.class);
		return convertListMap(listAllowedCags);
	}
	
	private Map<String,String> convertListMap(List<String> allowedCags){
		Map<String,String> allowedCagsMap = new HashMap<String,String>();
		Iterator<String> iterator = allowedCags.iterator();
		while(iterator.hasNext()) {
			String cagValue = iterator.next();
			allowedCagsMap.put(cagValue, cagValue );
		}
		return allowedCagsMap;
	}
		
	private DataSource dataSource() {
		String driver="org.postgresql.Driver";
		String url="jdbc:postgresql://127.0.0.1:5432/benchmark";
		String dbuser="postgres";
		String dbpassword="postgrespassword";
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(url);
		driverManagerDataSource.setUsername(dbuser);
		driverManagerDataSource.setPassword(dbpassword);
		driverManagerDataSource.setDriverClassName(driver);
		return driverManagerDataSource;
	}
	
	
}
