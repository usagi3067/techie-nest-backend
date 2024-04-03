package com.dango.learning.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName choose_course
 */
@TableName(value ="choose_course")
@Data
public class ChooseCourse implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程id
     */
    @TableField(value = "course_id")
    private Long courseId;

    /**
     * 课程名称
     */
    @TableField(value = "course_name")
    private String courseName;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 机构id
     */
    @TableField(value = "company_id")
    private Long companyId;

    /**
     * 选课类型
     */
    @TableField(value = "order_type")
    private String orderType;

    /**
     * 课程图片
     */
    @TableField(value = "pic")
    private String pic;

    /**
     * 添加时间
     */
    @TableField(value = "create_date")
    private LocalDateTime createDate;

    /**
     * 课程价格
     */
    @TableField(value = "course_price")
    private Double coursePrice;

    /**
     * 课程有效期(天)
     */
    @TableField(value = "valid_days")
    private Integer validDays;

    /**
     * 选课状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 开始服务时间
     */
    @TableField(value = "valid_time_start")
    private LocalDateTime validTimeStart;

    /**
     * 结束服务时间
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