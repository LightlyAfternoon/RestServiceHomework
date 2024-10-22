package org.example.config;

import org.example.db.ConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "org.example.repository")
@EnableWebMvc
@ComponentScan({"org.example.service.impl", "org.example.controller"})
public class MyWebConfig extends AnnotationConfigWebApplicationContext implements WebMvcConfigurer {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IOException {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(setConfig());
        em.setPackagesToScan("org.example.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    public static DataSource setConfig() throws IOException {
        try (InputStream inputStream = ConnectionManager.class.getResourceAsStream("/resources/db.properties")) {
            Properties prop = new Properties();
            prop.load(inputStream);
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(prop.getProperty("driverClassName"));
            dataSource.setUrl(prop.getProperty("jdbcUrl"));
            dataSource.setUsername(prop.getProperty("username"));
            dataSource.setPassword(prop.getProperty("password"));

            return dataSource;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.show_sql", "true");

        return properties;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index");
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager dbTransactionManager() throws IOException {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws IOException {
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();

        sf.setDataSource(setConfig());
        sf.setPackagesToScan("org.example.model");
        sf.setHibernateProperties(additionalProperties());

        return sf;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }
}
