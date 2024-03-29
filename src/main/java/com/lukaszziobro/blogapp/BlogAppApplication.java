package com.lukaszziobro.blogapp;

import com.lukaszziobro.blogapp.service.CategoryService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Blog App REST API",
                description = "Blog App REST API Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Lukasz",
                        url = "https://github.com/lukaszziobro89"
                )
        )
)
public class BlogAppApplication {

    private final static Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public static void main(String[] args) {
        SpringApplication.run(BlogAppApplication.class, args);
        logger.debug("running BlogApp in DEBUG mode");
    }

}
