package no.satyam.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.NamingStrategy;

import java.util.Optional;

@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

    //This solves the "Quoting of Identifiers" problem for postgresql.
    //Read this post : https://spring.io/blog/2020/05/20/migrating-to-spring-data-jdbc-2-0
    @Bean
    @Override
    public JdbcMappingContext jdbcMappingContext(Optional<NamingStrategy> namingStrategy, JdbcCustomConversions customConversions) {
        JdbcMappingContext mappingContext = super.jdbcMappingContext(namingStrategy, customConversions);
        mappingContext.setForceQuote(false);
        return mappingContext;
    }
}
