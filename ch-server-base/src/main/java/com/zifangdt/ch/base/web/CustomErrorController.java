package com.zifangdt.ch.base.web;

import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by 袁兵 on 2017/8/24.
 */
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController extends AbstractErrorController {

    public CustomErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        System.out.println("################CustomErrorController");
    }

    @RequestMapping
    public ResponseEntity<RestResult> handleError(HttpServletRequest request) throws ClassNotFoundException {
        Map<String, Object> attrs = getErrorAttributes(request, false);
        HttpStatus status = getStatus(request);
        String message = (String) attrs.get("message");
        Exception e = (Exception) request.getAttribute("javax.servlet.error.exception");
        if (e != null) {
            Throwable cause = NestedExceptionUtils.getMostSpecificCause(e);
            String otherMessage = cause instanceof NullPointerException ? e.getMessage() : cause.getMessage();
            if (otherMessage != null && !otherMessage.equals(message)) {
                message += " - " + otherMessage;
            }
        }
        return new ResponseEntity<>(RestResult.fail(message), status);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
