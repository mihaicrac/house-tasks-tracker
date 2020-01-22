package com.house.taskstracker.authentication.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static java.util.Objects.nonNull;

@Configuration
public class FiltersConfiguration {

    @Bean
    @Order(0)
    public CommonsRequestLoggingFilter requestLogFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter() {
            private static final String ELB_USER_AGENT = "ELB-HealthChecker/";

            /**
             * @param request request
             * @return true if the super.shouldLog() return true _and_ the request is not a ELB health check request
             */
            @Override
            protected boolean shouldLog(HttpServletRequest request) {
                return super.shouldLog(request) && !isHealthCheckRequest(request);
            }

            @Override
            protected void beforeRequest(HttpServletRequest request, String message) {
                // empty method to avoid logging in super.beforeRequest(request, message)
            }

            /**
             * Checks if the request is a health check made by the Amazon Load Balancer. A health check will:
             *  - have the user agent set to "ELB-HealthChecker/[version]"
             *  - NOT have an X-Forwarded-For header, since this is made directly by the load balancer, not forwarded
             *  on behalf of another.
             *
             * @param request request
             * @return true if this is a health check.
             */
            private boolean isHealthCheckRequest(HttpServletRequest request) {
                String userAgentHeader = request.getHeader(HttpHeaders.USER_AGENT);
                String forwardedForHeader = request.getHeader("X-Forwarded-For");
                return forwardedForHeader == null && userAgentHeader != null && userAgentHeader.startsWith(
                        ELB_USER_AGENT);
            }

            /**
             * Method implementation took from class
             *          org.springframework.web.filter.AbstractRequestLoggingFilter
             *
             * and adapted to suit obfuscation of JWT authorization header logging
             *
             * @param request
             * @param prefix
             * @param suffix
             * @return
             */
            @Override
            protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
                StringBuilder msg = new StringBuilder();
                msg.append(prefix);
                msg.append("uri=").append(request.getRequestURI());
                String payload;

                if (this.isIncludeQueryString()) {
                    payload = request.getQueryString();
                    if (payload != null) {
                        msg.append('?').append(payload);
                    }
                }

                if (this.isIncludeClientInfo()) {
                    payload = request.getRemoteAddr();
                    if (StringUtils.hasLength(payload)) {
                        msg.append(";client=").append(payload);
                    }

                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        msg.append(";session=").append(session.getId());
                    }

                    String user = request.getRemoteUser();
                    if (user != null) {
                        msg.append(";user=").append(user);
                    }
                }
                if (this.isIncludeHeaders()) {
                    HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
                    String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
                    if (nonNull(authorizationHeader)) {
                        int lastPointIndex = authorizationHeader.lastIndexOf(".");
                        if (lastPointIndex != -1) {
                            headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader.substring(0, lastPointIndex));
                        }
                    }
                    msg.append(";headers=").append(headers);
                }

                if (this.isIncludePayload()) {
                    payload = this.getMessagePayload(request);
                    if (payload != null) {
                        msg.append(";payload=").append(payload);
                    }
                }

                msg.append(suffix);
                return msg.toString();
            }
        };


        filter.setMaxPayloadLength(1024 * 1024);
        filter.setIncludeHeaders(true);
        filter.setIncludePayload(false);
        filter.setIncludeQueryString(true);
        filter.setIncludeClientInfo(true);
        return filter;
    }

    @Bean
    public FilterRegistrationBean securityFilterChain(
            @Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME) Filter securityFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
        registration.setOrder(-1);
        registration
                .setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
        return registration;
    }

}
