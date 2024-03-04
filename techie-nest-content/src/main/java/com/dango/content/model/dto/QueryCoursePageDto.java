package com.dango.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dango
 * @description 分页查询课程基本信息 req
 * @date 2024-01-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryCoursePageDto implements Serializable {
    private static final long serialVersionUID = -3042686055658047285L;
    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("审核状态")
    private String auditStatus;

    @ApiModelProperty("发布状态")
    private String publishStatus;

}
