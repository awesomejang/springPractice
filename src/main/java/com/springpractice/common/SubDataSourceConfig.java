package com.springpractice.common;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.springpractice.dao.repository.sub",
        entityManagerFactoryRef = "subEntityManagerFactory",
        transactionManagerRef = "subTransactionManager"
)
public class SubDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.second-db")
    public DataSource subDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean subEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("subDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.springpractice.dao.entities.sub")
                .persistenceUnit("sub")
                .build();
    }

    @Bean
    public PlatformTransactionManager subTransactionManager(
            @Qualifier("subEntityManagerFactory") EntityManagerFactory subEntityManagerFactory) {
        return new JpaTransactionManager(subEntityManagerFactory);
    }
}
