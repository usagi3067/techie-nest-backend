package com.dango.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dango
 * @description 课程分类树形结构响应参数
 * @date 2024-03-03
 */
@Data
public class CourseCategoryTreeDto implements Serializable {
    @ApiModelProperty("分类id")
    private String id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("父分类id")
    private String parentId;

    @ApiModelProperty("是否显示")
    private Integer isShow;

    @ApiModelProperty("排序字段")
    private Integer orderBy;

    @ApiModelProperty("是否叶子")
    private Integer isLeaf;

    @ApiModelProperty("子节点")
    List<CourseCategoryTreeDto> childrenTreeNode;

    private static final long serialVersionUID = 1L;

}
