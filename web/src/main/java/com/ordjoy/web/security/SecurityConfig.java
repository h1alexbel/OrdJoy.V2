package com.ordjoy.web.security;

import com.ordjoy.model.entity.user.Role;
import com.ordjoy.web.security.filter.CharsetFilter;
import com.ordjoy.web.util.UrlPathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USER_ANT_MATCHERS = "/user/**";
    private static final String AUTH_ANT_MATCHERS = "/auth/**";
    private static final String ADMIN_ANT_MATCHERS = "/admin/**";
    private static final String STATIC_RESOURCES_PATH = "/resources/**";
    private static final String FRAGMENTS_PATH = "/fragments/**";
    private static final String REGISTER_PAGE = "registration";
    public static final String LOGIN_PAGE = "login";
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new CharsetFilter(), ChannelProcessingFilter.class);
        http
                .authorizeRequests()
                .antMatchers(USER_ANT_MATCHERS).hasAuthority(Role.USER.name())
                .antMatchers(AUTH_ANT_MATCHERS).hasAnyAuthority
                        (Role.USER.name(), Role.ADMIN.name())
                .antMatchers(ADMIN_ANT_MATCHERS).hasAuthority(Role.ADMIN.name())
                .antMatchers(STATIC_RESOURCES_PATH, FRAGMENTS_PATH,
                        LOGIN_PAGE, REGISTER_PAGE).permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage(UrlPathUtils.LOGIN_PAGE_URL)
                .defaultSuccessUrl(UrlPathUtils.SUCCESS_HANDLER_URL)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .logoutSuccessUrl(UrlPathUtils.LOGIN_PAGE_URL)
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}