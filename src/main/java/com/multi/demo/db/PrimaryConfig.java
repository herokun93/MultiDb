package com.multi.demo.db;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration()
@EnableJpaRepositories(basePackages = "com.multi.demo.repository.primary", entityManagerFactoryRef = "primaryEntityManager", transactionManagerRef = "primaryTransactionManager")
@EntityScan(basePackages = {"com.multi.demo.entity.primary"})
public class PrimaryConfig {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSourceProperties primaryProperties() {
        return new DataSourceProperties();
    }
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.primary.configuration")
    public DataSource primaryDataSource() {
        return primaryProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }
    @Primary
    @Bean(name = "primaryEntityManager")
    public LocalContainerEntityManagerFactoryBean primaryEntityManager(EntityManagerFactoryBuilder builder){
        Map<String,String> properties =new HashMap<>();
        properties.put("spring.jpa.properties.primary.hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("spring.jpa.properties.primary.show-sql", "true");
        properties.put("spring.jpa.properties.primary.hibernate.ddl-auto", "update");
        properties.put("spring.jpa.properties.primary.database", "POSTGRESQL");
        properties.put("spring.jpa.properties.primary.generate-ddl", "true");
        return builder
                .dataSource(primaryDataSource()).packages("com.multi.demo.entity.primary").persistenceUnit("primary").properties(properties).build();
    }
    @Primary
    public PlatformTransactionManager primaryTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);}
    @Bean
    @Primary
    public JpaTransactionManager primaryTransactionManager(final @Qualifier("primaryEntityManager") LocalContainerEntityManagerFactoryBean primaryEntityManager) {
        return new JpaTransactionManager(primaryEntityManager.getObject());
    }
}

