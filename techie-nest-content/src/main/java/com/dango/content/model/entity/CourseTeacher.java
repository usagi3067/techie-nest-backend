package com.dango.content.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 课程-教师关系表
 * @TableName course_teacher
 */
@TableName(value ="course_teacher")
@Data
public class CourseTeacher implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程标识
     */
    @TableField(value = "course_id")
    private Long courseId;

    /**
     * 教师名字
     */
    @TableField(value = "teacher_name")
    private String teacherName;

    /**
     * 教师职位
     */
    @TableField(value = "position")
    private String position;

    /**
     * 教师简介
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 照片
     */
    @TableField(value = "photograph")
    private String photograph;

    /**
     * 创建时间
     */
    @TableField(value = "date_created")
    private LocalDateTime dateCreated;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}