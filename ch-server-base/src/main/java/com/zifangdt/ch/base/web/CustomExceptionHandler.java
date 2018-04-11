package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.constant.Constants;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2017/9/6.
 */
@ControllerAdvice
@ResponseBody
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public String handleFileSizeLimitExceededException(MultipartException e) {
        if (e.getRootCause() instanceof FileUploadBase.FileSizeLimitExceededException) {
            return "单个文件最大不能超过" + Constants.FILE_MAX_SIZE;
        }
        return "文件上传出错." + e.getMessage();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return "参数类型转换错误." + e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return getBindingErrors(e.getBindingResult());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBindException(BindException e) {
        return getBindingErrors(e.getBindingResult());
    }

    private String getBindingErrors(BindingResult bindingResult) {
        return "参数不合法." + bindingResult.getFieldErrors().stream().map(fieldError -> {
            return String.format("[%s]%s", fieldError.getField(), fieldError.getDefaultMessage());
        }).collect(Collectors.joining(";"));
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUnsatisfiedServletRequestParameterException(UnsatisfiedServletRequestParameterException e) {
        return "参数不合法.";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return "参数不合法.缺少" + e.getParameterName() + "参数";
    }
}
