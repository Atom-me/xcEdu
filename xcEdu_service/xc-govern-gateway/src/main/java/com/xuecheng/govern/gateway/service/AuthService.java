package com.xuecheng.govern.gateway.service;

import com.xuecheng.framework.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author atom
 */
@Service
public class AuthService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 从cookie查询身份令牌 uid(jti)
     *
     * @param request
     * @return
     */
    public String getTokenFromCookie(HttpServletRequest request) {
        Map<String, String> cookieMap = CookieUtil.readCookie(request, "uid");
        String access_token = cookieMap.get("uid");
        if (StringUtils.isEmpty(access_token)) {
            return null;
        }
        return access_token;
    }

    /**
     * 从header中查询jwt access_token 令牌
     *
     * @param request
     * @return
     */
    public String getJwtFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            //拒绝访问
            return null;
        }
        if (!authorization.startsWith("Bearer ")) {
            //拒绝访问
            return null;
        }
        return authorization;
    }

    /**
     * 查询jwt access_token令牌的有效期
     *
     * @param access_token
     * @return
     */
    public long getExpire(String access_token) {
        //token在redis中的key
        String key = "user_token:" + access_token;
        return stringRedisTemplate.getExpire(key);
    }

}
