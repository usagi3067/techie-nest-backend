package com.dango.model.state;

import lombok.Getter;

@Getter
public enum CourseAuditStatus {
    REJECTED("100001", "审核未通过"),
    NOT_SUBMITTED("100002", "未提交"),
    SUBMITTED("100003", "已提交"),
    APPROVED("100004", "审核通过");

    private final String code;
    private final String description;

    CourseAuditStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    // Getter methods
}
