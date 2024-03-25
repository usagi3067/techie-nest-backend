package com.dango.pay.domain.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author dango
 * @description
 * @date 2024-03-24
 */
@Data
@ToString
public class AddOrderDto  {

    /**
     * 总价
     */
    private Double totalPrice;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单名称
     */
    private String orderName;
    /**
     * 订单描述
     */
    private String orderDescrip;

    /**
     * 订单明细json，不可为空
     * [{"goodsId":"","goodsType":"","goodsName":"","goodsPrice":"","goodsDetail":""},{...}]
     */
    private String orderDetail;

    /**
     * 外部系统业务id
     */
    private String outBusinessId;

}

