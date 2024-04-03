package com.dango.model.state;

import lombok.Getter;

@Getter
public enum CourseAuditStatus {
    REJECTED("202001", "审核未通过"),
    NOT_SUBMITTED("202002", "未提交"),
    SUBMITTED("202003", "已提交"),
    APPROVED("202004", "审核通过");

    private final String code;
    private final String description;

    CourseAuditStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    // Getter methods
}
