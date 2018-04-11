package com.zifangdt.ch.base.web;

import org.springframework.http.HttpStatus;

/**
 * Created by 袁兵 on 2017/8/28.
 */
public class RestResult {
    private int code = Codes.SUCCESS;
    private String message;
    private Object data;

    private RestResult() {
    }

    private RestResult(Object data) {
        this.data = data;
    }

    private RestResult(String message) {
        this.message = message;
    }

    public static RestResult of(HttpStatus httpStatus, Object body) {
        RestResult restResult = new RestResult();
        if (httpStatus.is2xxSuccessful()) {
            restResult.data = body;
        } else if (httpStatus.is4xxClientError() || httpStatus.is5xxServerError()) {
            restResult.code = Codes.UNKNOWN_ERROR;
            restResult.message = (String) body;
        }
        return restResult;
    }

    public static RestResult fail(String message) {
        RestResult restResult = new RestResult(message);
        restResult.code = Codes.UNKNOWN_ERROR;
        return restResult;
    }

    public static RestResult fail(String message, HttpStatus status) {
        RestResult restResult = new RestResult(message);
        if (status == HttpStatus.FORBIDDEN) {
            restResult.code = Codes.ACCESS_DENIED;
        } else {
            restResult.code = Codes.UNKNOWN_ERROR;
        }
        return restResult;
    }

    public static RestResult fail(int code, String message) {
        RestResult restResult = new RestResult(message);
        restResult.code = code;
        return restResult;
    }

    public static RestResult success(Object data) {
        return new RestResult(data);
    }

    public static RestResult success() {
        return new RestResult();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
