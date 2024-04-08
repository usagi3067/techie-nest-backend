package com.dango.content.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

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
     * 课程名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 标签(json格式)
     */
    @TableField(value = "tags")
    private String tags;

    /**
     * 讲师ID
     */
    @TableField(value = "lecturer_id")
    private Long lecturerId;

    /**
     * 讲师名称
     */
    @TableField(value = "lecturer_name")
    private String lecturerName;

    /**
     * 大分类
     */
    @TableField(value = "main_category")
    private String mainCategory;

    /**
     * 主分类名称
     */
    @TableField(value = "main_category_name")
    private String mainCategoryName;

    /**
     * 次分类
     */
    @TableField(value = "sub_category")
    private String subCategory;

    /**
     * 次分类名称
     */
    @TableField(value = "sub_category_name")
    private String subCategoryName;

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
     * 预备知识
     */
    @TableField(value = "pre_knowledge")
    private String preKnowledge;

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
     * 讲师简介， json格式
     */
    @TableField(value = "lecturer_info")
    private String lecturerInfo;

    /**
     * 发布时间
     */
    @TableField(value = "publish_date")
    private LocalDateTime publishDate;

    /**
     * 下架时间
     */
    @TableField(value = "offline_date")
    private LocalDateTime offlineDate;

    /**
     * 发布状态(20001:未发布, 20002:已发布, 20003:下线)
     */
    @TableField(value = "status")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 是否免费(1: 免费, 0: 收费)
     */
    @TableField(value = "is_free")
    private Integer isFree;

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

    /**
     * 购买人数
     */
    @TableField(value = "count_buy")
    private Integer countBuy;

    /**
     * 学习人数
     */
    @TableField(value = "count_study")
    private Integer countStudy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}