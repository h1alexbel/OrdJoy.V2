package com.ordjoy.web.controller.handler;

import com.ordjoy.web.util.AttributeUtils;
import com.ordjoy.web.util.PageUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ResponseStatusExceptionControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatusException(Model model, ResponseStatusException e) {
        model.addAttribute(AttributeUtils.EXCEPTION_MESSAGE, e.getMessage());
        return PageUtils.ERROR_PAGE;
    }
}