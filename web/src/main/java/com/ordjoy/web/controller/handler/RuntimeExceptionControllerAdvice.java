package com.ordjoy.web.controller.handler;

import com.ordjoy.model.util.LoggingUtils;
import com.ordjoy.web.util.AttributeUtils;
import com.ordjoy.web.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RuntimeExceptionControllerAdvice {

    /**
     * Handles any Runtime Exception and redirect to error page
     *
     * @param e Runtime Exception
     * @return html page with context error
     * @see Model
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(Model model, RuntimeException e) {
        handle(e, model);
        return PageUtils.ERROR_PAGE;
    }

    private void handle(RuntimeException runtimeException, Model model) {
        log.debug(LoggingUtils.EXCEPTION_WAS_THROWN, runtimeException);
        model.addAttribute(AttributeUtils.EXCEPTION_MESSAGE, runtimeException.getMessage());
    }
}