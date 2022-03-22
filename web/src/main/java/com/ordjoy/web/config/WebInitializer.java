package com.ordjoy.web.config;

import com.ordjoy.model.config.PersistenceConfig;
import com.ordjoy.web.util.UrlPathUtils;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{PersistenceConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{UrlPathUtils.DISPATCHER_SERVLET_URL_PATTERN};
    }
}