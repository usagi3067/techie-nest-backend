package com.dango.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages={"com.dango.**.feignclient"})
public class TechieNestAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechieNestAuthApplication.class, args);
    }

}
