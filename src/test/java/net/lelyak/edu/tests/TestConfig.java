package net.lelyak.edu.tests;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("net.lelyak.edu.tests.util")
public class TestConfig {
    @Bean
    DataFactory dataFactory() {
        return new DataFactory();
    }
}
