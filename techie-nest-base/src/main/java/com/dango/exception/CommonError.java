package com.dango.exception;

import lombok.Getter;

/**
 * @author dango
 * @description 通用错误
 * @date
 */
@Getter
public enum CommonError {
    OBJECT_NULL("对象为空"),
    QUERY_NULL("查询为空");
    private final String errMessage;

    private CommonError(String errMessage) {
        this.errMessage = errMessage;
    }
}
