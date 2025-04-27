package com.maybank.apiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ApiserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiserviceApplication.class, args);
    }

}
