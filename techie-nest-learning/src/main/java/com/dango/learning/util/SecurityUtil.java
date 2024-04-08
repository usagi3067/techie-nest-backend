package com.dango.learning.util;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dango.exception.BusinessException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author dango
 * @description
 * @date 2024-03-22
 */
@Slf4j
public class SecurityUtil {

    public static User getUser() {
        try {
            Object principalObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principalObj instanceof Jwt) {
                //取出用户身份信息
                String user = ((Jwt) principalObj).getClaim("user_name").toString();
                //将json转成对象
                return JSON.parseObject(user, User.class);
            }
        } catch (Exception e) {
            log.error("获取当前登录用户身份出错:{}", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public static Long getLecturerId() {
        //当前登录用户
        User user = SecurityUtil.getUser();
        if (user == null) {
            throw new BusinessException("用户未登录");
        }
        if (user.getUType() != 2 && user.getUType() != 3) {
            throw new BusinessException("用户无权限");
        }
        return user.getId();
    }

    @Data
    public static class User implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         *
         */
        @TableId(value = "id")
        private Long id;

        /**
         *
         */
        @TableField(value = "username")
        private String username;


        /**
         * 微信unionid
         */
        private String wxUnionId;

        /**
         * 头像
         */
        private String userPic;

        /**
         * 1为学生， 2为老师， 3为管理员
         */
        @TableField(value = "u_type")
        private Integer uType;

        /**
         *
         */
        private LocalDateTime birthday;

        /**
         *
         */
        private String sex;

        /**
         *
         */
        private String email;

        /**
         *
         */
        private String cellphone;

        /**
         *
         */
        private String qq;

        /**
         * 用户状态
         */
        private String status;


        private LocalDateTime createTime;

        /**
         *
         */
        private LocalDateTime updateTime;


    }


}

