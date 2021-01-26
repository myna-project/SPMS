package it.mynaproject.spms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import it.mynaproject.spms.domain.*;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("it.mynaproject.spms.dao"), @ComponentScan("it.mynaproject.spms.service") })
public class HibernateConfig {

	@Autowired
	private ApplicationContext context;

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

		factoryBean.setConfigLocation(context.getResource("classpath:hibernate.xml"));
		factoryBean.setAnnotatedClasses(
				Additive.class,
				AdditiveProductionOrder.class,
				CleaningPhase.class,
				Customer.class,
				MixtureMode.class,
				Packaging.class,
				ProductionOrder.class,
				RawMaterial.class,
				Role.class,
				SettingPhase.class,
				SystemPreparationPhase.class,
				User.class,
				ValidationPhase.class,
				WorkingPhase.class,
				WorkingPhaseMeasure.class
		);
		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}

}
