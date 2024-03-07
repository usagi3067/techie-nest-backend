package com.dango.content.model.dto;

import com.dango.content.model.entity.TeachPlanMedia;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
//todo 需要改大驼峰
/**
 * @author dango
 * @description 课程计划响应 Dto
 * @date 2024-03-06
 */
@Data
public class TeachPlanDto {
    @ApiModelProperty("课程计划id")
    private Long id;

    //todo
    @ApiModelProperty("课程计划名称")
    private String planName;

    // todo
    @ApiModelProperty("课程计划父级Id")
    private Long parentId;

    @ApiModelProperty("层级")
    private Integer grade;

    @ApiModelProperty("媒体类型:1视频、2文档")
    private String mediaType;

    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("章节及课程时介绍")
    private String description;

    //todo
    @ApiModelProperty("时长 单位时:分:秒")
    private String timeLength;

    //todo
    @ApiModelProperty("排序")
    private Integer orderBy;

    @ApiModelProperty("课程id")
    private Long courseId;

    @ApiModelProperty("课程发布id")
    private Long coursePubId;

    @ApiModelProperty("状态（0正常  1删除）")
    private Integer isDelete;

    @ApiModelProperty("是否支持试学或预览（试看）")
    private String isPreview;

    //todo
    @ApiModelProperty("媒资信息")
    private TeachPlanMedia teachPlanMedia;

    @ApiModelProperty("小章节list")
    private List<TeachPlanDto> teachPlanTreeNodes;
}
