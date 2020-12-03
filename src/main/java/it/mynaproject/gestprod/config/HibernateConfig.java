package it.mynaproject.gestprod.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import it.mynaproject.gestprod.domain.*;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("it.mynaproject.gestprod.dao"), @ComponentScan("it.mynaproject.gestprod.service") })
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
				WorkingPhaseUser.class,
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