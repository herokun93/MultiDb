package com.multi.demo.db;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration(enforceUniqueMethods=true)
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.multi.demo.repository.auth",
        entityManagerFactoryRef = "authEntityManager",
        transactionManagerRef = "authTransactionManager")
@EntityScan(basePackages = {"com.multi.demo.entity.auth"})
public class AuthConfig {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.auth")
    public DataSourceProperties authProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.auth.configuration")
    public DataSource authDataSource() {
        return authProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean authEntityManager(){
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        Map<String,String> properties =new HashMap<>();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        em.setDataSource(authDataSource());
        em.setPackagesToScan("com.multi.demo.entity.auth");
        em.setPersistenceUnitName("auth");
        em.setJpaPropertyMap(properties);
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Primary
    @Bean
    public PlatformTransactionManager authTransactionManager(@Qualifier("authEntityManager") LocalContainerEntityManagerFactoryBean  entityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }

//    @Primary
//    @Bean
//    public JpaTransactionManager authTransactionManager(final @Qualifier("authEntityManager") LocalContainerEntityManagerFactoryBean authEntityManager) {
//        return new JpaTransactionManager(authEntityManager.getObject());
//    }
}

