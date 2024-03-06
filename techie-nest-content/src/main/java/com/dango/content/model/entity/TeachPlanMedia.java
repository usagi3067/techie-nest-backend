package com.dango.content.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 课程计划媒资文件
 * @TableName teach_plan_media
 */
@TableName(value ="teach_plan_media")
@Data
public class TeachPlanMedia implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 媒资文件id
     */
    @TableField(value = "media_id")
    private String mediaId;

    /**
     * 课程计划标识
     */
    @TableField(value = "teach_plan_id")
    private Long teachPlanId;

    /**
     * 课程标识
     */
    @TableField(value = "course_id")
    private Long courseId;

    /**
     * 媒资文件原始名称
     */
    @TableField(value = "media_file_name")
    private String mediaFileName;

    /**
     * 创建时间
     */
    @TableField(value = "date_created")
    private LocalDateTime dateCreated;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}