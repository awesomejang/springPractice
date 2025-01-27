package com.springpractice.common;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
/**
 * 평소에는 사용할 필요가 없다. @EnableAutoConfiguration(SpringBootApplication)이 메인 패키지 하위로 스캔 영역 지정
 * 그래서 직접 Repository 스캔 영역을 지정할 필요가 있거나 다중 데이터소스를 사용할때 사용한다.
 */
@EnableJpaRepositories(
        basePackages = "com.springpractice.dao.repository.main",
        entityManagerFactoryRef = "mainEntityManagerFactory",
        transactionManagerRef = "mainTransactionManager"
)
public class MainDataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.main-db")
    public DataSource mainDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("mainDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.springpractice.dao.entities.main")
                .persistenceUnit("main")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager mainTransactionManager(
            @Qualifier("mainEntityManagerFactory") EntityManagerFactory mainEntityManagerFactory) {
            return new JpaTransactionManager(mainEntityManagerFactory);
    }
}
