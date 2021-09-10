package com.silkdog.ambulance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@EnableAspectJAutoProxy
@ServletComponentScan
@SpringBootApplication
public class AmbulanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmbulanceApplication.class, args);
    }

}
