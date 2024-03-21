package com.dango;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author dango
 * @description
 * @date ${date}
 */// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@SpringBootApplication
@EnableFeignClients(basePackages={"com.dango.content.feignclient"})
public class TechieNestContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(TechieNestContentApplication.class, args);
    }
}

