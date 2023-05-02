package com.multi.demo.db;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.multi.demo.repository.secondary",
        entityManagerFactoryRef = "secondaryEntityManager",
        transactionManagerRef = "secondaryTransactionManager"
)
public class SecondaryConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSourceProperties secondaryProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Autowired
    @ConfigurationProperties("spring.datasource.secondary.configuration")
    public DataSource secondaryDataSource() {
        return secondaryProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean secondaryEntityManager(EntityManagerFactoryBuilder builder, @Qualifier("secondaryDataSource") DataSource dataSource){
        Map<String,String> properties =new HashMap<>();
        properties.put("spring.jpa.properties.secondary.hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("spring.jpa.properties.secondary.show-sql", "true");
        properties.put("spring.jpa.properties.secondary.hibernate.ddl-auto", "update");
        properties.put("spring.jpa.properties.secondary.database", "POSTGRESQL");
        properties.put("spring.jpa.properties.secondary.generate-ddl", "true");

        return builder
                .dataSource(dataSource)
                .packages("com.multi.demo.entity.secondary")
                .persistenceUnit("secondary")
                .properties(properties)
                .build();
    }


    @Bean
    @Autowired
    public JpaTransactionManager secondaryTransactionManager(@Qualifier("secondaryEntityManager") LocalContainerEntityManagerFactoryBean secondaryEntityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(secondaryEntityManager.getObject());
        return transactionManager;
    }
}

