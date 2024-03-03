package com.dango.content.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 课程分类
 * @TableName course_category
 */
@TableName(value ="course_category")
@Data
public class CourseCategory implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private String id;

    /**
     * 分类名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 父结点id（第一级的父节点是0，自关联字段id）
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 是否显示
     */
    @TableField(value = "is_show")
    private Integer isShow;

    /**
     * 排序字段
     */
    @TableField(value = "order_by")
    private Integer orderBy;

    /**
     * 是否叶子
     */
    @TableField(value = "is_leaf")
    private Integer isLeaf;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}