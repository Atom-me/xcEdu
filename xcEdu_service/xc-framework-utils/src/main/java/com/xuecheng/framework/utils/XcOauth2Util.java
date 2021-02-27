package com.xuecheng.framework.utils;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author atom
 */
public class XcOauth2Util {

    public UserJwt getUserJwtFromHeader(HttpServletRequest request) {
        Map<String, String> jwtClaims = Oauth2Util.getJwtClaimsFromHeader(request);
        if (jwtClaims == null || StringUtils.isEmpty(jwtClaims.get("id"))) {
            return null;
        }
        UserJwt userJwt = new UserJwt();
        userJwt.setId(jwtClaims.get("id"));
        userJwt.setName(jwtClaims.get("name"));
        userJwt.setCompanyId(jwtClaims.get("companyId"));
        userJwt.setUtype(jwtClaims.get("utype"));
        userJwt.setUserpic(jwtClaims.get("userpic"));
        return userJwt;
    }

    @Data
    public static class UserJwt {
        private String id;
        /**
         * 用户昵称
         */
        private String name;
        /**
         * 用户头像
         */
        private String userpic;
        /**
         * 用户类型
         */
        private String utype;
        /**
         * 用户所属教学机构
         */
        private String companyId;
    }

}
