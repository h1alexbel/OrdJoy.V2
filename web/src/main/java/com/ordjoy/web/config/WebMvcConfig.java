package com.ordjoy.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "com.ordjoy")
@EnableWebMvc
@Import(ViewResolverConfig.class)
public class WebMvcConfig {
}