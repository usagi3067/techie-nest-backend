package com.dango.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dango
 * @description 保存或新增课程计划的 dto
 * @date 2024-03-06
 */
@Data
@ApiModel(value = "SaveTeachPlanDto", description = "保存或新增课程计划的 dto")
public class SaveTeachPlanDto implements Serializable {
    private static final long serialVersionUID = 1L;


    /***
     * 教学计划id
     */
    @ApiModelProperty("教学计划id")
    private Long id;

    /**
     * 课程计划名称
     */
    @ApiModelProperty("课程计划名称")
    private String planName;

    /**
     * 课程计划父级Id
     */
    @ApiModelProperty("课程计划父级Id")
    private Long parentId;

    /**
     * 层级，分为1、2级
     */
    @ApiModelProperty("层级")
    private Integer grade;

    /**
     * 课程类型:1视频、2文档
     */
    @ApiModelProperty("课程类型:1视频、2文档")
    private String mediaType;


    /**
     * 课程标识
     */
    @ApiModelProperty("课程标识")
    private Long courseId;

    /**
     * 课程发布标识
     */
    @ApiModelProperty("课程发布标识")
    private Long coursePubId;


    /**
     * 是否支持试学或预览（试看）
     */
    @ApiModelProperty("是否支持试学或预览（试看）")
    private String isPreview;
}
