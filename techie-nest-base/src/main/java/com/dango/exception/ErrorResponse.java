package com.dango.exception;

import java.io.Serializable;

/**
 * 错误响应类。
 * 用于封装错误信息，以便统一返回给客户端的错误响应格式。
 *
 * @author dango
 * @since 2024-03-05
 */
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String errMessage;

    public ErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}

