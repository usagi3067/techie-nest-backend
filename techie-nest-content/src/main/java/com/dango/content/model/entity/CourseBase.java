package com.dango.content.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 机构名称
     */
    private String companyName;

    /**
     * 机构id
     */
    private Long companyId;

    /**
     * 适用人群
     */
    private String users;

    /**
     * 大分类
     */
    private String mt;

    /**
     * 小分类
     */
    private String st;

    /**
     * 课程等级
     */
    private String grade;

    /**
     * 授课模式(common普通, record录播, live直播)
     */
    private String teachMode;

    /**
     * 课程介绍
     */
    private String description;

    /**
     * 课程图片
     */
    private String pic;

    /**
     * 创建时间
     */
    private LocalDateTime dateCreated;

    /**
     * 更新时间
     */
    private LocalDateTime dateUpdated;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 审核状态
     */
    private String auditStatus;

    /**
     * 课程状态(1:未发布, 2:已发布, 3: 下线)
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}