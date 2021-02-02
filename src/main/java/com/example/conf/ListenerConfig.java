package com.example.conf;

import com.example.listener.LoginListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerConfig {
    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        ServletListenerRegistrationBean servletListenerRegistrationBean=new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(loginListener());
        return servletListenerRegistrationBean;
    }
    @Bean
    public LoginListener loginListener(){
        return new LoginListener();
    }
}
