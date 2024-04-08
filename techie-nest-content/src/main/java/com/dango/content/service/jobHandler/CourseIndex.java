package com.dango.content.service.jobHandler;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CourseIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 讲师ID
     */
    private Long lecturerId;

    /**
     * 讲师名称
     */
    private String lecturerName;

    /**
     * 课程名称
     */
    private String name;


    /**
     * 标签
     */
    private String tags;


    /**
     * 大分类
     */
    private String mainCategory;

    /**
     * 大分类名称
     */
    private String mainCategoryName;

    /**
     * 小分类
     */
    private String subCategory;

    /**
     * 小分类名称
     */
    private String subCategoryName;


    /**
     * 课程图片
     */
    private String pic;

    /**
     * 课程介绍
     */
    private String description;


    /**
     * 发布时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 收费规则，对应数据字典--203
     */
    private Integer isFree;

    /**
     * 现价
     */
    private Double price;
    /**
     * 原价
     */
    private Double originalPrice;

    /**
     * 课程有效期天数
     */
    private Integer validDays;


}