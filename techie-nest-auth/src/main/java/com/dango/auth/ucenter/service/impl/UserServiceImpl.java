package com.dango.auth.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dango.auth.ucenter.mapper.UserMapper;
import com.dango.auth.ucenter.model.dto.AuthParamsDto;
import com.dango.auth.ucenter.model.dto.UserExt;
import com.dango.auth.ucenter.model.entity.User;
import com.dango.auth.ucenter.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dango
 * @description
 * @date 2024-03-22
 */
@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService {
    @Resource
    UserMapper userMapper;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {


        AuthParamsDto authParamsDto = null;
        try {
            //将认证参数转为AuthParamsDto类型
            authParamsDto = JSON.parseObject(s, AuthParamsDto.class);
        } catch (Exception e) {
            log.info("认证请求不符合项目要求:{}",s);
            throw new RuntimeException("认证请求数据格式不对");
        }
        //认证方法
        String authType = authParamsDto.getAuthType();
        AuthService authService =  applicationContext.getBean(authType + "_authservice",AuthService.class);

        UserExt user = authService.execute(authParamsDto);

        return getUserDetails(user);

    }

    private UserDetails getUserDetails(UserExt authParamsDto) {
        //账号
        String username = authParamsDto.getUsername();


        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if(user==null){
            //返回空表示用户不存在
            return null;
        }
        //取出数据库存储的正确密码
        String password  =user.getPassword();
        //用户权限,如果不加报Cannot pass a null GrantedAuthority collection
        String[] authorities = {"p1"};
        //为了安全在令牌中不放密码
        user.setPassword(null);
        //将user对象转json
        String userString = JSON.toJSONString(user);
        //创建UserDetails对象
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(userString).password(password).authorities(authorities).build();

        return userDetails;
    }

}
