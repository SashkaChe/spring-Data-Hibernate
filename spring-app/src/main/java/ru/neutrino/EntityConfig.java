package ru.neutrino;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@PropertySource("classpath:/datasource.properties")
@ComponentScan(basePackages = "ru.neutrino.dao")
public class EntityConfig {

	@Value("${data.url}")
	private String url;
	
	@Value("${data.username}")
	private String username;
	
	@Value("${data.password}")
	private String password;
	
	@Value("${data.driver}")
	private String driver;
	
	
	@Bean
	public DataSource dataSource() {
				
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		return dataSource;
		    }
	
	@Bean 
	public JpaVendorAdapter jpaVendorAdapter() { 
	return new HibernateJpaVendorAdapter(); 
}
	
	
	@Bean 
	public PlatformTransactionManager transactionManager() { 
	return new JpaTransactionManager(entityFactory());
	}

	
	@Bean
	public EntityManagerFactory entityFactory() {
		
		
		Properties hibernateProperties = new Properties();
	    hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean(); 
        factoryBean.setPackagesToScan("ru.neutrino"); 
        factoryBean.setDataSource(dataSource()); 
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); 
        factoryBean.setJpaProperties(hibernateProperties); 
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter()); 
    	factoryBean.afterPropertiesSet(); 
      
        return factoryBean.getNativeEntityManagerFactory(); 

		    }

	
}