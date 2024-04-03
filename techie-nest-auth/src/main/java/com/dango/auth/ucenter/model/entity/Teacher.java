package com.dango.auth.ucenter.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName teacher
 */
@TableName(value ="teacher")
@Data
public class Teacher implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private String id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 称呼
     */
    @TableField(value = "name")
    private String name;

    /**
     * 个人简介
     */
    @TableField(value = "intro")
    private String intro;

    /**
     * 个人简历
     */
    @TableField(value = "resume")
    private String resume;

    /**
     * 老师照片
     */
    @TableField(value = "pic")
    private String pic;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}