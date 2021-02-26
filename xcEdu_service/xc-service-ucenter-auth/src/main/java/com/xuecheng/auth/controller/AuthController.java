package com.xuecheng.auth.controller;

import com.xuecheng.api.auth.AuthControllerApi;
import com.xuecheng.auth.service.AuthService;
import com.xuecheng.framework.domain.ucenter.ext.AuthToken;
import com.xuecheng.framework.domain.ucenter.request.LoginRequest;
import com.xuecheng.framework.domain.ucenter.response.AuthCode;
import com.xuecheng.framework.domain.ucenter.response.JwtResult;
import com.xuecheng.framework.domain.ucenter.response.LoginResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * @author atom
 */
@RestController
public class AuthController implements AuthControllerApi {

    /**
     * 客户端ID
     */
    @Value("${auth.clientId}")
    private String clientId;

    /**
     * 客户端密码
     */
    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    @Resource
    private AuthService authService;


    @Override
    @PostMapping("/userlogin")
    public LoginResult login(LoginRequest loginRequest) {
        //校验账号是否输入
        if (Objects.isNull(loginRequest) || StringUtils.isEmpty(loginRequest.getUsername())) {
            ExceptionCast.cast(AuthCode.AUTH_USERNAME_NONE);
        }
        //校验密码是否输入
        if (StringUtils.isEmpty(loginRequest.getPassword())) {
            ExceptionCast.cast(AuthCode.AUTH_PASSWORD_NONE);
        }
        //获取JWT令牌（token）
        AuthToken authToken = authService.login(loginRequest.getUsername(),
                loginRequest.getPassword(), clientId, clientSecret);
        //获取访问token就是短令牌 jti：JWT ID
        String access_token = authToken.getAccess_token();
        //将访问令牌存储到cookie
        saveCookie(access_token);
        return new LoginResult(CommonCode.SUCCESS, access_token);
    }


    @Override
    @PostMapping("/userlogout")
    public ResponseResult logout() {
        String access_token = getTokenFormCookie();
        // 清除redis中的JWT令牌（token）
        authService.clearToken(access_token);
        // 清除cookie
        clearCookie(access_token);
        return ResponseResult.SUCCESS();
    }

    /**
     * 1。 客户端携带cookie中的身份令牌请求认证服务获取JWT
     * 2。 认证服务根据身份令牌从redis中查询JWT令牌返回给客户端
     *
     * @return
     */
    @Override
    @GetMapping("userjwt")
    public JwtResult userjwt() {
        String access_token = getTokenFormCookie();
        AuthToken authToken = authService.getUserToken(access_token);
        if (Objects.isNull(authToken)) {
            return new JwtResult(CommonCode.FAIL, null);
        }
        return new JwtResult(CommonCode.SUCCESS, authToken.getJwt_token());
    }

    /**
     * 从cookie中读取访问令牌（jti：JWT ID）短令牌
     *
     * @return
     */
    private String getTokenFormCookie() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> cookieMap = CookieUtil.readCookie(request, "uid");
        return cookieMap.get("uid");
    }


    /**
     * 将令牌保存到cookie
     *
     * @param token
     */
    private void saveCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getResponse();
        //添加cookie 认证令牌，最后一个参数httpOnly设置为false，表示允许浏览器获取cookie
        /**
         *
         * cookie domain: xuecheng.com
         * cookie path: /
         * cookie name : uid
         *
         */
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", token, cookieMaxAge, false);
    }


    /**
     * 清除cookie,直接设置 maxAge=0
     *
     * @param token
     */
    private void clearCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getResponse();
        //添加cookie 认证令牌，最后一个参数httpOnly设置为false，表示允许浏览器获取cookie
        /**
         *
         * cookie domain: xuecheng.com
         * cookie path: /
         * cookie name : uid
         *
         */
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", token, 0, false);
    }
}