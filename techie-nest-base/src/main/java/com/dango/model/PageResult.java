package com.dango.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author dango
 * @description
 * @date 2024-01-16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {
    // 数据列表
    private List<T> items;

    //总记录数
    private Long counts;

    //当前页码
    private Integer page;

    //每页记录数
    private Integer pageSize;
}
