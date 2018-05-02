package com.kibo.pegateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = {"com.kibo"})
@EntityScan
public class PeGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PeGatewayApplication.class, args);
    }
}
