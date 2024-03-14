package com.dango;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dango
 * @description
 * @date ${date}
 */
@MapperScan("com.dango.system.mapper")
@SpringBootApplication
public class TechieNestSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(TechieNestSystemApplication.class, args);
    }
}
