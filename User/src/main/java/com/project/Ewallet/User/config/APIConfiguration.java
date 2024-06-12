package com.project.Ewallet.User.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class APIConfiguration {

    @Bean
    public RestTemplate createRestTemplate (){
        return new RestTemplate();
    }
}
