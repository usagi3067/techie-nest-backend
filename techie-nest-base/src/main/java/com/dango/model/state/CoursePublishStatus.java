package com.dango.model.state;

import lombok.Getter;

@Getter
public enum CoursePublishStatus {
    UNPUBLISHED("200001", "未发布"),
    PUBLISHED("200002", "已发布"),
    OFFLINE("200003", "下线");

    private final String code;
    private final String description;

    CoursePublishStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }



}
