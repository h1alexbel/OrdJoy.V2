package com.ordjoy.web.controller.handler;

import com.ordjoy.model.util.LoggingUtils;
import com.ordjoy.web.util.AttributeUtils;
import com.ordjoy.web.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class LazyInitializationExceptionControllerAdvice {

    @ExceptionHandler(LazyInitializationException.class)
    public String handleLazyInitializationException(Model model, LazyInitializationException e) {
        log.debug(LoggingUtils.EXCEPTION_WAS_THROWN, e);
        model.addAttribute(AttributeUtils.EXCEPTION_MESSAGE, e.getMessage());
        return PageUtils.ERROR_PAGE;
    }
}