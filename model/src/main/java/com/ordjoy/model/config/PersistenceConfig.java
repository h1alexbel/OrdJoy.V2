package com.ordjoy.model.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan("com.ordjoy")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class PersistenceConfig {

    private static final String DB_URL_KEY = "db.url";
    private static final String DB_DRIVER_KEY = "db.driver";
    private static final String DB_USERNAME_KEY = "db.username";
    private static final String DB_PASSWORD_KEY = "db.password";
    private static final String ENTITY_PACKAGE = "com.ordjoy.model.entity";
    private static final String RESOURCE_PATH_OF_HIBERNATE_PROPERTIES = "classpath:application.properties";

    @Bean
    public DataSource dataSource(Environment environment) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty(DB_URL_KEY));
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty(DB_DRIVER_KEY)));
        dataSource.setUsername(environment.getProperty(DB_USERNAME_KEY));
        dataSource.setPassword(environment.getProperty(DB_PASSWORD_KEY));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, Properties hibernateProperties) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan(ENTITY_PACKAGE);
        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        return sessionFactoryBean;
    }

    @Bean
    public Properties hibernateProperties(@Value(RESOURCE_PATH_OF_HIBERNATE_PROPERTIES) Resource resource)
            throws IOException {
        Properties properties = new Properties();
        properties.load(resource.getInputStream());
        return properties;
    }

    @Bean
    public TransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}