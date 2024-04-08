package com.dango.learning.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName course_tables
 */
@TableName(value ="course_tables")
@Data
public class CourseTables implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 选课记录id
     */
    @TableField(value = "choose_course_id")
    private Long chooseCourseId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 课程id
     */
    @TableField(value = "course_id")
    private Long courseId;

    /**
     * 讲师id
     */
    @TableField(value = "lecturer_id")
    private Long lecturerId;

    /**
     * 课程名称
     */
    @TableField(value = "course_name")
    private String courseName;

    /**
     * 是否免费(1: 免费, 0: 收费)
     */
    @TableField(value = "is_free")
    private Integer isFree;

    /**
     * 课程图片
     */
    @TableField(value = "pic")
    private String pic;

    /**
     * 开始服务时间
     */
    @TableField(value = "valid_time_start")
    private LocalDateTime validTimeStart;

    /**
     * 到期时间
     */
    @TableField(value = "valid_time_end")
    private LocalDateTime validTimeEnd;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}