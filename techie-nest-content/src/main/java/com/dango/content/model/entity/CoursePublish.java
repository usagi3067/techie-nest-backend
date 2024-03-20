package com.dango.content.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 课程发布
 * @TableName course_publish
 */
@TableName(value ="course_publish")
@Data
public class CoursePublish implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 机构ID
     */
    @TableField(value = "company_id")
    private Long companyId;

    /**
     * 公司名称
     */
    @TableField(value = "company_name")
    private String companyName;

    /**
     * 课程名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 适用人群
     */
    @TableField(value = "users")
    private String users;

    /**
     * 标签
     */
    @TableField(value = "tags")
    private String tags;

    /**
     * 创建人
     */
    @TableField(value = "username")
    private String username;

    /**
     * 大分类
     */
    @TableField(value = "mt")
    private String mt;

    /**
     * 大分类名称
     */
    @TableField(value = "mt_name")
    private String mtName;

    /**
     * 小分类
     */
    @TableField(value = "st")
    private String st;

    /**
     * 小分类名称
     */
    @TableField(value = "st_name")
    private String stName;

    /**
     * 课程等级
     */
    @TableField(value = "grade")
    private String grade;

    /**
     * 教育模式
     */
    @TableField(value = "teach_mode")
    private String teachMode;

    /**
     * 课程图片
     */
    @TableField(value = "pic")
    private String pic;

    /**
     * 课程介绍
     */
    @TableField(value = "description")
    private String description;

    /**
     * 课程营销信息，json格式
     */
    @TableField(value = "market")
    private String market;

    /**
     * 所有课程计划，json格式
     */
    @TableField(value = "teach_plan")
    private String teachPlan;

    /**
     * 教师信息，json格式
     */
    @TableField(value = "teachers")
    private String teachers;

    /**
     * 发布时间
     */
    @TableField(value = "create_date")
    private LocalDateTime createDate;

    /**
     * 上架时间
     */
    @TableField(value = "online_date")
    private LocalDateTime onlineDate;

    /**
     * 下架时间
     */
    @TableField(value = "offline_date")
    private LocalDateTime offlineDate;

    /**
     * 发布状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 收费规则，对应数据字典--203
     */
    @TableField(value = "charge")
    private String charge;

    /**
     * 现价
     */
    @TableField(value = "price")
    private Double price;

    /**
     * 原价
     */
    @TableField(value = "original_price")
    private Double originalPrice;

    /**
     * 课程有效期天数
     */
    @TableField(value = "valid_days")
    private Integer validDays;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}