package com.example.Servletdemofull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServletDemoFullApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServletDemoFullApplication.class, args);
        System.out.println("El servidor está en funcionamiento");
    }
}
