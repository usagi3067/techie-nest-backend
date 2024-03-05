package com.dango.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理器
 * 用于捕获和处理应用中的各类异常，确保返回客户端的是统一格式的错误响应。
 *
 * @author dango
 * @since 2024-03-05
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 业务异常处理方法。
     * 捕获 {@link BusinessException} 并返回内部服务器错误状态，同时记录错误信息。
     *
     * @param e 捕获到的业务异常
     * @return 统一格式的错误响应
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getErrMessage());
        return new ErrorResponse(e.getErrMessage());
    }

    /**
     * 参数校验异常处理方法。
     * 捕获 {@link MethodArgumentNotValidException}，整合校验失败的所有错误信息，并返回。
     *
     * @param e 方法参数不合法异常
     * @return 统一格式的错误响应，包含所有参数校验错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> msgList = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(item -> msgList.add(item.getDefaultMessage()));
        String msg = StringUtils.join(msgList, ",");
        log.error("参数校验异常: {}", msg);
        return new ErrorResponse(msg);
    }

    /**
     * 通用异常处理方法。
     * 捕获除业务和参数校验异常以外的所有异常，返回内部服务器错误状态。
     *
     * @param e 捕获到的异常
     * @return 统一格式的错误响应
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        log.error("系统异常: {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }
}

