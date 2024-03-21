package com.dango.messagesdk.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置
 */
@Configuration("messagesdk_mpconfig")
@MapperScan("com.dango.messagesdk.mapper")
public class MyBatisPlusConfig {

}