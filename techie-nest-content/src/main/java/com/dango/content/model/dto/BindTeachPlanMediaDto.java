package com.dango.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dango
 * @description
 * @date 2024-03-14
 */
@Data
@ApiModel(value="BindTeachPlanMediaDto", description="教学计划-媒资绑定提交数据")
public class BindTeachPlanMediaDto {

    @ApiModelProperty(value = "媒资文件id", required = true)
    private String mediaId;

    @ApiModelProperty(value = "媒资文件名称", required = true)
    private String fileName;

    @ApiModelProperty(value = "课程计划标识", required = true)
    private Long teachPlanId;
}

