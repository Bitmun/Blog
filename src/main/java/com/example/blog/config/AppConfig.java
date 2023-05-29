package com.example.blog.config;

import com.example.blog.filter.AdminRoleFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    static final Logger log = LoggerFactory.getLogger(AppConfig.class);
    @Bean
    public FilterRegistrationBean<AdminRoleFilter> adminRoleFilterFilterRegistrationBean() {
        FilterRegistrationBean<AdminRoleFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminRoleFilter());
        log.info("Filtering role...");
        registrationBean.addUrlPatterns("/admin");
        return registrationBean;
    }
}

//    @Bean
//    public FilterRegistrationBean<UserRoleFilter> userRoleFilterFilterRegistrationBean() {
//        FilterRegistrationBean<UserRoleFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new UserRoleFilter());
//        registrationBean.addUrlPatterns("/home");
//        return registrationBean;
//    }
