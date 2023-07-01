package com.lukaszziobro.blogapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@Configuration
public class AppConfig {

    @Bean(name = "mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean(name = "handlerExceptionResolver")
    public HandlerExceptionResolver handlerExceptionResolver(){
        return new DefaultHandlerExceptionResolver();
    }

}
