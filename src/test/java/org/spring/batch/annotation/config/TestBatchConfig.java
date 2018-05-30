package org.spring.batch.annotation.config;

import java.util.Arrays;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.batch.annotation.repository.ReportRepository;
import org.spring.batch.annotation.service.ProcessJobTestParent;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Overriding of the DB config for unit testing.
 * 
 * @author Rija RAMAMPIANDRA.
 *
 */
@TestConfiguration
@PropertySource({ "classpath:application-test.properties" })
@EnableTransactionManagement
@EnableAutoConfiguration
@ConditionalOnResource(resources = "classpath:application-test.properties")
@ComponentScan(basePackages = { "${base.package}" })
public class TestBatchConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessJobTestParent.class);

	@Value("${test.hibernate.dialect}")
	private String hibernatesDialect;

	@Value("${test.hibernate.show_sql}")
	private String hibernateShowSql;

	@Value("${test.hibernate.hbm2ddl.auto}")
	private String hibernateHbm2ddlAuto;

	@Value("${test.hibernate.enable_lazy_load_no_trans}")
	private String hibernateEnableLazyLoadNoTrans;

	@Value("${base.package}")
	private String basePackage;

	@ConfigurationProperties(prefix = "test.spring.datasource")
	@Bean(name = "datasource")
	@Primary
	@Profile("test")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

		factory.setDataSource(dataSource());
		factory.setJpaProperties(additionalProperties());
		factory.setPackagesToScan(basePackage);

		final JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		return factory;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public Properties additionalProperties() {

		final Properties props = new Properties();
		props.setProperty("hibernate.dialect", hibernatesDialect);
		props.setProperty("hibernate.show_sql", hibernateShowSql);
		props.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
		props.setProperty("hibernate.enable_lazy_load_no_trans", hibernateEnableLazyLoadNoTrans);

		return props;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public JobLauncherTestUtils jobLauncherTestUtils() {
		return new JobLauncherTestUtils();
	}

	public static void clearDataTest(Environment environment, ReportRepository processRepository) {
		// vider les tables en cas de test.
		if (!Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			LOGGER.info(
					"------------------------------------------------------- Spring profile : DEV or PROD --------------------------------------------------------");
			throw new AssertionError("Spring profile shoul'd be TEST.");
		}

		LOGGER.info(
				"------------------------------------------------------- Spring profile : TEST --------------------------------------------------------");
		processRepository.deleteAll();
	}
}
