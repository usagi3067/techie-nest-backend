package com.dango.model.state;

import lombok.Getter;

@Getter
public enum CourseFeeStatus {
    FREE(1, "免费"),
    PAID(0, "收费");

    private final Integer code;
    private final String description;

    CourseFeeStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    // Getter methods
}
