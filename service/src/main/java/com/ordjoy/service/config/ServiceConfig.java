package com.ordjoy.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.ordjoy")
@EnableTransactionManagement
public class ServiceConfig {
}