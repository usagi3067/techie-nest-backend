package com.dango.auth.config;//package com.dango.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.annotation.Resource;

/**
 * @author dango
 * @description
 * @date 2024-03-22
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Resource(name = "authorizationServerTokenServicesCustom")
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    //配置客户端详情服务

    /**
     * 服务提供商会给批准接入的客户端一个身份，用于接入时的凭据，有客户端标识和客户端秘钥，在这里配置批准接入的客户端的详细信息。
     * @param clients the client details configurer
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.inMemory()// 使用in-memory存储
                .withClient("WebApp")// client_id
//                .secret("WebApp")//客户端密钥
                .secret(new BCryptPasswordEncoder().encode("WebApp"))//客户端密钥
                .resourceIds("techie-nest")//资源列表
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")// 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .scopes("all")// 允许的授权范围
                .autoApprove(false)//false跳转到授权页面
                //客户端接收授权码的重定向地址
                .redirectUris("http://127.0.0.1:8106")
        ;
    }


    //令牌端点的访问配置
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)//认证管理器 这个AuthenticationManager是用来处理认证请求的，即它负责验证用户身份。
                .tokenServices(authorizationServerTokenServices)//令牌管理服务 是用于创建和管理OAuth2令牌（access tokens和refresh tokens）的组件。
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    //令牌端点的安全配置
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("permitAll()")                    //oauth/token_key是公开
                .checkTokenAccess("permitAll()")                  //oauth/check_token公开
                .allowFormAuthenticationForClients()             //表单认证（申请令牌）
        ;
    }


}

