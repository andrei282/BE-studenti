package ro.tacheandrei.cercetare.database.database.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ro.tacheandrei.cercetare.database.database.utils.LiquibaseConfigCreator;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
public class AdministrareConfiguration {

    private final Environment env;

    @Autowired
    public AdministrareConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    @Primary
    public DataSource nomenclatoareDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("datasource.administrare.driver-class-name")));
        dataSource.setUrl(env.getProperty("datasource.administrare.url"));
        dataSource.setUsername(env.getProperty("datasource.administrare.username"));
        dataSource.setPassword(env.getProperty("datasource.administrare.password"));

        return dataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.administrare.liquibase")
    public LiquibaseProperties nomenclatoareLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase nomenclatoareLiquibase() {
        System.out.println("Se executa liquibase");
        return LiquibaseConfigCreator.getSpringLiquibase(nomenclatoareDataSource(), nomenclatoareLiquibaseProperties());
    }
}

