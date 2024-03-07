package com.dango.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty("机构名称")
    private String companyName;

    @ApiModelProperty("机构id")
    private Long companyId;

    @ApiModelProperty("适用人群")
    private String users;

    @ApiModelProperty("课程标签")
    private String tags;

    @ApiModelProperty("大分类")
    private String mt;

    @ApiModelProperty("小分类")
    private String st;

    @ApiModelProperty("课程等级")
    private String grade;

    //todo 需要改大驼峰
    @ApiModelProperty("教学模式（普通、 录播、 直播等）")
    private String teachMode;

    @ApiModelProperty("课程介绍")
    private String description;

    @ApiModelProperty("课程封面")
    private String pic;

    @ApiModelProperty("创建人")
    private String createdBy;

    @ApiModelProperty("更新人")
    private String updatedBy;

    @ApiModelProperty("是否删除")
    private Integer isDelete;

    @ApiModelProperty("审核状态")
    private String auditStatus;

    @ApiModelProperty("发布状态")
    private String status;

    @ApiModelProperty("收费规则")
    private String charge;

    @ApiModelProperty("课程价格")
    private Float price;

    @ApiModelProperty("课程原价")
    private Float originalPrice;

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
