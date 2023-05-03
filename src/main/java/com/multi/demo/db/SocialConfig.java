package com.multi.demo.db;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        basePackages = "com.multi.demo.repository.social",
        entityManagerFactoryRef = "socialEntityManager",
        transactionManagerRef = "socialTransactionManager")
@EntityScan(basePackages = {"com.multi.demo.entity.social"})
public class SocialConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.social")
    public DataSourceProperties socialProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.social.configuration")
    public DataSource socialDataSource() {
        return socialProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "socialEntityManager")
    public LocalContainerEntityManagerFactoryBean socialEntityManager(){
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        Map<String,String> properties =new HashMap<>();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        em.setDataSource(socialDataSource());
        em.setPackagesToScan("com.multi.demo.entity.social");
        em.setPersistenceUnitName("social");
        em.setJpaPropertyMap(properties);
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }


    @Bean
    public PlatformTransactionManager socialTransactionManager(@Qualifier("socialEntityManager") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        return new JpaTransactionManager(Objects.requireNonNull(localContainerEntityManagerFactoryBean.getObject()));
    }

//    @Bean
//    public JpaTransactionManager socialTransactionManager(final @Qualifier("socialEntityManager") LocalContainerEntityManagerFactoryBean socialEntityManager) {
//        return new JpaTransactionManager(socialEntityManager.getObject());
//    }
}

