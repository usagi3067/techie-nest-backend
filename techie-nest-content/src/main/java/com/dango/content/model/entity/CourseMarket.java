package com.dango.content.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 课程营销信息
 * @TableName course_market
 */
@TableName(value ="course_market")
@Data
public class CourseMarket implements Serializable {
    /**
     * 主键，课程id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 是否免费(1: 免费, 0: 收费)
     */
    @TableField(value = "is_free")
    private Integer isFree;

    /**
     * 原价
     */
    @TableField(value = "original_price")
    private Double originalPrice;

    /**
     * 现价
     */
    @TableField(value = "price")
    private Double price;

    /**
     * 咨询qq
     */
    @TableField(value = "qq")
    private String qq;

    /**
     * 微信
     */
    @TableField(value = "wechat")
    private String wechat;

    /**
     * 电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 有效期天数
     */
    @TableField(value = "valid_days")
    private Integer validDays;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}