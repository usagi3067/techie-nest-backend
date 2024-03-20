package com.dango.content.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author dango
 * @description
 * @date 2024-03-19
 */
@Data
public class CoursePreviewDto {

    // 课程基本信息和营销信息
    CourseBaseInfoDto courseBase;

    // 课程计划信息
    List<TeachPlanDto> teachPlans;

    // 师资信息暂时不添加...

}
