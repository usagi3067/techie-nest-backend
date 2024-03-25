package com.dango.pay.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName xc_orders_goods
 */
@TableName(value ="xc_orders_goods")
@Data
public class XcOrdersGoods implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    @TableField(value = "order_id")
    private Long orderId;

    /**
     * 商品id
     */
    @TableField(value = "goods_id")
    private String goodsId;

    /**
     * 商品类型
     */
    @TableField(value = "goods_type")
    private String goodsType;

    /**
     * 商品名称
     */
    @TableField(value = "goods_name")
    private String goodsName;

    /**
     * 商品交易价，单位分
     */
    @TableField(value = "goods_price")
    private Double goodsPrice;

    /**
     * 商品详情json
     */
    @TableField(value = "goods_detail")
    private String goodsDetail;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}