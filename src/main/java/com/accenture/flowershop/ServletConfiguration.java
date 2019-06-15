package com.accenture.flowershop;

import com.accenture.flowershop.fe.servlets.*;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfiguration {

    @Bean
    public ServletRegistrationBean loginServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new LoginServlet(), "/login");
        bean.setLoadOnStartup(1);
        return bean;
    }

    @Bean
    public ServletRegistrationBean mainServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new RootServlet(), "/");
        bean.setLoadOnStartup(1);
        return bean;
    }

    @Bean
    public ServletRegistrationBean registerServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new RegisterServlet(), "/register");
        bean.setLoadOnStartup(1);
        return bean;
    }

    @Bean
    public ServletRegistrationBean logoutServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new LogoutServlet(), "/logout");
        bean.setLoadOnStartup(1);
        return bean;
    }
}
