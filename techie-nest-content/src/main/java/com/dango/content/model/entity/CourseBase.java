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
     * 机构名称
     */
    @TableField(value = "company_name")
    private String companyName;

    /**
     * 机构id
     */
    @TableField(value = "company_id")
    private Long companyId;

    /**
     * 适用人群
     */
    @TableField(value = "users")
    private String users;

    /**
     * 课程标签
     */
    @TableField(value = "tags")
    private String tags;

    /**
     * 大分类
     */
    @TableField(value = "mt")
    private String mt;

    /**
     * 小分类
     */
    @TableField(value = "st")
    private String st;

    /**
     * 课程等级
     */
    @TableField(value = "grade")
    private String grade;

    /**
     * 授课模式(common普通, record录播, live直播)
     */
    @TableField(value = "teach_mode")
    private String teachMode;

    /**
     * 课程介绍
     */
    @TableField(value = "description")
    private String description;

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
     * 创建人
     */
    @TableField(value = "created_by")
    private String createdBy;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    private String updatedBy;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    /**
     * 审核状态
     */
    @TableField(value = "audit_status")
    private String auditStatus;

    /**
     * 课程状态(1:未发布, 2:已发布, 3: 下线)
     */
    @TableField(value = "status")
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}