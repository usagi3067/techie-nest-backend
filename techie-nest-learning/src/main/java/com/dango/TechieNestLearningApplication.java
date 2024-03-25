package com.dango;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableFeignClients(basePackages={"com.dango.**.feignclient"})
@SpringBootApplication
@EnableWebMvc
public class TechieNestLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechieNestLearningApplication.class, args);
    }

}
