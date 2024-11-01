package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.example.config")
@PropertySource("classpath:moviePlayer.properties")
public class AnnotationSpringConfig {
}
