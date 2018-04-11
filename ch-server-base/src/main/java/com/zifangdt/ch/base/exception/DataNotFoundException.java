package com.zifangdt.ch.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 袁兵 on 2017/8/31.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(){
        super("数据不存在");
    }
    public DataNotFoundException(String message){
        super(message);
    }
}
