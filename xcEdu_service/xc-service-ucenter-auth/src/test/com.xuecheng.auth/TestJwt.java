package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Atom
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestJwt {

    /**
     * 生成JWT令牌
     */
    @Test
    public void testCreateJwt() {

        //密钥库文件
        String keystore = "xc.keystore";

        //密钥库密码
        String keystore_password = "xuechengkeystore";

        ClassPathResource classPathResource = new ClassPathResource(keystore);

        //密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, keystore_password.toCharArray());
        //密钥别名
        String alias = "xckey";

        //密钥的访问密码
        String key_password = "xuecheng";

        //密钥对（公钥和私钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, key_password.toCharArray());
        //获取私钥
        RSAPrivateKey keyPairPrivate = (RSAPrivateKey) keyPair.getPrivate();


        Map<String, String> body = new HashMap<>();
        body.put("name", "atom");
        String bodyStr = JSON.toJSONString(body);
        //生成JWT令牌
        Jwt jwt = JwtHelper.encode(bodyStr, new RsaSigner(keyPairPrivate));

        //生成JWT令牌编码
        String encoded = jwt.getEncoded();
        System.err.println(encoded);
/**
 * eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiYXRvbSJ9.PplmF-BRcYEzh-w_49miCBzixK3Mc5ysrtZxLm1RuDjpqXsw4hdqa1ASuHBtQAmXawYG4ESGn3RxM-dn9OWuR_pXjRnyeO3AzP7d1Z3mjKdRAWhIoWC_Xm4McBz3QsS8x5SgtRXsiJGOSrod4oXDdYnhIWTFOVRKW_BuUDbty3IX5usUuKA6s06Q5QFlbLBb5lXni1LzJma_aAWAMvfPBCZhcV2QQq_lBvylK63U8ShcKf3CQtbThaoyTOwyb1aFT0BtZt18wHcD-s_vtGEq2lyvP8dnAUGXjoiDVDOvMNM4OpQof1jMQAcFAlLsKkMDtqku5CqKWuz0w465Za4scg
 */
    }


    /**
     * 校验JWT令牌
     */
    @Test
    public void testVerify() {

        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnASXh9oSvLRLxk901HANYM6KcYMzX8vFPnH/To2R+SrUVw1O9rEX6m1+rIaMzrEKPm12qPjVq3HMXDbRdUaJEXsB7NgGrAhepYAdJnYMizdltLdGsbfyjITUCOvzZ/QgM1M4INPMD+Ce859xse06jnOkCUzinZmasxrmgNV3Db1GtpyHIiGVUY0lSO1Frr9m5dpemylaT0BV3UwTQWVW9ljm6yR3dBncOdDENumT5tGbaDVyClV0FEB1XdSKd7VjiDCDbUAUbDTG1fm3K9sx7kO1uMGElbXLgMfboJ963HEJcU01km7BmFntqI5liyKheX+HBUCD4zbYNPw236U+7QIDAQAB-----END PUBLIC KEY-----";

        //JWT令牌
        String jwtStr = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiYXRvbSJ9.PplmF-BRcYEzh-w_49miCBzixK3Mc5ysrtZxLm1RuDjpqXsw4hdqa1ASuHBtQAmXawYG4ESGn3RxM-dn9OWuR_pXjRnyeO3AzP7d1Z3mjKdRAWhIoWC_Xm4McBz3QsS8x5SgtRXsiJGOSrod4oXDdYnhIWTFOVRKW_BuUDbty3IX5usUuKA6s06Q5QFlbLBb5lXni1LzJma_aAWAMvfPBCZhcV2QQq_lBvylK63U8ShcKf3CQtbThaoyTOwyb1aFT0BtZt18wHcD-s_vtGEq2lyvP8dnAUGXjoiDVDOvMNM4OpQof1jMQAcFAlLsKkMDtqku5CqKWuz0w465Za4scg";

        //解码和校验JWT令牌
        Jwt jwt = JwtHelper.decodeAndVerify(jwtStr, new RsaVerifier(publickey));

        //拿到JWT令牌中自定义的内容
        String claims = jwt.getClaims();
        System.err.println(claims);


    }

}
