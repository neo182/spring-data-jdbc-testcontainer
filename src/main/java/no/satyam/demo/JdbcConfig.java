package no.satyam.demo;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {
    private AtomicLong customerId = new AtomicLong(0);

    //This solves the "Quoting of Identifiers" problem for postgresql.
    //Read this post : https://spring.io/blog/2020/05/20/migrating-to-spring-data-jdbc-2-0
    @Bean
    @Override
    public JdbcMappingContext jdbcMappingContext(Optional<NamingStrategy> namingStrategy, JdbcCustomConversions customConversions) {
        JdbcMappingContext mappingContext = super.jdbcMappingContext(namingStrategy, customConversions);
        mappingContext.setForceQuote(false);
        return mappingContext;
    }

    //This bean sets the id before saving indicating that it is a new instance
    //Read this part of Ref Doc : https://docs.spring.io/spring-data/jdbc/docs/1.1.0.RC2/reference/html/#entity-callbacks.implement
    @Bean
    public ApplicationListener<BeforeSaveEvent<Object>> setId() {

        return event -> {
            Object entity = event.getEntity();
            if (entity instanceof Customer) {
                Customer customer = (Customer)entity;

                if (customer.getId() == null) {
                    customer.setId(customerId.incrementAndGet());
                }
            }
        };
    }
}
