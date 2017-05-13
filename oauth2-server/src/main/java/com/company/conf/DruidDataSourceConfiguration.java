package com.company.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 *
 * 用JavaConfig的方式配置bean
 * Druid数据源配置
 * 配置说明：https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8
 */
@Configuration
public class DruidDataSourceConfiguration {
	@Value("${spring.datasource.dataSourceClassName}")
	private String driverClass;
	@Value("${spring.datasource.url}")
	private String jdbcUrl;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.initialSize}")
	private int initialSize;// 初始化时建立物理连接的个数
	@Value("${spring.datasource.maxActive}")
	private int maxActive;  // 最大连接池数量 
	@Value("${spring.datasource.minIdle}")
	private int minIdle;    // 最小连接池数量
	@Value("${spring.datasource.maxWait}")
	private long maxWait;    // 获取连接时最大等待时间，单位毫秒
	
	@Value("${spring.datasource.validationQuery}")
	private String validationQuery; //用来检测连接是否有效的sql，要求是一个查询语句。
	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;
	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;
	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;
	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private long minEvictableIdleTimeMillis;
	
	@Value("${spring.datasource.removeAbandoned}")
	private boolean removeAbandoned;
	@Value("${spring.datasource.removeAbandonedTimeout}")
	private int removeAbandonedTimeout;
	@Value("${spring.datasource.logAbandoned}")
	private boolean logAbandoned;
	
	@Value("${spring.datasource.filters}")
	private String filters;

	@Value("${spring.datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;
	
	/**
	 * 数据源
	 */
	@Bean(name = "druidDataSource",initMethod = "init",destroyMethod = "close")
	DruidDataSource dataSource(){
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(driverClass);
		druidDataSource.setUrl(jdbcUrl);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		
		druidDataSource.setInitialSize(initialSize);
		druidDataSource.setMaxActive(maxActive);
		druidDataSource.setMinIdle(minIdle);
		druidDataSource.setMaxWait(maxWait);
		
		druidDataSource.setValidationQuery(validationQuery);
		druidDataSource.setTestOnBorrow(testOnBorrow);
		druidDataSource.setTestOnReturn(testOnReturn);
		druidDataSource.setTestWhileIdle(testWhileIdle);
		druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		
		druidDataSource.setRemoveAbandoned(removeAbandoned);
		druidDataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
		druidDataSource.setLogAbandoned(logAbandoned);

		druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		
		try {
			druidDataSource.setFilters(filters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return druidDataSource;
	}
}
