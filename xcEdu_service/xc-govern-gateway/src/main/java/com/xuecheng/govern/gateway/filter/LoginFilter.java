package com.xuecheng.govern.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.govern.gateway.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 1. 从cookie查询用户身份令牌 uid (jti) 是否存在，不存在则拒绝访问
 * 2. 从header查询JWT令牌是否存在，不存在则拒绝访问
 * 3. 从Redis查询user_token令牌是否过期，过期则拒绝访问
 *
 * @author atom
 */
@Component
public class LoginFilter extends ZuulFilter {


    @Resource
    private AuthService authService;

    @Override
    public String filterType() {
        //四种类型：pre、routing、post、error
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }


    @Override
    public Object run() {
        //上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        //请求对象
        HttpServletRequest request = requestContext.getRequest();
        //查询身份令牌
        String access_token = authService.getTokenFromCookie(request);
        if (StringUtils.isBlank(access_token)) {
            //拒绝访问
            access_denied();
            return null;
        }
        //查询jwt令牌
        String jwt = authService.getJwtFromHeader(request);
        if (StringUtils.isBlank(jwt)) {
            //拒绝访问
            access_denied();
            return null;
        }
        //从redis中校验身份令牌是否过期
        long expire = authService.getExpire(access_token);
        if (expire <= 0) {
            //拒绝访问
            access_denied();
            return null;
        }
        return null;
    }

    /**
     * 拒绝访问
     */
    private void access_denied() {
        //上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        //拒绝访问
        requestContext.setSendZuulResponse(false);
        //设置响应内容
        ResponseResult responseResult = new ResponseResult(CommonCode.UNAUTHENTICATED);
        String responseResultString = JSON.toJSONString(responseResult);
        requestContext.setResponseBody(responseResultString);
        //设置状态码
        requestContext.setResponseStatusCode(200);
        requestContext.getResponse().setContentType("application/json;charset=utf-8");
    }


}