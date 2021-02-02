package com.example.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
@Slf4j
public class LoginListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
       log.info("---------------监听开始-----------------");
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("---------------监听销毁-----------------");
    }


}
