package com.zifangdt.ch.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 袁兵 on 2017/10/19.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotPermittedException extends RuntimeException{
    public NotPermittedException(){
        super("没有权限");
    }

    public NotPermittedException(String message){
        super(message);
    }
}
