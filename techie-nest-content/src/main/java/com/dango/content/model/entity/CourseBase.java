package com.dango.content.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 课程基本信息表
 * @TableName course_base
 */
@TableName(value ="course_base")
@Data
public class CourseBase implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 讲师名称
     */
    @TableField(value = "lecturer_name")
    private String lecturerName;

    /**
     * 讲师id
     */
    @TableField(value = "lecturer_id")
    private Long lecturerId;

    /**
     * 课程标签(json数组)
     */
    @TableField(value = "tags")
    private String tags;

    /**
     * 主分类
     */
    @TableField(value = "main_category")
    private String mainCategory;

    /**
     * 子分类
     */
    @TableField(value = "sub_category")
    private String subCategory;

    /**
     * 课程介绍
     */
    @TableField(value = "description")
    private String description;

    /**
     * 预备知识
     */
    @TableField(value = "pre_knowledge")
    private String preKnowledge;

    /**
     * 课程图片
     */
    @TableField(value = "pic")
    private String pic;

    /**
     * 创建时间
     */
    @TableField(value = "date_created")
    private LocalDateTime dateCreated;

    /**
     * 更新时间
     */
    @TableField(value = "date_updated")
    private LocalDateTime dateUpdated;

    /**
     * 审核状态（10001:审核未通过 10002审核通过 10003已提交 10004未提交）
     */
    @TableField(value = "audit_status")
    private String auditStatus;

    /**
     * 发布状态(20001:未发布, 20002:已发布, 20003:下线)
     */
    @TableField(value = "publish_status")
    private String publishStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}