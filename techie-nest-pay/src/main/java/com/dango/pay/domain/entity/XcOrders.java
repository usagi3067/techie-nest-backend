package com.dango.pay.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName xc_orders
 */
@TableName(value ="xc_orders")
@Data
public class XcOrders implements Serializable {
    /**
     * 订单号
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 总价
     */
    @TableField(value = "total_price")
    private Double totalPrice;

    /**
     * 创建时间
     */
    @TableField(value = "create_date")
    private LocalDateTime createDate;

    /**
     * 交易状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 订单类型
     */
    @TableField(value = "order_type")
    private String orderType;

    /**
     * 订单名称
     */
    @TableField(value = "order_name")
    private String orderName;

    /**
     * 订单描述
     */
    @TableField(value = "order_descrip")
    private String orderDescrip;

    /**
     * 订单明细json
     */
    @TableField(value = "order_detail")
    private String orderDetail;

    /**
     * 外部系统业务id
     */
    @TableField(value = "out_business_id")
    private String outBusinessId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}