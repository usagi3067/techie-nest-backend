package com.dango.model;

import com.dango.constant.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dango
 * @description
 * @date 2024-01-16
 */
@Data
public class PageParams{
    @ApiModelProperty(value = "当前页号")
    private int pageNo = 1;

    @ApiModelProperty(value = "页面大小")
    private int pageSize = 10;

    @ApiModelProperty(value = "排序字段")
    private String sortField;

    @ApiModelProperty(value = "排序顺序 (默认升序)")
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
