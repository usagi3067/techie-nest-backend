package com.dango.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 数据字典
 * @TableName dictionary
 */
@TableName(value ="dictionary")
@Data
public class Dictionary implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据字典名称
     */
    private String name;

    /**
     * 数据字典编码
     */
    private String code;

    /**
     * 数据字典项值
     */
    private String itemValues;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}