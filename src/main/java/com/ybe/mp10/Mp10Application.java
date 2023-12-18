package com.ybe.mp10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
@ConfigurationPropertiesScan
@SpringBootApplication
public class Mp10Application {

    public static void main(String[] args) {
        SpringApplication.run(Mp10Application.class, args);
    }

}
