package com.dango.auth.ucenter.service.impl;

import com.dango.auth.ucenter.mapper.UserMapper;
import com.dango.auth.ucenter.mapper.UserRoleMapper;
import com.dango.auth.ucenter.model.dto.RegisterDto;
import com.dango.auth.ucenter.model.entity.User;
import com.dango.auth.ucenter.model.entity.UserRole;
import com.dango.auth.ucenter.service.VerifyService;
import com.dango.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author dango
 * @description
 * @date
 */
@Service
public class VerifyServiceImpl implements VerifyService {

    @Resource
    UserMapper userMapper;

    @Resource
    UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public void register(RegisterDto registerDto) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String shortUuid = uuid.substring(0, 16); // 取前16位
        String password = registerDto.getPassword();
        String confirmpwd = registerDto.getConfirmpwd();
        if (!password.equals(confirmpwd)) {
            throw new RuntimeException("两次输入的密码不一致");
        }
        User user = new User();
        BeanUtils.copyProperties(registerDto, user);
        user.setId(shortUuid);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setUType(2);  // 学生类型
        user.setStatus("1");
        user.setCreateTime(LocalDateTime.now());
        int insert = userMapper.insert(user);
        if (insert <= 0) {
            throw new BusinessException("新增用户信息失败");
        }
        UserRole userRole = new UserRole();
        userRole.setId(shortUuid);
        userRole.setUserId(shortUuid);
        userRole.setRoleId("50");
        userRole.setCreateTime(LocalDateTime.now());
        int insert1 = userRoleMapper.insert(userRole);
        if (insert1 <= 0) {
            throw new RuntimeException("新增用户角色信息失败");
        }
    }
}
