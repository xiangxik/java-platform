package com.whenling.module.domain.repository;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 仓库配置
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:03:51
 */
@Configuration
@EnableJpaRepositories(basePackages = {
		"com.whenling" }, repositoryImplementationPostfix = "Impl", repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
@EnableJpaAuditing
public class RepositoryConfiguration {

	@Value("${jdbc.url?:jdbc:mysql://localhost:3306/platform?useUnicode=true&characterEncoding=utf-8}")
	private String jdbcUrl;

	@Value("${jdbc.username?:root}")
	private String jdbcUsername;

	@Value("${jdbc.password?:123456}")
	private String jdbcPassword;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(Boolean.TRUE);
		vendorAdapter.setShowSql(Boolean.TRUE);

		factory.setDataSource(dataSource());
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.whenling");
		factory.setJpaDialect(new HibernateJpaDialect());

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.id.new_generator_mappings", false);
		jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		jpaProperties.put("hibernate.show_sql", "true");
		jpaProperties.put("hibernate.format_sql", "true");
		jpaProperties.put("hibernate.current_session_context_class",
				"org.springframework.orm.hibernate5.SpringSessionContext");

		jpaProperties.put("javax.persistence.validation.mode", "none");
		jpaProperties.put("hibernate.query.substitutions", "true 1, false 0");
		jpaProperties.put("hibernate.default_batch_fetch_size", "16");
		jpaProperties.put("hibernate.max_fetch_depth", "4");
		jpaProperties.put("hibernate.enable_lazy_load_no_trans", "true");
		jpaProperties.put("hibernate.bytecode.use_reflection_optimizer", "true");

		jpaProperties.put("hibernate.cache.use_second_level_cache", "true");
		jpaProperties.put("hibernate.cache.region.factory_class",
				"org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
		jpaProperties.put("javax.persistence.sharedCache.mode", "ALL");
		jpaProperties.put("hibernate.generate_statistics", "true");

		jpaProperties.put("hibernate.cache.use_query_cache", "false");
		factory.setJpaProperties(jpaProperties);

		return factory;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(jdbcUsername);
		dataSource.setPassword(jdbcPassword);

		try {
			// 配置监控统计拦截的filters
			dataSource.setFilters("stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 配置初始化大小、最小、最大
		dataSource.setMaxActive(20);
		dataSource.setInitialSize(1);

		// 配置获取连接等待超时的时间
		dataSource.setMaxWait(60000);
		dataSource.setMinIdle(1);

		// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		dataSource.setTimeBetweenEvictionRunsMillis(60000);
		// 配置一个连接在池中最小生存的时间，单位是毫秒
		dataSource.setMinEvictableIdleTimeMillis(300000);

		dataSource.setValidationQuery("SELECT 'x'");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);

		return dataSource;
	}

}
