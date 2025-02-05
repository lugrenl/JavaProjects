package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("org.example")
@PropertySource("classpath:jdbc.properties")
@EnableJpaRepositories(basePackages = "org.example.dao", entityManagerFactoryRef = "sessionFactory")
public class ContactsManagerConfig {
}
