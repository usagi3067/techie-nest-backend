package com.dango.exception;

/**
 * 自定义业务异常类。
 * 用于在业务逻辑处理过程中，当遇到不符合业务规则的情况时抛出。
 *
 * @author dango
 * @since 2024-03-05
 */
public class BusinessException extends RuntimeException {
    private final String errMessage;

    public BusinessException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    /**
     * 快速创建并抛出业务异常的静态方法。
     *
     * @param commonError 包含错误信息的枚举
     */
    public static void cast(CommonError commonError) {
        throw new BusinessException(commonError.getErrMessage());
    }

    /**
     * 快速创建并抛出业务异常的静态方法。
     *
     * @param message 错误信息字符串
     */
    public static void cast(String message) {
        throw new BusinessException(message);
    }
}

