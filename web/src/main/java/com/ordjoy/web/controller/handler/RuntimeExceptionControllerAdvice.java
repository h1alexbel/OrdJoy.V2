package com.ordjoy.web.controller.handler;

import com.ordjoy.model.util.LoggingUtils;
import com.ordjoy.web.util.AttributeUtils;
import com.ordjoy.web.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@ControllerAdvice
public class RuntimeExceptionControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatusException(Model model, ResponseStatusException e) {
        handle(e, model);
        return PageUtils.ERROR_PAGE;
    }

    @ExceptionHandler(LazyInitializationException.class)
    public String handleLazyInitializationException(Model model, LazyInitializationException e) {
        handle(e, model);
        return PageUtils.ERROR_PAGE;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUsernameNotFoundException(Model model, UsernameNotFoundException e) {
        handle(e, model);
        return PageUtils.ERROR_PAGE;
    }

    @ExceptionHandler(NoSuchBeanDefinitionException.class)
    public String handleNoSuchBeanDefinitionException(Model model, NoSuchBeanDefinitionException e) {
        handle(e, model);
        return PageUtils.ERROR_PAGE;
    }

    private void handle(RuntimeException runtimeException, Model model) {
        log.debug(LoggingUtils.EXCEPTION_WAS_THROWN, runtimeException);
        model.addAttribute(AttributeUtils.EXCEPTION_MESSAGE, runtimeException.getMessage());
    }
}