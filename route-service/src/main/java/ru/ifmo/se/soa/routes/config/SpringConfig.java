package ru.ifmo.se.soa.routes.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.ifmo.se.soa.routes.config.properties.DataSourceProperties;
import ru.ifmo.se.soa.routes.config.properties.JpaProperties;

import java.util.Objects;

@Configuration
@ComponentScan("ru.ifmo.se.soa.routes")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("ru.ifmo.se.soa.routes.repository")
@EnableWebMvc
@EnableTransactionManagement
@RequiredArgsConstructor
public class SpringConfig {
    private final DataSourceProperties dataSourceProperties;
    private final JpaProperties jpaProperties;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());

        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan("ru.ifmo.se.soa.routes.entity");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(jpaProperties.get());

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }
}
