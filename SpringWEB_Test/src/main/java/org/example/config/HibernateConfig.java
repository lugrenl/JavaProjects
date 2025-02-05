package org.example.config;

import org.example.model.Contact;
import org.hibernate.SessionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory() {
        var configuration = new org.hibernate.cfg.Configuration()
                .addAnnotatedClass(Contact.class)
                .configure();
        return configuration.buildSessionFactory();
    }
}