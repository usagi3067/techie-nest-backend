package com.dango.content.model.dto;

import com.dango.exception.ValidationGroups;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

/**
 * @author dango
 * @description
 * @date
 */
@Data
public class EditCourseDto {
    @ApiModelProperty(value = "课程id", required = true)
    private Long id;

    @NotEmpty(groups = {ValidationGroups.Inster.class},message = "添加课程名称不能为空")
    @NotEmpty(groups = {ValidationGroups.Update.class},message = "修改课程名称不能为空")
    @ApiModelProperty(value = "课程名称", required = true)
    private String name;

    @ApiModelProperty(value = "课程标签")
    private ArrayList<String> tags;

    @NotEmpty(message = "课程大分类不能为空")
    @ApiModelProperty(value = "课程大分类", required = true)
    private String mainCategory;

    @NotEmpty(message = "课程小分类不能为空")
    @ApiModelProperty(value = "课程小分类", required = true)
    private String subCategory;

    @ApiModelProperty(value = "课程介绍")
    private String description;

    @ApiModelProperty(value = "课程封面", required = true)
    private String pic;

    @ApiModelProperty(value = "预备知识")
    private String preKnowledge;

    @NotNull(message = "是否收费")
    @ApiModelProperty(value = "是否收费", required = true)
    private Integer isFree;

    @ApiModelProperty(value = "课程价格", required = true)
    private Double price;

    @ApiModelProperty(value = "课程原价", required = true)
    private Double originalPrice;

    @ApiModelProperty(value = "qq")
    private String qq;

    @ApiModelProperty(value = "微信")
    private String wechat;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "有效期")
    private Integer validDays;

}
