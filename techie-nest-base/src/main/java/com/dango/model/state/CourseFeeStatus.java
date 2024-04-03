package com.dango.model.state;

import lombok.Getter;

@Getter
public enum CourseFeeStatus {
    FREE("201000", "免费"),
    PAID("201001", "收费");

    private final String code;
    private final String description;

    CourseFeeStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    // Getter methods
}
