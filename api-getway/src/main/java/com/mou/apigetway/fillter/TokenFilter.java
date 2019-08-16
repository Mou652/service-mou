package com.mou.apigetway.fillter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author: mou
 * @date: 2019-08-03
 */
@Component
public class TokenFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = currentContext.getRequest();
//
//        //获取token
//        String token = request.getParameter("token");
//        if (StringUtils.isEmpty(token)) {
////            currentContext.setSendZuulResponse(false);
////            currentContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
//        }
//        return null;
        HttpServletResponse response = currentContext.getResponse();
        response.setHeader("name", "mujiangchuan");
        return null;
    }
}
