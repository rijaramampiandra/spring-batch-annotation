package org.spring.batch.annotation.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Database configuration.
 * 
 * @author Rija RAMAMPIANDRA.
 *
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfigApp {

	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean(name = "datasource")
	@Primary
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

}
