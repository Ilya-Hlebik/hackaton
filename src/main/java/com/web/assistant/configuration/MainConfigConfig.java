package com.web.assistant.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:swagger.properties")
public class MainConfigConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
