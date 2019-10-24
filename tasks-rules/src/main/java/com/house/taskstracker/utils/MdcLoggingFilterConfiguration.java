package com.house.taskstracker.utils;

import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class MdcLoggingFilterConfiguration {
    @Bean
    @Order(0)
    public Filter mdcLogFilter() {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                MDC.put("url", String.format("[%s %s] ", request.getMethod(), request.getRequestURI()));

                filterChain.doFilter(request, response);
                MDC.clear();
            }
        };
    }

}
