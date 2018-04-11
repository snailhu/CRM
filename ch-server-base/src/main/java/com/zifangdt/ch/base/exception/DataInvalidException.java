package com.zifangdt.ch.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 袁兵 on 2017/8/31.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataInvalidException extends RuntimeException{
    public DataInvalidException(){
        super("数据不合法");
    }

    public DataInvalidException(String message){
        super(message);
    }
}
