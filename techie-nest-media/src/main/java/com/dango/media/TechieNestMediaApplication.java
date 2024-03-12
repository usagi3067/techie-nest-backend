package com.dango.media;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.dango.media.mapper")
@SpringBootApplication
public class TechieNestMediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechieNestMediaApplication.class, args);
    }

}
