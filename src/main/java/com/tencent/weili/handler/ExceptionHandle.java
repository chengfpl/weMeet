package com.tencent.weili.handler;

import com.tencent.weili.dto.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(Exception.class)
    private Result<Exception> exceptionHandle(Exception e) {
        Result<Exception> result = new Result<Exception>(false, e.getMessage());
        return result;
    }

}
