package com.dango.model.state;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dango
 * @description
 * @date 2024-04-04
 */
@Getter
public enum ChooseCourseStatus {
    SUCCESS("400001", "选课成功"),
    NEED_PAY("400002", "待支付"),
    OUTDATED("400003", "已过期");

    private final String code;
    private final String description;

    ChooseCourseStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
