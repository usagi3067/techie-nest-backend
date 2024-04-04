package com.dango.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dango
 * @description 课程信息响应 Dto
 * @date 2024-03-04
 */
@Data
public class CourseBaseInfoDto {
    @ApiModelProperty("课程id")
    private Long id;

    @ApiModelProperty("课程名称")
    private String name;

    @ApiModelProperty("讲师名称")
    private String lecturerName;

    @ApiModelProperty("讲师id")
    private Long lecturerId;

    @ApiModelProperty("课程标签")
    private List<String> tags;

    @ApiModelProperty("主分类")
    private String mainCategory;

    @ApiModelProperty("次分类")
    private String subCategory;

    @ApiModelProperty("课程介绍")
    private String description;

    @ApiModelProperty("课程封面")
    private String pic;

    @ApiModelProperty("预备知识")
    private String preKnowledge;

    @ApiModelProperty("审核状态")
    private String auditStatus;

    @ApiModelProperty("发布状态")
    private String publishStatus;

    @ApiModelProperty("收费规则")
    private Integer isFree;

    @ApiModelProperty("课程价格")
    private Double price;

    @ApiModelProperty("课程原价")
    private Double originalPrice;

    @ApiModelProperty("qq")
    private String qq;

    @ApiModelProperty("微信")
    private String wechat;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("有效期")
    private Integer validDays;

    @ApiModelProperty("大分类名称")
    private String mtName;

    @ApiModelProperty("小分类名称")
    private String stName;
}
