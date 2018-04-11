package com.zifangdt.ch.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 袁兵 on 2017/8/31.
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class WrongOperationException extends RuntimeException{
    public WrongOperationException(){
        super("操作失败");
    }

    public WrongOperationException(String message){
        super(message);
    }
}
