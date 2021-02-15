package com.xuecheng.api.auth;

import com.xuecheng.framework.domain.ucenter.request.LoginRequest;
import com.xuecheng.framework.domain.ucenter.response.JwtResult;
import com.xuecheng.framework.domain.ucenter.response.LoginResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 1. 用户请求认证服务，登录成功
 * 2。 用户登录成功，认证服务向cookie写入身份令牌，向redis写入user_token（身份令牌以及授权JWT授权令牌）
 * 3。 客户端携带cookie中的身份令牌请求认证服务获取JWT
 * 4。 认证服务根据身份令牌从redis中查询JWT令牌返回给客户端
 * 5。 客户端拿到JWT获取客户信息，（用户登录成功在页头显示登录用户昵称）
 */
@Api(value = "用户认证", description = "用户认证接口")
public interface AuthControllerApi {

    @ApiOperation("登录")
    LoginResult login(LoginRequest loginRequest);

    @ApiOperation("退出")
    ResponseResult logout();

    /**
     * 1。 客户端携带cookie中的身份令牌请求认证服务获取JWT
     * 2。 认证服务根据身份令牌从redis中查询JWT令牌返回给客户端
     *
     * @return
     */
    @ApiOperation("查询user jwt令牌")
    JwtResult userjwt();

}