package com.dango.content.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName lecturer
 */
@TableName(value ="lecturer")
@Data
public class Lecturer implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private String id;

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
     * 讲师照片
     */
    @TableField(value = "pic")
    private String pic;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}