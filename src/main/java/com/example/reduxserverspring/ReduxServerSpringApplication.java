package com.example.reduxserverspring;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Counter REST API",
        description = "A simple REST API providing counters powered by Spring Boot",
        version = "1.0.0"))
public class ReduxServerSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReduxServerSpringApplication.class, args);
    }

}
