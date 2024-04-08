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
 * @TableName pay_record
 */
@TableName(value ="pay_record")
@Data
public class PayRecord implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 本系统支付交易号
     */
    @TableField(value = "pay_no")
    private Long payNo;

    /**
     * 第三方支付交易流水号
     */
    @TableField(value = "out_pay_no")
    private String outPayNo;

    /**
     * 第三方支付渠道编号
     */
    @TableField(value = "out_pay_channel")
    private String outPayChannel;

    /**
     * 商品订单号
     */
    @TableField(value = "order_id")
    private Long orderId;

    /**
     * 订单名称
     */
    @TableField(value = "order_name")
    private String orderName;

    /**
     * 订单总价单位元
     */
    @TableField(value = "total_price")
    private Double totalPrice;

    /**
     * 币种CNY
     */
    @TableField(value = "currency")
    private String currency;

    /**
     * 创建时间
     */
    @TableField(value = "create_date")
    private LocalDateTime createDate;

    /**
     * 支付状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 支付成功时间
     */
    @TableField(value = "pay_success_time")
    private LocalDateTime paySuccessTime;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}