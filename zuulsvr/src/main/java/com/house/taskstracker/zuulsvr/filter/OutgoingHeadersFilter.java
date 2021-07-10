package com.house.taskstracker.zuulsvr.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class OutgoingHeadersFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    private static final Logger logger = LoggerFactory.getLogger(OutgoingHeadersFilter.class);

    @Autowired
    private FilterUtils filterUtils;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String token = ctx.getRequest().getHeader("Authorization");
        getUserId(token).ifPresent(v -> ctx.addZuulRequestHeader("user-id", v.toString()));
        return null;
    }

    private Optional<UUID> getUserId(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            OAuth2AccessToken decodedToken = tokenStore.readAccessToken(token.replace("Bearer ", ""));
            Map<String, Object> details = decodedToken.getAdditionalInformation();
            return Optional.of(UUID.fromString((String) details.get("userId")));
        }
        return Optional.empty();
    }
}
