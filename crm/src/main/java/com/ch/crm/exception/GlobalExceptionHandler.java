package com.ch.crm.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = UserLoginException.class)
    public HashMap<String , Object>  doLoginActAndPwd(Exception e){
        HashMap<String , Object> map = new HashMap<>();
        map.put("success",false);
        map.put("msg",e.getMessage());
        return map;
    }
}
