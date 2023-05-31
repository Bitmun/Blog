package com.example.blog.config;

import com.example.blog.filter.RoleFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    static final Logger log = LoggerFactory.getLogger(AppConfig.class);
//    @Bean
//    public FilterRegistrationBean<RoleFilter> adminRoleFilterFilterRegistrationBean() {
//        FilterRegistrationBean<RoleFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new RoleFilter());
//        log.info("Filtering role...");
//        registrationBean.addUrlPatterns("/admin");
//        return registrationBean;
//    }
    @Bean
    public FilterRegistrationBean<RoleFilter> roleFilterRegistrationBean() {
        FilterRegistrationBean<RoleFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RoleFilter());
        registration.addUrlPatterns("/admin/*", "/home", "/about");
        registration.setName("RoleFilter");
        log.info("filtering!!!");
        registration.setOrder(1);
        return registration;
    }
}

//    @Bean
//    public FilterRegistrationBean<UserRoleFilter> userRoleFilterFilterRegistrationBean() {
//        FilterRegistrationBean<UserRoleFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new UserRoleFilter());
//        registrationBean.addUrlPatterns("/home");
//        return registrationBean;
//    }
