package com.xuecheng.framework.domain.ucenter.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author mrt
 * @date 2018/5/21
 */
@Data
@ToString
@NoArgsConstructor
public class AuthToken {
    /**
     * 访问token,里面放的是 JWT 的 ID编号： jti。 作为用户的身份标识，短令牌
     * <p>
     * [ jti (JWT ID)：编号 ]
     */
    String access_token;
    /**
     * 刷新token
     */
    String refresh_token;
    /**
     * jwt令牌 里面放的是 jwt 的 access_token,长令牌
     */
    String jwt_token;
}
