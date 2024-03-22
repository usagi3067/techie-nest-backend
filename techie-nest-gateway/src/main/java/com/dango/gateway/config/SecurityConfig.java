package com.dango.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @description 安全配置类
 * @author Mr.M
 * @date 2022/9/27 12:07
 * @version 1.0
 */
 @EnableWebFluxSecurity
 @Configuration
 public class SecurityConfig {


 //安全拦截配置
// 使用@Bean注解定义一个Spring管理的bean，这里的bean是用于配置Web安全性的SecurityWebFilterChain对象。
 @Bean
 public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {
  // 开始配置HTTP安全性
  return http.authorizeExchange()
          // .pathMatchers("/**").permitAll() 表示对所有路径的请求（/**）不需要进行认证，即允许匿名访问。
          .pathMatchers("/**").permitAll()
          // .anyExchange().authenticated() 表示除了通过pathMatchers明确允许的路径外，其他所有请求都需要进行认证。
          .anyExchange().authenticated()
          // 然后是对CSRF（跨站请求伪造）的配置。.csrf().disable()表示禁用CSRF保护功能。
          // 在某些基于REST API的应用程序中，可能会选择禁用CSRF保护，因为它们主要使用无状态的认证机制（如Token），而不是依赖于会话。
          .and().csrf().disable()
          // 最后，调用.build()方法构建并返回SecurityWebFilterChain对象，完成安全配置的设置。
          .build();
 }
}

