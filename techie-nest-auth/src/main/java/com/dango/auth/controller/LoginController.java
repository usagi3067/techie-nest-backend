package com.dango.auth.controller;

import com.dango.auth.ucenter.mapper.UserMapper;
import com.dango.auth.ucenter.model.dto.RegisterDto;
import com.dango.auth.ucenter.model.entity.User;
import com.dango.auth.ucenter.service.VerifyService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.M
 * @version 1.0
 * @description 测试controller
 * @date 2022/9/27 17:25
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    VerifyService verifyService;


    @RequestMapping("/login-success")
    public String loginSuccess() {

        return "登录成功";
    }


    @RequestMapping("/user/{id}")
    public User getuser(@PathVariable("id") String id) {
        User User = userMapper.selectById(id);
        return User;
    }

    @PreAuthorize("hasAuthority('p1')")//拥有p1权限方可访问
    @RequestMapping("/r/r1")
    public String r1() {
        return "访问r1资源";
    }

    @PreAuthorize("hasAuthority('p2')") // 拥有p2权限方可访问
    @RequestMapping("/r/r2")
    public String r2() {
        return "访问r2资源";
    }

    @ApiOperation(value = "注册", tags = "注册")
    @PostMapping("/register")
    public void register(@RequestBody RegisterDto registerDto) {
        verifyService.register(registerDto);
    }

}
