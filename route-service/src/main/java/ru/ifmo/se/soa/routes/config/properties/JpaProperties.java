package ru.ifmo.se.soa.routes.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class JpaProperties {
    @Value("${spring.jpa.show-sql}")
    // @Value("${spring.jpa.properties.hibernate.show_sql}")
    private String showSql;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    // @Value("${spring.jpa.properties.hibernate.hbm2ddl.auto}")
    private String ddlAuto;

    @Value("${spring.jpa.properties.hibernate.physical_naming_strategy}")
    private String physicalNamingStrategy;

    public Properties get() {
        var properties = new Properties();
        properties.setProperty("hibernate.show_sql", showSql);
        properties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
        properties.setProperty("hibernate.physical_naming_strategy", physicalNamingStrategy);
        return properties;
    }
}
