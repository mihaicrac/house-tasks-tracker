package com.house.taskstracker.authentication;

import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableTransactionManagement
@EnableJpaRepositories("com.house.taskstracker.authentication.*")
@TestConfiguration
public class DataSourceConfig {

//    @Bean
//    public DataSource dataSource() {
//        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
//        return dbBuilder.setType(EmbeddedDatabaseType.H2)
//                .build();
//    }


    @Bean
    public PostgreSQLContainer genericContainer() {
        Logger logger = LoggerFactory.getLogger(PostgreSQLContainer.class);
        Slf4jLogConsumer consumer = new Slf4jLogConsumer(logger);
        PostgreSQLContainer container = new PostgreSQLContainer("postgres:11.1")
                .withDatabaseName("integration-tests-db")
                .withUsername("sa")
                .withPassword("sa");
        container.withCommand("postgres -c log_statement=all");
        container.start();
        container.followOutput(consumer);
        return container;
    }

    @Bean
    public DataSource dataSource(final PostgreSQLContainer container) {
        DataSourceBuilder ds = DataSourceBuilder.create();
        // Datasource initialization
        ds.url(container.getJdbcUrl());
        ds.username(container.getUsername());
        ds.password(container.getPassword());
        ds.driverClassName(container.getDriverClassName());
        // Additional parameters configuration omitted
        return ds.build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();

        result.setPackagesToScan("com.house.taskstracker.authentication");
        result.setDataSource(dataSource);
        result.setJpaVendorAdapter(jpaVendorAdapter());

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.ddl-auto", "validate");
        jpaProperties.put("hibernate.show_sql", true);
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        result.setJpaPropertyMap(jpaProperties);

        return result;
    }

    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public SpringLiquibase liquibase(@Autowired DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase/master-changelog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

}
