package com.dango.learning.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;

/**
 * @description 资源服务配置
 * @author Mr.M
 * @date 2022/10/18 16:33
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

 @Autowired
 private JwtDecoder jwtDecoder;

 @Override
 protected void configure(HttpSecurity http) throws Exception {
  http
          .oauth2ResourceServer()
          .jwt()
          .decoder(jwtDecoder);

  http
          .csrf().disable()
          .authorizeRequests()
          .anyRequest().permitAll();
 }
}