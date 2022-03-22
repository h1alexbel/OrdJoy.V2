package com.ordjoy.web.controller.handler;

import com.ordjoy.web.util.AttributeUtils;
import com.ordjoy.web.util.PageUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RuntimeExceptionControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeError(Model model, RuntimeException e) {
        model.addAttribute(AttributeUtils.EXCEPTION_MESSAGE, e.getMessage());
        return PageUtils.ERROR_PAGE;
    }
}