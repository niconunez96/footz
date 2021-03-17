package shared.infrastructure.jpa;

import ftz.teams.domain.Team;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    private static String DRIVER_CLASS_NAME = "org.h2.Driver";
    private static String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static String DB_USERNAME = "sa";
    private static String DB_PASSWORD = "";

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
//        sessionFactory.setPackagesToScan("ftz.tournament.domain");
        sessionFactory.setAnnotatedClasses(Team.class);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public Properties hibernateProperties(){
        Properties hibernateProperty = new Properties();
        hibernateProperty.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperty.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProperty.setProperty("hibernate.show_sql", "true");
        return hibernateProperty;
    }
}
