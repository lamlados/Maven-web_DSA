package com.pro.config;

import com.pro.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public User getUser(){
        return new User();
    }
}
