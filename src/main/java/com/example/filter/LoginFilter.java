package com.example.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;
@Slf4j
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      log.info("--------初始化过滤器---------");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("--------------进入目标资源之前先干点啥------------");
        chain.doFilter(request,response);
        log.info("--------------处理一下服务端返回的response------------");
    }
    @Override
    public void destroy() {
        log.info("--------销毁过滤器---------");
    }
}
