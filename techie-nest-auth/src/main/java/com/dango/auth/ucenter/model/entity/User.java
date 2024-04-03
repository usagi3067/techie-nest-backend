package com.dango.auth.ucenter.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 
     */
    @TableField(value = "username")
    private String username;

    /**
     * 
     */
    @TableField(value = "password")
    private String password;

    /**
     * 微信unionid
     */
    @TableField(value = "wx_union_id")
    private String wxUnionId;

    /**
     * 头像
     */
    @TableField(value = "user_pic")
    private String userPic;

    /**
     * 
     */
    @TableField(value = "company_id")
    private String companyId;

    /**
     * 1为学生， 2未用户
     */
    @TableField(value = "u_type")
    private Integer uType;

    /**
     * 
     */
    @TableField(value = "birthday")
    private LocalDateTime birthday;

    /**
     * 
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 
     */
    @TableField(value = "email")
    private String email;

    /**
     * 
     */
    @TableField(value = "cellphone")
    private String cellphone;

    /**
     * 
     */
    @TableField(value = "qq")
    private String qq;

    /**
     * 用户状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}