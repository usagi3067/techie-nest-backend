package com.dango.content.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 课程计划
 * @TableName teach_plan
 */
@TableName(value ="teach_plan")
@Data
public class TeachPlan implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程计划名称
     */
    @TableField(value = "plan_name")
    private String planName;

    /**
     * 课程计划父级Id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 层级，分为1、2级
     */
    @TableField(value = "grade")
    private Integer grade;

    /**
     * 课程类型:1视频、2文档
     */
    @TableField(value = "media_type")
    private String mediaType;

    /**
     * 开始直播时间
     */
    @TableField(value = "start_time")
    private LocalDateTime startTime;

    /**
     * 直播结束时间
     */
    @TableField(value = "end_time")
    private LocalDateTime endTime;

    /**
     * 章节及课程时介绍
     */
    @TableField(value = "description")
    private String description;

    /**
     * 时长，单位时:分:秒
     */
    @TableField(value = "time_length")
    private String timeLength;

    /**
     * 排序字段
     */
    @TableField(value = "order_by")
    private Integer orderBy;

    /**
     * 课程标识
     */
    @TableField(value = "course_id")
    private Long courseId;

    /**
     * 课程发布标识
     */
    @TableField(value = "course_pub_id")
    private Long coursePubId;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    /**
     * 是否支持试学或预览（试看）
     */
    @TableField(value = "is_preview")
    private String isPreview;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}