package com.onebill.pricing.testconfig;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "com.onebill")
@EnableWebMvc
@EnableTransactionManagement
public class PricingAppTestConfiguration {

	@Autowired
	private Environment env;

	@Bean
	public LocalEntityManagerFactoryBean getEmf() {
		LocalEntityManagerFactoryBean bean = new LocalEntityManagerFactoryBean();
		bean.setPersistenceUnitName("priceManagementTest");
		return bean;
	}

	@Bean
	public JpaTransactionManager getManager() {
		JpaTransactionManager mgrBean = new JpaTransactionManager();
		mgrBean.setEntityManagerFactory(getEmf().getObject());
		return mgrBean;
	}

	@Bean
	public ModelMapper getModelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper;
	}


}
