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
    private Long userId;

    /**
     * 讲师id
     */
    @TableField(value = "lecturer_id")
    private Long lecturerId;

    /**
     * 1:免费 0： 收费
     */
    @TableField(value = "is_free")
    private Integer isFree;

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
     * 选课状态 400001:选课成功 400002:待支付 400003  
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