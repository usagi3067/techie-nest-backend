package com.dango.model.state;

import lombok.Getter;

/**
 * @author dango
 * @description
 * @date 2024-04-04
 */
@Getter
public enum PayStatus {
    PAID("300001", "已支付"),
    UNPAID("300002", "未支付"),
    REFUNDED("300003", "已退款");

    private final String code;
    private final String description;

    PayStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
